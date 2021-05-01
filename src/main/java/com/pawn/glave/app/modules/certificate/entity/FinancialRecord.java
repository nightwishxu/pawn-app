package com.pawn.glave.app.modules.certificate.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("financial_record")
public class FinancialRecord implements Serializable {
    private static final long serialVersionUID = -1052025118811910959L;
    @TableId
    private Long id;

    private Long certificateId;

    private String mortgageFinancial;

    private String mechanism;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String remark;
}
