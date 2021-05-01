package com.pawn.glave.app.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.modules.sys.dao.AppraisalCategoryDao;
import com.pawn.glave.app.modules.sys.entity.AppraisalCategoryEntity;
import com.pawn.glave.app.modules.sys.service.AppraisalCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys/appraisalCategory")
public class AppraisalCategoryController {

    @Resource
    private AppraisalCategoryDao appraisalCategoryDao;

    @Resource
    private AppraisalCategoryService appraisalCategoryService;

    @GetMapping("/lists")
    public R lists() {
        LambdaQueryWrapper<AppraisalCategoryEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(AppraisalCategoryEntity::getId);
        List<AppraisalCategoryEntity> list = appraisalCategoryDao.selectList(queryWrapper);
        return R.ok().put("data", list);
    }

    @GetMapping("/save")
    public R save(long id, BigDecimal categoryPrice){
        LambdaUpdateWrapper<AppraisalCategoryEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AppraisalCategoryEntity::getId,id)
                .set(AppraisalCategoryEntity::getCategoryPrice,categoryPrice);
        appraisalCategoryService.update(updateWrapper);
        return R.ok();
    }
}
