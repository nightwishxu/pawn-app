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
 * 瓷器鉴定
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("mini_appraisal_porcelain")
@ApiModel
public class PorcelainPojo implements Serializable {

    private static final long serialVersionUID = -8828408147423284678L;
    @ApiModelProperty(value="id")
    @ApiParam(value="id")
    @TableId
    private Long id;

    @ApiModelProperty(value="商品编号")
    @ApiParam(value="商品编号")
    private String goodsCode;

    @ApiModelProperty(value="正面图")
    @ApiParam(value="正面图")
    private String frontImg;

    @ApiModelProperty(value="侧面图")
    @ApiParam(value="侧面图")
    private String sideImg;

    @ApiModelProperty(value="背面图")
    @ApiParam(value="背面图")
    private String backImg;

    @ApiModelProperty(value="口沿图")
    @ApiParam(value="口沿图")
    private String mouthImg;

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
