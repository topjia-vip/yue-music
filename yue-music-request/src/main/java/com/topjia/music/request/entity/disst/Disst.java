package com.topjia.music.request.entity.disst;

import com.topjia.music.request.entity.song.Song;
import lombok.Data;

import java.util.List;

/**
 * 歌单实体类
 *
 * @author wjh
 * @date 2019-12-08 15:36
 */
@Data
public class Disst {
    /**
     * 歌单id
     */
    private String disstId;
    /**
     * 歌单名字
     */
    private String disstName;
    /**
     * 歌单图片
     */
    private String disstLogo;
    /**
     * 歌单歌曲列表
     */
    private List<Song> songlist;
    /**
     * 歌单创建人名字
     */
    private String nickname;
    /**
     * 创建人登录加密信息
     */
    private String encrypt_uin;
    /**
     * 歌单简介
     */
    private String desc;
    /**
     * 歌单播放量
     */
    private Integer visitnum;
    /**
     * 歌单标签
     */
    private List<Tag> tags;
    /**
     * 歌曲数量
     */
    private Integer songNum;
    /**
     * 创建人头像
     */
    private String headUrl;
}
