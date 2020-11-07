package com.topjia.music.request.entity.video;

import com.topjia.music.request.entity.singer.Singer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author topjia
 * @date 2020-11-05 18:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {
    /**
     * VideoID
     */
    private String vid;

    /**
     * MV ID
     */
    private Integer mvId;

    /**
     * MV 简介
     */
    private String mvDesc;

    /**
     * MV 标题
     */
    private String mvTitle;

    /**
     * MV 副标题
     */
    private String subTitle;

    /**
     * MV 封面图片Url
     */
    private String mvPicUrl;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 播放次数
     */
    private Integer listenNum;

    /**
     * 播放时长
     */
    private Integer duration;

    /**
     * 演出歌手
     */
    private List<Singer> singers;

    /**
     * 上传者昵称
     */
    private String uploaderNick;
}


