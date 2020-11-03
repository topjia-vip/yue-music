package com.topjia.music.request.entity.album;

import lombok.Data;

/**
 * @author wjh
 * @date 2020-05-22 9:21
 */
@Data
public class SingerAlbum {
    private Integer albumID;
    private String albumMid;
    private String albumName;
    private String albumTranName;
    private String albumType;
    private String img;
    private String publishDate;
    private String singerName;
}
