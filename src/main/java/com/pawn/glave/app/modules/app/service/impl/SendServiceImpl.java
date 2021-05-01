package com.pawn.glave.app.modules.app.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.common.exception.RRException;
import com.pawn.glave.app.common.utils.HttpUtils;
import com.pawn.glave.app.modules.app.dao.SendDao;
import com.pawn.glave.app.modules.app.entity.*;
import com.pawn.glave.app.modules.app.service.*;
import com.pawn.glave.app.modules.sys.entity.SysFileEntity;
import com.pawn.glave.app.modules.sys.service.SysFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Queue;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class SendServiceImpl extends ServiceImpl<SendDao, SendPojo> implements SendService {
    @Autowired

    @Qualifier(value = "convert")
    private Queue convertQueue;

    @Resource
    private SysFileService sysFileService;

    @Resource
    private SendService sendService;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void send(SendPojo sendPojo) {
        this.save(sendPojo);
        jmsMessagingTemplate.convertAndSend(convertQueue, sendPojo);
    }

    public void send1(SendPojo sendPojo) {
        this.save(sendPojo);
        sendPojo = httpConvertPdf(sendPojo);
        download(sendPojo);
    }

    public void download(SendPojo sendPojo) {
        try {
            String url = sendPojo.getPdfUrl();
            HttpUtil.downloadFile(url, FileUtil.file("/webapp/files/pdf/" + sendPojo.getCode() + ".pdf"));
            SysFileEntity sysFileEntity = SysFileEntity.builder().fileName(sendPojo.getCode() + ".pdf")
                    .fileOldName(sendPojo.getCode() + ".pdf")
                    .fileType("pdf").fileUploadTime(new Date()).fileUrl("/files/pdf/" + sendPojo.getCode() + ".pdf").build();
            sysFileService.save(sysFileEntity);
            sendPojo.setPdfId(sysFileEntity.getId());
            sendPojo.setState("4");
            sendService.updateById(sendPojo);
        } catch (Exception e) {
            log.error("定时任务生成PDF文件失败{}", sendPojo.toString());
        }
    }

    public SendPojo httpConvertPdf(SendPojo sendPojo) {
        String host = "https://api.9yuntu.cn";
        String path = "/execute/Convert";
        String method = "GET";
        String appcode = "ef1ed3bef5a94a5a90129760008bafe5";
        Map<String, String> headers = new HashMap();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap();
        String url = "";
        try {
            url = URLEncoder.encode(sendPojo.getWordUrl(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        querys.put("docURL", url);
        querys.put("outputType", "pdf");
        querys.put("title", sendPojo.getCode());
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            if (response.getStatusLine().getStatusCode() != 200) {
                sendPojo.setState("-1");
            } else {
                String retCode = jsonObject.getString("retCode");
                if ("0".equals(retCode)) {//API的0 代表直接转换成功
                    sendPojo.setState("3");
                    JSONArray arr = jsonObject.getJSONArray("outputURLs");
                    sendPojo.setPdfUrl(String.valueOf(arr.get(0)));
                } else if ("1".equals(retCode)) {//API的1 代表还在转换中
                    sendPojo.setState("1");
                    sendPojo.setDocId(jsonObject.getString("docID"));
                } else if ("2".equals(retCode)) {//API的2 代表转换失败了
                    sendPojo.setState("2");
                }

            }
//todo:            super.updateById(sendPojo);

            return sendPojo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("生成PDF证书失败");
        }
    }
}
