package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.modules.app.dao.GemstoneDao;
import com.pawn.glave.app.modules.app.entity.GemstonePojo;
import com.pawn.glave.app.modules.app.service.GemstoneService;
import org.springframework.stereotype.Service;

@Service
public class GemstoneServiceImpl extends ServiceImpl<GemstoneDao, GemstonePojo> implements GemstoneService {
}
