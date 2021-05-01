package com.pawn.glave.app.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.modules.app.entity.MiniProjectUser;

import java.util.Map;

public interface MiniProjectUserService extends IService<MiniProjectUser> {
    PageUtils queryPage(Map<String,Object> param,String state);
}
