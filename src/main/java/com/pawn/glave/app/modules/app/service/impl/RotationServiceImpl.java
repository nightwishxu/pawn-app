package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.Query;
import com.pawn.glave.app.modules.app.dao.RotationDao;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;
import com.pawn.glave.app.modules.app.entity.RotationPojo;
import com.pawn.glave.app.modules.app.service.RotationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class RotationServiceImpl implements RotationService {

    @Resource
    private RotationDao rotationDao;

    @Override
    public PageUtils findPage(Map<String, Object> params) {
        IPage iPage = new Query<RotationPojo>().getPage(params);

        IPage<Map<String,Object>> page = rotationDao.findPage(iPage);

        return new PageUtils(page);
    }

    /**
     * 保存
     *
     * @param rotationPojo
     */
    @Override
    public void save(RotationPojo rotationPojo) {
        if ("1".equals(rotationPojo.getJumpType()))rotationPojo.setJumpValue("");

        if (rotationPojo.getId() == null){
            /**
             * 新增
             */
            rotationPojo.setCreatedTime(new Date());
            rotationDao.save(rotationPojo);
        }else{
            /**
             * 修改
             */
            LambdaUpdateWrapper<RotationPojo> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(RotationPojo::getId,rotationPojo.getId())
                    .set(RotationPojo::getCoverType,rotationPojo.getCoverType())
                    .set(RotationPojo::getCoverValue,rotationPojo.getCoverValue())
                    .set(RotationPojo::getJumpType,rotationPojo.getJumpType())
                    .set(RotationPojo::getJumpValue,rotationPojo.getJumpValue())
                    .set(RotationPojo::getCurOrders,rotationPojo.getCurOrders())
                    .set(RotationPojo::getRotationTitle,rotationPojo.getRotationTitle());
            rotationDao.update(null,updateWrapper);
        }
    }

    /**
     * 获取轮播详情
     *
     * @param rotationId
     * @return
     */
    @Override
    public RotationPojo info(String rotationId) {
        return rotationDao.selectById(rotationId);
    }

    /**
     * 删除轮播图
     *
     * @param rotationId
     */
    @Override
    public void delete(String rotationId) {
        rotationDao.deleteById(rotationId);
    }
}
