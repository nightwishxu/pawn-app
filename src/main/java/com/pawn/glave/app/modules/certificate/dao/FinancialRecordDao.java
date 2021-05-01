package com.pawn.glave.app.modules.certificate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pawn.glave.app.modules.certificate.entity.FinancialRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FinancialRecordDao extends BaseMapper<FinancialRecord> {
}
