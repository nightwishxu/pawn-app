package com.pawn.glave.app.modules.app.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.support.json.JSONUtils;
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
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    @Resource
    private SendDao sendDao;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void send(SendPojo sendPojo) {
        this.save(sendPojo);
        jmsMessagingTemplate.convertAndSend(convertQueue, sendPojo);
    }

    public Long send1(SendPojo sendPojo) {
        this.save(sendPojo);
        sendPojo = httpConvertPdf(sendPojo);
        return download(sendPojo);
    }

    public Long download(SendPojo sendPojo) {
        try {
            String url = sendPojo.getPdfUrl();
            if (url == null) {
                if (sendPojo.getState().equals("2")) {
                    sendPojo = httpConvertPdf(sendPojo);
                    return download(sendPojo);
                } else {
                    sendPojo = httpConvertPdfResult(sendPojo);
                    return download(sendPojo);
                }
            } else {
                File file = FileUtil.file("/webapp/files/pdf/" + sendPojo.getCode() + ".pdf");
                HttpUtil.downloadFile(url, file);
                if (!file.exists() || file.length()==0){
                    return null;
                }
                log.info("file name:{},path:{},length:{}",file.getName(),file.getPath(),file.length());
                SysFileEntity sysFileEntity = SysFileEntity.builder().fileName(sendPojo.getCode() + ".pdf")
                        .fileOldName(sendPojo.getCode() + ".pdf")
                        .fileType("pdf").fileUploadTime(new Date()).fileUrl("/files/pdf/" + sendPojo.getCode() + ".pdf").build();
                sysFileService.save(sysFileEntity);
                sendPojo.setPdfId(sysFileEntity.getId());
                sendPojo.setState("4");
                sendService.updateById(sendPojo);
                return sysFileEntity.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("??????????????????PDF????????????{}", sendPojo.toString());
        }
        return null;
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
            log.info("httpConvertPdf status:{},response:{},info:{}", response.getStatusLine(),JSONUtil.toJsonStr(response),jsonObject!=null?jsonObject.toJSONString():null);
            if (response.getStatusLine().getStatusCode() != 200) {
                sendPojo.setState("-1");
            } else {
                String retCode = jsonObject.getString("retCode");
                if ("0".equals(retCode)) {//API???0 ????????????????????????
                    sendPojo.setState("3");
                    JSONArray arr = jsonObject.getJSONArray("outputURLs");
                    sendPojo.setPdfUrl(String.valueOf(arr.get(0)));
                } else if ("1".equals(retCode)) {//API???1 ?????????????????????
                    sendPojo.setState("1");
                    sendPojo.setDocId(jsonObject.getString("docID"));
                } else if ("2".equals(retCode)) {//API???2 ?????????????????????
                    sendPojo.setState("2");
                }

            }
            this.updateById(sendPojo);

            return sendPojo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("??????PDF????????????");
        }
    }

    public SendPojo httpConvertPdfResult(SendPojo sendPojo) {
        String host = "https://api.9yuntu.cn";
        String path = "/execute/GetOutputResult";
        String method = "GET";
        String appcode = "ef1ed3bef5a94a5a90129760008bafe5";
        Map<String, String> headers = new HashMap();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap();
        querys.put("docID", sendPojo.getDocId());
        querys.put("outputType", "pdf");
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            log.info("httpConvertPdf status:{},response:{},info:{}", response.getStatusLine(),JSONUtil.toJsonStr(response),jsonObject!=null?jsonObject.toJSONString():null);
            if (response.getStatusLine().getStatusCode() != 200) {
                sendPojo.setState("-1");
            } else {
                String retCode = jsonObject.getString("retCode");
                if ("0".equals(retCode)) {//API???0 ????????????????????????
                    sendPojo.setState("3");
                    JSONArray arr = jsonObject.getJSONArray("outputURLs");
                    sendPojo.setPdfUrl(String.valueOf(arr.get(0)));
                } else if ("1".equals(retCode)) {//API???1 ?????????????????????
                    sendPojo.setState("1");
                    sendPojo.setDocId(jsonObject.getString("docID"));
                } else if ("2".equals(retCode)) {//API???2 ?????????????????????
                    sendPojo.setState("2");
                }

            }
            this.updateById(sendPojo);

            return sendPojo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("??????PDF????????????");
        }
    }

    @Override
    public List<SendPojo> findByCodeAndType(String code){
        return sendDao.findAllByCodeLike(code);
    }

    public static void main(String[] args) {
//        HttpUtil.downloadFile("https://resource3.9yuntu.cn/documents/A4aH97JU0HUJcBqkHtM6Ip.pdf?Expires=3558073320&OSSAccessKeyId=LTAI4FwVgGYyBaSAv3hvmNEy&Signature=S%2FJ1GHr8kI7kvw3gtKtH51pQwAc%3D", FileUtil.file("/webapp/files/pdf/" + "11223399" + ".pdf"));
        File file = FileUtil.file("/webapp/files/pdf/" + "11223399" + ".pdf");
        System.out.println(file.exists());
    }
}
