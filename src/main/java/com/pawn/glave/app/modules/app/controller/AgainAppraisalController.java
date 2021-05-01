package com.pawn.glave.app.modules.app.controller;

import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.modules.app.annotation.Login;
import com.pawn.glave.app.modules.app.param.AgainAppraisalSaveParam;
import com.pawn.glave.app.modules.app.service.AgainAppraisalService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 再次提交鉴定
 */
@Validated
@RestController
@RequestMapping("/app/mini/project/againAppraisal")
public class AgainAppraisalController {

    @Resource
    private AgainAppraisalService againAppraisalService;

    /**
     * 再次提交鉴定申请
     * @param param
     * @return
     */
    @Login
    @ApiOperation("再次提交鉴定申请")
    @PostMapping("/save")
    public R save(@Valid AgainAppraisalSaveParam param){
        againAppraisalService.save(param);
        return R.ok();
    }
}
