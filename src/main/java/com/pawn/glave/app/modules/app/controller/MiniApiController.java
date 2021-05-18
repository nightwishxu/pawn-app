package com.pawn.glave.app.modules.app.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.modules.app.AgainConstant;
import com.pawn.glave.app.modules.app.annotation.Login;
import com.pawn.glave.app.modules.app.annotation.LoginUser;
import com.pawn.glave.app.modules.app.entity.*;
import com.pawn.glave.app.modules.app.service.*;
import com.pawn.glave.app.modules.app.service.impl.PaidangApiService;
import com.pawn.glave.app.modules.app.utils.KeyUtil;
import com.pawn.glave.app.modules.sys.entity.SysFileEntity;
import com.pawn.glave.app.modules.sys.service.AppraisalCategoryService;
import com.pawn.glave.app.modules.sys.service.SysFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/mini/project")
@Api("小程序相关接口")
@Slf4j
public class MiniApiController {
    @Autowired
    private MiniApiService miniApiService;
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
    private KdService kdService;
    @Autowired
    private AppraisalService appraisalService;
    @Autowired
    private CertificateService certificateService;
    @Autowired
    private ExpertAppraisalService expertAppraisalService;
    @Autowired
    private IntegralService integralService;
    @Autowired
    private MiniProjectUserService miniProjectUserService;
    @Autowired
    private SendService sendService;
    @Autowired
    private SysFileService sysFileService;
    @Value("${web.upload-path}")
    private String fileUploadPath;

    @Autowired
    private PaidangApiService paidangApiService;

    @Resource
    private AppraisalCategoryService appraisalCategoryService;

    @Login
    @RequestMapping(value = "/diamonds/save", method = RequestMethod.POST)
    @ApiOperation("保存钻石鉴定")
    @Transactional
    public R diamondsSave(DiamondsPojo diamondsPojo, @RequestParam("source") @ApiParam(value = "来源 06app 08小程序") String source, @LoginUser MiniProjectUser miniProjectUser) {
        if (diamondsPojo.getId() == null) {
            String goodsCode = IdUtil.simpleUUID();
            diamondsPojo.setGoodsCode(goodsCode);
            diamondsService.save(diamondsPojo);
            return miniApiService.saveInAppraisal("01", KeyUtil.generateUniqueKey(), "", miniProjectUser.getId(), goodsCode, source, miniProjectUser,diamondsPojo.getUserGoodsId());
        } else {
            diamondsService.updateById(diamondsPojo);
            miniApiService.updateInAppraisal("01", "", diamondsPojo.getGoodsCode());
            return R.ok();
        }
    }


    @Login
    @RequestMapping(value = "/gemstone/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存或修改彩色宝石鉴定", notes = "修改需要传id和goods_code")
    @Transactional
    public R gemstoneSave(GemstonePojo gemstonePojo, @RequestParam("source") @ApiParam(value = "来源 06app 08小程序") String source, @LoginUser MiniProjectUser miniProjectUser) {
        if (gemstonePojo.getId() == null) {
            gemstonePojo.setPhotos(sysFileService.saveUrls(gemstonePojo.getPhotos()));
            gemstonePojo.setVideo(sysFileService.saveUrls(gemstonePojo.getVideo()));
            gemstonePojo.setEnclosureFile(sysFileService.saveUrls(gemstonePojo.getEnclosureFile()));
            String goodsCode = IdUtil.simpleUUID();
            gemstonePojo.setGoodsCode(goodsCode);
            gemstoneService.save(gemstonePojo);
            return miniApiService.saveInAppraisal("02", KeyUtil.generateUniqueKey(), gemstonePojo.getPhotos(), miniProjectUser.getId(), goodsCode, source, miniProjectUser,gemstonePojo.getUserGoodsId());
        } else {
            gemstoneService.updateById(gemstonePojo);
            miniApiService.updateInAppraisal("02", gemstonePojo.getPhotos(), gemstonePojo.getGoodsCode());
            return R.ok();
        }
    }

