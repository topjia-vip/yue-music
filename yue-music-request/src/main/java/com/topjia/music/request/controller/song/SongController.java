package com.topjia.music.request.controller.song;

import com.topjia.music.common.domain.dto.result.RequestData;
import com.topjia.music.request.security.RequestException;
import com.topjia.music.request.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author wjh
 * @date 2020-01-04 14:12
 */
@RestController
@RequestMapping("/song")
@Slf4j
public class SongController {
    @Autowired
    private SongService songService;

    /**
     * 从QQ音乐官方获取歌曲播放链接
     *
     * @param reqData 请求参数
     * @return Object
     */
    @PostMapping("/getsongurlqqyy")
    public Object getSongPlayUrlByQQYY(@RequestBody RequestData reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            String songPlayUrl = songService.getSongPlayUrlByQQYY(reqData.getReqData());
            result.put("code", 0);
            result.put("songPlayUrl", songPlayUrl);
            return result;
        } catch (Exception e) {
            log.error("从QQ音乐官方获取歌曲播放链接失败:{}", e.getMessage());
            throw new RequestException();
        }
    }

    /**
     * 从其他途径获取歌曲播放链接
     *
     * @param reqData 请求参数
     * @return Object
     */
    @PostMapping("/getsongurl")
    public Object getSongPurlByOther(@RequestBody RequestData reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            String songPlayUrl = songService.getSongPurlByOther(reqData.getReqData());
            // 判断是否获取成功
            if (songPlayUrl == null) {
                // 抛出异常，前端再次从官方获取播放链接
                throw new RequestException();
            }
            result.put("code", 0);
            result.put("songPlayUrl", songPlayUrl);
            return result;
        } catch (Exception e) {
            log.error("从其他途径获取歌曲播放链接失败:{}", e.getMessage());
            throw new RequestException();
        }
    }

    /**
     * 获取歌词
     *
     * @param reqData 请求参数
     * @return Object
     */
    @PostMapping("/songlyric")
    public Object getSongLyric(@RequestBody RequestData reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            String songLyric = songService.getSongLyric(reqData.getReqData());
            result.put("code", 0);
            result.put("songLyric", songLyric);
            return result;
        } catch (Exception e) {
            log.error("获取歌词失败:{}", e.getMessage());
            throw new RequestException();
        }
    }
}
