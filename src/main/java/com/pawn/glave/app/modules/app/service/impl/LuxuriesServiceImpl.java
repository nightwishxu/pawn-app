package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.modules.app.dao.JadeiteDao;
import com.pawn.glave.app.modules.app.dao.LuxuriesDao;
import com.pawn.glave.app.modules.app.entity.JadeitePojo;
import com.pawn.glave.app.modules.app.entity.LuxuriesPojo;
import com.pawn.glave.app.modules.app.service.JadeiteService;
import com.pawn.glave.app.modules.app.service.LuxuriesService;
import org.springframework.stereotype.Service;

@Service
public class LuxuriesServiceImpl extends ServiceImpl<LuxuriesDao, LuxuriesPojo> implements LuxuriesService {
}
