package com.topjia.music.music.domain.entity;

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
    private String singerMid;
    private String singerName;
    private String singerPic;
}
