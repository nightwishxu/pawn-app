package com.pawn.glave.app.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pawn.glave.app.common.annotation.SysLog;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.common.validator.ValidatorUtils;
import com.pawn.glave.app.modules.sys.entity.SysDictTypeEntity;
import com.pawn.glave.app.modules.sys.service.SysDictTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 字典类型controller
 *
 */
@RestController
@RequestMapping("/sys/dictType")
public class SysDictTypeController extends AbstractController {
	@Autowired
	private SysDictTypeService sysDictTypeService;
	
	/**
	 * 所有字典类型列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:dictType:list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = sysDictTypeService.queryPage(params);

		return R.ok().put("page", page);
	}

	@SysLog("保存字典类型")
	@PostMapping("/save")
	public R save(@RequestBody SysDictTypeEntity sysDictTypeEntity){
		ValidatorUtils.validateEntity(sysDictTypeEntity);
		SysDictTypeEntity sysDictType = sysDictTypeService.getOne(
				new QueryWrapper<SysDictTypeEntity>()
						.eq("code",sysDictTypeEntity.getCode()));
		if(sysDictType!=null){
			return R.error(1,"已存在相同的唯一标识，请重新输入！");
		}else {
			sysDictTypeEntity.setTime(new Date());
			sysDictTypeService.save(sysDictTypeEntity);
			return R.ok();
		}
	}

	@SysLog("删除字典类型")
	@PostMapping("/delete")
	public R delete(@RequestBody Long[] ids){
		sysDictTypeService.remove(new QueryWrapper<SysDictTypeEntity>().in("id",ids));
		return R.ok();
	}

}
