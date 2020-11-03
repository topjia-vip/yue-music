package com.topjia.music.request.controller.search;

import com.alibaba.fastjson.JSONObject;
import com.topjia.music.request.security.RequestException;
import com.topjia.music.request.service.SearchService;
import com.topjia.music.request.util.HandleReqData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wjh
 * @date 2019-12-20 15:02
 */
@CrossOrigin
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {
    @Autowired
    private SearchService searchService;

    /**
     * 获取热搜
     *
     * @param reqData 请求参数
     * @return JSONObject
     */
    @PostMapping("/getHotKey")
    public JSONObject hotKey(String reqData) {
        try {
            return (JSONObject) HandleReqData.sendRequest(reqData);
        } catch (Exception e) {
            log.error("获取热搜失败:{}", e.getMessage());
            throw new RequestException();
        }
    }

    /**
     * 迷你搜索
     *
     * @param reqData 请求参数
     * @return HashMap
     */
    @PostMapping("/smartsearch")
    public JSONObject smartSearch(String reqData) {
        try {
            return (JSONObject) HandleReqData.sendRequest(reqData);
        } catch (Exception e) {
            log.error("迷你搜索失败:{}", e.getMessage());
            throw new RequestException();
        }
    }

    /**
     * 单曲搜索
     *
     * @param reqData 请求参数
     * @return Object
     */
    @PostMapping("/song")
    public Object searchSong(String reqData) {
        try {
            return this.searchService.searchSong(reqData);
        } catch (Exception e) {
            log.error("单曲搜索失败:{}", e.getMessage());
            throw new RequestException();
        }
    }
}
