package com.topjia.music.request.entity.search;

import com.topjia.music.request.entity.singer.Singer;
import com.topjia.music.request.entity.song.Song;
import lombok.Data;

import java.util.List;

/**
 * @author wjh
 * @date 2019-12-20 14:58
 */
@Data
public class SmartSearch {
    private List<Singer> singers;
    private List<Song> songs;
}
