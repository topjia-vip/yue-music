package com.topjia.music.user.util;

import java.util.Random;

/**
 * @author topjia
 * @date 2020-08-01 15:24
 */
public class CaptchaUtil {

    public static String createCaptcha() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
}
