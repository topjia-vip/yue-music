package com.topjia.music.music.domain.entity;

import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author topjia
 * @since 2020-06-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Disst implements Serializable {

    private static final long serialVersionUID=1L;

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
