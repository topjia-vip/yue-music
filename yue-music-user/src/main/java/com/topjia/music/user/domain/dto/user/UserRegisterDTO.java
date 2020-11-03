package com.topjia.music.user.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author topjia
 * @date 2020-08-01 19:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDTO {
    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户邮箱
     */
    private String userEmil;

    /**
     * 用户手机号
     */
    private String userTelephoneNum;

    /**
     * 注册验证码
     */
    private String captcha;
}
