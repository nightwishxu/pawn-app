package com.pawn.glave.app.modules.app.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.pawn.glave.app.common.utils.*;
import com.pawn.glave.app.modules.app.annotation.Login;
import com.pawn.glave.app.modules.app.annotation.LoginUser;
import com.pawn.glave.app.modules.app.entity.*;
import com.pawn.glave.app.modules.app.service.*;
import com.pawn.glave.app.modules.app.service.impl.PaidangApiService;
import com.pawn.glave.app.modules.app.utils.BarCoreUtils;
import com.pawn.glave.app.modules.app.utils.BaseUtils;
import com.pawn.glave.app.modules.app.utils.KeyUtil;
import com.pawn.glave.app.modules.app.utils.ListUtil;
import com.pawn.glave.app.modules.certificate.entity.CirculationRecord;
import com.pawn.glave.app.modules.certificate.entity.FinancialRecord;
import com.pawn.glave.app.modules.certificate.entity.ZipEntity;
import com.pawn.glave.app.modules.certificate.service.CirculationRecordService;
import com.pawn.glave.app.modules.certificate.service.FinancialRecordService;
import com.pawn.glave.app.modules.sys.entity.SysFileEntity;
import com.pawn.glave.app.modules.sys.service.SysFileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.compress.archivers.zip.ZipUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/appraisal")
public class AppraisalController {

    @Resource
    private AppraisalService appraisalService;
    @Resource
    private ExpertAppraisalService expertAppraisalService;
    @Resource
    private MiniApiService miniApiService;
    @Resource
    private CertificateService certificateService;
    @Resource
    private IntegralService integralService;
    @Resource
    private SysFileService sysFileService;
    @Resource
    private SendService sendService;

    @Resource
    private PaidangApiService paidangApiService;

    @Value("${web.upload-path}")
    private String fileUploadPath;

