package com.pawn.glave.app.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.Query;
import com.pawn.glave.app.modules.sys.dao.SysDictTypeDao;
import com.pawn.glave.app.modules.sys.entity.SysDictTypeEntity;
import com.pawn.glave.app.modules.sys.service.SysDictTypeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 字典类型实现类
 */

@Service("sysDictTypeService")
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeDao, SysDictTypeEntity> implements SysDictTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");
        IPage<SysDictTypeEntity> page = this.page(
                new Query<SysDictTypeEntity>().getPage(params),
                new QueryWrapper<SysDictTypeEntity>()
                        .like(StringUtils.isNotBlank(name),"name", name)
        );
        return new PageUtils(page);
    }
}
