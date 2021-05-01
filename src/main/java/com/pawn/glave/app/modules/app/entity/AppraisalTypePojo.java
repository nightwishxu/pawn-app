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
 * 鉴定类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("mini_appraisal_type")
@ApiModel
public class AppraisalTypePojo implements Serializable {


    private static final long serialVersionUID = -3569864026854526137L;
    @ApiModelProperty(value="id")
    @ApiParam(value="id")
    @TableId
    private Long id;

    @ApiModelProperty(value="鉴定类型code")
    @ApiParam(value="鉴定类型code")
    private String code;

    @ApiModelProperty(value="鉴定类型name")
    @ApiParam(value="鉴定类型name")
    private String name;

    @ApiModelProperty(value="备注")
    @ApiParam(value="备注")
    private String remark;

}
