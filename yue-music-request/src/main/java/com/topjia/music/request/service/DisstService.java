package com.topjia.music.request.service;

import com.topjia.music.request.entity.disst.Disst;
import com.topjia.music.request.entity.disst_category_group.DisstCategoryGroup;
import com.topjia.music.request.entity.song.Song;

import java.util.List;
import java.util.Map;

/**
 * @author wjh
 * @date 2020-05-24 17:19
 */
public interface DisstService {
    List<DisstCategoryGroup> getDisstCategoryGroup(String reqData) throws Exception;

    Map<String, Object> getDisstList(String reqData) throws Exception;

    Disst getDisstDetail(String reqData) throws Exception;

    List<Song> getDisstSongList(String reqData) throws Exception;
}
