package com.topjia.music.common.domain.dto.result;

import lombok.*;

/**
 * @author wjh
 * @date 2020-06-06 12:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResult {
    private ResultDTO data;
    private Integer code;
}
