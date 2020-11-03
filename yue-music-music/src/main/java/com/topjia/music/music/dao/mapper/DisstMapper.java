package com.topjia.music.music.dao.mapper;

import com.topjia.music.music.domain.entity.Disst;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author topjia
 * @since 2020-06-10
 */
public interface DisstMapper extends BaseMapper<Disst> {
    @Select("select d.disst_id, d.disst_desc,d.disst_logo,d.disst_name,d.head_url,d.nickname,d.song_num,d.visitnum from  disst as d , user_like_dissts uld where uld.user_id = #{userId} and uld.disst_id = d.disst_id ORDER BY uld.id ASC")
    List<Disst> selectAllByUserId(Integer userId);
}