    @Login
    @RequestMapping(value = "/watch/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存或修改手表鉴定", notes = "修改需要传id和goods_code")
    @Transactional
    public R watchSave(WatchPojo watchPojo, @RequestParam("source") @ApiParam(value = "来源 06app 08小程序") String source, @LoginUser MiniProjectUser miniProjectUser) {
        if (watchPojo.getId() == null) {
            watchPojo.setPhotos(sysFileService.saveUrls(watchPojo.getPhotos()));
            watchPojo.setVideo(sysFileService.saveUrls(watchPojo.getVideo()));
            watchPojo.setEnclosureFile(sysFileService.saveUrls(watchPojo.getEnclosureFile()));
            String goodsCode = IdUtil.simpleUUID();
            watchPojo.setGoodsCode(goodsCode);
            watchService.save(watchPojo);
            return miniApiService.saveInAppraisal("03", KeyUtil.generateUniqueKey(), watchPojo.getPhotos(), miniProjectUser.getId(), goodsCode, source, miniProjectUser,watchPojo.getUserGoodsId());
        } else {
            watchService.updateById(watchPojo);
            miniApiService.updateInAppraisal("03", watchPojo.getPhotos(), watchPojo.getGoodsCode());
            return R.ok();
        }
    }

    @Login
    @RequestMapping(value = "/luxuries/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存或修改奢侈品珠宝鉴定", notes = "修改需要传id和goods_code")
    @Transactional
    public R luxuriesSave(LuxuriesPojo luxuriesPojo, @RequestParam("source") @ApiParam(value = "来源 06app 08小程序") String source, @LoginUser MiniProjectUser miniProjectUser) {
        if (luxuriesPojo.getId() == null) {
            luxuriesPojo.setPhotos(sysFileService.saveUrls(luxuriesPojo.getPhotos()));
            luxuriesPojo.setVideo(sysFileService.saveUrls(luxuriesPojo.getVideo()));
            luxuriesPojo.setEnclosureFile(sysFileService.saveUrls(luxuriesPojo.getEnclosureFile()));
            String goodsCode = IdUtil.simpleUUID();
            luxuriesPojo.setGoodsCode(goodsCode);
            luxuriesService.save(luxuriesPojo);
            return miniApiService.saveInAppraisal("04", KeyUtil.generateUniqueKey(), luxuriesPojo.getPhotos(), miniProjectUser.getId(), goodsCode, source, miniProjectUser,luxuriesPojo.getUserGoodsId());
        } else {
            luxuriesService.updateById(luxuriesPojo);
            miniApiService.updateInAppraisal("04", luxuriesPojo.getPhotos(), luxuriesPojo.getGoodsCode());
            return R.ok();
        }
    }

    @Login
    @RequestMapping(value = "/jadeite/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存或修改翡翠玉石鉴定", notes = "修改需要传id和goods_code")
    @Transactional
    public R jadeiteSave(JadeitePojo jadeitePojo, @RequestParam("source") @ApiParam(value = "来源 06app 08小程序") String source, @LoginUser MiniProjectUser miniProjectUser) {
        if (jadeitePojo.getId() == null) {
            jadeitePojo.setPhotos(sysFileService.saveUrls(jadeitePojo.getPhotos()));
            jadeitePojo.setVideo(sysFileService.saveUrls(jadeitePojo.getVideo()));
            String goodsCode = IdUtil.simpleUUID();
            jadeitePojo.setGoodsCode(goodsCode);
            jadeiteService.save(jadeitePojo);
            return miniApiService.saveInAppraisal("05", KeyUtil.generateUniqueKey(), jadeitePojo.getPhotos(), miniProjectUser.getId(), goodsCode, source, miniProjectUser,jadeitePojo.getUserGoodsId());
        } else {
            jadeiteService.updateById(jadeitePojo);
            miniApiService.updateInAppraisal("05", jadeitePojo.getPhotos(), jadeitePojo.getGoodsCode());
            return R.ok();
        }
    }

