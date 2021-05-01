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
 * 翡翠玉石鉴定
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("mini_appraisal_jadeite")
@ApiModel
public class JadeitePojo implements Serializable {

    private static final long serialVersionUID = -3678662099985768014L;
    @ApiModelProperty(value="id")
    @ApiParam(value="id")
    @TableId
    private Long id;

    @ApiModelProperty(value="商品编号")
    @ApiParam(value="商品编号")
    private String goodsCode;

    @ApiModelProperty(value="外观照片")
    @ApiParam(value="外观照片")
    private String photos;

    @ApiModelProperty(value="视频")
    @ApiParam(value="视频")
    private String video;

    @ApiModelProperty(value="翡翠颜色")
    @ApiParam(value="翡翠颜色")
    private String color;

    @ApiModelProperty(value="翡翠种水地")
    @ApiParam(value="翡翠种水地")
    private String type;

    @ApiModelProperty(value="尺寸")
    @ApiParam(value="尺寸")
    private String size;

    @ApiModelProperty(value="描述补充")
    @ApiParam(value="描述补充")
    private String remark;

    @ApiModelProperty(value="userGoodsId")
    @ApiParam(value="userGoodsId")
    private Integer userGoodsId;

}
