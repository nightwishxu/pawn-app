/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.pawn.glave.app.modules.sys.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.Query;
import com.pawn.glave.app.modules.sys.dao.SysLogDao;
import com.pawn.glave.app.modules.sys.entity.SysLogEntity;
import com.pawn.glave.app.modules.sys.service.SysLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        JSONObject dataForm = JSONObject.parseObject(String.valueOf(params.get("dataForm")));
        String username = dataForm.getString("username");
        String operation = dataForm.getString("operation");
        String method = dataForm.getString("method");
        String ip = dataForm.getString("ip");
        String url = dataForm.getString("url");
        String userAgent = dataForm.getString("userAgent");
        JSONArray createDateArr = dataForm.getJSONArray("createDate");
        String startDate = "";
        String endDate = "";
        if (null != createDateArr && createDateArr.size() == 2) {
            startDate = createDateArr.getString(0);
            endDate = createDateArr.getString(1);
        }

        IPage<SysLogEntity> page = this.page(
            new Query<SysLogEntity>().getPage(params),
            new QueryWrapper<SysLogEntity>()
                    .like(StringUtils.isNotBlank(username),"username", username)
                    .like(StringUtils.isNotBlank(operation),"operation", operation)
                    .like(StringUtils.isNotBlank(method),"method", method)
                    .like(StringUtils.isNotBlank(ip),"ip", ip)
                    .like(StringUtils.isNotBlank(url),"url", url)
                    .like(StringUtils.isNotBlank(userAgent),"userAgent", userAgent)
                    .between(null != createDateArr && createDateArr.size() == 2,"create_date",startDate,endDate)
                    .orderByDesc("create_date")
        );

        return new PageUtils(page);
    }
}
