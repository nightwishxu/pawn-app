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
 * 贵金属
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("mini_appraisal_metal")
@ApiModel
public class MetalPojo implements Serializable {
    private static final long serialVersionUID = -8172035981845415604L;
    @ApiModelProperty(value="id")
    @ApiParam(value="id")
    @TableId
    private Long id;

    @ApiModelProperty(value="商品编号")
    @ApiParam(value="商品编号")
    private String goodsCode;

    @ApiModelProperty(value="金属种类")
    @ApiParam(value="金属种类")
    private String metalType;

    @ApiModelProperty(value="重量")
    @ApiParam(value="重量")
    private String weight;

    @ApiModelProperty(value="纯度")
    @ApiParam(value="纯度")
    private String purity;

    @ApiModelProperty(value="备注")
    @ApiParam(value="备注")
    private String remark;

    @ApiModelProperty(value="")
    @ApiParam(value="")
    private Integer userGoodsId;
}
