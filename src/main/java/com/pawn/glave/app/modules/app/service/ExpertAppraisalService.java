package com.pawn.glave.app.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.modules.app.entity.AppraisalPojo;
import com.pawn.glave.app.modules.app.entity.ExpertAppraisalPojo;

import java.util.List;
import java.util.Map;

public interface ExpertAppraisalService extends IService<ExpertAppraisalPojo> {
    List<Map<String,Object>> findList(String appraisalId,String name,String phone);
 }