    @Login
    @RequestMapping(value = "/nephrite/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存或修改和田玉鉴定", notes = "修改需要传id和goods_code")
    @Transactional
    public R nephriteSave(NephritePojo nephritePojo, @RequestParam("source") @ApiParam(value = "来源 06app 08小程序") String source, @LoginUser MiniProjectUser miniProjectUser) {
        if (nephritePojo.getId() == null) {
            nephritePojo.setPhotos(sysFileService.saveUrls(nephritePojo.getPhotos()));
            nephritePojo.setVideo(sysFileService.saveUrls(nephritePojo.getVideo()));
            String goodsCode = IdUtil.simpleUUID();
            nephritePojo.setGoodsCode(goodsCode);
            nephriteService.save(nephritePojo);
            return miniApiService.saveInAppraisal("06", KeyUtil.generateUniqueKey(), nephritePojo.getPhotos(), miniProjectUser.getId(), goodsCode, source, miniProjectUser,nephritePojo.getUserGoodsId());
        } else {
            nephriteService.updateById(nephritePojo);
            miniApiService.updateInAppraisal("06", nephritePojo.getPhotos(), nephritePojo.getGoodsCode());
            return R.ok();
        }
    }

    @Login
    @RequestMapping(value = "/metal/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存或修改贵金属鉴定", notes = "修改需要传id和goods_code")
    @Transactional
    public R metalSave(MetalPojo metalPojo, @RequestParam("source") @ApiParam(value = "来源 06app 08小程序") String source, @LoginUser MiniProjectUser miniProjectUser) {
        if (metalPojo.getId() == null) {
            String goodsCode = IdUtil.simpleUUID();
            metalPojo.setGoodsCode(goodsCode);
            metalService.save(metalPojo);
            return miniApiService.saveInAppraisal("07", KeyUtil.generateUniqueKey(), "", miniProjectUser.getId(), goodsCode, source, miniProjectUser,metalPojo.getUserGoodsId());
        } else {
            metalService.updateById(metalPojo);
            miniApiService.updateInAppraisal("07", "", metalPojo.getGoodsCode());
            return R.ok();
        }
    }

    @Login
    @RequestMapping(value = "/porcelain/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存或修改瓷器鉴定", notes = "修改需要传id和goods_code")
    @Transactional
    public R porcelainSave(PorcelainPojo porcelainPojo, @RequestParam("source") @ApiParam(value = "来源 06app 08小程序") String source, @LoginUser MiniProjectUser miniProjectUser) {
        if (porcelainPojo.getId() == null) {
            porcelainPojo.setFrontImg(sysFileService.saveUrls(porcelainPojo.getFrontImg()));
            porcelainPojo.setSideImg(sysFileService.saveUrls(porcelainPojo.getSideImg()));
            porcelainPojo.setBackImg(sysFileService.saveUrls(porcelainPojo.getBackImg()));
            porcelainPojo.setBottomImg(sysFileService.saveUrls(porcelainPojo.getBottomImg()));
            porcelainPojo.setMouthImg(sysFileService.saveUrls(porcelainPojo.getMouthImg()));
            String goodsCode = IdUtil.simpleUUID();
            porcelainPojo.setGoodsCode(goodsCode);
            porcelainService.save(porcelainPojo);
            return miniApiService.saveInAppraisal("08", KeyUtil.generateUniqueKey(), porcelainPojo.getFrontImg(), miniProjectUser.getId(), goodsCode, source, miniProjectUser,porcelainPojo.getUserGoodsId());
        } else {
            porcelainService.updateById(porcelainPojo);
            miniApiService.updateInAppraisal("08", porcelainPojo.getFrontImg(), porcelainPojo.getGoodsCode());
            return R.ok();
        }
    }

