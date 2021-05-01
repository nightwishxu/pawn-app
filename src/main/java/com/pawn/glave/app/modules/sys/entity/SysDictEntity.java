package com.pawn.glave.app.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 字典实体类
 *
 */
@Data
@TableName("sys_dict")
public class SysDictEntity {
	@TableId
	private Long id;
	private String typeId;
	private String value;
	private String sort;
	private String cn;
	private String ext1;
	private String ext2;
	private String ext3;
	private String status;
	private String remark;

}
