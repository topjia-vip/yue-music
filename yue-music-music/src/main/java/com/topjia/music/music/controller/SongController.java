package com.topjia.music.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.topjia.music.common.domain.dto.result.BaseResult;
import com.topjia.music.common.domain.dto.result.ResultDTO;
import com.topjia.music.common.domain.enums.ResultEnum;
import com.topjia.music.common.util.AesDecryptUtil;
import com.topjia.music.music.auth.CheckLogin;
import com.topjia.music.music.domain.dto.song.SongDTO;
import com.topjia.music.music.domain.dto.user.UserDTO;
import com.topjia.music.music.domain.entity.Singer;
import com.topjia.music.music.domain.entity.Song;
import com.topjia.music.music.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author topjia
 * @since 2020-06-07
 */
@CrossOrigin
@RestController
@RequestMapping("/music/song")
@Slf4j
public class SongController {
    @Autowired
    private SongService songService;

    /**
     * 用户收藏歌曲
     *
     * @param reqData 请求参数
     * @param request request
     * @return BaseResult
     */
    @PostMapping("/save")
    @CheckLogin
    public BaseResult saveSong(String reqData, HttpServletRequest request) {
        UserDTO userDTO = (UserDTO) request.getAttribute("user");
        // 解密数据
        AesDecryptUtil decryptUtil = new AesDecryptUtil(reqData);
        JSONObject songDTOJSON = JSONObject.parseObject(decryptUtil.getAesDecrypt());
        Song song = Song.builder()
                .id(songDTOJSON.getInteger("id"))
                .mid(songDTOJSON.getString("mid"))
                .album(songDTOJSON.getString("album"))
                .name(songDTOJSON.getString("name"))
                .title(songDTOJSON.getString("title"))
                .subTitle(songDTOJSON.getString("subTitle"))
                .image(songDTOJSON.getString("image"))
                .duration(Integer.parseInt(songDTOJSON.getString("duration")))
                .mediaMid(songDTOJSON.getString("mediaMid"))
                .build();
        ArrayList<Singer> singers = new ArrayList<>();
        songDTOJSON.getJSONArray("singers").forEach(singer -> {
            JSONObject s = (JSONObject) singer;
            singers.add(Singer.builder()
                    .singerId(s.getInteger("singerId"))
                    .singerMid(s.getString("singerMid"))
                    .singerName(s.getString("singerName"))
                    .singerPic(s.getString("singerPic"))
                    .build());
        });
        log.info("保存的歌曲{}", song);
        this.songService.saveSong(song, singers, userDTO.getId());
        // 重新查询用户收藏的歌曲
        List<SongDTO> songDTOS = this.userFavoriteSongs(userDTO.getId());
        BaseResult baseResult = new BaseResult();
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ResultEnum.SUCCESS.getDesc());
        resultDTO.setStatus(ResultEnum.SUCCESS.getCode());
        resultDTO.setData(songDTOS);
        baseResult.setCode(0);
        baseResult.setData(resultDTO);
        return baseResult;
    }

    /**
     * 用户取消收藏
     *
     * @param reqData 请求数据
     * @param request request
     * @return BaseResult
     */
    @PostMapping("/delete")
    @CheckLogin
    public BaseResult deleteSong(String reqData, HttpServletRequest request) {
        UserDTO userDTO = (UserDTO) request.getAttribute("user");
        AesDecryptUtil decryptUtil = new AesDecryptUtil(reqData);
        String songID = decryptUtil.getAesDecrypt();
        log.info("用户{}取消收藏的歌曲ID为{}", userDTO, songID);
        int i = this.songService.deleteSongBySongId(userDTO.getId(), Integer.parseInt(songID));
        BaseResult baseResult = new BaseResult();
        ResultDTO resultDTO = new ResultDTO();
        if (i > 0) {
            // 删除成功
            // 重新查询用户收藏的歌曲
            List<SongDTO> songDTOS = this.userFavoriteSongs(userDTO.getId());
            resultDTO.setMessage(ResultEnum.SUCCESS.getDesc());
            resultDTO.setStatus(ResultEnum.SUCCESS.getCode());
            resultDTO.setData(songDTOS);
            baseResult.setCode(0);
            baseResult.setData(resultDTO);
        } else {
            // 删除失败
            resultDTO.setMessage(ResultEnum.SUCCESS.getDesc());
            resultDTO.setStatus(ResultEnum.SUCCESS.getCode());
            resultDTO.setData(null);
            baseResult.setCode(0);
            baseResult.setData(resultDTO);
        }
        return baseResult;
    }

    /**
     * 根据用户id 获取其所有收藏的歌曲信息
     *
     * @param id 用户id
     * @return List<SongDTO>
     */
    @GetMapping("/{id}")
    public List<SongDTO> userFavoriteSongs(@PathVariable("id") Integer id) {
        log.info("查询用户ID为{}收藏的歌曲", id);
        return songService.selectAllByUserId(id);
    }
}

