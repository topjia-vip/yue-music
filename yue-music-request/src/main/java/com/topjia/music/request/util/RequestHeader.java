package com.topjia.music.request.util;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 设置请求头的pojo
 *
 * @author wjh
 * @date 2019-11-20 17:48
 */
@Data
public class RequestHeader {
    private String Host = "";
    private String Referer = "";
    private String Cookie = "";

    public RequestHeader(String host, String referer, String cookie) {
        if (!StringUtils.isEmpty(host)) {
            Host = host;
        }
        if (!StringUtils.isEmpty(referer)) {
            Referer = referer;
        }
        if (!StringUtils.isEmpty(cookie)) {
            Cookie = cookie;
        }
    }

    public RequestHeader() {
    }
}
