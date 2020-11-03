package com.topjia.music.user.domain.dto.user;

import lombok.*;

/**
 * @author wjh
 * @date 2020-06-04 20:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtTokenRespDTO {
    /**
     * token
     */
    private String token;
    /**
     * 过期时间
     */
    private Long expirationTime;
}
