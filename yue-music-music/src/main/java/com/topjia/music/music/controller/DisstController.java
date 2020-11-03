package com.topjia.music.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.topjia.music.common.domain.dto.result.BaseResult;
import com.topjia.music.common.domain.dto.result.ResultDTO;
import com.topjia.music.common.domain.enums.ResultEnum;
import com.topjia.music.common.util.AesDecryptUtil;
import com.topjia.music.music.auth.CheckLogin;
import com.topjia.music.music.domain.dto.user.UserDTO;
import com.topjia.music.music.domain.entity.Disst;
import com.topjia.music.music.service.DisstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>O
 *
 * @author topjia
 * @since 2020-06-10
 */
@CrossOrigin
@RestController
@RequestMapping("/music/disst")
@Slf4j
public class DisstController {
    @Autowired
    private DisstService disstService;

    /**
     * 用户收藏歌单
     *
     * @param reqData 前端请求参数
     * @param request request
     * @return BaseResult
     */
    @PostMapping("/save")
    @CheckLogin
    public BaseResult saveDisst(String reqData, HttpServletRequest request) {
        UserDTO userDTO = (UserDTO) request.getAttribute("user");
        // 解密数据
        AesDecryptUtil decryptUtil = new AesDecryptUtil(reqData);
        JSONObject disstDTOJSON = JSONObject.parseObject(decryptUtil.getAesDecrypt());
        Disst disst = Disst.builder()
                .disstId(disstDTOJSON.getString("disstId"))
                .disstDesc(disstDTOJSON.getString("desc"))
                .disstLogo(disstDTOJSON.getString("disstLogo"))
                .disstName(disstDTOJSON.getString("disstName"))
                .headUrl(disstDTOJSON.getString("headUrl"))
                .nickname(disstDTOJSON.getString("nickname"))
                .songNum(disstDTOJSON.getInteger("songNum"))
                .visitnum(disstDTOJSON.getInteger("visitnum"))
                .build();
        log.info("用户{}将要保存的歌单为{}", userDTO, disst);
        disstService.saveDisst(disst, userDTO.getId());
        // 重新查询出所有的该用户收藏的歌单
        List<Disst> dissts = this.userFavoriteDissts(userDTO.getId());
        return BaseResult.builder()
                .code(0)
                .data(
                        ResultDTO.builder()
                                .data(dissts)
                                .status(ResultEnum.SUCCESS.getCode())
                                .message(ResultEnum.SUCCESS.getDesc())
                                .build()
                )
                .build();
    }

    @PostMapping("/delete")
    @CheckLogin
    public BaseResult deleteDisst(String reqData, HttpServletRequest request) {
        UserDTO userDTO = (UserDTO) request.getAttribute("user");
        AesDecryptUtil decryptUtil = new AesDecryptUtil(reqData);
        String disstId = decryptUtil.getAesDecrypt();
        log.info("用户{}取消收藏的歌单ID为{}", userDTO, disstId);
        this.disstService.deleteDisstByDisstId(userDTO.getId(), disstId);
        BaseResult baseResult = new BaseResult();
        ResultDTO resultDTO = new ResultDTO();
        // 删除成功
        // 重新查询用户收藏的歌曲
        List<Disst> dissts = this.userFavoriteDissts(userDTO.getId());
        resultDTO.setMessage(ResultEnum.SUCCESS.getDesc());
        resultDTO.setStatus(ResultEnum.SUCCESS.getCode());
        resultDTO.setData(dissts);
        baseResult.setCode(0);
        baseResult.setData(resultDTO);
        return baseResult;
    }

    /**
     * 根据用户id 获取其所有收藏的歌单信息
     *
     * @param id 用户id
     * @return List<Disst>
     */
    @GetMapping("/{id}")
    public List<Disst> userFavoriteDissts(@PathVariable("id") Integer id) {
        log.info("查询用户ID为{}收藏的歌单", id);
        return disstService.selectAllByUserId(id);
    }
}

