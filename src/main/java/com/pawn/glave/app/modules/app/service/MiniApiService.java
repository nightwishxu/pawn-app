package com.pawn.glave.app.modules.app.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.pawn.glave.app.common.exception.RRException;
import com.pawn.glave.app.common.utils.Constant;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.common.utils.StringUtils;
import com.pawn.glave.app.modules.app.dao.AppraisalAgainDao;
import com.pawn.glave.app.modules.app.entity.*;
import com.pawn.glave.app.modules.app.utils.KeyUtil;
import com.pawn.glave.app.modules.sys.service.AppraisalCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * api相关方法
 */
@Service
@Slf4j
public class MiniApiService {
    @Autowired
    private AppraisalService appraisalService;
    @Autowired
    private PayService payService;

    @Autowired
    private GemstoneService gemstoneService;
    @Autowired
    private JadeiteService jadeiteService;
    @Autowired
    private LuxuriesService luxuriesService;
    @Autowired
    private MetalService metalService;
    @Autowired
    private NephriteService nephriteService;
    @Autowired
    private WatchService watchService;
    @Autowired
    private OtherService otherService;
    @Autowired
    private DiamondsService diamondsService;
    @Autowired
    private PorcelainService porcelainService;
    @Autowired
    private CalligraphyService calligraphyService;
    @Autowired
    private PaintingService paintingService;
    @Autowired
    private WenwanService wenwanService;
    @Autowired
    private CertificateService certificateService;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private KdService kdService;
    @Autowired
    private ExpertAppraisalService expertAppraisalService;

    @Resource
    private AgainAppraisalService againAppraisalService;

    @Resource
    private AppraisalCategoryService appraisalCategoryService;

    /**
     * 保存鉴定表
     *
     * @param classify
     * @param number
     * @param coverPhoto
     * @param createUser
     * @param goodsCode
     */
    public R saveInAppraisal(String classify, String number, String coverPhoto, Long createUser, String goodsCode, String source, MiniProjectUser miniProjectUser) {
        AppraisalPojo appraisalPojo = AppraisalPojo.builder().classify(classify).number(number)
                .coverPhoto(coverPhoto).createUser(createUser).source(source)
                .createTime(new Date()).goodsCode(goodsCode).state("0").method("3").build();

        //获取鉴定费用
        BigDecimal payMoney = getPayMoney(classify);

        if (payMoney.compareTo(BigDecimal.ZERO) == 0) {
            throw new RRException("提交失败");
        }

        R r = wxPayService.createOrder(miniProjectUser, number, payMoney);
        appraisalService.save(appraisalPojo);
        return r;
    }

    private BigDecimal getPayMoney(String classify) {
        BigDecimal payMoney = BigDecimal.ZERO;
        switch (classify) {
            case "01":
                payMoney = appraisalCategoryService.getPriceById(Constant.AppraisalCategoryEnum.DIAMONDS.getId());
                break;
            case "02":
                payMoney = appraisalCategoryService.getPriceById(Constant.AppraisalCategoryEnum.COLORED_GEMSTONE.getId());
                break;
            case "03":
                payMoney = appraisalCategoryService.getPriceById(Constant.AppraisalCategoryEnum.WRIST_WATCH.getId());
                break;
            case "04":
                payMoney = appraisalCategoryService.getPriceById(Constant.AppraisalCategoryEnum.LUXURY_JEWELRY.getId());
                break;
            case "05":
                payMoney = appraisalCategoryService.getPriceById(Constant.AppraisalCategoryEnum.JADEITE.getId());
                break;
            case "06":
                payMoney = appraisalCategoryService.getPriceById(Constant.AppraisalCategoryEnum.NEPHRITE.getId());
                break;
            case "07":
                payMoney = appraisalCategoryService.getPriceById(Constant.AppraisalCategoryEnum.NOBLE_METAL.getId());
                break;
            case "08":
                payMoney = appraisalCategoryService.getPriceById(Constant.AppraisalCategoryEnum.PORCELAIN.getId());
                break;
            case "09":
                payMoney = appraisalCategoryService.getPriceById(Constant.AppraisalCategoryEnum.CALLIGRAPHY.getId());
                break;
            case "10":
                payMoney = appraisalCategoryService.getPriceById(Constant.AppraisalCategoryEnum.PAINTING.getId());
                break;
            case "11":
                payMoney = appraisalCategoryService.getPriceById(Constant.AppraisalCategoryEnum.MISCELLANEOUS.getId());
                break;
            case "12":
                payMoney = appraisalCategoryService.getPriceById(Constant.AppraisalCategoryEnum.OTHER.getId());
                break;
        }
        return payMoney;
    }

