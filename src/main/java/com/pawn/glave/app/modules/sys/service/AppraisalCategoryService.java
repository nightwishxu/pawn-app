package com.pawn.glave.app.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pawn.glave.app.modules.sys.entity.AppraisalCategoryEntity;

import java.math.BigDecimal;

public interface AppraisalCategoryService extends IService<AppraisalCategoryEntity> {
    BigDecimal getPriceById(long id);
}
