package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.modules.app.dao.PayDao;
import com.pawn.glave.app.modules.app.entity.PayPojo;
import com.pawn.glave.app.modules.app.service.PayService;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl extends ServiceImpl<PayDao, PayPojo> implements PayService {

}
