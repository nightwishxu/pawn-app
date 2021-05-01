package com.pawn.glave.app.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.modules.app.entity.RotationPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface RotationDao extends BaseMapper<RotationPojo> {
    /**
     * 获取最新的轮播图片ID
     * @return
     */
    RotationPojo getNewFileId();

    IPage<Map<String, Object>> findPage(@Param("iPage") IPage iPage);


    /**
     * 保存轮播信息
     * @param rotationPojo
     */
    void save(@Param("param") RotationPojo rotationPojo);
}