    @Login
    @RequestMapping(value = "/calligraphy/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存或修改书法鉴定", notes = "修改需要传id和goods_code")
    @Transactional
    public R calligraphySave(CalligraphyPojo calligraphyPojo, @RequestParam("source") @ApiParam(value = "来源 06app 08小程序") String source, @LoginUser MiniProjectUser miniProjectUser) {
        if (calligraphyPojo.getId() == null) {
            calligraphyPojo.setFrontImg(sysFileService.saveUrls(calligraphyPojo.getFrontImg()));
            calligraphyPojo.setPostscriptImg(sysFileService.saveUrls(calligraphyPojo.getPostscriptImg()));
            calligraphyPojo.setAutographImg(sysFileService.saveUrls(calligraphyPojo.getAutographImg()));
            calligraphyPojo.setLocalImg(sysFileService.saveUrls(calligraphyPojo.getLocalImg()));
            calligraphyPojo.setSettlementImg(sysFileService.saveUrls(calligraphyPojo.getSettlementImg()));
            String goodsCode = IdUtil.simpleUUID();
            calligraphyPojo.setGoodsCode(goodsCode);
            calligraphyService.save(calligraphyPojo);
            return miniApiService.saveInAppraisal("09", KeyUtil.generateUniqueKey(), calligraphyPojo.getFrontImg(), miniProjectUser.getId(), goodsCode, source, miniProjectUser,calligraphyPojo.getUserGoodsId());
        } else {
            calligraphyService.updateById(calligraphyPojo);
            miniApiService.updateInAppraisal("09", calligraphyPojo.getFrontImg(), calligraphyPojo.getGoodsCode());
            return R.ok();
        }
    }

    @Login
    @RequestMapping(value = "/painting/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存或修改绘画鉴定", notes = "修改需要传id和goods_code")
    @Transactional
    public R paintingSave(PaintingPojo paintingPojo, @RequestParam("source") @ApiParam(value = "来源 06app 08小程序") String source, @LoginUser MiniProjectUser miniProjectUser) {
        if (paintingPojo.getId() == null) {
            paintingPojo.setFrontImg(sysFileService.saveUrls(paintingPojo.getFrontImg()));
            paintingPojo.setPostscriptImg(sysFileService.saveUrls(paintingPojo.getPostscriptImg()));
            paintingPojo.setAutographImg(sysFileService.saveUrls(paintingPojo.getAutographImg()));
            paintingPojo.setLocalImg(sysFileService.saveUrls(paintingPojo.getLocalImg()));
            paintingPojo.setSettlementImg(sysFileService.saveUrls(paintingPojo.getSettlementImg()));
            String goodsCode = IdUtil.simpleUUID();
            paintingPojo.setGoodsCode(goodsCode);
            paintingService.save(paintingPojo);
            return miniApiService.saveInAppraisal("10", KeyUtil.generateUniqueKey(), paintingPojo.getFrontImg(), miniProjectUser.getId(), goodsCode, source, miniProjectUser,paintingPojo.getUserGoodsId());
        } else {
            paintingService.updateById(paintingPojo);
            miniApiService.updateInAppraisal("10", paintingPojo.getFrontImg(), paintingPojo.getGoodsCode());
            return R.ok();
        }
    }

