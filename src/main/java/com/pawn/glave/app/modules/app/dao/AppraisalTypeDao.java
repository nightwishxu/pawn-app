package com.pawn.glave.app.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pawn.glave.app.modules.app.entity.AppraisalPojo;
import com.pawn.glave.app.modules.app.entity.AppraisalTypePojo;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppraisalTypeDao extends BaseMapper<AppraisalTypePojo> {

}
