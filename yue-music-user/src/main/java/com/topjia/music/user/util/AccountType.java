package com.topjia.music.user.util;

import com.topjia.music.common.domain.enums.ResultEnum;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author topjia
 * @date 2020-10-23 16:41
 */
@Slf4j
public class AccountType {
    // 常规手机号判断
    private static final String TELEPHONE_NUM_PATTERN = "^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$";

    private static final String EMIL_PATTERN = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

    public static String accountType(String account) {
        Pattern telephoneNumCompile = Pattern.compile(TELEPHONE_NUM_PATTERN);
        Pattern emilCompile = Pattern.compile(EMIL_PATTERN);
        Matcher telephoneNumMatcher = telephoneNumCompile.matcher(account);
        Matcher emilMatcher = emilCompile.matcher(account);
        if (telephoneNumMatcher.matches()) {
            return "phone";
        } else if (emilMatcher.matches()) {
            return "emil";
        } else {
            return "account_type_error";
        }
    }
}
