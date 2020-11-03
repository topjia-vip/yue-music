package com.topjia.music.request.entity.top;

import lombok.Data;

import java.util.List;

/**
 * @author wjh
 * @date 2019-12-06 21:32
 */
@Data
public class TopGroup {
    private String groupId;
    private String groupName;
    private List<Top> topList;
}
