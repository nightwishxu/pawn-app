package com.pawn.glave.app.modules.app.service;

import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.modules.app.entity.MiniProjectUser;

import java.math.BigDecimal;

public interface WxPayService {
    String getOpenId(String openId, String out_trade_no, int money, MiniProjectUser miniProjectUser,String appraisalCode);

    /**
     * 创建订单
     * @param miniProjectUser
     * @param appraisalCode
     * @return
     */
    R createOrder(MiniProjectUser miniProjectUser, String appraisalCode, BigDecimal payMoney);
}
