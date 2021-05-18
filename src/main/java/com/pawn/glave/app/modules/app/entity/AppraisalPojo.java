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
 * 鉴定主表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("mini_appraisal")
@ApiModel
public class AppraisalPojo implements Serializable {

    private static final long serialVersionUID = -7978262462388775088L;
    @ApiModelProperty(value="id")
    @ApiParam(value="id")
    @TableId
    private Long id;

    @ApiModelProperty(value="鉴定类型分类 与mini_appraisal_type对应")
    @ApiParam(value="鉴定类型分类 与mini_appraisal_type对应")
    private String classify;

    @ApiModelProperty(value="鉴定编号")
    @ApiParam(value="鉴定编号")
    private String number;

    @ApiModelProperty(value="鉴定商品照片")
    @ApiParam(value="鉴定商品照片")
    private String coverPhoto;

    @ApiModelProperty(value="来源 app:06 小程序:08")
    @ApiParam(value="来源 app:06 小程序:08")
    private String source;

    @ApiModelProperty(value="待鉴定状态0待鉴定 1已分配专家 2鉴定完成 3无法鉴定")
    @ApiParam(value="待鉴定状态0待鉴定 1已分配专家 2鉴定完成 3无法鉴定")
    private String state;

    @ApiModelProperty(value="鉴定方式 0邮寄鉴定 1在线鉴定")
    @ApiParam(value="鉴定方式 0邮寄鉴定 1在线鉴定")
    private String method;

    @ApiModelProperty(value="鉴定时间")
    @ApiParam(value="鉴定时间")
    private Date time;

    @ApiModelProperty(value="名称（专家鉴定填写）")
    @ApiParam(value="名称（专家鉴定填写）")
    private String name;

    @ApiModelProperty(value="尺寸（专家鉴定填写）")
    @ApiParam(value="尺寸（专家鉴定填写）")
    private String size;

    @ApiModelProperty(value="重量（专家鉴定填写）")
    @ApiParam(value="重量（专家鉴定填写）")
    private String weight;

    @ApiModelProperty(value="主材质（专家鉴定填写）")
    @ApiParam(value="主材质（专家鉴定填写）")
    private String mainMaterial;

    @ApiModelProperty(value="副材质（专家鉴定填写）")
    @ApiParam(value="副材质（专家鉴定填写）")
    private String subMaterial;

    @ApiModelProperty(value="年代（专家鉴定填写）")
    @ApiParam(value="年代（专家鉴定填写）")
    private String years;

    @ApiModelProperty(value="其他（专家鉴定填写）")
    @ApiParam(value="其他（专家鉴定填写）")
    private String other;

    @ApiModelProperty(value="市场流通性（专家鉴定填写）")
    @ApiParam(value="市场流通性（专家鉴定填写）")
    private String marketLiquidity;

    @ApiModelProperty(value="价值稳定性（专家鉴定填写）")
    @ApiParam(value="价值稳定性（专家鉴定填写）")
    private String valueStability;

    @ApiModelProperty(value="材质易损性（专家鉴定填写）")
    @ApiParam(value="材质易损性（专家鉴定填写）")
    private String materialVulnerability;

    @ApiModelProperty(value="典当价格（专家鉴定填写）")
    @ApiParam(value="典当价格（专家鉴定填写）")
    private BigDecimal pawnPrice;

    @ApiModelProperty(value="鉴定详情表中的商品编号（每个类型的表都不一样）")
    @ApiParam(value="鉴定详情表中的商品编号（每个类型的表都不一样）")
    private String goodsCode;

    @ApiModelProperty(value="提交鉴定人")
    @ApiParam(value="提交鉴定人")
    private Long createUser;

    @ApiModelProperty(value="提交鉴定时间")
    @ApiParam(value="提交鉴定时间")
    private Date createTime;

    @ApiModelProperty(value="鉴定人（专家）")
    @ApiParam(value="鉴定人（专家）")
    private String appraisalUser;

    @ApiModelProperty(value="无法鉴定原因（专家无法鉴定时填写）")
    @ApiParam(value="无法鉴定原因（专家无法鉴定时填写）")
    private String reason;

    @ApiModelProperty(value="对应证书编号")
    @ApiParam(value="对应证书编号")
    private String certificateCode;

    @ApiModelProperty(value="对应鉴定详情")
    @ApiParam(value="对应鉴定详情")
    @TableField(exist=false)
    private Object info;

    @TableField(exist=false)
    private Object info2;

    @ApiModelProperty(value="拆箱视频")
    @ApiParam(value="拆箱视频")
    private String unpackingVideo;

    @ApiModelProperty(value="鉴宝视频")
    @ApiParam(value="鉴宝视频")
    private String appraisalVideo;

    @ApiModelProperty(value="装箱视频")
    @ApiParam(value="装箱视频")
    private String packingVideo;

    @ApiModelProperty(value="1展示 0不展示（邮寄复检，之前的记录需要更新）",hidden = true)
    @ApiParam(value="1展示 0不展示（邮寄复检，之前的记录需要更新）")
    private String isShow;

    /**
     * 在次提交申请ID
     */
    @ApiModelProperty(value = "在次提交申请ID")
    @ApiParam(value = "在次提交申请ID")
    private String againId;

    @ApiModelProperty(value="在次提交申请信息")
    @ApiParam(value="在次提交申请信息")
    @TableField(exist=false)
    private AppraisalAgain appraisalAgain;

    @ApiModelProperty(value = "")
    @ApiParam(value = "")
    private Integer userGoodsId;
}
