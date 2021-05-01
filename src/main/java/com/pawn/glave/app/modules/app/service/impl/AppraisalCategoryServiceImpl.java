package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.modules.sys.dao.AppraisalCategoryDao;
import com.pawn.glave.app.modules.sys.entity.AppraisalCategoryEntity;
import com.pawn.glave.app.modules.sys.service.AppraisalCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@Transactional
public class AppraisalCategoryServiceImpl extends ServiceImpl<AppraisalCategoryDao, AppraisalCategoryEntity> implements AppraisalCategoryService {
    @Resource
    private AppraisalCategoryDao appraisalCategoryDao;

    @Override
    public BigDecimal getPriceById(long id) {
        LambdaQueryWrapper<AppraisalCategoryEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppraisalCategoryEntity::getId, id);
        AppraisalCategoryEntity categoryEntity = appraisalCategoryDao.selectOne(queryWrapper);
        if (categoryEntity != null) {
            return categoryEntity.getCategoryPrice();
        } else {
            return null;
        }
    }
}
