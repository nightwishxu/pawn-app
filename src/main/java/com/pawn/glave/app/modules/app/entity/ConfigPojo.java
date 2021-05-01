package com.pawn.glave.app.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 配置表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("mini_config")
@ApiModel
public class ConfigPojo implements Serializable {

    private static final long serialVersionUID = 4374277024202746364L;
    @ApiModelProperty(value="id")
    @ApiParam(value="id")
    @TableId
    private Long id;

    @ApiModelProperty(value="配置项名称")
    @ApiParam(value="配置项名称")
    private String configName;

    @ApiModelProperty(value="配置项值")
    @ApiParam(value="配置项值")
    private String configValue;
}
