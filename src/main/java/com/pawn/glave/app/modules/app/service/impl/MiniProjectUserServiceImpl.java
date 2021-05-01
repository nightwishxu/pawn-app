package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.Query;
import com.pawn.glave.app.modules.app.dao.MiniProjectUserDao;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;
import com.pawn.glave.app.modules.app.entity.MiniProjectUser;
import com.pawn.glave.app.modules.app.service.MiniProjectUserService;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MiniProjectUserServiceImpl extends ServiceImpl<MiniProjectUserDao, MiniProjectUser> implements MiniProjectUserService {
    @Override
    public PageUtils queryPage(Map<String, Object> param,String state) {
        String name = MapUtils.getString(param,"name");
        String phone =  MapUtils.getString(param,"phone");
        IPage<MiniProjectUser> page = this.page( new Query<MiniProjectUser>().getPage(param),
                new QueryWrapper<MiniProjectUser>().eq("state",state).
                        like(StringUtils.isNotBlank(name),"name",name).
                        like(StringUtils.isNotBlank(phone),"phone",phone));
        return new PageUtils(page);
    }
}