    @GetMapping("/list")
    public R appraisalTypePojoList(@RequestParam Map<String, Object> params) {
        PageUtils page = appraisalService.findPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 分配list
     *
     * @param params
     * @return
     */
    @GetMapping("/result/list")
    public R distributionList(@RequestParam Map<String, Object> params) {
        String name = MapUtils.getString(params, "name");
        String phone = MapUtils.getString(params, "phone");
        String appraisalId = MapUtils.getString(params, "appraisalId");//鉴定列表id
        // 查询id对应的分配列表
        List<Map<String, Object>> list = expertAppraisalService.findList(appraisalId, name, phone);
        return R.ok().put("list", list);
    }

    /**
     * 选择一个结果
     *
     * @param appraisalId
     * @param expertAppraisalId
     * @return
     */
    @GetMapping("/choose/{appraisalId}/{expertAppraisalId}")
    @Transactional
    public R choose(@PathVariable("appraisalId") String appraisalId, @PathVariable("expertAppraisalId") Long expertAppraisalId) {
        ExpertAppraisalPojo expertAppraisalPojo = ExpertAppraisalPojo.builder().id(expertAppraisalId).appraisalId(appraisalId).isSelection("1").build();
        expertAppraisalService.updateById(expertAppraisalPojo);
        ExpertAppraisalPojo select = expertAppraisalService.getById(expertAppraisalId);
        AppraisalPojo appraisalPojo = appraisalService.getById(appraisalId);
        appraisalPojo.setState(select.getState());
        appraisalPojo.setTime(select.getTime());
        appraisalPojo.setName(select.getName());
        appraisalPojo.setSize(select.getSize());
        appraisalPojo.setWeight(select.getWeight());
        appraisalPojo.setMainMaterial(select.getMainMaterial());
        appraisalPojo.setSubMaterial(select.getSubMaterial());
        appraisalPojo.setYears(select.getYears());
        appraisalPojo.setOther(select.getOther());
        appraisalPojo.setMarketLiquidity(select.getMarketLiquidity());
        appraisalPojo.setValueStability(select.getValueStability());
        appraisalPojo.setMaterialVulnerability(select.getMaterialVulnerability());
        appraisalPojo.setPawnPrice(select.getPawnPrice());
        appraisalPojo.setAppraisalUser(select.getAppraisalUser());
        appraisalPojo.setReason(select.getReason());
        appraisalService.updateById(appraisalPojo);

        //如果是鉴定成功的 被选中了 给此专家加25积分
        if (select.getState().equals("2")) {
            IntegralPojo integralPojo = new IntegralPojo();
            integralPojo.setAddIntegral("25");
            integralPojo.setAppraisalId(appraisalPojo.getId());
            integralPojo.setAppraisalNum(appraisalPojo.getNumber());
            integralPojo.setCreateTime(new Date());
            integralPojo.setExpertCode(appraisalPojo.getAppraisalUser());
            integralService.save(integralPojo);
        }
        return R.ok();
    }

    @RequestMapping("/changeAndChoose/{appraisalId}/{expertAppraisalId}")
    @Transactional
    public R changeAndChoose(
            @PathVariable("appraisalId") String appraisalId,
            @PathVariable("expertAppraisalId") Long expertAppraisalId,
            @RequestBody ExpertAppraisalPojo expertAppraisalPojo) {
        expertAppraisalPojo.setIsSelection("1");
        expertAppraisalService.updateById(expertAppraisalPojo);
        ExpertAppraisalPojo select = expertAppraisalService.getById(expertAppraisalId);
        AppraisalPojo appraisalPojo = appraisalService.getById(appraisalId);
        appraisalPojo.setState(select.getState());
        appraisalPojo.setTime(select.getTime());
        appraisalPojo.setName(select.getName());
        appraisalPojo.setSize(select.getSize());
        appraisalPojo.setWeight(select.getWeight());
        appraisalPojo.setMainMaterial(select.getMainMaterial());
        appraisalPojo.setSubMaterial(select.getSubMaterial());
        appraisalPojo.setYears(select.getYears());
        appraisalPojo.setOther(select.getOther());
        appraisalPojo.setMarketLiquidity(select.getMarketLiquidity());
        appraisalPojo.setValueStability(select.getValueStability());
        appraisalPojo.setMaterialVulnerability(select.getMaterialVulnerability());
        appraisalPojo.setPawnPrice(select.getPawnPrice());
        appraisalPojo.setAppraisalUser(select.getAppraisalUser());
        appraisalPojo.setReason(select.getReason());
        appraisalService.updateById(appraisalPojo);

        expertAppraisalPojo = expertAppraisalService.getById(expertAppraisalPojo.getId());

        Integer authResult = null;
        if ("3".equals(expertAppraisalPojo.getState())){
            authResult = 2;
        }else if ("2".equals(expertAppraisalPojo.getState())){
            if ("6".equals(appraisalPojo.getMethod())){
                authResult =4;
            }

        }

        Certificate certificate = new Certificate(null,appraisalPojo.getName(),null,null,null,null,appraisalPojo.getWeight(),appraisalPojo.getSubMaterial(),appraisalPojo.getMainMaterial(),
                appraisalPojo.getSubMaterial(),appraisalPojo.getYears(),appraisalPojo.getOther(),null, org.apache.commons.lang.StringUtils.isNotBlank(appraisalPojo.getMarketLiquidity())?Integer.valueOf(appraisalPojo.getMarketLiquidity()):null
                , org.apache.commons.lang.StringUtils.isNotBlank(appraisalPojo.getValueStability())?Integer.valueOf(appraisalPojo.getValueStability()):null, org.apache.commons.lang.StringUtils.isNotBlank(appraisalPojo.getMaterialVulnerability())?Integer.valueOf(appraisalPojo.getMaterialVulnerability()):null
                ,null,null,null,null,appraisalPojo.getUserGoodsId(),expertAppraisalPojo.getPawnPrice(),appraisalPojo.getOther(),appraisalPojo.getSize(),"3".equals(appraisalPojo.getMethod())?expertAppraisalPojo.getPawnPrice():null,"6".equals(appraisalPojo.getMethod())?expertAppraisalPojo.getPawnPrice():null,authResult);
        Map<String,Object> paramMap = BeanUtil.beanToMap(certificate);
        paidangApiService.saveCertificate(paramMap);

        //如果是鉴定成功的 被选中了 给此专家加25积分
        if (select.getState().equals("2")) {
            IntegralPojo integralPojo = new IntegralPojo();
            integralPojo.setAddIntegral("25");
            integralPojo.setAppraisalId(appraisalPojo.getId());
            integralPojo.setAppraisalNum(appraisalPojo.getNumber());
            integralPojo.setCreateTime(new Date());
            integralPojo.setExpertCode(appraisalPojo.getAppraisalUser());
            integralService.save(integralPojo);
        }
        return R.ok();
    }

    /**
     * 上传 拆箱 鉴定 包装 视频
     *
     * @param param
     * @return
     */
    @PostMapping("/save/video")
    public R saveVideo(@RequestBody Map<String, Object> param) {
        String bzVideoUploadId = MapUtils.getString(param, "bzVideoUploadId");//包装视频id
        String cxVideoUploadId = MapUtils.getString(param, "cxVideoUploadId");//拆箱视频id
        String jdVideoUploadId = MapUtils.getString(param, "jdVideoUploadId");//鉴定视频id
        Long id = MapUtils.getLong(param, "id");//鉴定主表id
        AppraisalPojo appraisalPojo = AppraisalPojo.builder().id(id).unpackingVideo(cxVideoUploadId)
                .packingVideo(bzVideoUploadId).appraisalVideo(jdVideoUploadId).build();
        appraisalService.updateById(appraisalPojo);
        AppraisalPojo byId = appraisalService.getById(id);
        if (byId!=null && byId.getUserGoodsId()!=null){
            String bzVideo = null;
            String cxVideo = null;
            String jdVideo = null;
            if(StringUtils.isNotBlank(bzVideoUploadId)){
                SysFileEntity entity = sysFileService.getById(Long.valueOf(bzVideoUploadId));
                bzVideo = entity.getFileUrl();
            }
            if(StringUtils.isNotBlank(cxVideoUploadId)){
                SysFileEntity entity = sysFileService.getById(Long.valueOf(cxVideoUploadId));
                cxVideo = entity.getFileUrl();
            }
            if(StringUtils.isNotBlank(jdVideoUploadId)){
                SysFileEntity entity = sysFileService.getById(Long.valueOf(jdVideoUploadId));
                jdVideo = entity.getFileUrl();
            }
            if (StringUtils.isNotBlank(bzVideo) || StringUtils.isNotBlank(cxVideo) || StringUtils.isNotBlank(jdVideo)){
                paidangApiService.saveVideo(byId.getUserGoodsId(),bzVideo,cxVideo,jdVideo);
            }
        }
        return R.ok();
    }

    /**
     * 获取鉴定主表 视频
     *
     * @return
     */
    @GetMapping("/get/video/{id}")
    public R getVideo(@PathVariable("id") Long id) {
        return R.ok().put("data", appraisalService.getById(id));
    }


    @GetMapping("/photo")
    public R getPhoto(@RequestParam("appraisalId") Long appraisalId) {
        List<SysFileEntity> files = appraisalService.photoList(appraisalId);
        return R.ok().put("data", files);
    }

    @GetMapping(value = "/detail")
    public R appraisalDetail(Long id) {
        AppraisalPojo appraisalPojo = miniApiService.appraisalDetail(id);
        return R.ok().put("data", appraisalPojo);
    }

    @GetMapping(value = "/certificate/reCreateZs")
    @Transactional
    public R certificateCreate(@RequestParam("id") Long id) {
        AppraisalPojo appraisalPojo = appraisalService.getById(id);
        if (StringUtils.isEmpty(appraisalPojo.getCertificateCode())) {
            return R.error("请先选择结果");
        }
        List<SendPojo> sendPojoList = sendService.findByCodeAndType(appraisalPojo.getCertificateCode() + "F");
        if (sendPojoList != null && sendPojoList.size() > 0) {
            sendPojoList.forEach(sendPojo -> {
                sendService.download(sendPojo);
            });
        }
        return R.ok();
    }

    /**
     * 生成证书
     *
     * @param certificatePojo
     * @return
     */
    @GetMapping(value = "/certificate/createNew")
    @Transactional
    public R certificateCreate(CertificatePojo certificatePojo, @RequestParam("classify") String classify) {
        AppraisalPojo appraisalPojo = new AppraisalPojo();
        appraisalPojo.setName(certificatePojo.getName());
        appraisalPojo.setSize(certificatePojo.getSize());
        appraisalPojo.setWeight(certificatePojo.getWeight());
        appraisalPojo.setMainMaterial(certificatePojo.getMainMaterial());
        appraisalPojo.setSubMaterial(certificatePojo.getSubMaterial());
        appraisalPojo.setYears(certificatePojo.getYears());
        appraisalPojo.setOther(certificatePojo.getOther());
        appraisalPojo.setMarketLiquidity(certificatePojo.getMarketLiquidity());
        appraisalPojo.setValueStability(certificatePojo.getValueStability());
        appraisalPojo.setMaterialVulnerability(certificatePojo.getMaterialVulnerability());
        appraisalPojo.setPawnPrice(certificatePojo.getPawnPrice());
        appraisalPojo.setMethod("3");
        appraisalPojo.setSource("08");
        appraisalPojo.setNumber(KeyUtil.generateUniqueKey());
        appraisalPojo.setClassify(classify);

        String code = KeyUtil.getCertificateCode(appraisalPojo);
        certificatePojo.setAppraisalCode(appraisalPojo.getNumber());
        certificatePojo.setCode(code);
        certificatePojo.setCreateTime(new Date());
        //生成条形码
        MultipartFile file = BarCoreUtils.generate(code);
        if (file != null) {
            R r = sysFileService.upload(file);
            certificatePojo.setCodeImg(r.get("file_id").toString());
        }
        //生成证书文件
        Map<String, Object> threeMap = word(certificatePojo, 3);
        Map<String, Object> twoMap = word(certificatePojo, 2);

        long threeWordId = Long.valueOf(threeMap.get("wordId").toString());
        long twoWordId = Long.valueOf(twoMap.get("wordId").toString());

        Long threeZ = pdf1(threeWordId, code + "F3" + threeWordId, threeWordId);
        Long twoZ = pdf1(twoWordId, code + "F2" + twoWordId, twoWordId);

        certificatePojo.setThreeFFileId(threeWordId);
        certificatePojo.setTwoFFileId(twoWordId);
//        certificatePojo.setThreeZFileId(threeZ);
//        certificatePojo.setTwoZFileId(twoZ);
        certificateService.save(certificatePojo);

        //设置证书CODE
        appraisalPojo.setCertificateCode(code);

//        appraisalService.updateById(appraisalPojo);
        //生成word文档格式的证书
        return R.ok();
    }

    /**
     * 生成证书
     *
     * @param certificatePojo
     * @param appraisalId
     * @return
     */
    @GetMapping(value = "/certificate/create")
    @Transactional
    public R certificateCreate(CertificatePojo certificatePojo,
                               @ApiParam(value = "鉴定记录id") @RequestParam("appraisalId") Long appraisalId) {
        AppraisalPojo appraisalPojo = appraisalService.getById(appraisalId);
        appraisalPojo.setName(certificatePojo.getName());
        appraisalPojo.setSize(certificatePojo.getSize());
        appraisalPojo.setWeight(certificatePojo.getWeight());
        appraisalPojo.setMainMaterial(certificatePojo.getMainMaterial());
        appraisalPojo.setSubMaterial(certificatePojo.getSubMaterial());
        appraisalPojo.setYears(certificatePojo.getYears());
        appraisalPojo.setOther(certificatePojo.getOther());
        appraisalPojo.setMarketLiquidity(certificatePojo.getMarketLiquidity());
        appraisalPojo.setValueStability(certificatePojo.getValueStability());
        appraisalPojo.setMaterialVulnerability(certificatePojo.getMaterialVulnerability());
        appraisalPojo.setPawnPrice(certificatePojo.getPawnPrice());

        String code = KeyUtil.getCertificateCode(appraisalPojo);
        certificatePojo.setAppraisalCode(appraisalPojo.getNumber());
        certificatePojo.setCode(code);
        certificatePojo.setCreateTime(new Date());
        //生成条形码
        MultipartFile file = BarCoreUtils.generate(code);
        if (file != null) {
            R r = sysFileService.upload(file);
            certificatePojo.setCodeImg(r.get("file_id").toString());
        }
        //生成证书文件
        Map<String, Object> threeMap = word(certificatePojo, 3);
        Map<String, Object> twoMap = word(certificatePojo, 2);

        long threeWordId = Long.valueOf(threeMap.get("wordId").toString());
        long twoWordId = Long.valueOf(twoMap.get("wordId").toString());

        Long threeZ = pdf1(threeWordId, code + "F3" + threeWordId, threeWordId);
        Long twoZ = pdf1(twoWordId, code + "F2" + twoWordId, twoWordId);

        certificatePojo.setThreeFFileId(threeWordId);
        certificatePojo.setTwoFFileId(twoWordId);
//        certificatePojo.setThreeZFileId(threeZ);
//        certificatePojo.setTwoZFileId(twoZ);
        certificateService.save(certificatePojo);

        //设置证书CODE
        appraisalPojo.setCertificateCode(code);

        appraisalService.updateById(appraisalPojo);
        //生成word文档格式的证书
        return R.ok();
    }

    /**
     * 注入鉴定记录service
     */
    @Resource
    private CirculationRecordService circulationRecordService;

    @Resource
    private FinancialRecordService financialRecordService;


    /**
     * 创建证书正面
     *
     * @param appraisalCode 鉴定编号
     * @return
     */
    @GetMapping(value = "/certificate/createZ")
    public R createTwoFoldFront(String appraisalCode) {
        LambdaQueryWrapper<CertificatePojo> certificateQueryWrapper = new LambdaQueryWrapper<>();
        certificateQueryWrapper.eq(CertificatePojo::getAppraisalCode, appraisalCode);
        CertificatePojo certificatePojo = certificateService.getOne(certificateQueryWrapper);
        /**
         * 获取鉴定ID
         */
        LambdaQueryWrapper<AppraisalPojo> appraisalQueryWrapper = new LambdaQueryWrapper<>();
        appraisalQueryWrapper.eq(AppraisalPojo::getNumber, appraisalCode);
        AppraisalPojo appraisal = appraisalService.getOne(appraisalQueryWrapper);

        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> circulationList = new ArrayList<>();

        /**
         * 获取证书流通记录
         */
        LambdaQueryWrapper<CirculationRecord> circulationQueryWrapper = new LambdaQueryWrapper<>();
        circulationQueryWrapper
                .eq(CirculationRecord::getCertificateId, certificatePojo.getId()).orderByDesc(CirculationRecord::getId);
        List<CirculationRecord> circulationRecords = circulationRecordService.list(circulationQueryWrapper);

        if (ListUtil.isNotNull(circulationRecords)) {
            for (int i = 0; i < circulationRecords.size(); i++) {
                CirculationRecord circulationRecord = circulationRecords.get(i);
                Map<String, Object> circulationMap = new HashMap<>();
                circulationMap.put("date", formatDate(circulationRecord.getDate()));
                circulationMap.put("type", circulationRecord.getMode());
                circulationMap.put("price", circulationRecord.getPrice());
                circulationMap.put("org", circulationRecord.getTarget());
                circulationMap.put("remark", circulationRecord.getRemark());
                circulationList.add(circulationMap);
                if (i == 3) break;
            }
        }
        data.put("circulationList", circulationList);

        /**
         * 获取证书金融记录 最多六条
         */
        List<Map<String, Object>> financialList = new ArrayList<>();
        LambdaQueryWrapper<FinancialRecord> financialQueryWrapper = new LambdaQueryWrapper<>();
        financialQueryWrapper.eq(FinancialRecord::getCertificateId, certificatePojo.getId())
                .orderByDesc(FinancialRecord::getId);
        List<FinancialRecord> financialRecords = financialRecordService.list(financialQueryWrapper);
        if (ListUtil.isNotNull(financialRecords)) {
            for (int i = 0; i < financialRecords.size(); i++) {
                FinancialRecord financialRecord = financialRecords.get(i);
                Map<String, Object> financialMap = new HashMap<>();
                financialMap.put("date", formatDate(financialRecord.getDate()));
                financialMap.put("mortgage", financialRecord.getMortgageFinancial());
                financialMap.put("org", financialRecord.getMechanism());
                financialMap.put("remark", financialRecord.getRemark());
                financialList.add(financialMap);
                if (i == 5) break;
            }
        }
        data.put("financialList", financialList);
        data.put("remarks", "");

//        String qrCode = QRCodeUtil.crateQRCode("https://mycz.glaveinfo.com/paidang/check.html?number=" + appraisal.getNumber() + "&id=" + appraisal.getId() + "&certificateId=" + certificatePojo.getId(), 100, 100);
        String qrCode = QRCodeUtil.crateQRCode("https://paidang2.su.bcebos.com/pawnWechat/check.html?number=" + certificatePojo.getAppraisalCode() + "&id=" + certificatePojo.getId() + "&certificateId=" + certificatePojo.getId() + "&code=" + certificatePojo.getCode(), 200, 200);
        data.put("ewm", qrCode);

        Long threeZFileId = createWordFile(data, "/home/soft/template/zs-szy-z.ftl");
        Long twoZFileId = createWordFile(data, "/home/soft/template/zs-ezy-z.ftl");


        pdf1(threeZFileId, certificatePojo.getCode() + "Z3" + threeZFileId, +threeZFileId);
        pdf1(twoZFileId, certificatePojo.getCode() + "Z2" + twoZFileId, twoZFileId);

        /**
         * 修改证书
         */
        LambdaUpdateWrapper<CertificatePojo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CertificatePojo::getAppraisalCode, appraisalCode)
                .set(CertificatePojo::getThreeZFileId, threeZFileId)
                .set(CertificatePojo::getTwoZFileId, twoZFileId);
        certificateService.update(updateWrapper);
        return R.ok();
    }

