package com.topjia.music.request.service;

import java.util.HashMap;

/**
 * @author wjh
 * @date 2020-05-24 18:59
 */
public interface SingerService {
    HashMap<String, Object> getSingerList(String reqData) throws Exception;

    HashMap<String, Object> getSingerSongList(String reqData) throws Exception;

    HashMap<String, Object> getSingerAlbums(String reqData) throws Exception;

    HashMap<String, Object> getSingerConcern(String reqData, String singerMid) throws Exception;

    HashMap<String, Object> getSingerInfo(String reqData) throws Exception;
}
