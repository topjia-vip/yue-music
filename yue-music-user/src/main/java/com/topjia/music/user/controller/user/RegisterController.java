package com.topjia.music.user.controller.user;

import com.topjia.music.common.domain.dto.result.BaseResult;
import com.topjia.music.user.domain.dto.user.UserRegisterDTO;
import com.topjia.music.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author topjia
 * @date 2020-08-01 18:02
 */
@RestController
@CrossOrigin
@RequestMapping("/user1")
@Slf4j
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/emil/register")
    public BaseResult registerByEmil(UserRegisterDTO registerDTO) {
        log.info("{}", registerDTO);
        return userService.registerByEmil(registerDTO);
    }
}
