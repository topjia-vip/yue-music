package com.topjia.music.common.util;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author wjh
 * @date 2020-05-24 14:43
 */
public class AesUtil {
    //密钥 (需要前端和后端保持一致)十六位作为密钥
    private static final String KEY = "pEXiCeq1i4^L@Ihe";

    //密钥偏移量 (需要前端和后端保持一致)十六位作为密钥偏移量
    private static final String IV = "FoLTbtM2UqJAEzo4";

    /**
     * base 64 decode
     *
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     */
    public static byte[] base64Decode(String base64Code) {
        return StringUtils.isEmpty(base64Code) ? null : Base64.decodeBase64(base64Code.getBytes());
    }

    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @return 解密后的String
     */
    public static String aesDecryptByBytes(byte[] encryptBytes) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            byte[] temp = IV.getBytes(StandardCharsets.US_ASCII);
            IvParameterSpec iv = new IvParameterSpec(temp);

            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY.getBytes(StandardCharsets.US_ASCII), "AES"), iv);
            byte[] decryptBytes = cipher.doFinal(encryptBytes);

            return new String(decryptBytes);
        } catch (Exception e) {
            throw new SecurityException("参数不合法");
        }
    }

    /**
     * 将base 64 code AES解密
     *
     * @param encryptStr 待解密的base 64 code
     * @return 解密后的string
     * @throws SecurityException SecurityException
     */
    public static String aesDecrypt(String encryptStr) throws SecurityException {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr));
    }
}
