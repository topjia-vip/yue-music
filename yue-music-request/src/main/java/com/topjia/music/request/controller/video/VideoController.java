package com.topjia.music.request.controller.video;

import com.topjia.music.common.domain.dto.result.RequestData;
import com.topjia.music.request.entity.song.Song;
import com.topjia.music.request.entity.top.Top;
import com.topjia.music.request.entity.video.Video;
import com.topjia.music.request.security.RequestException;
import com.topjia.music.request.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author topjia
 * @date 2020-11-05 18:18
 */
@RestController
@RequestMapping("/video")
@Slf4j
public class VideoController {
    @Autowired
    private VideoService videoService;

    @PostMapping("/getrecommendmv")
    public Object getRecommendMV(@RequestBody RequestData reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            List<Video> videos = videoService.getRecommendMV(reqData.getReqData());
            result.put("code", 0);
            result.put("videos", videos);
            return result;
        } catch (Exception e) {
            log.error("获取推荐MV失败:{}", e.getMessage());
            throw new RequestException();
        }
    }

    @PostMapping("/getmvrankinfo")
    public Object getMVRank(@RequestBody RequestData reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            Top mvRankInfo = videoService.getMVRankInfo(reqData.getReqData());
            result.put("code", 0);
            result.put("mvRankInfo", mvRankInfo);
            return result;
        } catch (Exception e) {
            log.error("获取MV排行榜失败:{}", e.getMessage());
            throw new RequestException();
        }
    }

    @PostMapping("/getmvrankdata")
    public Object getMVRankData(@RequestBody RequestData reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            List<Video> videos = videoService.getMVRankData(reqData.getReqData());
            result.put("code", 0);
            result.put("mvRankData", videos);
            return result;
        } catch (Exception e) {
            log.error("获取MV排行榜数据失败:{}", e.getMessage());
            throw new RequestException();
        }
    }

    @PostMapping("/getvideostags")
    public Object getVideoTags(@RequestBody RequestData reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            HashMap<String, Object> videoTags = videoService.getVideoTags(reqData.getReqData());
            result.put("code", 0);
            result.put("videoTags", videoTags);
            return result;
        } catch (Exception e) {
            log.error("获取视频库标签失败:{}", e.getMessage());
            throw new RequestException();
        }
    }

    @PostMapping("/getvideos")
    public Object getVideos(@RequestBody RequestData reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            List<Video> videos = videoService.getVideos(reqData.getReqData());
            result.put("code", 0);
            result.put("videos", videos);
            return result;
        } catch (Exception e) {
            log.error("获取视频库失败:{}", e.getMessage());
            throw new RequestException();
        }
    }
}
