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
 * 钻石鉴定
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("mini_appraisal_diamonds")
@ApiModel
public class DiamondsPojo implements Serializable {

    private static final long serialVersionUID = 957985348530718376L;
    @ApiModelProperty(value="id")
    @ApiParam(value="id")
    @TableId
    private Long id;

    @ApiModelProperty(value="商品编号")
    @ApiParam(value="商品编号")
    private String goodsCode;

    @ApiModelProperty(value="形状")
    @ApiParam(value="形状")
    private String shape;

    @ApiModelProperty(value="克拉")
    @ApiParam(value="克拉")
    private String carat;

    @ApiModelProperty(value="颜色")
    @ApiParam(value="颜色")
    private String color;

    @ApiModelProperty(value="净度")
    @ApiParam(value="净度")
    private String clarity;

    @ApiModelProperty(value="切工")
    @ApiParam(value="切工")
    private String cut;

    @ApiModelProperty(value="备注")
    @ApiParam(value="备注")
    private String remark;

    @ApiModelProperty(value="userGoodsId")
    @ApiParam(value="userGoodsId")
    private Integer userGoodsId;

}
