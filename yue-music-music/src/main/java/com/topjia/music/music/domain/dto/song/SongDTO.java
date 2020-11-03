package com.topjia.music.music.domain.dto.song;

import com.topjia.music.music.domain.entity.Singer;
import io.swagger.models.auth.In;
import lombok.*;

import java.util.List;

/**
 * @author wjh
 * @date 2020-06-07 21:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongDTO {
    private Integer id;
    private String mid;
    private String mediaMid;
    private List<Singer> singers;
    private String name;
    private String album;
    private String duration;
    private String image;
    private String playUrl;
    private String title;
    private String subTitle;
}
