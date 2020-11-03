package com.topjia.music.user.util;

import com.topjia.music.common.domain.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author topjia
 * @date 2020-08-01 22:03
 */
@Slf4j
public class CheckFieldUtil {
    // 常规手机号判断
    private static final String TELEPHONE_NUM_PATTERN = "^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$";

    // 邮箱
    private static final String EMIL_PATTERN = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    // 不能全为数字，不能全为字母，必须数字加字母，8-16位
    private static final String PASSWORD_PATTERN = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";

    public static void checkEmil(String emil) {
        // 验证邮箱
        Pattern emilCompile = Pattern.compile(EMIL_PATTERN);
        Matcher emilMatcher = emilCompile.matcher(emil);
        if (!emilMatcher.matches()) {
            log.warn("邮箱校验失败");
            throw new SecurityException(String.valueOf(ResultEnum.EMIL_ERROR.getCode()));
        }
    }

    public static void checkPhone(String telephoneNum) {
        // 验证手机号
        Pattern telephoneNumCompile = Pattern.compile(TELEPHONE_NUM_PATTERN);
        Matcher telephoneNumMatcher = telephoneNumCompile.matcher(telephoneNum);
        if (!telephoneNumMatcher.matches()) {
            log.warn("手机号格式校验失败");
            throw new SecurityException(String.valueOf(ResultEnum.TELEPHONE_NUM_ERROR.getCode()));
        }
    }

    public static void checkPassword(String password) {
        // 验证密码
        Pattern passwordCompile = Pattern.compile(PASSWORD_PATTERN);
        Matcher passwordMatcher = passwordCompile.matcher(password);
        if (!passwordMatcher.matches()) {
            log.warn("密码格式校验失败");
            throw new SecurityException(String.valueOf(ResultEnum.PASSWORD_ERROR.getCode()));
        }
    }
}
