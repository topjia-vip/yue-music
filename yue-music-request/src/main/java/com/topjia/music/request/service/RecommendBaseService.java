package com.topjia.music.request.service;

/**
 * @author wjh
 * @date 2020-05-24 16:25
 */
public interface RecommendBaseService {
    Object getRecommendFocus(String reqData) throws Exception;

    Object getRecommendPlayListAndNewMusic(String reqData) throws Exception;

    Object getNewSong(String reqData) throws Exception;
}
