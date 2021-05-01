package com.pawn.glave.app.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.modules.sys.entity.SysDictEntity;

import java.util.Map;

/**
 * 字典service
 *
 */
public interface SysDictService extends IService<SysDictEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