    /**
     * @param classify
     * @param number
     * @param coverPhoto
     * @param createUser
     * @param goodsCode
     * @param source
     * @param miniProjectUser
     * @return
     */
    public R saveInKdAppraisal(String classify, String number, String coverPhoto, Long createUser, String goodsCode, String source, MiniProjectUser miniProjectUser, String jdid) {
        //邮寄鉴定 step1：先查看当前商品编号是否有对应的在线鉴定记录
//        AppraisalPojo select = appraisalService.getOne(new QueryWrapper<AppraisalPojo>().eq("goods_code", goodsCode));
        AppraisalPojo select = appraisalService.getOne(new QueryWrapper<AppraisalPojo>().eq("id", jdid));

        //step2:当存在鉴定记录，并且是邮寄鉴定
        if (select != null && ("6".equals(select.getMethod()) || "9".equals(select.getMethod()))) {
            throw new RRException("当前商品已存在邮寄鉴定，不能重复提交！");
        }
        //step3:当存在在线鉴定记录，并且在线鉴定记录还未完结
        if (select != null && "3".equals(select.getMethod())
                && ("0".equals(select.getState()) || "1".equals(select.getState()))) {
            throw new RRException("当前商品在线鉴定还未完结不能发起邮寄复检，！");
        }

        boolean payStatus = true;   //是否付费
        if (StringUtils.isNotEmpty(jdid)) {
            /**
             *  修改鉴定表状态    鉴定方式改为邮寄鉴定6
             */
            LambdaUpdateWrapper<AppraisalPojo> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(AppraisalPojo::getId, Long.valueOf(jdid))
                    .set(AppraisalPojo::getMethod, 6)
                    .set(AppraisalPojo::getState, 0)
                    .set(AppraisalPojo::getGoodsCode, goodsCode);
            appraisalService.update(updateWrapper);

            /**
             * 修改专家表状态
             */
            LambdaUpdateWrapper<ExpertAppraisalPojo> expertUpdateWrapper = new LambdaUpdateWrapper<>();
            expertUpdateWrapper.set(ExpertAppraisalPojo::getState, 9)
                    .eq(ExpertAppraisalPojo::getAppraisalId, jdid);//改成删除状态
            expertAppraisalService.update(expertUpdateWrapper);
            payStatus = false;
        }

        //step4:当存在在线鉴定记录，并且在线鉴定记录已完结，邮寄复检
//        if (select != null && "3".equals(select.getMethod())
//                && ("2".equals(select.getState()) || "3".equals(select.getState()))) {
//            //更新之前的鉴定状态为不显示
//            select.setIsShow("0");
//            appraisalService.updateById(select);
//            //保存邮寄复检
//            AppraisalPojo appraisalPojo = AppraisalPojo.builder().classify(classify).number(number)
//                    .coverPhoto(coverPhoto).createUser(createUser).source(source)
//                    .createTime(new Date()).goodsCode(goodsCode).state("0").method("9").build();
//            appraisalService.save(appraisalPojo);
//        }

        //step:5 不存在鉴定记录，直接保存邮寄鉴定
        if (select == null && payStatus) {
            AppraisalPojo appraisalPojo = AppraisalPojo.builder().classify(classify).number(number)
                    .coverPhoto(coverPhoto).createUser(createUser).source(source)
                    .createTime(new Date()).goodsCode(goodsCode).state("0").method("6").build();

            appraisalService.save(appraisalPojo);
        }

        if (payStatus) {
            //获取鉴定费用
            BigDecimal payMoney = getPayMoney(classify);

            if (payMoney.compareTo(BigDecimal.ZERO) > 0) {
                return wxPayService.createOrder(miniProjectUser, number, payMoney);
            } else {
//                throw new RRException("提交失败");
                PayPojo payPojo = PayPojo.builder().orderCode("").nonceStr("").payeeId(miniProjectUser.getId()).appraisalCode(number)
                        .state("1").createTime(new Date()).payeeName(miniProjectUser.getName())
                        .payeePhone(miniProjectUser.getPhone()).orderInfo("鉴定支付")
                        .totalFee((long) 0).build();
                payService.save(payPojo);
                return R.ok();
            }
        } else {
            return R.ok().put("data", null);
        }
    }

