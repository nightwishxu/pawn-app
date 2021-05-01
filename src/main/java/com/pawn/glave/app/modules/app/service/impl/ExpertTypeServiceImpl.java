package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.modules.app.dao.ExpertTypeDao;
import com.pawn.glave.app.modules.app.dao.ExpertsDao;
import com.pawn.glave.app.modules.app.entity.AppraisalTypePojo;
import com.pawn.glave.app.modules.app.entity.ExpertTypePojo;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;
import com.pawn.glave.app.modules.app.service.AppraisalTypeService;
import com.pawn.glave.app.modules.app.service.ExpertTypeService;
import com.pawn.glave.app.modules.app.service.ExpertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpertTypeServiceImpl extends ServiceImpl<ExpertTypeDao,ExpertTypePojo> implements ExpertTypeService {

    @Autowired
    private AppraisalTypeService appraisalTypeService;

    @Override
    public List<ExpertTypePojo> getExpertTypeByCode(String code) {
        List<ExpertTypePojo> appraisalTypePojos = this.list(new QueryWrapper<ExpertTypePojo>().eq("expert_code",code));
        for (ExpertTypePojo expertTypePojo: appraisalTypePojos) {
            String name = appraisalTypeService.getOne(new QueryWrapper<AppraisalTypePojo>().eq("code",expertTypePojo.getAppraisalTypeCode())).getName();
            expertTypePojo.setAppraisalTypeName(name);
        }
        return appraisalTypePojos;
    }
}
