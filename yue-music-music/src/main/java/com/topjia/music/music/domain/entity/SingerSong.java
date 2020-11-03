package com.topjia.music.music.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.*;

/**
 * <p>
 *
 * </p>
 *
 * @author topjia
 * @since 2020-06-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class SingerSong implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 歌曲mid
     */
    private String songMid;

    /**
     * 歌手mid
     */
    private String singerMid;


}
