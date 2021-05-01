package com.pawn.glave.app.modules.certificate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.modules.certificate.entity.FinancialRecord;

import java.util.Map;

public interface FinancialRecordService extends IService<FinancialRecord> {
    PageUtils queryPage(Map<String, Object> params);
}
