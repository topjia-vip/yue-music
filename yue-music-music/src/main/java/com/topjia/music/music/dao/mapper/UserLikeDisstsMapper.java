package com.topjia.music.music.dao.mapper;

import com.topjia.music.music.domain.entity.UserLikeDissts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author topjia
 * @since 2020-06-10
 */
public interface UserLikeDisstsMapper extends BaseMapper<UserLikeDissts> {
    @Delete("DELETE from user_like_dissts where user_id = #{userId} and disst_id = #{disstId}")
    int deleteDisst(Integer userId, String disstId);

    @Insert("insert into user_like_dissts (user_id,disst_id) values(#{userId},#{disstId})")
    int saveUserLikeDisst(Integer userId, String disstId);
}