    private Long createWordFile(Map<String, Object> data, String templateFile) {
        for (String key : data.keySet()) {
            log.info("key = {}", key);
            log.info("value = {}", data.get(key));
        }
        String fileId = com.pawn.glave.app.common.utils.StringUtils.getUUID();
        String exportFile = fileUploadPath + "files/word/" + fileId + ".doc";
        WordExportUtil.getInstance().createDocFile(templateFile, data, exportFile, 1);

        SysFileEntity sysFileEntity = SysFileEntity.builder().fileName(fileId + ".doc")
                .fileOldName(fileId + ".doc")
                .fileType("doc").fileUploadTime(new Date()).fileUrl("/files/word/" + fileId + ".doc").build();
        sysFileService.save(sysFileEntity);

        return sysFileEntity.getId();
    }

    private String formatDate(Date date) {
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy年MM月dd日");
        return formatter2.format(date);
    }

    public Map<String, Object> word(CertificatePojo certificatePojo, Integer createType) {

        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(certificatePojo);
        jsonObject.put("zsewm", getImageBase(certificatePojo.getCodeImg()));
//        setLtx(jsonObject);//设置流通性
//        setJzwdx(jsonObject);//设置价值稳定性
//        setCzysx(jsonObject);//设置材质易损性
//        setAttention(jsonObject);//设置保养注意事项
        jsonObject.put("zsbh", certificatePojo.getCode());
        jsonObject.put("material", certificatePojo.getMainMaterial());
        setImages(jsonObject);

        String currentFileId = com.pawn.glave.app.common.utils.StringUtils.getUUID();
        String exportFile = fileUploadPath + "files/word/" + currentFileId + ".doc";
        //String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//        String templateFile = "/home/soft/template/zs.ftl";
        String templateFile = "";
        if (createType == null || createType == 3) {
            //三折页
            templateFile = "/home/soft/template/zs-szy.ftl";
        } else if (createType == 2) {
            //二折页
            templateFile = "/home/soft/template/zs-ezy.ftl";
        }
        WordExportUtil.getInstance().createDocFile(templateFile, jsonObject, exportFile, 1);
        SysFileEntity sysFileEntity = SysFileEntity.builder().fileName(currentFileId + ".doc")
                .fileOldName(currentFileId + ".doc")
                .fileType("doc").fileUploadTime(new Date()).fileUrl("/files/word/" + currentFileId + ".doc").build();
        sysFileService.save(sysFileEntity);

        Map<String, Object> result = new HashMap<>();
        result.put("wordId", sysFileEntity.getId());
        return result;
    }