    @Login
    @RequestMapping(value = "/wenwan/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存或修改文玩鉴定", notes = "修改需要传id和goods_code")
    @Transactional
    public R wenwanSave(WenwanPojo wenwanPojo, @RequestParam("source") @ApiParam(value = "来源 06app 08小程序") String source, @LoginUser MiniProjectUser miniProjectUser) {
        if (wenwanPojo.getId() == null) {
            wenwanPojo.setWholeImg(sysFileService.saveUrls(wenwanPojo.getWholeImg()));
            wenwanPojo.setSideImg(sysFileService.saveUrls(wenwanPojo.getSideImg()));
            wenwanPojo.setBackImg(sysFileService.saveUrls(wenwanPojo.getBackImg()));
            wenwanPojo.setBottomImg(sysFileService.saveUrls(wenwanPojo.getBottomImg()));
            wenwanPojo.setAngleImg(sysFileService.saveUrls(wenwanPojo.getAngleImg()));
            wenwanPojo.setLocalImg(sysFileService.saveUrls(wenwanPojo.getLocalImg()));
            String goodsCode = IdUtil.simpleUUID();
            wenwanPojo.setGoodsCode(goodsCode);
            wenwanService.save(wenwanPojo);
            return miniApiService.saveInAppraisal("11", KeyUtil.generateUniqueKey(), wenwanPojo.getWholeImg(), miniProjectUser.getId(), goodsCode, source, miniProjectUser,wenwanPojo.getUserGoodsId());
        } else {
            wenwanService.updateById(wenwanPojo);
            miniApiService.updateInAppraisal("11", wenwanPojo.getWholeImg(), wenwanPojo.getGoodsCode());
            return R.ok();
        }
    }

    @Login
    @RequestMapping(value = "/other/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存或修改其他鉴定", notes = "修改需要传id和goods_code")
    @Transactional
    public R otherSave(OtherPojo otherPojo, @RequestParam("source") @ApiParam(value = "来源 06app 08小程序") String source, @LoginUser MiniProjectUser miniProjectUser) {
        if (otherPojo.getId() == null) {
            otherPojo.setPhotos(sysFileService.saveUrls(otherPojo.getPhotos()));
            otherPojo.setVideo(sysFileService.saveUrls(otherPojo.getVideo()));
            String goodsCode = IdUtil.simpleUUID();
            otherPojo.setGoodsCode(goodsCode);
            otherService.save(otherPojo);
            return miniApiService.saveInAppraisal("12", KeyUtil.generateUniqueKey(), otherPojo.getPhotos(), miniProjectUser.getId(), goodsCode, source, miniProjectUser,otherPojo.getUserGoodsId());
        } else {
            otherService.updateById(otherPojo);
            miniApiService.updateInAppraisal("12", otherPojo.getPhotos(), otherPojo.getGoodsCode());
            return R.ok();
        }
    }


    @Login
    @RequestMapping(value = "/kd/save", method = RequestMethod.POST)
    @ApiOperation("保存邮寄鉴定")
    @Transactional
    public R kdSave(KdPojo kdPojo,
                    @RequestParam(value = "classify",required = false) @ApiParam(value = "鉴定商品的类型") String classify,
                    @RequestParam("source") @ApiParam(value = "来源 06app 08小程序") String source,
                    @LoginUser MiniProjectUser miniProjectUser,
                    @RequestParam("jdid") String jdid) {
        if ("06".equals(source)){
            kdPojo.setPhotos(sysFileService.saveUrls(kdPojo.getPhotos()));
            kdPojo.setVideo(sysFileService.saveUrls(kdPojo.getVideo()));
            QueryWrapper<AppraisalPojo> query = new QueryWrapper<>();
            query.eq("user_goods_id",kdPojo.getUserGoodsId()).eq("method","3");
            List<AppraisalPojo> list = appraisalService.list(query);
            if (CollectionUtils.isEmpty(list)){
                return R.ok();
            }
            jdid = list.get(0).getId().toString();
        }

        String goodsCode = IdUtil.simpleUUID();
        kdPojo.setGoodsCode(goodsCode);
        kdService.save(kdPojo);


        return miniApiService.saveInKdAppraisal(classify, KeyUtil.generateUniqueKey(), kdPojo.getPhotos(), miniProjectUser.getId(), goodsCode, source, miniProjectUser, jdid,kdPojo.getUserGoodsId());
    }

