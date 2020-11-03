package com.topjia.music.user.domain.entity;

import lombok.*;

/**
 * @author wjh
 * @date 2020-06-07 21:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Singer {
    private Integer singerId;
    private String SingerMid;
    private String SingerName;
    private String SingerPic;
}
