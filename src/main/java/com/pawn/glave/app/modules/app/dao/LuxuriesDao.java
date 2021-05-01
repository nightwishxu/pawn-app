package com.pawn.glave.app.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pawn.glave.app.modules.app.entity.JadeitePojo;
import com.pawn.glave.app.modules.app.entity.LuxuriesPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LuxuriesDao extends BaseMapper<LuxuriesPojo> {
}
