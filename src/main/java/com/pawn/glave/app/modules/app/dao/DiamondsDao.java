package com.pawn.glave.app.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pawn.glave.app.modules.app.entity.DiamondsPojo;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DiamondsDao extends BaseMapper<DiamondsPojo> {
}
