package com.topjia.music.request.controller.rank;

import com.topjia.music.common.domain.dto.result.RequestData;
import com.topjia.music.request.entity.top.TopGroup;
import com.topjia.music.request.security.RequestException;
import com.topjia.music.request.service.RankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author wjh
 * @date 2019-12-11 19:58
 */
@RestController
@RequestMapping("/rank")
@Slf4j
public class RankController {
    @Autowired
    private RankService rankService;

    /**
     * 获取排行榜
     *
     * @param reqData 请求参数
     * @return Object
     */
    @PostMapping("/toplistinfo")
    public Object TopListInfo(@RequestBody RequestData reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            List<TopGroup> topGroups = rankService.getTopListInfo(reqData.getReqData());
            result.put("code", 0);
            result.put("toplist", topGroups);
            return result;
        } catch (Exception e) {
            log.error("获取排行榜失败:{}", e.getMessage());
            throw new RequestException();
        }
    }

    /**
     * 获取排行榜详情
     *
     * @param reqData 请求参数
     * @return Object
     */
    @PostMapping("/topdetail")
    public Object TopDetail(@RequestBody RequestData reqData) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            Map<String, Object> topDetail = rankService.getTopDetail(reqData.getReqData());
            result.put("code", 0);
            result.put("topDetail", topDetail);
            return result;
        } catch (Exception e) {
            log.error("获取排行榜失败:{}", e.getMessage());
            throw new RequestException();
        }
    }
}
