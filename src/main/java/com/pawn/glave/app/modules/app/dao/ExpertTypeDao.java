package com.pawn.glave.app.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pawn.glave.app.modules.app.entity.ExpertTypePojo;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExpertTypeDao extends BaseMapper<ExpertTypePojo> {
}
