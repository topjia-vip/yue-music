package com.topjia.music.music.auth;

import com.topjia.music.common.domain.dto.result.ResultDTO;
import com.topjia.music.common.domain.enums.ResultEnum;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wjh
 * @date 2020-06-06 11:29
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionErrorHandler {
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorBody> error(SecurityException e) {
        log.warn("发生SecurityException异常", e);
        String status = e.getMessage();
        ErrorBody errorBody = getErrorBody(status);
        return new ResponseEntity<>(
                errorBody,
                errorBody.getStatus()
        );
    }

    private ErrorBody getErrorBody(String status) {
        ErrorBody errorBody = null;
        if (String.valueOf(ResultEnum.UPLOAD_IMAGE_ERROR.getCode()).equals(status)) {
            // 图片上传错误
            errorBody = ErrorBody.builder()
                    .code(0)
                    .data(ResultDTO.builder()
                            .status(ResultEnum.UPLOAD_IMAGE_ERROR.getCode())
                            .message(ResultEnum.UPLOAD_IMAGE_ERROR.getDesc())
                            .build())
                    .status(HttpStatus.OK)
                    .build();
        } else if (String.valueOf(ResultEnum.TOKEN_CHECK_ERROR.getCode()).equals(status)) {
            // token校验异常
            errorBody = ErrorBody.builder()
                    .code(2000)
                    .data(ResultDTO.builder()
                            .status(ResultEnum.TOKEN_CHECK_ERROR.getCode())
                            .message(ResultEnum.TOKEN_CHECK_ERROR.getDesc())
                            .build())
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        } else if (String.valueOf(ResultEnum.REQUEST_PARAM_ERROR.getCode()).equals(status)) {
            // 请求参数错误
            errorBody = ErrorBody.builder()
                    .code(0)
                    .data(ResultDTO.builder()
                            .status(ResultEnum.REQUEST_PARAM_ERROR.getCode())
                            .message(ResultEnum.REQUEST_PARAM_ERROR.getDesc())
                            .build())
                    .status(HttpStatus.OK)
                    .build();
        } else if (String.valueOf(ResultEnum.TELEPHONE_NUM_ERROR.getCode()).equals(status)) {
            // 手机号格式错误
            errorBody = ErrorBody.builder()
                    .code(0)
                    .data(ResultDTO.builder()
                            .status(ResultEnum.TELEPHONE_NUM_ERROR.getCode())
                            .message(ResultEnum.TELEPHONE_NUM_ERROR.getDesc())
                            .build())
                    .status(HttpStatus.OK)
                    .build();
        } else if (String.valueOf(ResultEnum.PASSWORD_ERROR.getCode()).equals(status)) {
            // 密码验证错误
            errorBody = ErrorBody.builder()
                    .code(0)
                    .data(ResultDTO.builder()
                            .status(ResultEnum.PASSWORD_ERROR.getCode())
                            .message(ResultEnum.PASSWORD_ERROR.getDesc())
                            .build())
                    .status(HttpStatus.OK)
                    .build();
        } else if (String.valueOf(ResultEnum.HAVE_USER.getCode()).equals(status)) {
            // 注册时用户已存在错误
            errorBody = ErrorBody.builder()
                    .code(0)
                    .data(ResultDTO.builder()
                            .status(ResultEnum.HAVE_USER.getCode())
                            .message(ResultEnum.HAVE_USER.getDesc())
                            .build())
                    .status(HttpStatus.OK)
                    .build();
        } else if (String.valueOf(ResultEnum.REGISTER_ERROR.getCode()).equals(status)) {
            // 注册时出现错误
            errorBody = ErrorBody.builder()
                    .code(0)
                    .data(ResultDTO.builder()
                            .status(ResultEnum.REGISTER_ERROR.getCode())
                            .message(ResultEnum.REGISTER_ERROR.getDesc())
                            .build())
                    .status(HttpStatus.OK)
                    .build();
        } else if (String.valueOf(ResultEnum.USER_NOT_REGISTER.getCode()).equals(status)) {
            // 登录用户未注册
            errorBody = ErrorBody.builder()
                    .code(0)
                    .data(ResultDTO.builder()
                            .status(ResultEnum.USER_NOT_REGISTER.getCode())
                            .message(ResultEnum.USER_NOT_REGISTER.getDesc())
                            .build())
                    .status(HttpStatus.OK)
                    .build();
        } else if (String.valueOf(ResultEnum.ERROR.getCode()).equals(status)) {
            // 网络错误
            errorBody = ErrorBody.builder()
                    .code(1000)
                    .data(ResultDTO.builder()
                            .status(ResultEnum.ERROR.getCode())
                            .message(ResultEnum.ERROR.getDesc())
                            .build())
                    .status(HttpStatus.OK)
                    .build();
        }
        return errorBody;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class ErrorBody {
    private Integer code;
    private ResultDTO data;
    private HttpStatus status;
}
