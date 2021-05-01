package com.pawn.glave.app.modules.app.controller;

import com.pawn.glave.app.modules.app.entity.AppraisalTypePojo;
import com.pawn.glave.app.modules.app.service.AppraisalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appraisal/type")
public class AppraisalTypeController {

    @Autowired
    private AppraisalTypeService appraisalTypeService;

    @GetMapping("/list")
    public List<AppraisalTypePojo> appraisalTypePojoList(){
        return appraisalTypeService.list();
    }


}
