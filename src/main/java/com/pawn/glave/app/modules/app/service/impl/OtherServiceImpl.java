package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.modules.app.dao.JadeiteDao;
import com.pawn.glave.app.modules.app.dao.OtherDao;
import com.pawn.glave.app.modules.app.entity.JadeitePojo;
import com.pawn.glave.app.modules.app.entity.OtherPojo;
import com.pawn.glave.app.modules.app.service.JadeiteService;
import com.pawn.glave.app.modules.app.service.OtherService;
import org.springframework.stereotype.Service;

@Service
public class OtherServiceImpl extends ServiceImpl<OtherDao, OtherPojo> implements OtherService {
}
