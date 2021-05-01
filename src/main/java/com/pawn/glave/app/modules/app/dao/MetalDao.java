package com.pawn.glave.app.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pawn.glave.app.modules.app.entity.LuxuriesPojo;
import com.pawn.glave.app.modules.app.entity.MetalPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MetalDao extends BaseMapper<MetalPojo> {
}
