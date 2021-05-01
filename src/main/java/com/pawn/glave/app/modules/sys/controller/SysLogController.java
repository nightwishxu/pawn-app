package com.pawn.glave.app.modules.sys.controller;

import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.modules.sys.entity.SysLogEntity;
import com.pawn.glave.app.modules.sys.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 系统日志
 **/
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@PostMapping("/list")
	@RequiresPermissions("sys:log:list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = sysLogService.queryPage(params);

		return R.ok().put("page", page);
	}

	@ResponseBody
	@GetMapping("/info/{logId}")
	public R info(@PathVariable("logId") Long logId){
		SysLogEntity log = sysLogService.getById(logId);
		return R.ok().put("log", log);
	}
}
