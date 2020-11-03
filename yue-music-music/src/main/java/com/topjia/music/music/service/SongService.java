package com.topjia.music.music.service;

import com.topjia.music.music.domain.dto.song.SongDTO;
import com.topjia.music.music.domain.dto.user.UserDTO;
import com.topjia.music.music.domain.entity.Singer;
import com.topjia.music.music.domain.entity.Song;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author topjia
 * @since 2020-06-07
 */
public interface SongService extends IService<Song> {
    List<Song> selectSongsByUserId(Integer id);

    int saveSong(Song song, ArrayList<Singer> singers, Integer id);

    List<SongDTO> selectAllByUserId(Integer id);

    int deleteSongBySongId(Integer id, Integer songID);
}
