package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.modules.app.dao.NephriteDao;
import com.pawn.glave.app.modules.app.dao.WatchDao;
import com.pawn.glave.app.modules.app.entity.NephritePojo;
import com.pawn.glave.app.modules.app.entity.WatchPojo;
import com.pawn.glave.app.modules.app.service.NephriteService;
import com.pawn.glave.app.modules.app.service.WatchService;
import org.springframework.stereotype.Service;

@Service
public class WatchServiceImpl extends ServiceImpl<WatchDao, WatchPojo> implements WatchService {
}
