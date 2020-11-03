package com.topjia.music.user.oss.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.topjia.music.common.domain.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 *  @Description:图片上传至阿里OSS云库工具类
 *  
 */
@Slf4j
@Component
public class OSSUtil {
    private static String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
    private static String accessKeyId = "LTAI4FnyUoemDnytueg79rGj";
    private static String accessKeySecret = "Fw1yjywplu6AdJYvFAE6MrLLzgcsvA";
    private static String bucketName = "topjia-oss";
    /**
     * OSS的文件夹名
     */
    private static String folder = "music/user/header_images/";

    /**
     * 上传图片
     *
     * @param fileName    文件名
     * @param inputStream 流
     */
    public static String uploadImageToOSS(String fileName, InputStream inputStream) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            ossClient.putObject(new PutObjectRequest(bucketName, folder + fileName, inputStream));
            String lookUrl = "https://www.yt526.top/";
            return lookUrl + folder + fileName;
        } catch (Exception e) {
            log.error("图片上传失败", e);
            throw new SecurityException(String.valueOf(ResultEnum.UPLOAD_IMAGE_ERROR.getCode()));
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * 删除图片 警告：在没有调用其他方法的情况下，请调用closeClient方法
     *
     * @param fileName OSS中文件为准
     */
    public static void deleteImg(String fileName) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        /**
         * 对象是否存在
         */
        if (ossClient.doesObjectExist(bucketName, folder + fileName)) {
            /**
             * 删除存在对象
             */
            ossClient.deleteObject(bucketName, folder + fileName);
        }
        ossClient.shutdown();
    }
}