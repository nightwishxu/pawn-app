package com.pawn.glave.app.modules.app.service.impl;

import cn.hutool.core.lang.ObjectId;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.Query;
import com.pawn.glave.app.modules.app.dao.AppraisalDao;
import com.pawn.glave.app.modules.app.dao.ExpertsDao;
import com.pawn.glave.app.modules.app.entity.AppraisalPojo;
import com.pawn.glave.app.modules.app.entity.ExpertTypePojo;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;
import com.pawn.glave.app.modules.app.service.AppraisalService;
import com.pawn.glave.app.modules.app.service.ExpertTypeService;
import com.pawn.glave.app.modules.app.service.ExpertsService;
import com.pawn.glave.app.modules.sys.entity.SysUserEntity;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExpertsServiceImpl extends ServiceImpl<ExpertsDao,ExpertsPojo> implements ExpertsService {
    @Resource
    private ExpertsDao expertsDao;
    @Autowired
    private ExpertTypeService expertTypeService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = MapUtils.getString(params,"name");
        String phone =  MapUtils.getString(params,"phone");
        IPage iPage = new Query<ExpertsPojo>().getPage(params);

        IPage<ExpertsPojo> page = expertsDao.findPage(iPage,name,phone);

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void save(Map<String, Object> params) {
        String phone = MapUtils.getString(params,"phone");
        String password = MapUtils.getString(params,"password");
        String name = MapUtils.getString(params,"name");
        String types = MapUtils.getString(params,"types");
        String code = ObjectId.next().toUpperCase();//专家编号
        ExpertsPojo expertsPojo = ExpertsPojo.builder().code(code).name(name)
                .phone(phone).headImg("/images/default.png").password(DigestUtil.md5Hex(password)).build();
        this.save(expertsPojo);
        String[] typeArr = types.split(",");
        List<ExpertTypePojo> expertTypePojos = new ArrayList<>();
        for(String type : typeArr){
            ExpertTypePojo expertTypePojo = ExpertTypePojo.builder().expertCode(code).appraisalTypeCode(type).build();
            expertTypePojos.add(expertTypePojo);
        }
        expertTypeService.saveBatch(expertTypePojos);
    }

    @Override
    @Transactional
    public void update(Map<String, Object> params){
        String password = MapUtils.getString(params,"password");
        String name = MapUtils.getString(params,"name");
        String types = MapUtils.getString(params,"types");
        String code = MapUtils.getString(params,"code");
        Long id = MapUtils.getLong(params,"id");
        ExpertsPojo expertsPojo = ExpertsPojo.builder().id(id).name(name)
                .password(DigestUtil.md5Hex(password)).build();
        this.updateById(expertsPojo);
        expertTypeService.remove(new QueryWrapper<ExpertTypePojo>().eq("expert_code",code));
        List<ExpertTypePojo> expertTypePojos = new ArrayList<>();
        String[] typeArr = types.split(",");
        for(String type : typeArr){
            ExpertTypePojo expertTypePojo = ExpertTypePojo.builder().expertCode(code).appraisalTypeCode(type).build();
            expertTypePojos.add(expertTypePojo);
        }
        expertTypeService.saveBatch(expertTypePojos);
    }

    @Override
    public List<Map<String,Object>> listByAppraisalType(String code) {

        return expertsDao.listByAppraisalType(code);
    }
}
