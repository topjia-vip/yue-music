package com.topjia.music.request.controller.singer;

import com.topjia.music.common.domain.dto.result.RequestData;
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
    public Object getSingerList(@RequestBody RequestData reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            HashMap<String, Object> singerList = singerService.getSingerList(reqData.getReqData());
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
    public Object getSingerSongList(@RequestBody RequestData reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            HashMap<String, Object> data = singerService.getSingerSongList(reqData.getReqData());
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
    public Object getSingerAlbums(@RequestBody RequestData reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            HashMap<String, Object> data = singerService.getSingerAlbums(reqData.getReqData());
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
    public Object getSingerConcern(@RequestBody RequestData reqData, String singerMid) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            HashMap<String, Object> data = singerService.getSingerConcern(reqData.getReqData(), singerMid);
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
    public Object getSingerInfo(@RequestBody RequestData reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            HashMap<String, Object> data = singerService.getSingerInfo(reqData.getReqData());
            result.put("code", 0);
            result.put("data", data);
            return result;
        } catch (Exception e) {
            log.error("获取歌手基本信息失败:{}", e.getMessage());
            throw new RequestException();
        }
    }
}
