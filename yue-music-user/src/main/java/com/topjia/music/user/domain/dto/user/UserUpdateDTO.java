package com.topjia.music.user.domain.dto.user;

import lombok.*;

/**
 * @author wjh
 * @date 2020-06-06 18:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDTO {
    private Integer id;
    private String userNick;
    private String userDesc;
    private Integer userSex;
}
