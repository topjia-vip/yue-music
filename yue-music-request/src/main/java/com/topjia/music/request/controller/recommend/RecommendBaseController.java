package com.topjia.music.request.controller.recommend;

import com.topjia.music.request.security.RequestException;
import com.topjia.music.request.service.RecommendBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 处理进入推荐页发的请求
 *
 * @author wjh
 * @date 2019-12-08 14:24
 */
@CrossOrigin
@RestController
@RequestMapping("/recommend")
@Slf4j
public class RecommendBaseController {
    @Autowired
    private RecommendBaseService recommendBaseService;

    // 获取焦点图
    @PostMapping("/getfocus")
    public Object getRecommendFocus(String reqData) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            Object res = recommendBaseService.getRecommendFocus(reqData);
            result.put("code", 0);
            result.put("data", res);
            return result;
        } catch (Exception e) {
            log.error("获取焦点图失败:{}", e.getMessage());
            throw new RequestException();
        }
    }

    //获取推荐歌单、最新音乐
    @PostMapping("/getplaylistandmusic")
    public Object getRecommendPlayListAndNewMusic(String reqData) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            Object res = recommendBaseService.getRecommendPlayListAndNewMusic(reqData);
            result.put("code", 0);
            result.put("data", res);
            return result;
        } catch (Exception e) {
            log.error("获取推荐歌单、最新音乐失败:{}", e.getMessage());
            throw new RequestException();
        }
    }

    // 获取最新音乐
    @PostMapping("/getnewsong")
    public Object getNewSong(String reqData) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            Object res = recommendBaseService.getNewSong(reqData);
            result.put("code", 0);
            result.put("data", res);
            return result;
        } catch (Exception e) {
            log.error("获取最新音乐失败:{}", e.getMessage());
            throw new RequestException();
        }
    }
}