    public Long pdf1(Long word_id, String code, long fileId) {
        SysFileEntity word = sysFileService.getById(word_id);
        String url = "https://www.paidangwang.net/pawn";
        SendPojo sendPojo = SendPojo.builder().code(code).state("0").wordUrl(url + word.getFileUrl()).build();
        sendPojo.setBatchId(fileId);
        return sendService.send1(sendPojo);
    }

    public void pdf(Long word_id, String code) {
        SysFileEntity word = sysFileService.getById(word_id);
        String url = "https://www.paidangwang.net/pawn";
        SendPojo sendPojo = SendPojo.builder().code(code).state("0").wordUrl(url + word.getFileUrl()).build();
        sendService.send(sendPojo);
    }

    public void setImages(JSONObject jsonObject) {
        String images = jsonObject.getString("images");
        String defaultImage = "/home/soft/template/default.jpg";
        if (StringUtils.isBlank(images)) {
            jsonObject.put("pic1", WordExportUtil.getInstance().getImageBase(defaultImage));
            jsonObject.put("pic2", WordExportUtil.getInstance().getImageBase(defaultImage));
            jsonObject.put("pic3", WordExportUtil.getInstance().getImageBase(defaultImage));
            jsonObject.put("pic4", WordExportUtil.getInstance().getImageBase(defaultImage));
        } else {
            String[] imageArr = images.split(",");
            jsonObject.put("pic1", imageArr.length > 0 ? getImageBase(imageArr[0]) : WordExportUtil.getInstance().getImageBase(defaultImage));
            jsonObject.put("pic2", imageArr.length > 1 ? getImageBase(imageArr[1]) : WordExportUtil.getInstance().getImageBase(defaultImage));
            jsonObject.put("pic3", imageArr.length > 2 ? getImageBase(imageArr[2]) : WordExportUtil.getInstance().getImageBase(defaultImage));
            jsonObject.put("pic4", imageArr.length > 3 ? getImageBase(imageArr[3]) : WordExportUtil.getInstance().getImageBase(defaultImage));
        }
    }

