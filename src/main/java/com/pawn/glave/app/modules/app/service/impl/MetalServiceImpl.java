package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.modules.app.dao.LuxuriesDao;
import com.pawn.glave.app.modules.app.dao.MetalDao;
import com.pawn.glave.app.modules.app.entity.LuxuriesPojo;
import com.pawn.glave.app.modules.app.entity.MetalPojo;
import com.pawn.glave.app.modules.app.service.LuxuriesService;
import com.pawn.glave.app.modules.app.service.MetalService;
import org.springframework.stereotype.Service;

@Service
public class MetalServiceImpl extends ServiceImpl<MetalDao, MetalPojo> implements MetalService {
}
