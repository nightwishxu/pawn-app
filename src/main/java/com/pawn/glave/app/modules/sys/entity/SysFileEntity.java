package com.pawn.glave.app.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("sys_file")
public class SysFileEntity implements Serializable {
    private static final long serialVersionUID = 6859457470900064708L;
    @TableId
    private long id;

    private String fileName;

    private String fileType;

    private String fileUrl;

    private Date fileUploadTime;

    private String fileOldName;

    private Integer type;
}
