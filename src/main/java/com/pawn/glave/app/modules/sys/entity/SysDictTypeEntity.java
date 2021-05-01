package com.pawn.glave.app.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 字典类型实体类
 *
 */
@Data
@TableName("sys_dict_type")
public class SysDictTypeEntity implements Serializable {

	@TableId
	private Long id;
	@NotBlank(message="唯一标识不能为空")
	private String code;
	@NotBlank(message="字典名称不能为空")
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date time;

}
