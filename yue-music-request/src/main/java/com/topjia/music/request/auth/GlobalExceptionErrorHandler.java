package com.topjia.music.request.auth;

import com.topjia.music.request.entity.errordto.ErrorDTO;
import com.topjia.music.request.security.RequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wjh
 * @date 2020-06-06 11:29
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionErrorHandler {
    @ExceptionHandler(RequestException.class)
    public ErrorDTO error(RequestException e) {
        log.warn("发生RequestException异常", e);
        return ErrorDTO.builder()
                .code(500)
                .message("请求超时")
                .build();
    }
}
