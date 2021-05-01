package com.pawn.glave.app.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pawn.glave.app.common.exception.RRException;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.modules.app.entity.CertificatePojo;
import com.pawn.glave.app.modules.app.service.CertificateService;
import com.pawn.glave.app.modules.sys.entity.SysFileEntity;
import com.pawn.glave.app.modules.sys.service.SysFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/sys/file")
@Api("文件操作")
public class SysFileController {

    @Autowired
    private SysFileService sysFileService;
    @Autowired
    private CertificateService certificateService;
    @Value("${web.upload-path}")
    private String fileUploadPath;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation("图片上传")
    public R uploadImg(@RequestParam("file") MultipartFile file) {
        return sysFileService.upload(file);
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    @ApiOperation("图片预览")
    public void show(@ApiParam(value = "图片id") @PathVariable("id") int id, HttpServletResponse response) throws IOException {
        //获取传入参数
        //查询文件详情
        SysFileEntity sysFileEntity = sysFileService.getById(id);

        File file = new File(fileUploadPath + sysFileEntity.getFileUrl());
        if (!file.exists()) {
            throw new RRException("不存在图片");
        }
        FileInputStream inputStream = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        inputStream.read(data);
        inputStream.close();
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        os.write(data);
        os.flush();
        os.close();
    }

    /**
     * type: 1、反面word文件ID 2正面word文件ID
     *
     * @param zsbh
     * @return
     */
    @RequestMapping(value = "/url/{zsbh}", method = RequestMethod.GET)
    @ApiOperation("获取word文件路径")
    public String getWordUrl(@ApiParam(value = "证书编号") @PathVariable("zsbh") Long zsbh) {
//        CertificatePojo certificatePojo = certificateService.getOne(new QueryWrapper<CertificatePojo>().eq("code", zsbh));
//        if (certificatePojo != null) {
//            SysFileEntity sysFileEntity = sysFileService.getById(zsbh);
//            return sysFileEntity.getFileUrl();
//        }
//        return null;

        SysFileEntity sysFileEntity = sysFileService.getById(zsbh);
        return sysFileEntity.getFileUrl();
    }

    @GetMapping("/viewPDF/{fileId}")
    public void pdfStreamHandler(@PathVariable String fileId, HttpServletResponse response) {
        //PDF文件地址
        File file = new File("/webapp/files/pdf/" + fileId + ".pdf");
//        File file = new File("/Users/glavesoft/pdf/" + fileId + ".pdf");
        if (file.exists()) {
            byte[] data;
            FileInputStream input = null;
            try {
                input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
            } catch (Exception e) {
                System.out.println("pdf文件处理异常：" + e);
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
