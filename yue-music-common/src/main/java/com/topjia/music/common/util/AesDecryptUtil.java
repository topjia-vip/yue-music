package com.topjia.music.common.util;

import com.topjia.music.common.domain.enums.ResultEnum;
import lombok.Data;

/**
 * 解密工具类
 *
 * @author wjh
 * @date 2020-06-04 21:07
 */
@Data
public class AesDecryptUtil {
    /**
     * 待解密数据
     */
    private String needDecryptStr;

    /**
     * 已解密的数据
     */
    private String aesDecrypt;

    public AesDecryptUtil(String needDecryptStr) {
        this.needDecryptStr = needDecryptStr;
        this.tryDecrypt();
    }

    public String getAesDecrypt() {
        return aesDecrypt;
    }

    private void tryDecrypt() {
        try {
            // 解密,请求参数解密失败，抛出SecurityException异常
            aesDecrypt = AesUtil.aesDecrypt(needDecryptStr);
        } catch (SecurityException e) {
            throw new SecurityException(String.valueOf(ResultEnum.REQUEST_PARAM_ERROR.getCode()));
        }
    }
}
