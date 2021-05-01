package com.pawn.glave.app.modules.certificate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.Query;
import com.pawn.glave.app.modules.certificate.dao.FinancialRecordDao;
import com.pawn.glave.app.modules.certificate.entity.CirculationRecord;
import com.pawn.glave.app.modules.certificate.entity.FinancialRecord;
import com.pawn.glave.app.modules.certificate.service.FinancialRecordService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FinancialRecordServiceImpl extends ServiceImpl<FinancialRecordDao, FinancialRecord> implements FinancialRecordService {
    @Override
    public PageUtils queryPage(Map<String, Object> param) {
        LambdaQueryWrapper<FinancialRecord> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(FinancialRecord::getCertificateId, param.get("certificateId"));
        IPage<FinancialRecord> ipage = this.page(new Query<FinancialRecord>().getPage(param), queryWrapper);
        return new PageUtils(ipage);
    }
}
