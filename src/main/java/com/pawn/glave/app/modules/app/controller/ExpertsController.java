package com.pawn.glave.app.modules.app.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pawn.glave.app.common.exception.RRException;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.modules.app.entity.*;
import com.pawn.glave.app.modules.app.service.*;
import org.apache.commons.collections4.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/experts")
public class ExpertsController {
    @Autowired
    private ExpertsService expertsService;
    @Autowired
    private ExpertTypeService expertTypeService;
    @Autowired
    private AppraisalTypeService appraisalTypeService;
    @Autowired
    private ExpertAppraisalService expertAppraisalService;
    @Autowired
    private AppraisalService appraisalService;

    /**
     * 所有专家列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        //只有超级管理员，才能查看所有管理员列表
        PageUtils page = expertsService.queryPage(params);

        return R.ok().put("page", page);
    }


    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") Long id){
        ExpertsPojo expertsPojo = expertsService.getById(id);
        if(expertsPojo ==null){
            throw new RRException("不存在当前专家");
        }
        String code = expertsPojo.getCode();
        List<ExpertTypePojo> appraisalTypePojos = expertTypeService.list(new QueryWrapper<ExpertTypePojo>().eq("expert_code",code));
        return R.ok().put("expert",expertsPojo).put("types",appraisalTypePojos);
    }

    @PostMapping("/save")
    public R save(@RequestBody Map<String,Object> params){
        expertsService.save(params);
        return R.ok();
    }

    @PostMapping("/update")
    public R update(@RequestBody Map<String,Object> params){
        expertsService.update(params);
        return R.ok();
    }


    /**
     * 专家树
     * @return
     */
    @GetMapping("/expertTree")
    public List<Map<String,Object>> expertTree(){
        List<Map<String,Object>> appraisalTypePojoList = appraisalTypeService.listMaps();
        for(Map<String,Object> objectMap : appraisalTypePojoList){
            objectMap.put("isType",true);
            String code = MapUtils.getString(objectMap,"code");
            List<Map<String,Object>> expertsPojos = expertsService.listByAppraisalType(code);
            objectMap.put("children",expertsPojos);
        }
        return appraisalTypePojoList;
    }

    /**
     * 分配专家
     * @param params
     * @return
     */
    @PostMapping("/distribution")
    @Transactional
    public R distribution(@RequestBody Map<String,Object> params){
        String ids = MapUtils.getString(params,"ids");
        String appraisalId = MapUtils.getString(params,"appraisalId");
        AppraisalPojo app = appraisalService.getById(Long.valueOf(appraisalId));
        String[] idArr = ids.split(",");
        Date now = new Date();
        for(String id : idArr){
            if(id.indexOf("/")>-1){
                String[] experts = id.split("/");
                String expertCode = experts[1];
                ExpertAppraisalPojo expertAppraisalPojo = ExpertAppraisalPojo.builder().appraisalId(appraisalId).userGoodsId(app.getUserGoodsId())
                        .allocateTime(now).appraisalUser(expertCode).state("1").isSelection("0").build();
                expertAppraisalService.save(expertAppraisalPojo);
            }
        }
        AppraisalPojo appraisalPojo = AppraisalPojo.builder().id(Long.parseLong(appraisalId)).state("1").build();
        appraisalService.updateById(appraisalPojo);
        return R.ok();
    }

    @GetMapping("/result")
    public R result(@RequestParam Long id){
        ExpertAppraisalPojo expertAppraisalPojo = expertAppraisalService.getById(id);
        return R.ok().put("data",expertAppraisalPojo);
    }

}
