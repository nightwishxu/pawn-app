package com.pawn.glave.app.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pawn.glave.app.modules.app.entity.IntegralPojo;
import java.util.List;
import java.util.Map;

public interface IntegralService extends IService<IntegralPojo> {

    String getTotalIntegral(String code);
}
