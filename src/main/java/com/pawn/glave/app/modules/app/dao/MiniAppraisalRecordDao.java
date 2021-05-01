package com.pawn.glave.app.modules.app.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MiniAppraisalRecordDao {
    /**
     * 验证用户身份
     * @param phoneNumber
     * @param number
     */
    Integer verifyStatus(String phoneNumber, String number);
}
