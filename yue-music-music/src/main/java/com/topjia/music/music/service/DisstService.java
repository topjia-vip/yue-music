package com.topjia.music.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.topjia.music.music.domain.entity.Disst;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author topjia
 * @since 2020-06-10
 */
public interface DisstService extends IService<Disst> {

    int saveDisst(Disst disst, Integer id);

    List<Disst> selectAllByUserId(Integer id);

    int deleteDisstByDisstId(Integer id, String disstId);
}
