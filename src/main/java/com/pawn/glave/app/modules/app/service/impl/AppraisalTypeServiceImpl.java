package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.Query;
import com.pawn.glave.app.modules.app.dao.AppraisalTypeDao;
import com.pawn.glave.app.modules.app.dao.ExpertsDao;
import com.pawn.glave.app.modules.app.entity.AppraisalTypePojo;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;
import com.pawn.glave.app.modules.app.service.AppraisalService;
import com.pawn.glave.app.modules.app.service.AppraisalTypeService;
import com.pawn.glave.app.modules.app.service.ExpertsService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class AppraisalTypeServiceImpl extends ServiceImpl<AppraisalTypeDao,AppraisalTypePojo> implements AppraisalTypeService {

}
