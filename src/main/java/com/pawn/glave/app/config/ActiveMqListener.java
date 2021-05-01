package com.pawn.glave.app.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pawn.glave.app.common.utils.HttpUtils;
import com.pawn.glave.app.modules.app.entity.SendPojo;
import com.pawn.glave.app.modules.app.service.SendService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 队列监听
 */
@Service
@Slf4j
public class ActiveMqListener {
    @Autowired
    private SendService sendService;
    /**
     * 通过监听目标队列实现功能
     */
    @JmsListener(destination = "pawn.convert")
    public void dealSenderMessageMQ(ObjectMessage message) {
        try {
            Serializable object = message.getObject();
            SendPojo sendPojo = (SendPojo) object;
            log.info("队列监听到数据{}", object);
            httpConvertPdf(sendPojo);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    public void httpConvertPdf(SendPojo sendPojo){
        String host = "https://api.9yuntu.cn";
        String path = "/execute/Convert";
        String method = "GET";
        String appcode = "ef1ed3bef5a94a5a90129760008bafe5";
        Map<String, String> headers = new HashMap();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap();
        String url = "";
        try {
            url = URLEncoder.encode( sendPojo.getWordUrl(), "UTF-8" );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        querys.put("docURL", url);
        querys.put("outputType", "pdf");
        querys.put("title", sendPojo.getCode());
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            log.info("========================================");
            log.info("response=>{}",response.toString());
            log.info("jsonObject=>{}",jsonObject.toString());
            log.info("========================================");
            if(response.getStatusLine().getStatusCode() != 200){
                sendPojo.setState("-1");
            }else{
                String retCode = jsonObject.getString("retCode");
                if("0".equals(retCode)){//API的0 代表直接转换成功
                    sendPojo.setState("3");
                    JSONArray arr = jsonObject.getJSONArray("outputURLs");
                    sendPojo.setPdfUrl(String.valueOf(arr.get(0)));
                } else if("1".equals(retCode)){//API的1 代表还在转换中
                    sendPojo.setState("1");
                    sendPojo.setDocId(jsonObject.getString("docID"));
                } else if("2".equals(retCode)){//API的2 代表转换失败了
                    sendPojo.setState("2");
                }

            }
            sendService.updateById(sendPojo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    