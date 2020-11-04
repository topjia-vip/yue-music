package com.topjia.music.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.topjia.music.common.domain.dto.result.BaseResult;
import com.topjia.music.common.domain.dto.result.RequestData;
import com.topjia.music.common.domain.dto.result.ResultDTO;
import com.topjia.music.common.domain.enums.ResultEnum;
import com.topjia.music.common.util.AesDecryptUtil;
import com.topjia.music.user.auth.CheckLogin;
import com.topjia.music.user.domain.dto.user.UserLoginDTO;
import com.topjia.music.user.domain.entity.User;
import com.topjia.music.user.oss.util.OSSUtil;
import com.topjia.music.user.service.UserService;
import com.topjia.music.user.util.AccountType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author topjia
 * @since 2020-06-02
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录服务
     *
     * @param reqData 前端加密参数
     * @return ResultDTO
     */
    @PostMapping("/login")
    public BaseResult login(@RequestBody RequestData reqData) {
        AesDecryptUtil decryptUtil = new AesDecryptUtil(reqData.getReqData());
        JSONObject UserDTOJSON = JSONObject.parseObject(decryptUtil.getAesDecrypt());
        // 判断账号类型(手机号、邮箱)
        String type = AccountType.accountType(UserDTOJSON.getString("account"));
        UserLoginDTO userDTO = null;
        switch (type) {
            case "phone": {
                userDTO = UserLoginDTO
                        .builder()
                        .userPassword((UserDTOJSON.getString("userPassword")))
                        .userTelephoneNum(UserDTOJSON.getString("account"))
                        .build();
                break;
            }
            case "emil": {
                userDTO = UserLoginDTO
                        .builder()
                        .userPassword((UserDTOJSON.getString("userPassword")))
                        .userEmil(UserDTOJSON.getString("account"))
                        .build();
                break;
            }
            case "account_type_error": {
                break;
            }
        }
        log.info("账号{},尝试登录", userDTO);
        return userService.login(userDTO, type);
    }

    @PostMapping("/upload")
    @CheckLogin // 校验token的注解
    public BaseResult getUploadFile(MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            try {
                User user = (User) request.getAttribute("user");
                // 删除上一张头像（如果有的话）
                if (!StringUtils.isEmpty(user.getUserHeaderUrl())) {
                    // 删除图片 https://www.yt526.top/music/user/header_images/9/ZOOM9.jpeg
                    String fileName = user.getUserHeaderUrl().split("header_images/")[1];
                    System.out.println(fileName);
                    OSSUtil.deleteImg(fileName);
                }
                log.info("用户{}上传的文件为{}", user, file.getOriginalFilename());
                // 修改文件名和文件在OSS中存储的文件夹
                String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
                // 时间戳
                String currentTimeMillis = String.valueOf(System.currentTimeMillis() / 1000);
                String fileName = user.getId() + "/" + "ZOOM" + user.getId() + currentTimeMillis + "." + split[split.length - 1];
                log.info("待上传的文件为{}", fileName);
                // 获取输入流
                InputStream inputStream = file.getInputStream();
                // 上传至OSS，获取图片URL
                String url = OSSUtil.uploadImageToOSS(fileName, inputStream);
                user.setUserHeaderUrl(url);
                user.setUpdateTime(LocalDateTime.now());
                // 保存将URL存入用户数据
                return userService.modifyUser(user);
            } catch (SecurityException | IOException e) {
                log.error("图片上传失败", e);
                throw new SecurityException(String.valueOf(ResultEnum.UPLOAD_IMAGE_ERROR.getCode()));
            }
        } else {
            log.error("没有图片文件");
            throw new SecurityException(String.valueOf(ResultEnum.UPLOAD_IMAGE_ERROR.getCode()));
        }
    }

    @PostMapping("/update")
    @CheckLogin
    public BaseResult modifyUser(@RequestBody RequestData reqData) {
        AesDecryptUtil decryptUtil = new AesDecryptUtil(reqData.getReqData());
        JSONObject userDTOJSON = JSONObject.parseObject(decryptUtil.getAesDecrypt());
        User user = User.builder()
                .id(userDTOJSON.getInteger("id"))
                .userNick(userDTOJSON.getString("userNick"))
                .userDesc(userDTOJSON.getString("userDesc"))
                .userSex(userDTOJSON.getInteger("userSex"))
                .updateTime(LocalDateTime.now())
                .build();
        log.info("待修改的用户信息:{}", user);
        return userService.modifyUser(user);
    }

    @PostMapping("/check_name")
    @CheckLogin
    public BaseResult checkNickName(@RequestBody RequestData reqData) {
        AesDecryptUtil decryptUtil = new AesDecryptUtil(reqData.getReqData());
        String nickName = decryptUtil.getAesDecrypt();
        return userService.selectByNick(nickName);
    }

    @GetMapping("/checkUser")
    @CheckLogin
    public BaseResult checkUser() {
        return BaseResult.builder()
                .code(0)
                .data(ResultDTO.builder()
                        .message(ResultEnum.SUCCESS.getDesc())
                        .status(ResultEnum.SUCCESS.getCode())
                        .build())
                .build();
    }
}

