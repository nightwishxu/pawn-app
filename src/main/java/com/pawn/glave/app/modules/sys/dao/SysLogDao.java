package com.pawn.glave.app.modules.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pawn.glave.app.modules.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 *
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
	
}
