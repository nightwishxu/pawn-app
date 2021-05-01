package com.pawn.glave.app.modules.sys.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录表单
 *
 */
@Data
@ApiModel(value = "后台登录表单")
public class SysLoginForm implements Serializable {
    private static final long serialVersionUID = -8969533605548293123L;
    @ApiModelProperty(value = "用户名",required = true,example = "admin")
    private String username;
    @ApiModelProperty(value = "密码",required = true,example = "admin")
    private String password;
    @ApiModelProperty(hidden=true)
    private String captcha;
    @ApiModelProperty(hidden=true)
    private String uuid;


}
