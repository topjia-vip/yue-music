package com.topjia.music.request.entity.errordto;

import lombok.*;

/**
 * @author wjh
 * @date 2020-06-23 11:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO {
    /**
     * 错误代码
     */
    private Integer code;
    /**
     * 错误消息
     */
    private String message;
}
