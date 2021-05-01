package com.pawn.glave.app.modules.sys.controller;

import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.common.utils.ShowTree;
import com.pawn.glave.app.modules.sys.entity.SysDeptEntity;
import com.pawn.glave.app.modules.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends AbstractController {

    @Autowired
    private SysDeptService sysDeptService;

    @GetMapping("/list")
    public R list(){
        List<SysDeptEntity> deptEntityList = sysDeptService.deptList();
        List<SysDeptEntity> list = new ArrayList<>();
        for(SysDeptEntity treeMap : deptEntityList){
            if(!"0".equals(treeMap.getParentId())){
                continue;
            }
            SysDeptEntity tree = ShowTree.getDeptTree(treeMap, deptEntityList);
            list.add(tree);
        }
        return R.ok().put("data",list);
    }

    @GetMapping("/getParentInfo")
    public R getParentInfo(HttpServletRequest request){
        String id = request.getParameter("id");
        return R.ok().put("data",sysDeptService.getParentInfo(id));
    }

    @GetMapping("/getDeptInfo/{id}")
    public R getDeptInfo(@PathVariable String id){
        return R.ok().put("data",sysDeptService.getDeptInfo(id));
    }

    @PostMapping("/saveOrUpdate")
    public R save(@RequestBody Map<String,Object> dept){
        dept.put("create_user",getUserId());
        sysDeptService.saveOrUpdate(dept);
        return R.ok();
    }

    @PostMapping("/delete/{id}")
    public R delete(@PathVariable int id){
        sysDeptService.delete(id);
        return R.ok();
    }

    @GetMapping("/findUserByDept")
    public R list(@RequestParam Map<String, Object> params){
        //只有超级管理员，才能查看所有管理员列表
        PageUtils page = sysDeptService.findUserByDept(params);
        return R.ok().put("page", page);
    }
}
