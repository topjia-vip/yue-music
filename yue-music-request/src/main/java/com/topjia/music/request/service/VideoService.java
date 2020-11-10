package com.topjia.music.request.service;

import com.topjia.music.request.entity.top.Top;
import com.topjia.music.request.entity.video.Video;

import java.util.HashMap;
import java.util.List;

/**
 * @author topjia
 * @date 2020-11-05 19:24
 */
public interface VideoService {
    List<Video> getRecommendMV(String reqData) throws Exception;

    Top getMVRankInfo(String reqData) throws Exception;

    List<Video> getMVRankData(String reqData) throws Exception;

    HashMap<String, Object> getVideoTags(String reqData) throws Exception;

    List<Video> getVideos(String reqData) throws Exception;

    HashMap<String, Object> getVideoPlayUrl(String reqData, String vid) throws Exception;

    HashMap<String, Object> getVideoInfoAndOtherVideo(String reqData, String vid) throws Exception;
}
