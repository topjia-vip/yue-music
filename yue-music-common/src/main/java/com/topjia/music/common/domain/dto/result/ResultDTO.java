package com.topjia.music.common.domain.dto.result;

import lombok.*;

/**
 * 返回结果封装的对象
 *
 * @author wjh
 * @date 2020-06-03 11:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultDTO {
    /**
     * 本次操作的返回状态码
     */
    private int status;

    /**
     * 返回的原因
     */
    private String message;

    /**
     * 成功,返回的数据
     */
    private Object data;
}
