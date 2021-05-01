package com.pawn.glave.app.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.modules.app.dao.RotationDao;
import com.pawn.glave.app.modules.app.entity.RotationPojo;
import com.pawn.glave.app.modules.app.service.RotationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/mini/project/rotation")
public class RotationController {

    @Resource
    private RotationDao rotationDao;

    @Resource
    private RotationService rotationService;

    @GetMapping("/save/{fileId}")
    public R save(@PathVariable long fileId){
        RotationPojo rotationPojo = new RotationPojo();
        rotationPojo.setCreatedTime(new Date());
//        rotationPojo.setFileId(fileId);
        rotationDao.insert(rotationPojo);
        return R.ok();
    }

    @GetMapping("/getNewFileId")
    public R getNewFileId(){
        List<RotationPojo> list = new ArrayList<>();
        RotationPojo rotationPojo = rotationDao.getNewFileId();
        list.add(rotationPojo);
        return R.ok().put("data",list);
    }

    @GetMapping("/appLists")
    public R appLists(){
        LambdaQueryWrapper<RotationPojo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(RotationPojo::getCurOrders);
        return R.ok().put("data", rotationDao.selectList(queryWrapper));
    }

    @GetMapping("/lists")
    public R lists(@RequestParam Map<String, Object> params){
        PageUtils page = rotationService.findPage(params);
        return R.ok().put("page", page);
    }

    @GetMapping("/info/{rotationId}")
    public R info(@PathVariable String rotationId){
        RotationPojo rotationPojo = rotationService.info(rotationId);
        return R.ok().put("data",rotationPojo);
    }

    @GetMapping("/delete/{rotationId}")
    public R delete(@PathVariable String rotationId){
        rotationService.delete(rotationId);
        return R.ok();
    }

    @PostMapping("/save")
    public R save(@RequestBody RotationPojo rotationPojo){
        rotationService.save(rotationPojo);
        return R.ok();
    }
}
