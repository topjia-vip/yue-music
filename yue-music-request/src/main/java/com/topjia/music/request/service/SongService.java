package com.topjia.music.request.service;


/**
 * @author wjh
 * @date 2020-01-04 14:14
 */
public interface SongService {
    String getSongPlayUrlByQQYY(String reqData) throws Exception;

    String getSongPurlByOther(String reqData) throws Exception;

    String getSongLyric(String reqData) throws Exception;
}
