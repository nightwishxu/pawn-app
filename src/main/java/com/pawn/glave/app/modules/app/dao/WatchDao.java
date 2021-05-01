package com.pawn.glave.app.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pawn.glave.app.modules.app.entity.NephritePojo;
import com.pawn.glave.app.modules.app.entity.WatchPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WatchDao extends BaseMapper<WatchPojo> {
}
