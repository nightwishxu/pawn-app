package com.pawn.glave.app.modules.app.utils;

import com.pawn.glave.app.modules.app.entity.AppraisalPojo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class KeyUtil {

    private static final ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyyyMMddHHmmssSSS"));

    /**
     * 生成主键id
     * 时间+随机数
     *
     * @return
     */
    public static synchronized String generateUniqueKey() {
        Random random = new Random();
        // 随机数的量 自由定制，这是9位随机数
        Integer r = random.nextInt(900000000) + 100000000;
        // 返回  13位时间
        Long timeMillis = System.currentTimeMillis();
        return timeMillis + String.valueOf(r);
    }

    /**
     * 生成证书code
     *
     * @param appraisalPojo
     * @return
     */
    public static synchronized String getCertificateCode(AppraisalPojo appraisalPojo) {
        String marketLiquidity = appraisalPojo.getMarketLiquidity();
        Long ltx = Long.parseLong(marketLiquidity);
        String attribute = ltx < 3 ? "3" : "6";//证书属性
        String source = appraisalPojo.getSource();
        String classify = appraisalPojo.getClassify();
        String time = sdf.get().format(new Date());
        String object = "9";
        String method = appraisalPojo.getMethod();
        String type = "";
        if ("0".equals(method))
            type = "6";
        else if ("1".equals(method))
            type = "3";
        else if ("2".equals(method))
            type = "9";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(attribute).append(source).append(classify).append(time).append(marketLiquidity)
                .append(object).append(type);
        return stringBuffer.toString();
    }
}
