package com.topjia.music.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.topjia.music.common.domain.dto.result.BaseResult;
import com.topjia.music.user.domain.dto.user.UserLoginDTO;
import com.topjia.music.user.domain.dto.user.UserRegisterDTO;
import com.topjia.music.user.domain.entity.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author topjia
 * @since 2020-06-02
 */
public interface UserService extends IService<User> {

    BaseResult saveUser(UserLoginDTO userDTO);

    BaseResult login(UserLoginDTO userDTO, String type);

    BaseResult modifyUser(User user);

    BaseResult selectByNick(String nickName);

    BaseResult registerByEmil(UserRegisterDTO registerDTO);
}