    /**
     * 鉴定记录列表
     *
     * @param miniProjectUser
     * @return
     */
    @Login
    @GetMapping(value = "/appraisal/record")
    @ApiOperation("鉴定记录列表")
    public R appraisalRecord(@LoginUser MiniProjectUser miniProjectUser,
                             @ApiParam(value = "待鉴定状态0待鉴定 1已分配专家 2鉴定完成 3无法鉴定 可传值1,2,3,4") @RequestParam("state") String state,
                             @ApiParam(value = "每页条数") @RequestParam("limit") String limit,
                             @ApiParam(value = "页数") @RequestParam("page") String page) {
        PageUtils pageUtils = miniApiService.appraisalPojoList(miniProjectUser.getId(), state, limit, page);
        return R.ok().put("data", pageUtils);
    }

    /**
     * 鉴定详情
     *
     * @param miniProjectUser
     * @param id
     * @return
     */
    @Login
    @GetMapping(value = "/appraisal/detail")
    @ApiOperation("鉴定记录详情")
    public AppraisalPojo appraisalDetail(@LoginUser MiniProjectUser miniProjectUser,
                                         @ApiParam(value = "列表id") @RequestParam("id") Long id) {
        AppraisalPojo appraisalPojo = miniApiService.appraisalDetail(id);
        return appraisalPojo;
    }


    /**
     * 支付记录
     *
     * @param miniProjectUser
     * @return
     */
    @Login
    @GetMapping(value = "/pay/record")
    @ApiOperation("支付记录")
    public R payRecord(@LoginUser MiniProjectUser miniProjectUser) {
        List<PayPojo> payPojos = miniApiService.payPojoLists(miniProjectUser.getId());
        return R.ok().put("data", payPojos);
    }

    /**
     * 鉴定视频
     *
     * @param miniProjectUser
     * @return
     */
    @Login
    @GetMapping(value = "/appraisal/video")
    @ApiOperation("鉴定视频")
    public List<AppraisalPojo> appraisalVideo(@LoginUser MiniProjectUser miniProjectUser) {
        List<AppraisalPojo> appraisalPojoList = miniApiService.appraisalVideo(miniProjectUser.getId());
        return appraisalPojoList;
    }


    @Login
    @GetMapping(value = "/getUnIdentified")
    @ApiOperation("获取未鉴定列表")
    public R getUnidentifiedList(@LoginUser ExpertsPojo expertsPojo) {
        return R.ok().put("data", appraisalService.getUnidentifiedList(expertsPojo.getCode()));
    }

    @Login
    @GetMapping(value = "/getIdentified")
    @ApiOperation("获取已鉴定列表")
    public R getIdentifiedList(@LoginUser ExpertsPojo expertsPojo) {
        return R.ok().put("data", appraisalService.getIdentifiedList(expertsPojo.getCode()));
    }


    @GetMapping(value = "/getIdentifiedDetail")
    @ApiOperation("获取已鉴定详情")
    public R getIdentifiedDetail(@ApiParam(value = "鉴定记录表id") @RequestParam("appraisalId") Long appraisalId,
                                 @ApiParam(value = "专家鉴定表id") @RequestParam("expertAppraisalId") Long expertAppraisalId) {
        AppraisalPojo appraisalPojo = miniApiService.appraisalDetail(appraisalId);
        ExpertAppraisalPojo expertAppraisalPojo = expertAppraisalService.getById(expertAppraisalId);
        return R.ok().put("appraisalData", appraisalPojo).put("expertAppraisalData", expertAppraisalPojo);
    }


    @ApiOperation("获取证书详情")
    @GetMapping(value = "/getCertificate")
    public R getCertificate(@ApiParam(value = "证书id") @RequestParam("certificateId") String certificateId) {
        return R.ok().put("data", certificateService.getById(certificateId));
    }

