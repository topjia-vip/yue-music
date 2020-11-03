package com.topjia.music.music.auth;

import com.topjia.music.common.domain.enums.ResultEnum;
import com.topjia.music.music.domain.dto.user.UserDTO;
import com.topjia.music.music.util.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 检查用户是否登录的切面
 *
 * @author wjh
 * @date 2020-06-05 22:21
 */
@Aspect
@Component
@Slf4j
public class CheckLoginAspect {
    @Autowired
    private JwtOperator jwtOperator;

    @Around("@annotation(com.topjia.music.music.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point) {
        try {
            // 从header种获取token
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) attributes;
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String token = request.getParameter("token");
            // 校验token是否合法或已过期
            Boolean isValid = jwtOperator.validateToken(token);
            if (!isValid) {
                log.error("token不合法,{}", token);
                throw new SecurityException(String.valueOf(ResultEnum.TOKEN_CHECK_ERROR.getCode()));
            }
            // 校验成功将用户信息设置到request的attributes里
            Claims claims = jwtOperator.getClaimsFromToken(token);
            UserDTO user = UserDTO.builder()
                    .id((Integer) claims.get("id"))
                    .userNick((String) claims.get("userNick"))
                    .userHeaderUrl((String) claims.get("userHeaderUrl"))
                    .userDesc((String) claims.get("userDesc"))
                    .userSex((Integer) claims.get("userSex"))
                    .build();
            request.setAttribute("user", user);
            return point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new SecurityException(String.valueOf(ResultEnum.ERROR.getCode()));
        }
    }
}
