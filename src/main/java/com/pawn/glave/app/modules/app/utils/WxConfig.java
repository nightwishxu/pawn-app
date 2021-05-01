package com.pawn.glave.app.modules.app.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "wx.pay")
@Component
@Data
public class WxConfig {
    //小程序appid
    private String app_id;
    //微信支付的商户id
    private String mch_id;
    //微信支付的商户密钥
    private String sercet;
    //支付成功后的服务器回调url
    private String notify_url;
    //签名方式，固定值
    private String sign_type;
    //交易类型，小程序支付的固定值为JSAPI
    private String trade_type;
    //微信统一下单接口地址
    private String pay_url;
    //商户key
    private String key;
}
