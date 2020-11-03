package com.topjia.music.music.dao.mapper;

import com.topjia.music.music.domain.entity.Singer;
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
public interface SingerMapper extends BaseMapper<Singer> {
    @Select("select s.* from singer_song as ss, singer as s where ss.song_mid = #{songMid} and s.singer_mid = ss.singer_mid")
    List<Singer> selectListBySongMid(String songMid);
}
