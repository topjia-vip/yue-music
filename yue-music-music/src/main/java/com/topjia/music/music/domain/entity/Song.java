package com.topjia.music.music.domain.entity;

import lombok.*;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author topjia
 * @since 2020-06-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 歌曲id
     */
    private Integer id;

    /**
     * 歌曲mid
     */
    private String mid;

    /**
     * 歌曲媒体资源mid
     */
    private String mediaMid;

    /**
     * 歌曲名称
     */
    private String name;

    /**
     * 歌曲所属的专辑ID
     */
    private String album;

    /**
     * 歌曲播放时长
     */
    private Integer duration;

    /**
     * 歌曲图片URL
     */
    private String image;

    /**
     * 歌曲标题
     */
    private String title;

    /**
     * 歌曲额外标题
     */
    private String subTitle;


}
