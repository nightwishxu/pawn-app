package com.pawn.glave.app.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_dept")
public class SysDeptEntity implements Serializable {
    private static final long serialVersionUID = -6398061030012073735L;
    @TableId
    private String id;

    private String parentId;

    private String name;

    private long createUser;

    @TableField(exist = false)
    private String createUserName;

    private Date createTime;

    private String activitiSign;

    @TableField(exist = false)
    private List<SysDeptEntity> children;
}
