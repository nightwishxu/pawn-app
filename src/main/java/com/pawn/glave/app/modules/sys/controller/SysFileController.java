package com.pawn.glave.app.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pawn.glave.app.common.exception.RRException;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.common.utils.StringUtils;
import com.pawn.glave.app.common.utils.ZipUtils;
import com.pawn.glave.app.modules.app.entity.CertificatePojo;
import com.pawn.glave.app.modules.app.service.CertificateService;
import com.pawn.glave.app.modules.app.utils.BaseUtils;
import com.pawn.glave.app.modules.certificate.entity.ZipEntity;
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
import java.util.ArrayList;
import java.util.List;

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
    public R uploadImg(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return sysFileService.upload(file);
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    @ApiOperation("图片预览")
    public void show(@ApiParam(value = "图片id") @PathVariable("id") int id, HttpServletResponse response) throws IOException {
        //获取传入参数
        //查询文件详情
        SysFileEntity sysFileEntity = sysFileService.getById(id);

        // 不明原因，文件保存时会多保存一个fileUrl=原保存文件id的数据，因此根据这个反向查出正确的文件
        if (StringUtils.isEmpty(sysFileEntity.getFileOldName()) && StringUtils.isNumeric(sysFileEntity.getFileUrl())) {
            sysFileEntity = sysFileService.getById(Integer.valueOf(sysFileEntity.getFileUrl()));
        }

        if (sysFileEntity.getType()== null || sysFileEntity.getType()==0){
            File file = new File(fileUploadPath + sysFileEntity.getFileUrl());
            if (!file.exists()) {
                throw new RRException("不存在图片");
            }
            FileInputStream inputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            inputStream.read(data);
            inputStream.close();
//            response.setContentType("image/png");
            if ("mp4".equals(sysFileEntity.getFileType())) {
                response.setHeader("content-type", "video/mp4");
            } else if ("jpg,jpeg,gif,png,bmp".contains(sysFileEntity.getFileType())) {
                response.setHeader("content-type", "image/" + sysFileEntity.getFileType());
            } else {
                response.setHeader("Content-disposition", String.format("attachment;filename=\"%s\"", new String(sysFileEntity.getFileOldName().getBytes(), "ISO8859-1")));
                response.setHeader("Content-Length", String.valueOf(file.length()));
                response.setHeader("content-type", "application/octet-stream;charset=ISO8859-1");
                response.setContentType("application/octet-stream;charset=ISO8859-1");
            }
            OutputStream os = response.getOutputStream();
            os.write(data);
            os.flush();
            os.close();
        }else {
            byte[] data = BaseUtils.toByteArray(BaseUtils.getImageInputStream(sysFileEntity.getFileUrl()));
            response.setContentType("image/png");
            OutputStream os = response.getOutputStream();
            os.write(data);
            os.flush();
            os.close();
        }

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

    @GetMapping(value = "/certificate/downLoadZip/{id}")
    public void downLoadZip(@PathVariable Long id, HttpServletResponse response) throws IOException {
        CertificatePojo certificatePojo = certificateService.getById(id);
        String base = "/webapp/files/pdf/";
        List<String> list = new ArrayList<>();
        String F2 = certificatePojo.getCode() + "F2" + certificatePojo.getTwoFFileId();
        String F3 = certificatePojo.getCode() + "F3" + certificatePojo.getThreeFFileId();
        String Z2 = certificatePojo.getCode() + "Z2" + certificatePojo.getTwoZFileId();
        String Z3 = certificatePojo.getCode() + "Z3" + certificatePojo.getThreeZFileId();
        list.add(base + F2 + ".pdf");
        list.add(base + F3 + ".pdf");
        list.add(base + Z2 + ".pdf");
        list.add(base + Z3 + ".pdf");
        String zipName = "/webapp/files/zip/" + certificatePojo.getName() + certificatePojo.getCode() + "证书打包下载.zip";
        ZipUtils.downloadZipFiles(list, zipName);
        File file = new File(zipName);
        if (file.exists()) {
            byte[] data;
            FileInputStream input = null;
            try {
                input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
            } catch (Exception e) {
                System.out.println("zip文件处理异常：" + e);
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
