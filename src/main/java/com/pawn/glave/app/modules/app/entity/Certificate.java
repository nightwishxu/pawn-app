package com.pawn.glave.app.modules.app.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certificate implements Serializable {

	/**
	 *
	 */
	@ApiModelProperty(value="")
	@ApiParam(value="")
	private Integer id;

	/**
	 *名称
	 */
	@ApiModelProperty(value="名称")
	@ApiParam(value="名称")
	private String name;

	/**
	 *编号
	 */
	@ApiModelProperty(value="编号")
	@ApiParam(value="编号")
	private String code;

	/**
	 *尺寸--长
	 */
	@ApiModelProperty(value="尺寸--长")
	@ApiParam(value="尺寸--长")
	private String length;

	/**
	 *尺寸--宽
	 */
	@ApiModelProperty(value="尺寸--宽")
	@ApiParam(value="尺寸--宽")
	private String width;

	/**
	 *尺寸--高
	 */
	@ApiModelProperty(value="尺寸--高")
	@ApiParam(value="尺寸--高")
	private String height;

	/**
	 *重量
	 */
	@ApiModelProperty(value="重量")
	@ApiParam(value="重量")
	private String weight;

	/**
	 *材质
	 */
	@ApiModelProperty(value="材质")
	@ApiParam(value="材质")
	private String material;

	/**
	 *主体材质
	 */
	@ApiModelProperty(value="主体材质")
	@ApiParam(value="主体材质")
	private String mainMaterial;

	/**
	 *其他辅材
	 */
	@ApiModelProperty(value="其他辅材")
	@ApiParam(value="其他辅材")
	private String otherMaterial;

	/**
	 *创作年代
	 */
	@ApiModelProperty(value="创作年代")
	@ApiParam(value="创作年代")
	private String createYear;

	/**
	 *其他
	 */
	@ApiModelProperty(value="其他")
	@ApiParam(value="其他")
	private String other;

	/**
	 *图片概览
	 */
	@ApiModelProperty(value="图片概览")
	@ApiParam(value="图片概览")
	private String imgs;

	/**
	 *市场流通性0-5分
	 */
	@ApiModelProperty(value="市场流通性0-5分")
	@ApiParam(value="市场流通性0-5分")
	private Integer marketLiquidity;

	/**
	 *价值稳定性0-5分
	 */
	@ApiModelProperty(value="价值稳定性0-5分")
	@ApiParam(value="价值稳定性0-5分")
	private Integer valueStability;

	/**
	 *材质易损性
	 */
	@ApiModelProperty(value="材质易损性")
	@ApiParam(value="材质易损性")
	private Integer materialVulnerability;

	/**
	 *存放条件
	 */
	@ApiModelProperty(value="存放条件")
	@ApiParam(value="存放条件")
	private String storageCondition;

	/**
	 *肉眼可见缺陷
	 */
	@ApiModelProperty(value="肉眼可见缺陷")
	@ApiParam(value="肉眼可见缺陷")
	private String nakedEyeDefect;

	/**
	 *金融记录
	 */
	@ApiModelProperty(value="金融记录")
	@ApiParam(value="金融记录")
	private String financeLog;

	/**
	 *其他事项
	 */
	@ApiModelProperty(value="其他事项")
	@ApiParam(value="其他事项")
	private String otherBusiness;


	/**
	 *
	 */
	@ApiModelProperty(value="")
	@ApiParam(value="")
	private Integer userGoodsId;

	/**
	 *鉴定托底价
	 */
	@ApiModelProperty(value="鉴定托底价")
	@ApiParam(value="鉴定托底价")
	private java.math.BigDecimal price;

	/**
	 *鉴定说明
	 */
	@ApiModelProperty(value="鉴定说明")
	@ApiParam(value="鉴定说明")
	private String appraisalDsc;

	/**
	 *尺寸
	 */
	@ApiModelProperty(value="尺寸")
	@ApiParam(value="尺寸")
	private String size;


	@ApiModelProperty(value="估价")
	@ApiParam(value="估价")
	private BigDecimal authPriceTest;

	@ApiModelProperty(value="鉴定价")
	@ApiParam(value="鉴定价")
	private BigDecimal authPrice;

	@ApiModelProperty(value="鉴定结果")
	@ApiParam(value="鉴定结果")
	private Integer authResult;


}
