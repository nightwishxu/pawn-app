package com.pawn.glave.app.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
import java.util.Date;

/**
 * 专家与鉴定类型关系表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("mini_expert_type")
@ApiModel
public class ExpertTypePojo implements Serializable {


    private static final long serialVersionUID = -6900026012919324202L;
    @ApiModelProperty(value="id")
    @ApiParam(value="id")
    @TableId
    private Long id;

    @ApiModelProperty(value="专家code")
    @ApiParam(value="专家code")
    private String expertCode;

    @ApiModelProperty(value="鉴定类型code")
    @ApiParam(value="鉴定类型code")
    private String appraisalTypeCode;

    @TableField(exist = false)
    @ApiModelProperty(value="鉴定类型名称")
    @ApiParam(value="鉴定类型名称")
    private String appraisalTypeName;

}
