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
import java.util.Date;

/**
 * 再次提交鉴定申请
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("mini_appraisal_again")
@ApiModel
@Data
public class AppraisalAgain implements Serializable {
    private static final long serialVersionUID = -3131583634549830337L;

    @ApiModelProperty(value="id")
    @ApiParam(value="id")
    @TableId
    private Long id;

    @ApiModelProperty(value="再次提及申请ID")
    @ApiParam(value="再次提及申请ID")
    private String againId;

    private Long appraisalId;

    private Long userId;

    @ApiModelProperty(value="再次提交鉴定申请图片")
    @ApiParam(value="再次提交鉴定申请图片")
    private String againImg;

    @ApiModelProperty(value="再次提交鉴定申请原因")
    @ApiParam(value="再次提交鉴定申请原因")
    private String againReason;

    @ApiModelProperty(value="再次提交鉴定申请时间")
    @ApiParam(value="再次提交鉴定申请时间")
    private Date createdTime;
}
