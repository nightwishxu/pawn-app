package com.pawn.glave.app.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pawn.glave.app.modules.app.entity.ExpertTypePojo;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;

import java.util.List;

public interface ExpertTypeService extends IService<ExpertTypePojo> {

    List<ExpertTypePojo> getExpertTypeByCode(String code);
}
