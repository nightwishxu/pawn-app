package com.pawn.glave.app.modules.app.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


@Data
public class AgainAppraisalSaveParam implements Serializable {

    private static final long serialVersionUID = -8696075461291747937L;
    /**
     * 鉴定记录ID
     */
    @NotNull(message = "请求参数异常")
    private long appraisalId;

    /***
     * 图片文件ID 最多9张
     */
    private String imgStr;

    /**
     * 原因
     */
    private String reason;
}
