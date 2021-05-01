package com.pawn.glave.app.modules.app.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.modules.app.entity.AppraisalPojo;
import com.pawn.glave.app.modules.app.entity.SendPojo;
import com.pawn.glave.app.modules.sys.entity.SysFileEntity;

import java.util.List;
import java.util.Map;

public interface SendService extends IService<SendPojo> {
    void send(SendPojo sendPojo);
    void send1(SendPojo sendPojo);

}
