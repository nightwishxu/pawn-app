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
import java.math.BigDecimal;
import java.util.Date;

/**
 * 积分表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("mini_integral")
@ApiModel
public class IntegralPojo implements Serializable {

    private static final long serialVersionUID = -7978262462388775088L;
    @ApiModelProperty(value="id")
    @ApiParam(value="id")
    @TableId
    private Long id;

    @ApiModelProperty(value="鉴定记录id")
    @ApiParam(value="鉴定记录id")
    private Long appraisalId;

    @ApiModelProperty(value="鉴定记录编号")
    @ApiParam(value="鉴定记录编号")
    private String appraisalNum;

    @ApiModelProperty(value="积分记录时间")
    @ApiParam(value="积分记录时间")
    private Date createTime;

    @ApiModelProperty(value="本次增加积分")
    @ApiParam(value="本次增加积分")
    private String addIntegral;

    @ApiModelProperty(value="积分所属专家id")
    @ApiParam(value="积分所属专家id")
    private String expertCode;

}
