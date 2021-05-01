package com.pawn.glave.app.common.utils;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ret implements Serializable {
    private static final long serialVersionUID = 1926397510099853357L;

    @ApiModelProperty(value="状态码")
    @ApiParam(value="状态码")
    private int code;

    @ApiModelProperty(value="信息")
    @ApiParam(value="信息")
    private String msg;

    @ApiModelProperty(value="数据")
    @ApiParam(value="数据")
    private Object data;
}
