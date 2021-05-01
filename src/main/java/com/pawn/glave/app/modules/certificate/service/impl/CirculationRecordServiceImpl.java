package com.pawn.glave.app.modules.certificate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.Query;
import com.pawn.glave.app.modules.certificate.dao.CirculationRecordDao;
import com.pawn.glave.app.modules.certificate.entity.CirculationRecord;
import com.pawn.glave.app.modules.certificate.service.CirculationRecordService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CirculationRecordServiceImpl extends ServiceImpl<CirculationRecordDao, CirculationRecord> implements CirculationRecordService {
    @Override
    public PageUtils queryPage(Map<String, Object> param) {
        LambdaQueryWrapper<CirculationRecord> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(CirculationRecord::getCertificateId,param.get("certificateId"));
        IPage<CirculationRecord> ipage = this.page(new Query<CirculationRecord>().getPage(param), queryWrapper);
        return new PageUtils(ipage);
    }
}
