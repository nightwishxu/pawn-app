package com.pawn.glave.app.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pawn.glave.app.modules.app.entity.AppraisalPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppraisalDao extends BaseMapper<AppraisalPojo> {
    IPage<Map<String, Object>> findPage(@Param("iPage") IPage iPage, @Param("classify") String classify, @Param("state") String state,@Param("userGoodsIds")List<Integer> list);
    List<Map<String, Object>> findList( @Param("classify") String classify, @Param("state") String state,@Param("userGoodsIds")List<Integer> list);

    List<Map<String, Object>> getUnidentifiedList(@Param("userCode") String userCode);

    List<Map<String, Object>> getIdentifiedList(@Param("userCode") String userCode);


    IPage<Map<String, Object>> getPageAppraisal(@Param("iPage") IPage iPage, @Param("userId") Long userId, @Param("state") String state, @Param("stateList") List<String> stateList);
}
