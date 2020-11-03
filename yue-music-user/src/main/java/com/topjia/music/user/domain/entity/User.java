package com.topjia.music.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author topjia
 * @since 2020-08-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户随机码
     */
    @TableField("user_UUID")
    private String userUuid;

    /**
     * 用户呢称
     */
    private String userNick;

    /**
     * 用户头像URL
     */
    private String userHeaderUrl;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户邮箱
     */
    private String userEmil;

    /**
     * 用户手机号
     */
    private String userTelephoneNum;

    /**
     * 用户简介
     */
    private String userDesc;

    /**
     * 用户性别
     */
    private Integer userSex;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否禁用 0:否 1:禁用
     */
    private Integer banFlag;

    /**
     * 用户封禁时间
     */
    private LocalDateTime banTime;


}
