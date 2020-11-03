package com.topjia.music.common.domain.enums;

/**
 * 返回结果的状态码的枚举
 *
 * @author wjh
 * @date 2020-06-03 11:49
 */
public enum ResultEnum {
    SUCCESS(0, "成功"),
    TELEPHONE_NUM_ERROR(1, "手机号错误"),
    EMIL_ERROR(2, "邮箱错误"),
    PASSWORD_ERROR(3, "密码错误"),
    REGISTER_ERROR(4, "注册错误"),
    HAVE_USER(5, "用户已存在"),
    USER_NICK_ERROR(6, "昵称错误"),
    REQUEST_PARAM_ERROR(7, "请求参数错误"),
    USER_NOT_REGISTER(8, "用户未注册"),
    UPLOAD_IMAGE_ERROR(9, "图片上传失败"),
    TOKEN_CHECK_ERROR(10, "token校验异常"),
    ERROR(11, "ε(┬┬﹏┬┬)3网络走丢了"),
    SEND_EMIL_ERROR(12, "邮件发送失败"),
    CAPTCHA_EMPTY(13, "请先获取验证码"),
    CAPTCHA_UNEQUAL(14, "验证码不一致");

    private int code;
    private String desc;

    ResultEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
