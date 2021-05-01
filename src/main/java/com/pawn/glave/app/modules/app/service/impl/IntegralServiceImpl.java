package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.modules.app.dao.IntegralDao;
import com.pawn.glave.app.modules.app.entity.*;
import com.pawn.glave.app.modules.app.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class IntegralServiceImpl extends ServiceImpl<IntegralDao,IntegralPojo> implements IntegralService {

    @Resource
    private IntegralDao integralDao;

    @Override
    public String getTotalIntegral(String code) {
        return integralDao.getTotalIntegral(code);
    }
}
