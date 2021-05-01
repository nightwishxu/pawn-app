package com.pawn.glave.app.modules.app.controller;

import com.pawn.glave.app.common.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/hello")
public class HelloController {

    @GetMapping("/test")
    public R test(){
        return R.ok();
    }
}
