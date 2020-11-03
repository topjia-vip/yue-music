package com.topjia.music.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.topjia.music.music.dao.mapper.*;
import com.topjia.music.music.domain.dto.song.SongDTO;
import com.topjia.music.music.domain.dto.user.UserDTO;
import com.topjia.music.music.domain.entity.*;
import com.topjia.music.music.service.SongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author topjia
 * @since 2020-06-07
 */
@Service
@Slf4j
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {
    @Autowired
    private SongMapper songMapper;

    @Autowired
    private SingerMapper singerMapper;

    @Autowired
    private SingerSongMapper singerSongMapper;

    @Autowired
    private UserLikeSongsMapper userLikeSongsMapper;

    @Override
    public List<Song> selectSongsByUserId(Integer id) {
        return this.songMapper.selectSongsByUserId(id);
    }

    /**
     * 用户收藏歌曲
     *
     * @param song    待收藏的歌曲
     * @param singers 该歌曲的歌手
     * @param id      用户id
     * @return 00
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveSong(Song song, ArrayList<Singer> singers, Integer id) {
        // 如果该歌曲与歌手没有存如数据库，则先存入数据库
        saveSong(song, singers);
        // 防止网络延迟导致多次插入数据
        UserLikeSongs userLikeSongs = this.userLikeSongsMapper.selectOne(new QueryWrapper<UserLikeSongs>().eq("user_id", id).eq("song_id", song.getId()));
        log.info("{}", userLikeSongs);
        if (userLikeSongs == null) {
            // 操作用户歌曲表
            return this.userLikeSongsMapper.insert(UserLikeSongs.builder()
                    .songId(song.getId())
                    .userId(id)
                    .build());
        }
        return 1;
    }

    /**
     * 查询出用户收藏的所有歌曲
     *
     * @param id 用户id
     * @return List<SongDTO>
     */
    @Override
    public List<SongDTO> selectAllByUserId(Integer id) {
        // 结果返回
        ArrayList<SongDTO> songDTOS = new ArrayList<>();
        // 查询出用户收藏的所有歌曲信息
        List<Song> songs = this.songMapper.selectSongsByUserId(id);
        // 根据每一个songId 查询出对应歌曲信息和歌手信息 并封装成SongDTO
        songs.forEach(song -> {
            List<Singer> singers = this.singerMapper.selectListBySongMid(song.getMid());
            songDTOS.add(SongDTO.builder()
                    .id(song.getId())
                    .album(song.getAlbum())
                    .duration(String.valueOf(song.getDuration()))
                    .image(song.getImage())
                    .mediaMid(song.getMediaMid())
                    .mid(song.getMid())
                    .name(song.getName())
                    .singers(singers)
                    .subTitle(song.getSubTitle())
                    .title(song.getTitle())
                    .build());
        });
        return songDTOS;
    }

    @Override
    public int deleteSongBySongId(Integer id, Integer songID) {
        int delete = userLikeSongsMapper.delete(new UpdateWrapper<UserLikeSongs>().eq("user_id", id).eq("song_id", songID));
        log.info("删除的行数{}", delete);
        return delete;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveSong(Song song, ArrayList<Singer> singers) {
        Song selectSong = this.songMapper.selectById(song.getId());
        if (selectSong == null) {
            // 保存歌曲信息
            this.songMapper.insert(song);
        }
        singers.forEach(singer -> {
            Singer selectById = this.singerMapper.selectOne(new QueryWrapper<Singer>().eq("singer_id", singer.getSingerId()));
            System.out.println(selectById);
            if (selectById == null) {
                // 保存singer
                this.singerMapper.insert(singer);
            }
            QueryWrapper<SingerSong> wrapper = new QueryWrapper<SingerSong>()
                    .eq("song_mid", song.getMid())
                    .eq("singer_mid", singer.getSingerMid());
            SingerSong singerSong = this.singerSongMapper.selectOne(wrapper);
            System.out.println(singerSong);
            if (singerSong == null) {
                this.singerSongMapper.insert(SingerSong.builder()
                        .singerMid(singer.getSingerMid())
                        .songMid(song.getMid())
                        .build());
            }
        });
    }
}
