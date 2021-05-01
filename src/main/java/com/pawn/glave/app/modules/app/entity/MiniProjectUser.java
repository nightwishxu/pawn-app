package com.pawn.glave.app.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("mini_project_user")
public class MiniProjectUser {
    @TableId
    @ApiParam(hidden = true)
    private Long id;

    @ApiParam(hidden = true)
    private String phone;

    @ApiParam(hidden = true)
    private String name;

    @ApiParam(hidden = true)
    private String wxNick;

    @ApiParam(hidden = true)
    private String wxImage;

    @ApiParam(hidden = true)
    private String miniOpenId;

    @ApiParam(hidden = true)
    private String unionId;

    @ApiParam(hidden = true)
    private String wxOpenId;

    @ApiParam(hidden = true)
    private String state;
}
