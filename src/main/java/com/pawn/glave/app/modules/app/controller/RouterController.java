package com.pawn.glave.app.modules.app.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/app")
public class RouterController {
    @GetMapping("/pdf")
    public ModelAndView pdf( @RequestParam Map<String,Object> map){
        ModelAndView modelAndView = new ModelAndView("/pdf");
        Set<String> sets = map.keySet();
        for(String set : sets){
            String value = (String)map.get(set);
            if(StringUtils.isNotBlank(value)){
                modelAndView.addObject(set,value);
            }
        }

        return modelAndView;
    }
}
