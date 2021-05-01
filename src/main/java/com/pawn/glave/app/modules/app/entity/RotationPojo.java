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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_rotation")
@ApiModel
public class RotationPojo implements Serializable {
    private static final long serialVersionUID = -3517395526803618583L;

    @ApiModelProperty(value="id")
    @ApiParam(value="id")
    @TableId
    private Long id;


    @ApiModelProperty(value="创建时间")
    @ApiParam(value="创建时间")
    private Date createdTime;

    private String jumpType;

    private String jumpValue;

    /**
     * 1本地上传 2url
     */
    private String coverType;

    private String coverValue;

    private String rotationTitle;

    private Integer curOrders;
}
