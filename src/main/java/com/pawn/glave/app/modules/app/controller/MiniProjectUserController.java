package com.pawn.glave.app.modules.app.controller;

import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.modules.app.entity.MiniProjectUser;
import com.pawn.glave.app.modules.app.service.MiniProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/mini/user")
public class MiniProjectUserController {
    @Autowired
    private MiniProjectUserService miniProjectUserService;

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = miniProjectUserService.queryPage(params,"0");
        return R.ok().put("page", page);
    }

    @RequestMapping("/recycleBin")
    public R recycleBin(@RequestParam Map<String, Object> params){
        PageUtils page = miniProjectUserService.queryPage(params,"1");
        return R.ok().put("page", page);
    }

    @GetMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id){
        MiniProjectUser miniProjectUser = MiniProjectUser.builder().id(id).state("1").build();
        miniProjectUserService.updateById(miniProjectUser);
        return R.ok();
    }

    @GetMapping("/recovery/{id}")
    public R recovery(@PathVariable("id") Long id){
        MiniProjectUser miniProjectUser = MiniProjectUser.builder().id(id).state("0").build();
        miniProjectUserService.updateById(miniProjectUser);
        return R.ok();
    }
}
