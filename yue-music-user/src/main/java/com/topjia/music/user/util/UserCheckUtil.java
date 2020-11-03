package com.topjia.music.user.util;

import com.topjia.music.common.domain.enums.ResultEnum;
import com.topjia.music.user.domain.dto.user.UserLoginDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检查用户信息是否合法
 *
 * @author wjh
 * @date 2020-06-03 11:16
 */
@Slf4j
public class UserCheckUtil {
    // 常规手机号判断
    private static final String TELEPHONE_NUM_PATTERN = "^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$";

    // 不能全为数字，不能全为字母，必须数字加字母，8-16位
    private static final String PASSWORD_PATTERN = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";

    public static void checkUserDTO(UserLoginDTO userDTO) {
        // 验证手机号
        String telephoneNum = userDTO.getUserTelephoneNum();
        Pattern telephoneNumCompile = Pattern.compile(TELEPHONE_NUM_PATTERN);
        Matcher telephoneNumMatcher = telephoneNumCompile.matcher(telephoneNum);
        if (!telephoneNumMatcher.matches()) {
            log.warn("手机号格式校验失败");
            throw new SecurityException(String.valueOf(ResultEnum.TELEPHONE_NUM_ERROR.getCode()));
        }
        // 验证密码
        String password = userDTO.getUserPassword();
        Pattern passwordCompile = Pattern.compile(PASSWORD_PATTERN);
        Matcher passwordMatcher = passwordCompile.matcher(password);
        if (!passwordMatcher.matches()) {
            log.warn("密码格式校验失败");
            throw new SecurityException(String.valueOf(ResultEnum.PASSWORD_ERROR.getCode()));
        }
    }
}
