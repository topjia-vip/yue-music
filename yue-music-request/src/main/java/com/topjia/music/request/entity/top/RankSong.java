package com.topjia.music.request.entity.top;

import lombok.Data;

/**
 * @author wjh
 * @date 2020-05-16 13:05
 */
@Data
public class RankSong {
    private String albumMid;
    private Integer rank;
    private Integer rankType;
    private String rankValue;
    private Integer recType;
    private String singerName;
    private String singerMid;
    private Integer songId;
    private Integer songType;
    private String title;
}
