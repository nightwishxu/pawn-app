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
 * 专家
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("mini_experts")
@ApiModel
public class ExpertsPojo implements Serializable {

    private static final long serialVersionUID = -4019475637062385021L;

    @ApiModelProperty(value="id")
    @ApiParam(value="id",hidden = true)
    @TableId
    private Long id;

    @ApiModelProperty(value="专家code")
    @ApiParam(value="专家code",hidden = true)
    private String code;

    @ApiModelProperty(value="专家姓名")
    @ApiParam(value="专家姓名",hidden = true)
    private String name;

    @ApiModelProperty(value="专家手机号码")
    @ApiParam(value="专家手机号码",hidden = true)
    private String phone;

    @ApiModelProperty(value="密码")
    @ApiParam(value="密码",hidden = true)
    private String password;

    @ApiModelProperty(value="专家头像地址")
    @ApiParam(value="专家头像地址",hidden = true)
    private String headImg;

    @ApiModelProperty(value="微信open_id")
    @ApiParam(value="微信open_id",hidden = true)
    private String openId;

    @ApiModelProperty(value="微信union_id")
    @ApiParam(value="微信union_id",hidden = true)
    private Date unionId;

    @TableField(exist=false)
    @ApiParam(hidden = true)
    private String types;

}
