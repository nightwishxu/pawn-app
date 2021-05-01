package com.pawn.glave.app.modules.certificate.controller;

import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.modules.app.service.CertificateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 证书
 */
@RestController
@RequestMapping("/sys/certificate")
public class CertificateController {
    @Resource
    private CertificateService certificateService;

    @GetMapping("/page")
    public R page(@RequestParam Map<String,Object> params){
        PageUtils pageUtils = certificateService.queryPage(params);
        return R.ok().put("page",pageUtils);
    }


}
