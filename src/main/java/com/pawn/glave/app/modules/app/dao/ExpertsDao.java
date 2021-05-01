package com.pawn.glave.app.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pawn.glave.app.modules.app.entity.AppraisalPojo;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExpertsDao extends BaseMapper<ExpertsPojo> {

    IPage<ExpertsPojo> findPage(@Param("iPage") IPage iPage, @Param("name") String name,@Param("phone") String phone);

    List<Map<String,Object>> listByAppraisalType(@Param("code") String code);
}