    @Login
    @ApiOperation("开始鉴定数据提交")
    @PostMapping(value = "/saveIdentify")
    public R saveIdentify(@LoginUser ExpertsPojo expertsPojo, ExpertAppraisalPojo expertAppraisalPojo) {
        if (expertAppraisalPojo != null) {
            if (expertAppraisalPojo.getId() != null) {
                /**width="120"
                 * 获取鉴定表状态
                 */
                LambdaQueryWrapper<AppraisalPojo> appraisalQueryWrapper = new LambdaQueryWrapper<>();
                appraisalQueryWrapper.eq(AppraisalPojo::getId, expertAppraisalPojo.getAppraisalId());
                AppraisalPojo appraisalPojo = appraisalService.getOne(appraisalQueryWrapper);

                if (Integer.valueOf(appraisalPojo.getState()) >= 2) {
                    return R.error("已被鉴定");
                }


                if (AgainConstant.AgainEnum.AGAIN_APPRAISAL.getValue().equals(appraisalPojo.getState())) {
                    //将鉴定表的状态改成5,方便后续选择鉴定结果
                    LambdaUpdateWrapper<AppraisalPojo> appraisalUpdateWrapper = new LambdaUpdateWrapper<>();
                    appraisalUpdateWrapper.eq(AppraisalPojo::getId, expertAppraisalPojo.getAppraisalId())
                            .set(AppraisalPojo::getState, AgainConstant.AgainEnum.FINISH_AGAIN_APPRAISAL.getValue());
                    appraisalService.update(appraisalUpdateWrapper);
                }

                expertAppraisalPojo.setTime(new Date());
                expertAppraisalPojo.setState("2");
                expertAppraisalPojo.setAppraisalUser(expertsPojo.getCode());
                expertAppraisalPojo.setIsSelection("0");
                expertAppraisalService.updateById(expertAppraisalPojo);

                return R.ok();
            } else {
                return R.error("提交鉴定数据ID为空");
            }
        } else {
            return R.error("提交数据为空");
        }


    }

