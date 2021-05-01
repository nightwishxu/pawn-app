package com.pawn.glave.app.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.modules.app.entity.AppraisalPojo;
import com.pawn.glave.app.modules.app.entity.CertificatePojo;

import java.util.List;
import java.util.Map;

public interface CertificateService extends IService<CertificatePojo> {


    List<Map<String,Object>> getCertificateListByMiniUser(Long userId);

    List<CertificatePojo> queryLtCertificate(String name, String phone, String createTime);

    PageUtils queryPage(Map<String,Object> param);
}
