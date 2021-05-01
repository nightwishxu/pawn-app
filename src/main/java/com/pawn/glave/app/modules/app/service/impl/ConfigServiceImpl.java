package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.Query;
import com.pawn.glave.app.modules.app.dao.AppraisalDao;
import com.pawn.glave.app.modules.app.dao.ConfigDao;
import com.pawn.glave.app.modules.app.entity.AppraisalPojo;
import com.pawn.glave.app.modules.app.entity.ConfigPojo;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;
import com.pawn.glave.app.modules.app.service.AppraisalService;
import com.pawn.glave.app.modules.app.service.ConfigService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigDao,ConfigPojo> implements ConfigService {

}
