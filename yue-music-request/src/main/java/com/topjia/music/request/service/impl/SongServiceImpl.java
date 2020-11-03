package com.topjia.music.request.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.topjia.music.request.service.SongService;
import com.topjia.music.request.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wjh
 * @date 2020-01-04 14:15
 */
@Slf4j
@Service
public class SongServiceImpl implements SongService {
    @Override
    public String getSongPlayUrlByQQYY(String reqData) throws Exception {
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData);
        // 封装音乐
        JSONObject o = (JSONObject) response.getJSONObject("req_0").getJSONObject("data").getJSONArray("midurlinfo").get(0);
        log.info("{}", response.getJSONObject("req_0").getJSONObject("data").getJSONArray("midurlinfo"));
        if (StringUtils.isEmpty(o.getString("purl"))) {
            return null;
        }
        return "http://aqqmusic.tc.qq.com/amobile.music.tc.qq.com/" + o.getString("purl");
    }

    @Override
    public String getSongPurlByOther(String reqData) throws Exception {
        String songMid = AesUtil.aesDecrypt(reqData);
        String url = "http://www.douqq.com/qqmusic/qqapi.php";
        ArrayList<Object> params = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();
        params.add("mid");
        values.add(("https://y.qq.com/n/yqq/song/" + songMid + ".html").replaceAll("\"", ""));
        List<NameValuePair> paramsList = HttpSendUtil.getParams(params, values);
        String data = ((String) HttpSendUtil.sendPost(url, paramsList)).replaceAll("\\\\", "");
        JSONObject jsonObject = JSONObject.parseObject(data.substring(1, data.length() - 1));
        return jsonObject.getString("m4a");
    }

    @Override
    public String getSongLyric(String reqData) throws Exception {
        RequestHeader header = new RequestHeader("c.y.qq.com", "https://c.y.qq.com/", "");
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData, header);
        return response.getString("lyric");
    }
}
