package com.pawn.glave.app.modules.app.service.impl;

import cn.hutool.http.HttpUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author xww
 * @Description:
 * @date 2021/5/5 14:15
 */
@Service
public class PaidangApiService {

    private static Logger logger = LoggerFactory.getLogger(PaidangApiService.class);


    @Value("${paidang.url}")
    private String paidangUrl;

    public void saveVideo(Integer userGoodsId,String bzVideo,String cxVideo,String jdVideo){
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("userGoodsId",userGoodsId);
        paramMap.put("bzVideo",bzVideo);
        paramMap.put("cxVideo",cxVideo);
        paramMap.put("jdVideo",jdVideo);
        String url = paidangUrl + "api/userGoods/video/save";
        String body = HttpUtil.createPost(url).form(paramMap).execute().body();
        logger.info("paidang saveVideo： param:{},result:{}",paramMap,body);
    }

    public void saveCertificate(Map<String,Object> paramMap){
        String url = paidangUrl + "api/userGoods/certificate/save";
        String body = HttpUtil.createPost(url).form(paramMap).execute().body();
        logger.info("paidang saveCertificate： url:{},param:{},result:{}",url,paramMap,body);

    }
}
