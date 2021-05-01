package com.pawn.glave.app.modules.certificate.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("circulation_record")
public class CirculationRecord implements Serializable {
    private static final long serialVersionUID = -1194620483671402260L;
    @TableId
    private Long id;

    private Long certificateId;

    private String mode;

    private String price;

    private String target;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String remark;
}
