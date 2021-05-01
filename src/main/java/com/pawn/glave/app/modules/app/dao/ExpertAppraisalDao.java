package com.pawn.glave.app.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pawn.glave.app.modules.app.entity.AppraisalPojo;
import com.pawn.glave.app.modules.app.entity.ExpertAppraisalPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExpertAppraisalDao extends BaseMapper<ExpertAppraisalPojo> {
    List<Map<String, Object>> findList(@Param("appraisalId") String appraisalId, @Param("name") String name,@Param("phone") String phone);

    /**
     *
     * @param appraisalId
     * @param name
     * @param phone
     * @return
     */
    List<Map<String, Object>> findListByAppraisalId(String appraisalId, String name, String phone);
}
