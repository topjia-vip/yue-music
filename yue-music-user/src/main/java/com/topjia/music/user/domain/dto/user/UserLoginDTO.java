package com.topjia.music.user.domain.dto.user;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author wjh
 * @date 2020-06-02 22:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class UserLoginDTO {
    /**
     * 用户名称
     */
    private String userNick;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户手机号
     */
    private String userTelephoneNum;

    /**
     * 用户邮箱
     */
    private String userEmil;
}
