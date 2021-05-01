package com.pawn.glave.app.modules.app.entity;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("send_pdf")
@ApiModel
@Builder
public class SendPojo implements Serializable {
    @ApiModelProperty(value="id")
    @ApiParam(value="id")
    @TableId
    private Long id;

    @ApiModelProperty(value="证书编号")
    @ApiParam(value="证书编号")
    private String code;

    @ApiModelProperty(value="word文件路径")
    @ApiParam(value="word文件路径")
    private String wordUrl;

    @ApiModelProperty(value="转换接口返回的docId")
    @ApiParam(value="转换接口返回的docId")
    private String docId;


    @ApiModelProperty(value="pdf网络路径")
    @ApiParam(value="pdf网络路径")
    private String pdfUrl;


    @ApiModelProperty(value="转换后pdfId")
    @ApiParam(value="转换后pdfId")
    private Long pdfId;

    @ApiModelProperty(value="状态")
    @ApiParam(value="状态")
    private String state;

    private Long batchId;
}
