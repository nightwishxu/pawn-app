package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.Query;
import com.pawn.glave.app.modules.app.dao.AppraisalDao;
import com.pawn.glave.app.modules.app.dao.PaintingDao;
import com.pawn.glave.app.modules.app.entity.AppraisalPojo;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;
import com.pawn.glave.app.modules.app.entity.PaintingPojo;
import com.pawn.glave.app.modules.app.service.AppraisalService;
import com.pawn.glave.app.modules.app.service.PaintingService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class PaintingServiceImpl extends ServiceImpl<PaintingDao,PaintingPojo> implements PaintingService {

}
