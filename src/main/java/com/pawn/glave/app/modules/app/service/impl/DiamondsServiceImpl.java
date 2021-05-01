package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.modules.app.dao.AppraisalTypeDao;
import com.pawn.glave.app.modules.app.dao.DiamondsDao;
import com.pawn.glave.app.modules.app.entity.AppraisalTypePojo;
import com.pawn.glave.app.modules.app.entity.DiamondsPojo;
import com.pawn.glave.app.modules.app.service.AppraisalTypeService;
import com.pawn.glave.app.modules.app.service.DiamondsService;
import org.springframework.stereotype.Service;

@Service
public class DiamondsServiceImpl extends ServiceImpl<DiamondsDao,DiamondsPojo> implements DiamondsService {

}
