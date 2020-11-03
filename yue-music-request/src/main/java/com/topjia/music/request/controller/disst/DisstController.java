package com.topjia.music.request.controller.disst;

import com.topjia.music.request.entity.disst.Disst;
import com.topjia.music.request.entity.disst_category_group.DisstCategoryGroup;
import com.topjia.music.request.entity.song.Song;
import com.topjia.music.request.security.RequestException;
import com.topjia.music.request.service.DisstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 歌单
 *
 * @author wjh
 * @date 2019-12-19 17:00
 */
@CrossOrigin
@RestController
@RequestMapping("/disst")
@Slf4j
public class DisstController {
    @Autowired
    private DisstService disstService;

    /**
     * 获取歌单的分类标签
     */
    @PostMapping("/disstcategorygroup")
    public Object disstCategoryGroup(String reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            List<DisstCategoryGroup> categoryGroups = disstService.getDisstCategoryGroup(reqData);
            result.put("code", 0);
            result.put("categoryGroups", categoryGroups);
            return result;
        } catch (Exception e) {
            log.error("获取歌单的分类标签失败:{}", e.getMessage());
            throw new RequestException();
        }
    }

    /**
     * 获取标签下的歌单列表(可分页)
     */
    @PostMapping("/disstlist")
    public Object disstList(String reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            Map<String, Object> dissts = disstService.getDisstList(reqData);
            result.put("code", 0);
            result.put("data", dissts);
            return result;
        } catch (Exception e) {
            log.error("获取标签下的歌单列表失败:{}", e.getMessage());
            throw new RequestException();
        }
    }


    /**
     * 查看某个歌单的详情信息
     */
    @PostMapping("/disstDetail")
    public Object disstDetail(String reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            Disst disst = disstService.getDisstDetail(reqData);
            result.put("code", 0);
            result.put("disst", disst);
            return result;
        } catch (Exception e) {
            log.error("查看某个歌单的详情信息失败:{}", e.getMessage());
            throw new RequestException();
        }
    }

    /**
     * 歌单详情的歌曲列表
     */
    @PostMapping("/disstsonglist")
    public Object disstSongList(String reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            List<Song> songs = disstService.getDisstSongList(reqData);
            result.put("code", 0);
            result.put("songs", songs);
            return result;
        } catch (Exception e) {
            log.error("获取歌单详情的歌曲列表失败:{}", e.getMessage());
            throw new RequestException();
        }
    }
}
