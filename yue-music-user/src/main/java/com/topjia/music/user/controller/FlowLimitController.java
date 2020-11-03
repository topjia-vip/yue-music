package com.topjia.music.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author topjia
 * @date 2020-08-03 22:16
 */
@RestController
@Slf4j
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA() throws InterruptedException {
//        TimeUnit.SECONDS.sleep(10);
        log.info(Thread.currentThread().getName()+"...textA");
        return "testA";
    }

    @GetMapping("/testB")
    public String testB() {
        return "testB";
    }
}
