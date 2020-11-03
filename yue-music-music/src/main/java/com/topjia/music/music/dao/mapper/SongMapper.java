package com.topjia.music.music.dao.mapper;

import com.topjia.music.music.domain.entity.Song;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author topjia
 * @since 2020-06-07
 */
public interface SongMapper extends BaseMapper<Song> {
    @Select("select s.id,s.mid,s.media_mid,s.`name`,s.album,s.duration,s.image,s.title,s.sub_title from user_like_songs as u,song as s WHERE u.user_id = #{userId} and u.song_id = s.id ORDER BY u.id")
    List<Song> selectSongsByUserId(Integer userId);
}
