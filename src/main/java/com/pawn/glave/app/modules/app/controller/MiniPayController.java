package com.pawn.glave.app.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.modules.app.annotation.Login;
import com.pawn.glave.app.modules.app.annotation.LoginUser;
import com.pawn.glave.app.modules.app.entity.ConfigPojo;
import com.pawn.glave.app.modules.app.entity.DiamondsPojo;
import com.pawn.glave.app.modules.app.entity.MiniProjectUser;
import com.pawn.glave.app.modules.app.entity.PayPojo;
import com.pawn.glave.app.modules.app.service.ConfigService;
import com.pawn.glave.app.modules.app.service.PayService;
import com.pawn.glave.app.modules.app.service.WxPayService;
import com.pawn.glave.app.modules.app.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/mini/pay")
@Api("小程序微信支付接口")
@Slf4j
public class MiniPayController {
    @Autowired
    private ConfigService configService;
    @Autowired
    private WxConfig wxConfig;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private PayService payService;

    @Login
    @RequestMapping(value = "/createOrder",method = RequestMethod.POST)
    @ApiOperation("统一下单")
    @Transactional
    public R createOrder(@LoginUser MiniProjectUser miniProjectUser){
        return wxPayService.createOrder(miniProjectUser,null,new BigDecimal(0.01));
    }


    /**
     * @Description:微信支付回调
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/notify")
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine()) != null){
            sb.append(line);
        }
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        System.out.println("接收到的报文：" + notityXml);
        Map map = WxPayUtil.xmlToMap(notityXml);
        String returnCode = (String) map.get("return_code");
        if("SUCCESS".equals(returnCode)){
            //验证签名是否正确
            if(WxPayUtil.generateSignature(map, wxConfig.getKey()).equals(MapUtils.getString(map,"sign"))){
                System.out.println("签名验证通过");
                String out_trade_no = MapUtils.getString(map,"out_trade_no");
                String err_code_des = MapUtils.getString(map,"err_code_des");
                PayPojo select = payService.getOne(new QueryWrapper<PayPojo>().eq("order_code",out_trade_no));
                if("SUCCESS".equals(MapUtils.getString(map,"result_code"))){
                    select.setState("1");
                    payService.updateById(select);
                }else{
                    select.setState("3");
                    select.setFailReason(err_code_des);
                    payService.updateById(select);
                }
                /**此处添加自己的业务逻辑代码end**/
                //通知微信服务器已经支付成功
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }else{
                System.out.println("签名验证不通过");
            }
        }else{
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        System.out.println(resXml);
        System.out.println("微信支付回调数据结束");
        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }
}
