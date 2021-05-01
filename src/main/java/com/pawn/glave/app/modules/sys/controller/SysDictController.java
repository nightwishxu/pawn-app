package com.pawn.glave.app.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pawn.glave.app.common.annotation.SysLog;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.common.validator.ValidatorUtils;
import com.pawn.glave.app.modules.sys.entity.SysDictEntity;
import com.pawn.glave.app.modules.sys.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 字典controller
 **/
@RestController
@RequestMapping("/sys/dict")
public class SysDictController extends AbstractController {
	@Autowired
	private SysDictService sysDictService;
	
	/**
	 * 所有字典值列表
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = sysDictService.queryPage(params);
		return R.ok().put("page", page);
	}


	/**
	 * 配置信息
	 */
	@GetMapping("/info/{id}")
	public R info(@PathVariable("id") Long id){
		SysDictEntity dict = sysDictService.getById(id);
		return R.ok().put("dict", dict);
	}

	@SysLog("保存字典")
	@PostMapping("/save")
	public R save(@RequestBody SysDictEntity sysDictEntity){
		ValidatorUtils.validateEntity(sysDictEntity);
		//字典值不能重复
		SysDictEntity dict = sysDictService.getOne(
				new QueryWrapper<SysDictEntity>()
						.eq("type_id",sysDictEntity.getTypeId())
						.eq("value",sysDictEntity.getValue())
		);
		if (dict != null) {
			return R.error(1,"已存在相同的字典值！");
		}else {
			sysDictService.save(sysDictEntity);
			return R.ok();
		}
	}



	@SysLog("更新字典")
	@PostMapping("/update")
	public R update(@RequestBody SysDictEntity sysDictEntity){
		ValidatorUtils.validateEntity(sysDictEntity);
		//字典值不能重复
		SysDictEntity dict = sysDictService.getOne(
				new QueryWrapper<SysDictEntity>()
						.eq("type_id",sysDictEntity.getTypeId())
						.eq("value",sysDictEntity.getValue())
						.ne("id",sysDictEntity.getId())
		);
		if (dict != null) {
			return R.error(1,"已存在相同的字典值！");
		}else {
			sysDictService.updateById(sysDictEntity);
			return R.ok();
		}
	}

	@SysLog("删除字典类型")
	@PostMapping("/delete")
	public R delete(@RequestBody Long[] ids){
		sysDictService.remove(new QueryWrapper<SysDictEntity>().in("id",ids));
		return R.ok();
	}
}



