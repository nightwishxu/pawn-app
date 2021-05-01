package com.pawn.glave.app.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pawn.glave.app.modules.app.entity.AppraisalPojo;
import com.pawn.glave.app.modules.app.entity.CertificatePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CertificateDao extends BaseMapper<CertificatePojo> {

    List<Map<String,Object>> getCertificateListByMiniUser(@Param("userId") Long userId);

    List<CertificatePojo> queryLtCertificate(String name, String phone, String createTime);
}
