package com.topjia.music.gateway.config;

import lombok.Data;

import java.time.LocalTime;

/**
 * @author wjh
 * @date 2020-06-02 14:48
 */
@Data
public class TimeBetweenConfig {
    private LocalTime start;
    private LocalTime end;
}
