package com.pawn.glave.app.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("appraisal_category")
public class AppraisalCategoryEntity implements Serializable {

    @TableId
    private long id;

    private String categoryTitle;

    private BigDecimal categoryPrice;

    @TableField(exist = false)
    private boolean disabledState = true;
}