    public void updateInAppraisal(String classify, String coverPhoto, String goodsCode) {
        AppraisalPojo appraisalPojo = appraisalService.getOne(new QueryWrapper<AppraisalPojo>().eq("classify", classify).eq("goods_code", goodsCode));
        appraisalPojo.setCoverPhoto(coverPhoto);
        appraisalPojo.setState("1");
        appraisalService.updateById(appraisalPojo);
        expertAppraisalService.update(
                new LambdaUpdateWrapper<ExpertAppraisalPojo>().eq(ExpertAppraisalPojo::getAppraisalId, appraisalPojo.getId())
                        .set(ExpertAppraisalPojo::getName, "").set(ExpertAppraisalPojo::getSize, "").set(ExpertAppraisalPojo::getWeight, "")
                        .set(ExpertAppraisalPojo::getMainMaterial, "").set(ExpertAppraisalPojo::getSubMaterial, "").set(ExpertAppraisalPojo::getYears, "")
                        .set(ExpertAppraisalPojo::getOther, "").set(ExpertAppraisalPojo::getMarketLiquidity, "").set(ExpertAppraisalPojo::getValueStability, "")
                        .set(ExpertAppraisalPojo::getMaterialVulnerability, "").set(ExpertAppraisalPojo::getPawnPrice, null).set(ExpertAppraisalPojo::getState, "1")
                        .set(ExpertAppraisalPojo::getReason, "").set(ExpertAppraisalPojo::getIsSelection, 0)
        );
    }

