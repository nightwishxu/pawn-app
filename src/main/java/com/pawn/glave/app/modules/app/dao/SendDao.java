package com.pawn.glave.app.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pawn.glave.app.modules.app.entity.SendPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SendDao extends BaseMapper<SendPojo> {
}
