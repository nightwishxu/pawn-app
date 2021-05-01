package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.modules.app.dao.MetalDao;
import com.pawn.glave.app.modules.app.dao.NephriteDao;
import com.pawn.glave.app.modules.app.entity.MetalPojo;
import com.pawn.glave.app.modules.app.entity.NephritePojo;
import com.pawn.glave.app.modules.app.service.MetalService;
import com.pawn.glave.app.modules.app.service.NephriteService;
import org.springframework.stereotype.Service;

@Service
public class NephriteServiceImpl extends ServiceImpl<NephriteDao, NephritePojo> implements NephriteService {
}
