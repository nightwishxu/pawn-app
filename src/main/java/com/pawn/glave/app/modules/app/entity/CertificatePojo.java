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
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 证书主表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("mini_appraisal_certificate")
@ApiModel
public class CertificatePojo implements Serializable {

    private static final long serialVersionUID = 6596030002676252989L;
    @ApiModelProperty(value="id")
    @ApiParam(value="id")
    @TableId
    private Long id;

    @ApiModelProperty(value="证书code")
    @ApiParam(value="证书code")
    private String code;

    @ApiModelProperty(value="证书code二维码图片")
    @ApiParam(value="证书code二维码图片")
    private String codeImg;

    @ApiModelProperty(value="鉴定编号")
    @ApiParam(value="鉴定编号")
    private String appraisalCode;

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
    private String pawnPrice;

    @ApiModelProperty(value="图片（4张）")
    @ApiParam(value="图片（4张）")
    private String images;

    @ApiModelProperty(value="是否防潮 0否 1是")
    @ApiParam(value="是否防潮 0否 1是")
    private String isDampproof;

    @ApiModelProperty(value="防潮备注")
    @ApiParam(value="防潮备注")
    private String dampproofRemark;

    @ApiModelProperty(value="是否防震 0否 1是")
    @ApiParam(value="是否防震 0否 1是")
    private String isShockproof;

    @ApiModelProperty(value="防震备注")
    @ApiParam(value="防震备注")
    private String shockproofRemark;

    @ApiModelProperty(value="是否防霉 0否 1是")
    @ApiParam(value="是否防霉 0否 1是")
    private String isMouldproof;

    @ApiModelProperty(value="防霉备注")
    @ApiParam(value="防霉备注")
    private String mouldproofRemark;

    @ApiModelProperty(value="是否防虫 0否 1是")
    @ApiParam(value="是否防虫 0否 1是")
    private String isPestcontrol;

    @ApiModelProperty(value="防虫备注")
    @ApiParam(value="防虫备注")
    private String pestcontrolRemark;

    @ApiModelProperty(value="是否防热 0否 1是")
    @ApiParam(value="是否防热 0否 1是")
    private String isAntipyretic;

    @ApiModelProperty(value="防热备注")
    @ApiParam(value="防热备注")
    private String antipyreticRemark;

    @ApiModelProperty(value="是否防碎 0否 1是")
    @ApiParam(value="是否防碎 0否 1是")
    private String isShatterproof;

    @ApiModelProperty(value="防碎备注")
    @ApiParam(value="防碎备注")
    private String shatterproofRemark;

    @ApiModelProperty(value="是否防紫外线 0否 1是")
    @ApiParam(value="是否防紫外线 0否 1是")
    private String isUltravioletproof;

    @ApiModelProperty(value="防紫外线备注")
    @ApiParam(value="防紫外线备注")
    private String ultravioletproofRemark;

    @ApiModelProperty(value="缺陷描述")
    @ApiParam(value="缺陷描述")
    private String defectDescription;

    @ApiModelProperty(value="创建时间")
    @ApiParam(value="创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value="证书pdf文件id")
    @ApiParam(value="证书pdf文件id")
    private Long pdfId;

    private Long threeZFileId;
    private Long threeFFileId;
    private Long twoZFileId;
    private Long twoFFileId;

}
