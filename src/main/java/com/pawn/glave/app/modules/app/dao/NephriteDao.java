package com.pawn.glave.app.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pawn.glave.app.modules.app.entity.MetalPojo;
import com.pawn.glave.app.modules.app.entity.NephritePojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NephriteDao extends BaseMapper<NephritePojo> {
}
