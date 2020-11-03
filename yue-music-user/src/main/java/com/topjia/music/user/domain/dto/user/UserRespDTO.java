package com.topjia.music.user.domain.dto.user;

import com.topjia.music.user.domain.dto.disst.DisstDTO;
import com.topjia.music.user.domain.dto.song.SongDTO;
import lombok.*;

import java.util.List;

/**
 * @author wjh
 * @date 2020-06-04 20:30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRespDTO {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户名称
     */
    private String userNick;
    /**
     * 用户头像地址
     */
    private String userHeaderURL;

    /**
     * 用户简介
     */
    private String userDesc;

    /**
     * 用户性别
     */
    private Integer userSex;
}
