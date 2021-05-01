package com.pawn.glave.app.modules.certificate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.modules.certificate.entity.CirculationRecord;

import java.util.Map;

public interface CirculationRecordService extends IService<CirculationRecord> {
    PageUtils queryPage(Map<String,Object> param);
}