    @Login
    @ApiOperation("无法鉴定数据提交")
    @PostMapping(value = "/saveCannotIdentify")
    public R saveCannotIdentify(@ApiParam(value = "无法鉴定理由") @RequestParam("reason") String reason,
                                @ApiParam(value = "专家鉴定表id") @RequestParam("expertAppraisalId") Long expertAppraisalId) {
        ExpertAppraisalPojo expertAppraisalPojo = new ExpertAppraisalPojo();
        expertAppraisalPojo.setId(expertAppraisalId);
        expertAppraisalPojo.setReason(reason);
        expertAppraisalPojo.setTime(new Date());
        expertAppraisalPojo.setState("3");
//        expertAppraisalPojo.setIsSelection("0");
        expertAppraisalPojo.setName("无法鉴定");
        expertAppraisalService.updateById(expertAppraisalPojo);

        LambdaQueryWrapper<ExpertAppraisalPojo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExpertAppraisalPojo::getId, expertAppraisalId);
        ExpertAppraisalPojo newExpertAppraisal = expertAppraisalService.getOne(queryWrapper);
        /**
         * todo: 将鉴定表状态改成3
         */
        if ("1".equals(newExpertAppraisal.getIsSelection())) {
            //将鉴定表的状态改成3
            LambdaUpdateWrapper<AppraisalPojo> appraisalUpdateWrapper = new LambdaUpdateWrapper<>();
            appraisalUpdateWrapper.eq(AppraisalPojo::getId, newExpertAppraisal.getAppraisalId())
                    .set(AppraisalPojo::getState, AgainConstant.AgainEnum.UN_APPRAISAL.getValue());
            appraisalService.update(appraisalUpdateWrapper);
        }
        return R.ok();
    }

    @Login
    @ApiOperation("获取当前用户的证书列表")
    @GetMapping(value = "/getCertificateList")
    public R getCertificateList(@LoginUser MiniProjectUser miniProjectUser) {
        return R.ok().put("data", certificateService.getCertificateListByMiniUser(miniProjectUser.getId()));
    }

    @Login
    @ApiOperation("获取当前用户的积分列表")
    @GetMapping(value = "/getIntegralList")
    public R getIntegralList(@LoginUser ExpertsPojo expertsPojo) {
        List<IntegralPojo> integralList = integralService.list(
                new QueryWrapper<IntegralPojo>()
                        .eq("expert_code", expertsPojo.getCode())
                        .orderByDesc("create_time"));
        String count = integralService.getTotalIntegral(expertsPojo.getCode());
        return R.ok().put("data", integralList).put("totalIntegral", count);
    }

    @ApiOperation("根据条件证书查询接口")
    @GetMapping(value = "/queryCertificate")
    public R queryCertificate(@ApiParam(value = "证书编号") @RequestParam(value = "code", required = false) String code,
                              @ApiParam(value = "名称") @RequestParam(value = "name", required = false) String name) {
        List<CertificatePojo> certificatePojoList = certificateService.list(
                new QueryWrapper<CertificatePojo>()
                        .eq(StringUtils.isNotBlank(code), "code", code)
                        .like(StringUtils.isNotBlank(name), "name", name));
        return R.ok().put("data", certificatePojoList);
    }

    @ApiOperation("根据条件查询鉴定记录接口")
    @GetMapping(value = "/queryAppraisal")
    public R queryAppraisal(@ApiParam(value = "名称") @RequestParam("name") String name) {
        List<AppraisalPojo> appraisalPojoList = appraisalService.list(
                new QueryWrapper<AppraisalPojo>()
                        .like(StringUtils.isNotBlank(name), "name", name)
                        .ne("state", "0")
                        .ne("state", "1"));
        return R.ok().put("data", appraisalPojoList);
    }

    @ApiOperation("流通记录查询接口")
    @GetMapping(value = "/queryLtCertificate")
    public R queryLtCertificate(@ApiParam(value = "名称") @RequestParam(value = "name", required = false) String name,
                                @ApiParam(value = "手机号") @RequestParam(value = "phone", required = false) String phone,
                                @ApiParam(value = "生成时间") @RequestParam(value = "createTime", required = false) String createTime) {
        List<CertificatePojo> certificatePojoList = certificateService.queryLtCertificate(name, phone, createTime);
        return R.ok().put("data", certificatePojoList);
    }

    @ApiOperation("查看证书pdf文件")
    @RequestMapping("/show/pdf/{zsbh}")
    public R showPdf(@ApiParam(value = "证书编号") @PathVariable String zsbh) {
        SendPojo sendPojo = sendService.getOne(new QueryWrapper<SendPojo>().eq("code", zsbh));
        if (sendPojo == null || sendPojo.getState().equals("-1") || sendPojo.getState().equals("2")) {
            throw new RuntimeException("证书生成失败，请联系管理员！");
        }
        if (sendPojo.getState().equals("0") || sendPojo.getState().equals("1")) {
            throw new RuntimeException("证书正在生成中，请稍后！");
        }
        if (sendPojo.getState().equals("3")) {
            throw new RuntimeException("证书正在生成成功，等待下载！");
        }

        Long pdfId = sendPojo.getPdfId();
        SysFileEntity pdf = sysFileService.getById(pdfId);
        return R.ok().put("url", pdf.getFileUrl());
    }

    @ApiOperation("查看证书pdf文件")
    @RequestMapping("/show/pdf1/{zsbh}")
    public R showPdf1(@ApiParam(value = "证书编号") @PathVariable String zsbh) {
        SendPojo sendPojo = sendService.getOne(new LambdaQueryWrapper<SendPojo>().eq(SendPojo::getBatchId, zsbh));
        if (sendPojo == null || sendPojo.getState().equals("-1") || sendPojo.getState().equals("2")) {
            throw new RuntimeException("证书生成失败，请联系管理员！");
        }
        if (sendPojo.getState().equals("0") || sendPojo.getState().equals("1")) {
            throw new RuntimeException("证书正在生成中，请稍后！");
        }
        if (sendPojo.getState().equals("3")) {
            throw new RuntimeException("证书正在生成成功，等待下载！");
        }
        Long pdfId = sendPojo.getPdfId();
        SysFileEntity pdf = sysFileService.getById(pdfId);
        return R.ok().put("url", pdf.getFileUrl());
    }

}
