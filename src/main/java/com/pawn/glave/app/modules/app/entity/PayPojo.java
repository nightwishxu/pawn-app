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
 * 支付主表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("mini_user_pay")
@ApiModel
public class PayPojo implements Serializable {

    private static final long serialVersionUID = -1653825762997220076L;
    @ApiModelProperty(value="id")
    @ApiParam(value="id")
    @TableId
    private Long id;

    @ApiModelProperty(value="订单code")
    @ApiParam(value="订单code")
    private String orderCode;

    @ApiModelProperty(value="微信支付随机字符串")
    @ApiParam(value="微信支付随机字符串")
    private String nonceStr;

    @ApiModelProperty(value="鉴定编号")
    @ApiParam(value="鉴定编号")
    private String appraisalCode;

    @ApiModelProperty(value="支付人id")
    @ApiParam(value="支付人id")
    private Long payeeId;

    @ApiModelProperty(value="支付人姓名")
    @ApiParam(value="支付人姓名")
    private String payeeName;

    @ApiModelProperty(value="支付人手机号")
    @ApiParam(value="支付人手机号")
    private String payeePhone;

    @ApiModelProperty(value="订单信息")
    @ApiParam(value="订单信息")
    private String orderInfo;

    @ApiModelProperty(value="支付金额(分)")
    @ApiParam(value="支付金额(分)")
    private Long totalFee;

    @ApiModelProperty(value="0下单待支付 1下单已支付 2 支付取消 3支付失败")
    @ApiParam(value="0下单待支付 1下单已支付 2 支付取消 3支付失败")
    private String state;

    @ApiModelProperty(value="支付失败原因")
    @ApiParam(value="支付失败原因")
    private String failReason;

    @ApiModelProperty(value="下单时间")
    @ApiParam(value="下单时间")
    private Date createTime;

    @ApiModelProperty(value="支付/取消 时间")
    @ApiParam(value="支付/取消 时间")
    private Date modifyTime;
}
