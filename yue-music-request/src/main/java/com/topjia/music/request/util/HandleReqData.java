package com.topjia.music.request.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wjh
 * @date 2020-05-24 16:31
 */
public class HandleReqData {
    public static Object sendRequest(String reqData) throws Exception {
        String decrypt = AesUtil.aesDecrypt(reqData);
        JSONObject reqJSON = JSONObject.parseObject(decrypt);
        String url = reqJSON.getString("url");
        List<NameValuePair> paramsList = getParamsList(reqJSON);
        return HttpSendUtil.sendGet(url, paramsList, null);
    }

    public static Object sendRequest(String reqData, RequestHeader header) throws Exception {
        String decrypt = AesUtil.aesDecrypt(reqData);
        JSONObject reqJSON = JSONObject.parseObject(decrypt);
        String url = reqJSON.getString("url");
        List<NameValuePair> paramsList = getParamsList(reqJSON);
        return HttpSendUtil.sendGet(url, paramsList, header);
    }

    private static List<NameValuePair> getParamsList(JSONObject reqJSON) throws Exception {
        JSONArray params = reqJSON.getJSONArray("params");
        ArrayList<Object> ps = new ArrayList<>();
        ArrayList<Object> vs = new ArrayList<>();
        params.forEach(item -> {
            JSONObject o = (JSONObject) item;
            ps.add(o.getString("key"));
            vs.add(o.getString("value"));
        });
        return HttpSendUtil.getParams(ps, vs);
    }
}
