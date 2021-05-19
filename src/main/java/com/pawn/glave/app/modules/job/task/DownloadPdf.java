package com.pawn.glave.app.modules.job.task;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pawn.glave.app.common.utils.HttpUtils;
import com.pawn.glave.app.modules.app.entity.Certificate;
import com.pawn.glave.app.modules.app.entity.CertificatePojo;
import com.pawn.glave.app.modules.app.entity.SendPojo;
import com.pawn.glave.app.modules.app.service.CertificateService;
import com.pawn.glave.app.modules.app.service.SendService;
import com.pawn.glave.app.modules.sys.entity.SysFileEntity;
import com.pawn.glave.app.modules.sys.service.SysFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

@Slf4j
@Component("downloadPdf")
public class DownloadPdf implements ITask {
    @Resource
    private SendService sendService;
    @Resource
    private SysFileService sysFileService;

    @Resource
    private CertificateService certificateService;

    @Override
    public void run(String params) {
//        List<SendPojo> sendPojos = sendService.list(new QueryWrapper<SendPojo>().eq("state","3").last("limit 0,10"));
        List<SendPojo> sendPojos = sendService.list(new QueryWrapper<SendPojo>().eq("state", "3").last("limit 0,10"));
        for (SendPojo sendPojo : sendPojos) {
            download(sendPojo);
        }
    }

    public void download(SendPojo sendPojo) {
        try {
            String url = sendPojo.getPdfUrl();
            File file = FileUtil.file("/webapp/files/pdf/" + sendPojo.getCode() + ".pdf");
            HttpUtil.downloadFile(url, file);
            if (!file.exists() || file.length()==0){
                return;
            }
            log.info("file name:{},path:{},length:{}",file.getName(),file.getPath(),file.length());
            SysFileEntity sysFileEntity = SysFileEntity.builder().fileName(sendPojo.getCode() + ".pdf")
                    .fileOldName(sendPojo.getCode() + ".pdf")
                    .fileType("pdf").fileUploadTime(new Date()).fileUrl("/files/pdf/" + sendPojo.getCode() + ".pdf").build();
            sysFileService.save(sysFileEntity);
            sendPojo.setPdfId(sysFileEntity.getId());
            sendPojo.setState("4");
            sendService.updateById(sendPojo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("定时任务生成PDF文件失败{}", sendPojo.toString());
        }
    }
}
