package com.topjia.music.request.controller.singer;

import com.topjia.music.request.security.RequestException;
import com.topjia.music.request.service.SingerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author wjh
 * @date 2019-12-10 20:47
 */
@CrossOrigin
@RestController
@RequestMapping("/singer")
@Slf4j
public class SingerController {
    @Autowired
    private SingerService singerService;

    /**
     * 获取歌手列表
     *
     * @param reqData 请求参数
     * @return Object
     */
    @PostMapping("/getSingerList")
    public Object getSingerList(String reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            HashMap<String, Object> singerList = singerService.getSingerList(reqData);
            result.put("code", 0);
            result.put("data", singerList);
            return result;
        } catch (Exception e) {
            log.error("获取歌手列表失败:{}", e.getMessage());
            throw new RequestException();
        }
    }

    /**
     * 获取歌手歌曲列表
     *
     * @param reqData 请求参数
     * @return Object
     */
    @PostMapping("/songlist")
    public Object getSingerSongList(String reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            HashMap<String, Object> data = singerService.getSingerSongList(reqData);
            result.put("code", 0);
            result.put("data", data);
            return result;
        } catch (Exception e) {
            log.error("获取歌手歌曲列表失败:{}", e.getMessage());
            throw new RequestException();
        }
    }

    /**
     * 获取歌手专辑
     *
     * @param reqData 请求参数
     * @return Object
     */
    @PostMapping("/albums")
    public Object getSingerAlbums(String reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            HashMap<String, Object> data = singerService.getSingerAlbums(reqData);
            result.put("code", 0);
            result.put("data", data);
            return result;
        } catch (Exception e) {
            log.error("获取歌手专辑失败:{}", e.getMessage());
            throw new RequestException();
        }
    }

    /**
     * 获取歌手粉丝数
     *
     * @param reqData   请求参数
     * @param singerMid 歌手mid
     * @return Object
     */
    @PostMapping("/concern")
    public Object getSingerConcern(String reqData, String singerMid) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            HashMap<String, Object> data = singerService.getSingerConcern(reqData, singerMid);
            result.put("code", 0);
            result.put("data", data);
            return result;
        } catch (Exception e) {
            log.error("获取歌手粉丝数失败:{}", e.getMessage());
            throw new RequestException();
        }
    }

    /**
     * 获取歌手基本信息
     *
     * @param reqData 请求参数
     * @return Object
     */
    @PostMapping("/info")
    public Object getSingerInfo(String reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            HashMap<String, Object> data = singerService.getSingerInfo(reqData);
            result.put("code", 0);
            result.put("data", data);
            return result;
        } catch (Exception e) {
            log.error("获取歌手基本信息失败:{}", e.getMessage());
            throw new RequestException();
        }
    }
}
