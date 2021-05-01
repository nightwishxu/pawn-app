package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.Query;
import com.pawn.glave.app.modules.app.AgainConstant;
import com.pawn.glave.app.modules.app.dao.AppraisalDao;
import com.pawn.glave.app.modules.app.dao.ExpertAppraisalDao;
import com.pawn.glave.app.modules.app.entity.AppraisalPojo;
import com.pawn.glave.app.modules.app.entity.ExpertAppraisalPojo;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;
import com.pawn.glave.app.modules.app.service.AppraisalService;
import com.pawn.glave.app.modules.app.service.ExpertAppraisalService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ExpertAppraisalServiceImpl extends ServiceImpl<ExpertAppraisalDao, ExpertAppraisalPojo> implements ExpertAppraisalService {
    @Resource
    private ExpertAppraisalDao expertAppraisalDao;

    @Resource
    private AppraisalService appraisalService;

    @Override
    public List<Map<String, Object>> findList(String appraisalId, String name, String phone) {
        LambdaQueryWrapper<AppraisalPojo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppraisalPojo::getId, appraisalId);
        AppraisalPojo appraisalPojo = appraisalService.getOne(queryWrapper);

        if (AgainConstant.AgainEnum.FINISH_AGAIN_APPRAISAL.equals(appraisalPojo.getState())) {
            return expertAppraisalDao.findListByAppraisalId(appraisalId, name, phone);
        } else {
            return expertAppraisalDao.findList(appraisalId, name, phone);
        }
    }
}
