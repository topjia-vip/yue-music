package com.topjia.music.user.controller;

import com.topjia.music.common.domain.dto.result.BaseResult;
import com.topjia.music.common.domain.dto.result.ResultDTO;
import com.topjia.music.common.domain.enums.ResultEnum;
import com.topjia.music.user.util.CaptchaUtil;
import com.topjia.music.user.util.send.SendEmilUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author topjia
 * @date 2020-07-30 23:16
 */
@Slf4j
@RestController
@RequestMapping("/checkcode")
public class CheckCodeController {

    @Autowired
    private SendEmilUtil sendEmilUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送邮箱验证码
     *
     * @param emil 郵箱
     * @return BaseResult
     */
    @PostMapping("/getemilcheckcode")
    public BaseResult getEmilCheckCode(String emil) {
        log.info("emil:{}", emil);
        String message;
        int status;
        int code;
        try {
            // 创建验证码
            String captcha = CaptchaUtil.createCaptcha();

            // 先存入redis
            redisTemplate.opsForValue().set(emil, captcha, 5, TimeUnit.MINUTES);

            // 发送邮件
            sendEmilUtil.sendEmilCaptcha(emil, captcha);
            message = ResultEnum.SUCCESS.getDesc();
            status = ResultEnum.SUCCESS.getCode();
            code = 0;
        } catch (Exception e) {
            log.error("邮件发送失败:{}", e.getMessage());
            message = ResultEnum.SEND_EMIL_ERROR.getDesc();
            status = ResultEnum.SEND_EMIL_ERROR.getCode();
            code = 500;
            // 销毁redis中的数据
            redisTemplate.delete(emil);
        }
        return BaseResult.builder()
                .code(code)
                .data(
                        ResultDTO.builder()
                                .message(message)
                                .status(status)
                                .data(null)
                                .build()
                )
                .build();
    }
}