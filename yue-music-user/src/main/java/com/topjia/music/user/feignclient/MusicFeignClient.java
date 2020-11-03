package com.topjia.music.user.feignclient;

import com.topjia.music.user.domain.dto.disst.DisstDTO;
import com.topjia.music.user.domain.dto.song.SongDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author wjh
 * @date 2020-06-09 10:52
 */
@FeignClient(name = "music-music")
public interface MusicFeignClient {
    @GetMapping("/music/song/{id}")
    List<SongDTO> userFavoriteSongs(@PathVariable("id") Integer id);

    @GetMapping("/music/disst/{id}")
    List<DisstDTO> userFavoriteDissts(@PathVariable("id") Integer id);
}
