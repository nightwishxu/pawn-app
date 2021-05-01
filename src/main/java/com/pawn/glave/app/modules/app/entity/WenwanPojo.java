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

/**
 * 文玩杂项鉴定
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("mini_appraisal_wenwan")
@ApiModel
public class WenwanPojo implements Serializable {

    private static final long serialVersionUID = -8965159234775421933L;
    @ApiModelProperty(value="id")
    @ApiParam(value="id")
    @TableId
    private Long id;

    @ApiModelProperty(value="商品编号")
    @ApiParam(value="商品编号")
    private String goodsCode;

    @ApiModelProperty(value="整体图")
    @ApiParam(value="整体图")
    private String wholeImg;

    @ApiModelProperty(value="45度图")
    @ApiParam(value="45度图")
    private String angleImg;

    @ApiModelProperty(value="侧面图")
    @ApiParam(value="侧面图")
    private String sideImg;

    @ApiModelProperty(value="局部图")
    @ApiParam(value="局部图")
    private String localImg;

    @ApiModelProperty(value="背面图")
    @ApiParam(value="背面图")
    private String backImg;

    @ApiModelProperty(value="底面图")
    @ApiParam(value="底面图")
    private String bottomImg;

    @ApiModelProperty(value="备注")
    @ApiParam(value="备注")
    private String remark;

    @ApiModelProperty(value="userGoodsId")
    @ApiParam(value="userGoodsId")
    private Integer userGoodsId;

}
