package com.pawn.glave.app.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.modules.app.entity.AppraisalPojo;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;

import java.util.List;
import java.util.Map;

public interface ExpertsService extends IService<ExpertsPojo> {

    PageUtils queryPage(Map<String,Object> params);

    void save(Map<String,Object> params);

    void update(Map<String, Object> params);

    List<Map<String,Object>> listByAppraisalType(String code);
}
