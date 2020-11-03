package com.topjia.music.user.domain.dto.disst;

import lombok.*;

/**
 * @author wjh
 * @date 2020-06-10 18:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DisstDTO {
    /**
     * 歌单ID
     */
    private String disstId;

    /**
     * 歌单简介
     */
    private String disstDesc;

    /**
     * 歌单logoURL地址
     */
    private String disstLogo;

    /**
     * 歌单名称
     */
    private String disstName;

    /**
     * 创建人头像URL
     */
    private String headUrl;

    /**
     * 创建人昵称
     */
    private String nickname;

    /**
     * 歌单歌曲数量
     */
    private Integer songNum;

    /**
     * 歌单播放量
     */
    private Integer visitnum;
}
