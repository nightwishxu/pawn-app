package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pawn.glave.app.common.utils.IPUtils;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.modules.app.annotation.LoginUser;
import com.pawn.glave.app.modules.app.entity.ConfigPojo;
import com.pawn.glave.app.modules.app.entity.MiniProjectUser;
import com.pawn.glave.app.modules.app.entity.PayPojo;
import com.pawn.glave.app.modules.app.service.ConfigService;
import com.pawn.glave.app.modules.app.service.PayService;
import com.pawn.glave.app.modules.app.service.WxPayService;
import com.pawn.glave.app.modules.app.utils.HttpUtils;
import com.pawn.glave.app.modules.app.utils.WxConfig;
import com.pawn.glave.app.modules.app.utils.WxPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WxPayServiceImpl implements WxPayService {
    @Resource
    private WxConfig wxConfig;

    @Resource
    private HttpServletRequest request;

    @Resource
    private PayService payService;

    @Resource
    private ConfigService configService;

    public R createOrder(MiniProjectUser miniProjectUser,String appraisalCode, BigDecimal payMoney){
//        ConfigPojo configPojo = configService.getOne(new QueryWrapper<ConfigPojo>().eq("config_name","pay_money"));
//        log.info("用户{}调用同意下单接口，支付金额{}",miniProjectUser.getName(),configPojo.getConfigValue());
        String openId = miniProjectUser.getMiniOpenId();
        if(StringUtils.isBlank(openId)){
            throw new RuntimeException("请先微信授权再使用支付功能");
        }
        String mch_id= wxConfig.getMch_id(); //商户号
        int money =  Integer.valueOf(payMoney.multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString());
        String today = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String WXPay= WxPayUtil.createCode(5);
        String out_trade_no=mch_id+today+WXPay;//生成订单号
        Map<String,String> result=new HashMap<>();
        //去Service层中去生成签名,用户openid out_trade_no订单号  money支付的金额
        String formData=getOpenId(openId,out_trade_no,money,miniProjectUser,appraisalCode);
        //在servlet层中生成签名成功后，把下单所要的参数以xml的格式拼接，发送下单接口
        String httpResult = HttpUtils.httpXMLPost(wxConfig.getPay_url(),formData);
        //xml转换成Map对象或者值
        Map<String, Object> resultMap = WxPayUtil.xmlToMap(httpResult);
        if("FAIL".equals(MapUtils.getString(resultMap,"return_code"))){
            throw new RuntimeException(MapUtils.getString(resultMap,"return_msg"));
        }
        if("FAIL".equals(MapUtils.getString(resultMap,"result_code"))){
            throw new RuntimeException(MapUtils.getString(resultMap,"err_code_des"));
        }
        result.put("package", "prepay_id=" + resultMap.get("prepay_id")); //这里是拿下单成功的微信交易号去拼接，因为在下面的接口中必须要这个样子
        result.put("nonceStr",MapUtils.getString(resultMap,"nonce_str")); //随机字符串
        String times= WxPayUtil.getCurrentTimestamp()+""; //获取当前时间
        result.put("timeStamp",times); //当前时间戳
        //生成调用支付接口要的签名
        Map<String, Object> packageParams = new HashMap<>();
        packageParams.put("appId", wxConfig.getApp_id());
        packageParams.put("signType", wxConfig.getSign_type());
        packageParams.put("nonceStr",result.get("nonceStr")+"");
        packageParams.put("timeStamp",times);
        packageParams.put("package", result.get("package")+"");//商户订单号
        String sign= WxPayUtil.generateSignature(packageParams, wxConfig.getKey()); //生成签名:
        result.put("paySign",sign);
        result.put("out_trade_no",out_trade_no);
        log.info("签名成功----->"+result.get("paySign"));
        return R.ok().put("data",result);
    }

    @Override
    public String getOpenId(String openId, String out_trade_no, int money,MiniProjectUser miniProjectUser,String appraisalCode) {
        //下单的金额，因为在微信支付中默认是分所以要这样处理
        String nonceStr= WxPayUtil.generateUUID(); //设置UUID作为随机字符串
        Map<String ,Object> map = new HashMap();
        map.put("appid",wxConfig.getApp_id()); //商户appid
        map.put("mch_id", wxConfig.getMch_id());//商户号
        map.put("nonce_str",nonceStr); //随机数
        map.put("body","鉴定支付");//商户名称
        map.put("out_trade_no",out_trade_no);//商户订单号
        map.put("total_fee",money);//下单金额
        map.put("spbill_create_ip", IPUtils.getIpAddr(request));//终端IP
        map.put("notify_url",wxConfig.getNotify_url());//回调地址 这里的接口必须是在线上用户支付成功才能收到微信发送的信息
        map.put("trade_type",wxConfig.getTrade_type());//交易类型
        map.put("openid",openId+"");//用户openid
        map.put("sign_type",wxConfig.getSign_type());//加密类型
        String sign= WxPayUtil.generateSignature(map, wxConfig.getKey()); //生成sign签名WeChatTool.sercet_key是商户的支付秘钥
        map.put("sign",sign);
        String xmlString = WxPayUtil.mapToXml(map);

        PayPojo payPojo = PayPojo.builder().orderCode(out_trade_no).nonceStr(nonceStr).payeeId(miniProjectUser.getId()).appraisalCode(appraisalCode)
                .state("0").createTime(new Date()).payeeName(miniProjectUser.getName())
                .payeePhone(miniProjectUser.getPhone()).orderInfo("鉴定支付")
                .totalFee((long) money).build();
        payService.save(payPojo);
        return xmlString;
    }
}