    //获得图片的base64码
    public String getImageBase(String fileId) {
        SysFileEntity sysFileEntity = sysFileService.getById(fileId);
        if (sysFileEntity == null) {
            return "";
        }
        if (sysFileEntity.getType()!=null && sysFileEntity.getType()==1){
            try {
                byte[] bytes = BaseUtils.toByteArray(BaseUtils.getImageInputStream(sysFileEntity.getFileUrl()));
                BASE64Encoder encoder = new BASE64Encoder();
                return encoder.encode(bytes);
            } catch (IOException e) {
                e.printStackTrace();

                return null;
            }

        }else {
            String src = fileUploadPath + sysFileEntity.getFileUrl();
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

    }

    public static void main(String[] args) throws Exception {
        byte[] bytes = BaseUtils.toByteArray(BaseUtils.getImageInputStream("https://paidang2.cdn.bcebos.com/objectStream/2021-5-11/9188a2276a764584a8954b7fd5f94f60.jpg"));
        BASE64Encoder encoder = new BASE64Encoder();
        System.out.println(encoder.encode(bytes));
    }

//    public static void setValue(JSONObject jsonObject,String[] names,int star){
////        for(int i = 0; i < 5; i++){
////            if(i<star){
////                jsonObject.put(names[i],"rId12");
////            }else{
////                jsonObject.put(names[i],"rId13");
////            }
////        }
//        for(int i = 0; i < 5; i++){
//            if(i<star){
//                jsonObject.put(names[i],"rId9");
//            }else{
//                jsonObject.put(names[i],"rId10");
//            }
//        }
//    }
//
//    public void setLtx(JSONObject jsonObject){
//        int ltx = jsonObject.getIntValue("marketLiquidity");
//        String[] names = new String[]{"ltxOne","ltxTwo","ltxThree","ltxFour","ltxFive"};
//        setValue(jsonObject,names,ltx);
//    }
//
//    public void setJzwdx(JSONObject jsonObject){
//        int jzwdx = jsonObject.getIntValue("valueStability");
//        String[] names = new String[]{"jzwdxOne","jzwdxTwo","jzwdxThree","jzwdxFour","jzwdxFive"};
//        setValue(jsonObject,names,jzwdx);
//    }
//
//    public void setCzysx(JSONObject jsonObject){
//        int czysx = jsonObject.getIntValue("materialVulnerability");
//        String[] names = new String[]{"czysxOne","czysxTwo","czysxThree","czysxFour","czysxFive"};
//        setValue(jsonObject,names,czysx);
//    }
//
//    public void setAttention(JSONObject jsonObject){
//        String isDampproof = jsonObject.getString("isDampproof");
//        jsonObject.put("isDampproof","1".equals(isDampproof)?"rId12":"rId11");
//
//        String isShockproof = jsonObject.getString("isShockproof");
//        jsonObject.put("isShockproof","1".equals(isShockproof)?"rId12":"rId11");
//
//        String isMouldproof = jsonObject.getString("isMouldproof");
//        jsonObject.put("isMouldproof","1".equals(isMouldproof)?"rId12":"rId11");
//
//        String isPestcontrol = jsonObject.getString("isPestcontrol");
//        jsonObject.put("isPestcontrol","1".equals(isPestcontrol)?"rId12":"rId11");
//
//        String isAntipyretic = jsonObject.getString("isAntipyretic");
//        jsonObject.put("isAntipyretic","1".equals(isAntipyretic)?"rId12":"rId11");
//
//        String isShatterproof = jsonObject.getString("isShatterproof");
//        jsonObject.put("isShatterproof","1".equals(isShatterproof)?"rId12":"rId11");
//
//        String isUltravioletproof = jsonObject.getString("isUltravioletproof");
//        jsonObject.put("isUltravioletproof","1".equals(isUltravioletproof)?"rId12":"rId11");
//    }


    @GetMapping(value = "/getCertificate")
    public R getCertificate(@ApiParam(value = "鉴定记录id") @RequestParam("appraisalId") Long appraisalId) {
        AppraisalPojo appraisalPojo = appraisalService.getById(appraisalId);
        CertificatePojo certificatePojo = certificateService.getOne(
                new QueryWrapper<CertificatePojo>().eq("code", appraisalPojo.getCertificateCode()));
        return R.ok().put("data", certificatePojo);
    }
}
