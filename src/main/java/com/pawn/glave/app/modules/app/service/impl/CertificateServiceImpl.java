package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.Query;
import com.pawn.glave.app.modules.app.dao.AppraisalDao;
import com.pawn.glave.app.modules.app.dao.CertificateDao;
import com.pawn.glave.app.modules.app.entity.AppraisalPojo;
import com.pawn.glave.app.modules.app.entity.CertificatePojo;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;
import com.pawn.glave.app.modules.app.service.AppraisalService;
import com.pawn.glave.app.modules.app.service.CertificateService;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CertificateServiceImpl extends ServiceImpl<CertificateDao, CertificatePojo> implements CertificateService {

    @Resource
    private CertificateDao certificateDao;

    @Override
    public List<Map<String,Object>> getCertificateListByMiniUser(Long userId) {
        return certificateDao.getCertificateListByMiniUser(userId);
    }

    @Override
    public List<CertificatePojo> queryLtCertificate(String name, String phone, String createTime) {
        return certificateDao.queryLtCertificate(name,phone,createTime);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String condition = MapUtils.getString(params,"condition");
        LambdaQueryWrapper<CertificatePojo> queryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(condition)){
            queryWrapper.like(CertificatePojo::getCode,condition);
        }
        queryWrapper.orderByDesc(CertificatePojo::getId);
        IPage<CertificatePojo> ipage = this.page(new Query<CertificatePojo>().getPage(params), queryWrapper);
        return new PageUtils(ipage);
    }
}
