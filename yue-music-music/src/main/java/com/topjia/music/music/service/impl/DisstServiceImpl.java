package com.topjia.music.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.topjia.music.music.dao.mapper.DisstMapper;
import com.topjia.music.music.dao.mapper.UserLikeDisstsMapper;
import com.topjia.music.music.domain.entity.*;
import com.topjia.music.music.service.DisstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author topjia
 * @since 2020-06-10
 */
@Service
@Slf4j
public class DisstServiceImpl extends ServiceImpl<DisstMapper, Disst> implements DisstService {
    @Autowired
    private DisstMapper disstMapper;

    @Autowired
    private UserLikeDisstsMapper userLikeDisstsMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveDisst(Disst disst, Integer id) {
        // 首先查询数据库是否存在歌单
        saveDisst(disst);
        // 往用户歌单关系表插入数据
        QueryWrapper<UserLikeDissts> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(i -> i.eq("user_id", id).eq("disst_id", disst.getDisstId()));
        UserLikeDissts userLikeDissts = this.userLikeDisstsMapper.selectOne(queryWrapper);
        if (userLikeDissts == null) {
            log.info("用户{}收藏歌单{}", id, userLikeDissts);
            return this.userLikeDisstsMapper.saveUserLikeDisst(id, disst.getDisstId());
        }
        return 1;
    }

    @Override
    public List<Disst> selectAllByUserId(Integer id) {
        return this.disstMapper.selectAllByUserId(id);
    }

    @Override
    public int deleteDisstByDisstId(Integer id, String disstId) {
        try {
            int delete = userLikeDisstsMapper.deleteDisst(id, disstId);
            log.info("删除的行数{}", delete);
            return delete;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int saveDisst(Disst disst) {
        // 查询歌单
        Disst disstById = this.disstMapper.selectOne(new QueryWrapper<Disst>().eq("disst_id", disst.getDisstId()));
        if (disstById == null) {
            // 数据库没有保存此歌单
            return this.disstMapper.insert(disst);
        }
        return 0;
    }
}
