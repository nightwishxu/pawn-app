package com.pawn.glave.app.common.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import sun.misc.BASE64Encoder;

public class WordExportUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(WordExportUtil.class);

    private static WordExportUtil service = null;

    private WordExportUtil() {
        super();
    }

    public static WordExportUtil getInstance() {
        if (service == null) {
            synchronized (WordExportUtil.class) {
                if (service == null) {
                    service = new WordExportUtil();
                }
            }
        }
        return service;
    }

    public static void main(String[] args) {

        String base64 = WordExportUtil.getInstance().getImageBase("D:\\20200902092617.png");
        String str = "{\"mainMaterial\":\"1\",\"other\":\"2\",\"code\":\"608122020081215181372299\",\"valueStability\":\"6\",\"zsbh\":\"608122020081215181372299\",\"isPestcontrol\":\"1\",\"isMouldproof\":\"1\",\"isDampproof\":\"1\",\"czysxOne\":[{\"id\":\"262\"},{\"id\":\"263\"},{\"id\":\"264\"},{\"id\":\"265\"},{\"id\":\"266\"}],\"materialVulnerability\":\"5\",\"years\":\"咋\",\"antipyreticRemark\":\"2\",\"zsewm\":\""+base64+"\",\"pic1\":\"\",\"isAntipyretic\":\"1\",\"jzwdxOne\":[{\"id\":\"50\"},{\"id\":\"46\"},{\"id\":\"28\"},{\"id\":\"39\"},{\"id\":\"41\"}],\"defectDescription\":\"12312大发发\",\"czysxTwo\":[],\"ltxTwo\":\"rId12\",\"ltxThree\":\"rId12\",\"ltxFour\":\"rId12\",\"ltxFive\":\"rId13\",\"appraisalCode\":\"1596976437457135935544\",\"pic2\":\"\",\"pic3\":\"\",\"pic4\":\"\",\"images\":\"960,961,962,956\",\"isShatterproof\":\"1\",\"weight\":\"1\",\"subMaterial\":\"2\",\"isUltravioletproof\":\"1\",\"marketLiquidity\":\"9\",\"shatterproofRemark\":\"2\",\"jzwdxTwo\":[],\"ltxOne\":\"rId12\",\"size\":\"1\",\"material\":\"1\",\"codeImg\":\"990\",\"createTime\":1597216693722,\"name\":\"1\",\"pawnPrice\":1800.00,\"dampproofRemark\":\"1\",\"mouldproofRemark\":\"3\",\"isShockproof\":\"1\",\"ultravioletproofRemark\":\"哈哈哈\",\"shockproofRemark\":\"2\",\"pestcontrolRemark\":\"3\"}";
        JSONObject jsonObject = JSONObject.parseObject(str);
        String templateFile = "C:\\zs.ftl";
        String exportFile = "D:\\zs.doc";

        try {
            WordExportUtil.getInstance().createDocFile(templateFile, jsonObject, exportFile, 1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param templateFilePath eg: /template/test/test.ftl
     * @param dataMap
     * @param exportFilePath   eg: /tmp/test/test123.doc
     * @param loadType         设置路径加载方式。1-绝对路径，2-项目相对路径
     * @return
     * @throws Exception
     */
    public File createDocFile(String templateFilePath, Map<String, Object> dataMap, String exportFilePath, int loadType) {
//        Template t = null;
//        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
//        configuration.setDefaultEncoding("UTF-8");
//        Writer out = null;
//        try {
//            templateFilePath = pathReplace(templateFilePath);
//            String ftlPath = templateFilePath.substring(0, templateFilePath.lastIndexOf("/"));
//            if (loadType == 1) {
//                configuration.setDirectoryForTemplateLoading(new File(ftlPath)); // FTL文件所存在的位置
//            } else {
//                configuration.setClassForTemplateLoading(this.getClass(), ftlPath);//以类加载的方式查找模版文件路径
//            }
//            String ftlFile = templateFilePath.substring(templateFilePath.lastIndexOf("/") + 1);
//            t = configuration.getTemplate(ftlFile); // 模板文件名
//            File outFile = new File(exportFilePath);
//            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
//            t.process(dataMap, out);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("导出word文档出错");
//        }finally {
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return null;

        Template t = null;
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setDefaultEncoding("UTF-8");
        Writer out = null;
        try {
            templateFilePath = pathReplace(templateFilePath);
            String ftlPath = templateFilePath.substring(0, templateFilePath.lastIndexOf("/"));
            if (loadType == 1) {
                configuration.setDirectoryForTemplateLoading(new File(ftlPath)); // FTL文件所存在的位置
            } else {
                configuration.setClassForTemplateLoading(this.getClass(), ftlPath);//以类加载的方式查找模版文件路径
            }
            String ftlFile = templateFilePath.substring(templateFilePath.lastIndexOf("/") + 1);
            t = configuration.getTemplate(ftlFile,"UTF-8"); // 模板文件名
            File outFile = new File(exportFilePath);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
            t.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("导出word文档出错");
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    //获得图片的base64码
    public String getImageBase(String src) {
        if (src == null || src == "") {
            return "";
        }
        File file = new File(src);
        if (!file.exists()) {
            return "";
        }
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * 把路径的\替换成/
     *
     * @param path
     * @return
     */
    private String pathReplace(String path) {
        while (path != null && path.contains("\\")) {
            path = path.replace("\\", "/");
        }
        return path;
    }

    /**
     * 测试用的
     *
     * @param dataMap
     */
    public static void getData(Map<String, Object> dataMap) throws Exception {
        dataMap.put("name", "10");
        dataMap.put("size", "1");
        dataMap.put("weight", "12.2");
        dataMap.put("material", "石头");
        dataMap.put("years", "1999");
        dataMap.put("pawnPrice", "10800");
        dataMap.put("other", "没了");
        dataMap.put("mainMaterial", "haha");
        dataMap.put("subMaterial", "bbbb");
        dataMap.put("zsewm", WordExportUtil.getInstance().getImageBase("D:\\23ee39507302412c9ffce79cd7b4761b.608022020080415001257899"));
        dataMap.put("pic1",WordExportUtil.getInstance().getImageBase("D:\\20200811094052.png"));
        dataMap.put("pic2", WordExportUtil.getInstance().getImageBase("D:\\20200811095113.png"));
        dataMap.put("pic3", WordExportUtil.getInstance().getImageBase("D:\\u=2705060440,3986463846&fm=26&gp=0.jpg"));
        dataMap.put("pic4", WordExportUtil.getInstance().getImageBase("D:\\u=2013753808,1049443167&fm=26&gp=0.jpg"));
        dataMap.put("zsbh","123123");
        //流通性
        int ltx = 2;
        List<Map<String,String>> ltxOne = new ArrayList<>();
        List<Map<String,String>> ltxTwo = new ArrayList<>();
        String[] ltxIds = {"267","268","269","270","271"};
        for(int i = 0; i < 5; i++){
            Map<String,String> map = new HashMap<>();
            map.put("id",ltxIds[i]);
            if(i<ltx){
                ltxOne.add(map);
            }else{
                ltxTwo.add(map);
            }
        }
        dataMap.put("ltxOne",ltxOne);
        dataMap.put("ltxTwo",ltxTwo);
        //价值稳定性
        List<Map<String,String>> jzwdxOne = new ArrayList<>();
        Map<String,String> _jzwdxMapOne = new HashMap<>();
        _jzwdxMapOne.put("id","50");
        jzwdxOne.add(_jzwdxMapOne);
        Map<String,String> _jzwdxMapTwo = new HashMap<>();
        _jzwdxMapTwo.put("id","46");
        jzwdxOne.add(_jzwdxMapTwo);
        Map<String,String> _jzwdxMapThree = new HashMap<>();
        _jzwdxMapThree.put("id","28");
        jzwdxOne.add(_jzwdxMapThree);
        dataMap.put("jzwdxOne",jzwdxOne);
        List<Map<String,String>> jzwdxTwo = new ArrayList<>();
        Map<String,String> _jzwdxMapFour = new HashMap<>();
        _jzwdxMapFour.put("id","39");
        jzwdxTwo.add(_jzwdxMapFour);
        Map<String,String> _jzwdxMapFive = new HashMap<>();
        _jzwdxMapFive.put("id","41");
        jzwdxTwo.add(_jzwdxMapFive);
        dataMap.put("jzwdxTwo",jzwdxTwo);
        //材质易损性
        int czysx = 0;
        List<Map<String,String>> czysxOne = new ArrayList<>();
        List<Map<String,String>> czysxTwo = new ArrayList<>();
        String[] czysxIds = {"262","263","264","265","266"};
        for(int i = 0; i < 5; i++){
            Map<String,String> map = new HashMap<>();
            map.put("id",czysxIds[i]);
            if(i<czysx){
                czysxOne.add(map);
            }else{
                czysxTwo.add(map);
            }
        }
        dataMap.put("czysxOne",czysxOne);
        dataMap.put("czysxTwo",czysxTwo);

        dataMap.put("pcbz","防虫备注");
        dataMap.put("upbz","防紫外线备注");
        dataMap.put("mpbz","防潮备注");
        dataMap.put("mpbz1","防霉备注");
        dataMap.put("spbz","防碎备注");
        dataMap.put("qpbz","防震备注");
        dataMap.put("tpbz","防热备注");
        dataMap.put("defectDescription","没什么瑕疵");
    }
}
