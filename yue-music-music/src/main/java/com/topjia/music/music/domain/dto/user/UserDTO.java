package com.topjia.music.music.domain.dto.user;

import com.topjia.music.music.domain.dto.song.SongDTO;
import lombok.*;

import java.util.List;

/**
 * @author wjh
 * @date 2020-06-08 19:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    /**
     * 用户唯一标识
     */
    private Integer id;

    /**
     * 用户名称
     */
    private String userNick;

    /**
     * 用户头像URL
     */
    private String userHeaderUrl;

    /**
     * 用户简介
     */
    private String userDesc;

    /**
     * 用户性别 0:保密 1:男生 2:女生
     */
    private Integer userSex;

    /**
     * 用户收藏的歌曲列表
     */
    private List<SongDTO> favoriteSongs;
}
