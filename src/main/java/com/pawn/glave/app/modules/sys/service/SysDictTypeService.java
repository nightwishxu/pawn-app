package com.pawn.glave.app.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.modules.sys.entity.SysDictTypeEntity;

import java.util.Map;

/**
 * 字典类型service
 *
 */
public interface SysDictTypeService extends IService<SysDictTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