    /**
     * 鉴定记录
     *
     * @param userId
     * @return
     */
    public PageUtils appraisalPojoList(Long userId, String state, String limit, String page) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("limit", limit);
        params.put("userId", userId);
        params.put("state", state);
        return appraisalService.getPage(params);
    }

    /**
     * 鉴定详情
     *
     * @param id
     * @return
     */
    public AppraisalPojo appraisalDetail(Long id) {
        AppraisalPojo appraisalPojo = appraisalService.getById(id);
        if (appraisalPojo == null)
            return null;
        String method = appraisalPojo.getMethod();
        String classify = appraisalPojo.getClassify();//类别
        String goodsCode = appraisalPojo.getGoodsCode();//对应详情表中商品code
        if (method.equals("6") || method.equals("9")) {
            KdPojo kdPojo = kdService.getOne(new QueryWrapper<KdPojo>().eq("goods_code", goodsCode));
            appraisalPojo.setInfo(kdPojo);
        } else {
            switch (classify) {
                case "01":
                    DiamondsPojo diamondsPojo = diamondsService.getOne(new QueryWrapper<DiamondsPojo>().eq("goods_code", goodsCode));
                    appraisalPojo.setInfo(diamondsPojo);
                    break;
                case "02":
                    GemstonePojo gemstonePojo = gemstoneService.getOne(new QueryWrapper<GemstonePojo>().eq("goods_code", goodsCode));
                    appraisalPojo.setInfo(gemstonePojo);
                    break;
                case "03":
                    WatchPojo watchPojo = watchService.getOne(new QueryWrapper<WatchPojo>().eq("goods_code", goodsCode));
                    appraisalPojo.setInfo(watchPojo);
                    break;
                case "04":
                    LuxuriesPojo luxuriesPojo = luxuriesService.getOne(new QueryWrapper<LuxuriesPojo>().eq("goods_code", goodsCode));
                    appraisalPojo.setInfo(luxuriesPojo);
                    break;
                case "05":
                    JadeitePojo jadeitePojo = jadeiteService.getOne(new QueryWrapper<JadeitePojo>().eq("goods_code", goodsCode));
                    appraisalPojo.setInfo(jadeitePojo);
                    break;
                case "06":
                    NephritePojo nephritePojo = nephriteService.getOne(new QueryWrapper<NephritePojo>().eq("goods_code", goodsCode));
                    appraisalPojo.setInfo(nephritePojo);
                    break;
                case "07":
                    MetalPojo metalPojo = metalService.getOne(new QueryWrapper<MetalPojo>().eq("goods_code", goodsCode));
                    appraisalPojo.setInfo(metalPojo);
                    break;
                case "08":
                    PorcelainPojo porcelainPojo = porcelainService.getOne(new QueryWrapper<PorcelainPojo>().eq("goods_code", goodsCode));
                    appraisalPojo.setInfo(porcelainPojo);
                    break;
                case "09":
                    CalligraphyPojo calligraphyPojo = calligraphyService.getOne(new QueryWrapper<CalligraphyPojo>().eq("goods_code", goodsCode));
                    appraisalPojo.setInfo(calligraphyPojo);
                    break;
                case "10":
                    PaintingPojo paintingPojo = paintingService.getOne(new QueryWrapper<PaintingPojo>().eq("goods_code", goodsCode));
                    appraisalPojo.setInfo(paintingPojo);
                    break;
                case "11":
                    WenwanPojo wenwanPojo = wenwanService.getOne(new QueryWrapper<WenwanPojo>().eq("goods_code", goodsCode));
                    appraisalPojo.setInfo(wenwanPojo);
                    break;
                case "12":
                    OtherPojo otherPojo = otherService.getOne(new QueryWrapper<OtherPojo>().eq("goods_code", goodsCode));
                    appraisalPojo.setInfo(otherPojo);
                    break;
            }
        }

        //再次提交时申请ID
        String againId = appraisalPojo.getAgainId();
        if (StringUtils.isNotEmpty(againId)) {
            /**
             * 获取再次提交申请的相关信息
             */
            AppraisalAgain appraisalAgain = againAppraisalService.infoByAgainId(againId);
            appraisalPojo.setAppraisalAgain(appraisalAgain);
        }

        return appraisalPojo;
    }

    /**
     * 支付记录
     *
     * @param userId
     * @return
     */
    public List<PayPojo> payPojoLists(Long userId) {
        return payService.list(new QueryWrapper<PayPojo>().eq("payee_id", userId).orderByDesc("create_time"));
    }

    /**
     * 鉴定视频
     *
     * @param userId
     * @return
     */
    public List<AppraisalPojo> appraisalVideo(Long userId) {
        return appraisalService.list(new QueryWrapper<AppraisalPojo>().in("method", 6, 9).eq("create_user", userId)
                .isNotNull("unpacking_video").isNotNull("appraisal_video").isNotNull("packing_video").orderByDesc("create_time"));
    }

    /**
     * 生成证书
     *
     * @param id
     */
    @Transactional
    public void certificateCreate(CertificatePojo certificatePojo, Long id, String images) {
        AppraisalPojo appraisalPojo = appraisalService.getById(id);
        String code = KeyUtil.getCertificateCode(appraisalPojo);
        certificatePojo.setCode(code);//设置证书编号
        certificatePojo.setName(appraisalPojo.getName());//设置名称
        certificatePojo.setSize(appraisalPojo.getSize());//设置尺寸
        certificatePojo.setWeight(appraisalPojo.getWeight());//设置重量
        certificatePojo.setMainMaterial(appraisalPojo.getMainMaterial());//设置主材料
        certificatePojo.setSubMaterial(appraisalPojo.getSubMaterial());//设置副材料
        certificatePojo.setYears(appraisalPojo.getYears());//设置年份
        certificatePojo.setOther(appraisalPojo.getOther());//设置其他
        certificatePojo.setMarketLiquidity(appraisalPojo.getMarketLiquidity());//设置流通性
        certificatePojo.setValueStability(appraisalPojo.getValueStability());//设置价值稳定性
        certificatePojo.setMaterialVulnerability(appraisalPojo.getMaterialVulnerability());//设置材质易损性
        certificatePojo.setPawnPrice(appraisalPojo.getPawnPrice());//设置典当价格
        certificatePojo.setImages(images);//设置图片
        certificateService.save(certificatePojo);//保存证书表
        appraisalPojo.setCertificateCode(code);
        appraisalService.updateById(appraisalPojo);//更新证书code到鉴定表中
    }

}
