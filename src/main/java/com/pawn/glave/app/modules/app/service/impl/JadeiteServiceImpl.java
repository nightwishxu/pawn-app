package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.modules.app.dao.GemstoneDao;
import com.pawn.glave.app.modules.app.dao.JadeiteDao;
import com.pawn.glave.app.modules.app.entity.GemstonePojo;
import com.pawn.glave.app.modules.app.entity.JadeitePojo;
import com.pawn.glave.app.modules.app.service.GemstoneService;
import com.pawn.glave.app.modules.app.service.JadeiteService;
import org.springframework.stereotype.Service;

@Service
public class JadeiteServiceImpl extends ServiceImpl<JadeiteDao, JadeitePojo> implements JadeiteService {
}
