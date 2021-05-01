package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.modules.app.dao.ConfigDao;
import com.pawn.glave.app.modules.app.dao.KdDao;
import com.pawn.glave.app.modules.app.entity.ConfigPojo;
import com.pawn.glave.app.modules.app.entity.KdPojo;
import com.pawn.glave.app.modules.app.service.ConfigService;
import com.pawn.glave.app.modules.app.service.KdService;
import org.springframework.stereotype.Service;

@Service
public class KdServiceImpl extends ServiceImpl<KdDao, KdPojo> implements KdService {

}
