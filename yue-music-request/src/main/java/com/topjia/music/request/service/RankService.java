package com.topjia.music.request.service;

import com.topjia.music.request.entity.top.TopGroup;

import java.util.List;
import java.util.Map;

/**
 * @author wjh
 * @date 2020-05-24 18:02
 */
public interface RankService {
    List<TopGroup> getTopListInfo(String reqData) throws Exception;

    Map<String, Object> getTopDetail(String reqData) throws Exception;
}
