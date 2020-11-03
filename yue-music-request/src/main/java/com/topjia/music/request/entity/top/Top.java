package com.topjia.music.request.entity.top;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @author wjh
 * @date 2019-12-06 21:33
 */
@Data
public class Top {
    private String period;
    private Integer topId;
    private String headPicUrl;
    private String frontPicUrl;
    private String topTitle;
    private String titleDetail;
    private String titleShare;
    private String intro;
    private String updateTips;
    private String updateTime;
    private List<RankSong> rankSong;
    private JSONObject topHistory;
    private Integer listenNum;
    private JSONArray song;
}
