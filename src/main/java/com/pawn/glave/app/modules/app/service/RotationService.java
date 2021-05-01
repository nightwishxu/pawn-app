package com.pawn.glave.app.modules.app.service;

import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.modules.app.entity.RotationPojo;

import java.util.Map;

public interface RotationService {
    PageUtils findPage(Map<String, Object> params);

    /**
     *  保存
     */
    void save(RotationPojo rotationPojo);

    /**
     * 获取轮播详情
     * @param rotationId
     * @return
     */
    RotationPojo info(String rotationId);

    /**
     * 删除轮播图
     * @param rotationId
     */
    void delete(String rotationId);
}
