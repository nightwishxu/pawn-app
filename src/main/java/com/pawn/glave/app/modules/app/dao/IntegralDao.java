package com.pawn.glave.app.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pawn.glave.app.modules.app.entity.IntegralPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface IntegralDao extends BaseMapper<IntegralPojo> {

    @Select("select IFNULL(SUM(add_integral),'0') FROM mini_integral where expert_code = #{code}")
    String getTotalIntegral(@Param("code") String code);
}
