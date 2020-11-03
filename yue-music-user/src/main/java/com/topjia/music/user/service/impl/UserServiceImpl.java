package com.topjia.music.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.topjia.music.common.domain.dto.result.BaseResult;
import com.topjia.music.common.domain.dto.result.ResultDTO;
import com.topjia.music.common.domain.enums.ResultEnum;
import com.topjia.music.user.dao.mapper.UserMapper;
import com.topjia.music.user.domain.dto.disst.DisstDTO;
import com.topjia.music.user.domain.dto.song.SongDTO;
import com.topjia.music.user.domain.dto.user.*;
import com.topjia.music.user.domain.entity.User;
import com.topjia.music.user.feignclient.MusicFeignClient;
import com.topjia.music.user.service.UserService;
import com.topjia.music.user.util.CheckFieldUtil;
import com.topjia.music.user.util.JwtOperator;
import com.topjia.music.user.util.UserCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author topjia
 * @since 2020-06-02
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtOperator jwtOperator;

    @Autowired
    private MusicFeignClient musicFeignClient;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 注册
     *
     * @param userDTO userDTO
     * @return 成功的数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult saveUser(UserLoginDTO userDTO) {
//        // 1、根据手机号查询用户是否存在
//        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_telephone_num", userDTO.getUserTelephoneNum()));
//        if (user == null) {
//            // 2、校验用户信息是否合法,不合法抛出SecurityException
//            UserCheckUtil.checkUserDTO(userDTO);
//            // 3、封装User
//            User build = User.builder()
//                    .userNick(userDTO.getUserTelephoneNum()) // 初始账号昵称为手机号（后续可以修改）
//                    .userTelephoneNum(userDTO.getUserTelephoneNum())
//                    .userPassword(DigestUtils.md5DigestAsHex(userDTO.getUserPassword().getBytes()))
//                    .userUuid(UUID.randomUUID().toString())
//                    .userSex(0) // 初始性别为保密
//                    .build();
//            log.info("User:{}", build);
//            // 4、保存用户
//            int res = userMapper.insert(build);
//            // 5、判断是否保存成功
//            if (res == 1) {
//                log.info("用户{}注册成功", build);
//                return BaseResult.builder().code(0).data(
//                        ResultDTO.builder()
//                                .status(ResultEnum.SUCCESS.getCode())
//                                .message(ResultEnum.SUCCESS.getDesc())
//                                .build()
//                ).build();
//            } else {
//                log.error("用户{}注册失败", build);
//                throw new SecurityException(String.valueOf(ResultEnum.REGISTER_ERROR.getCode()));
//            }
//        } else {
//            // 用户已存在，返回错误
//            log.error("用户{}已存在", user);
//            throw new SecurityException(String.valueOf(ResultEnum.HAVE_USER.getCode()));
//        }
        return null;
    }

    /**
     * 登录
     *
     * @param userDTO userDTO
     * @return BaseResult
     */
    @Override
    public BaseResult login(UserLoginDTO userDTO, String type) {
        User user = null;
        // 根据账号类型查询用户是否已经注册
        if (type.equals("phone")) {
            user = this.userMapper.selectOne(new QueryWrapper<User>().eq("user_telephone_num", userDTO.getUserTelephoneNum()));
        } else if (type.equals("emil")) {
            user = this.userMapper.selectOne(new QueryWrapper<User>().eq("user_emil", userDTO.getUserEmil()));
        }
        if (user == null) {
            // 未注册
            log.error("用户{}未注册", userDTO);
            throw new SecurityException(String.valueOf(ResultEnum.USER_NOT_REGISTER.getCode()));
        } else {
            // 校验密码
            String inputPassword = DigestUtils.md5DigestAsHex(userDTO.getUserPassword().getBytes());
            if (!inputPassword.equals(user.getUserPassword())) {
                //密码错误
                log.error("用户{}密码错误", userDTO);
                throw new SecurityException(String.valueOf(ResultEnum.PASSWORD_ERROR.getCode()));
            }
            // 登录成功,颁发token
            String token = getToken(user);
            log.info("用户{}登录成功,生成的token为:{},有效期到:{}", user, token, jwtOperator.getExpiationDateFromToken(token));
            // 远程调用music服务查询用户收藏的所有歌曲
            List<SongDTO> songDTOS = musicFeignClient.userFavoriteSongs(user.getId());
            // 远程调用music服务查询用户收藏的所有歌单
            List<DisstDTO> disstDTOS = musicFeignClient.userFavoriteDissts(user.getId());
            // 构建返回值
            HashMap<String, Object> data = createData(user, token);
            UserRespDTO respDTO = (UserRespDTO) data.get("user");
            data.put("user", respDTO);
            data.put("favoriteDissts", disstDTOS);
            data.put("favoriteSongs", songDTOS);
            return BaseResult.builder()
                    .code(0)
                    .data(
                            ResultDTO.builder()
                                    .status(ResultEnum.SUCCESS.getCode())
                                    .message(ResultEnum.SUCCESS.getDesc())
                                    .data(data)
                                    .build()
                    )
                    .build();
        }
    }

    /**
     * 用户信息修改
     *
     * @param user User
     * @return BaseResult
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResult modifyUser(User user) {
        int num = this.userMapper.updateById(user);
        // 修改失败
        if (num == 0) {
            throw new SecurityException();
        }
        User modifyUser = this.userMapper.selectById(user.getId());
        // 重新生成token
        String token = getToken(modifyUser);
        log.info("用户{}修改成功,重新生成的token为:{},有效期到:{}", modifyUser, token, jwtOperator.getExpiationDateFromToken(token));
        // 构建返回值
        HashMap<String, Object> data = createData(modifyUser, token);
        return BaseResult.builder()
                .code(0)
                .data(
                        ResultDTO.builder()
                                .status(ResultEnum.SUCCESS.getCode())
                                .message(ResultEnum.SUCCESS.getDesc())
                                .data(data)
                                .build()
                ).build();
    }

    /**
     * 根据昵称查询用户
     *
     * @param nickName 用户昵称
     * @return BaseResult
     */
    @Override
    public BaseResult selectByNick(String nickName) {
        log.info("{}", nickName);
        User nick_user = this.userMapper.selectOne(new QueryWrapper<User>().eq("user_nick", nickName));
        log.info("user:{}", nick_user);
        UserRespDTO user = new UserRespDTO();
        if (nick_user == null) {
            // 昵称不存在，返回
            user = null;
        } else {
            user.setUserNick(nick_user.getUserNick());
        }
        return BaseResult.builder()
                .code(0)
                .data(ResultDTO.builder()
                        .message(ResultEnum.SUCCESS.getDesc())
                        .status(ResultEnum.SUCCESS.getCode())
                        .data(user)
                        .build())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult registerByEmil(UserRegisterDTO registerDTO) {
        // 检查字段是否合法
        CheckFieldUtil.checkEmil(registerDTO.getUserEmil());
        CheckFieldUtil.checkPassword(registerDTO.getUserPassword());

        //是否存在该用户
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_emil", registerDTO.getUserEmil()));
        if (user != null) {
            log.error("用户{}已存在", user);
            throw new SecurityException(String.valueOf(ResultEnum.HAVE_USER.getCode()));
        }

        // 从redis获取验证码，并校验
        String captcha = (String) redisTemplate.opsForValue().get(registerDTO.getUserEmil());
        if (StringUtils.isEmpty(captcha)) {
            // 该邮箱无对应的效验码
            log.warn("用户{},未获取验证码", registerDTO.getUserEmil());
            throw new SecurityException(String.valueOf(ResultEnum.CAPTCHA_EMPTY.getCode()));
        }

        // 校验验证码是否一致
        if (!StringUtils.pathEquals(captcha, registerDTO.getCaptcha())) {
            log.warn("用户{},验证码输入不一致", registerDTO.getUserEmil());
            throw new SecurityException(String.valueOf(ResultEnum.CAPTCHA_UNEQUAL.getCode()));
        }

        // 构建用户信息,保存用户
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        User saveUser = User.builder()
                .userNick("小悦民" + uuid.substring(0, 5))
                .userSex(0)
                .banFlag(0)
                .userDesc("")
                .userHeaderUrl("https://www.yt526.top/music/user/header_images/default_image/person_300.png")
                .userPassword(DigestUtils.md5DigestAsHex(registerDTO.getUserPassword().getBytes()))
                .userTelephoneNum("")
                .userEmil(registerDTO.getUserEmil())
                .userUuid(uuid)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .banTime(null)
                .build();
        // 保存用户信息
        int res = userMapper.insert(saveUser);
        // 5、判断是否保存成功
        if (res == 1) {
            log.info("用户{}注册成功", saveUser);
            // 注册成功，删除验证码缓存
            redisTemplate.delete(registerDTO.getUserEmil());
            return BaseResult.builder().code(0).data(
                    ResultDTO.builder()
                            .status(ResultEnum.SUCCESS.getCode())
                            .message(ResultEnum.SUCCESS.getDesc())
                            .build()
            ).build();
        } else {
            log.error("用户{}注册失败", saveUser);
            throw new SecurityException(String.valueOf(ResultEnum.REGISTER_ERROR.getCode()));
        }
    }

    private HashMap<String, Object> createData(User user, String token) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("user", UserRespDTO.builder()
                .id(user.getId())
                .userNick(user.getUserNick())
                .userHeaderURL(user.getUserHeaderUrl())
                .userDesc(user.getUserDesc())
                .userSex(user.getUserSex())
                .build());
        data.put("token", JwtTokenRespDTO.builder()
                .expirationTime(jwtOperator.getExpiationDateFromToken(token).getTime())
                .token(token)
                .build());
        return data;
    }

    private String getToken(User user) {
        HashMap<String, Object> userInfo = new HashMap<>(3);
        userInfo.put("id", user.getId());
        userInfo.put("userNick", user.getUserNick());
        userInfo.put("userHeaderUrl", user.getUserHeaderUrl());
        userInfo.put("userDesc", user.getUserDesc());
        userInfo.put("userSex", user.getUserSex());
        return jwtOperator.generateToken(userInfo);
    }
}
