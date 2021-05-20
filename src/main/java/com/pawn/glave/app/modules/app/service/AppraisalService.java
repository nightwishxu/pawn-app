package com.pawn.glave.app.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.modules.app.entity.AppraisalPojo;
import com.pawn.glave.app.modules.sys.entity.SysFileEntity;

import java.util.List;
import java.util.Map;

public interface AppraisalService extends IService<AppraisalPojo> {
    PageUtils findPage(Map<String,Object> params);

    List<Map<String,Object>> findList( String classify, String state,List<Integer> list);

    List<SysFileEntity> photoList(Long id);

    List<Map<String,Object>> getUnidentifiedList(String userCode);

    List<Map<String,Object>> getIdentifiedList(String userCode);

    PageUtils getPage(Map<String,Object> params);
}
