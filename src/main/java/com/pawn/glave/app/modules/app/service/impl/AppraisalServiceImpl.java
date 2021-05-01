package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.Query;
import com.pawn.glave.app.modules.app.dao.AppraisalDao;
import com.pawn.glave.app.modules.app.entity.*;
import com.pawn.glave.app.modules.app.service.*;
import com.pawn.glave.app.modules.sys.entity.SysFileEntity;
import com.pawn.glave.app.modules.sys.entity.SysUserEntity;
import com.pawn.glave.app.modules.sys.service.SysFileService;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class AppraisalServiceImpl extends ServiceImpl<AppraisalDao,AppraisalPojo> implements AppraisalService {
    @Resource
    private AppraisalDao appraisalDao;
    @Resource
    private GemstoneService gemstoneService;
    @Resource
    private JadeiteService jadeiteService;
    @Resource
    private LuxuriesService luxuriesService;
    @Resource
    private MetalService metalService;
    @Resource
    private NephriteService nephriteService;
    @Resource
    private WatchService watchService;
    @Resource
    private OtherService otherService;
    @Resource
    private DiamondsService diamondsService;
    @Resource
    private PorcelainService porcelainService;
    @Resource
    private CalligraphyService calligraphyService;
    @Resource
    private PaintingService paintingService;
    @Resource
    private WenwanService wenwanService;
    @Resource
    private SysFileService sysFileService;
    @Resource
    private KdService kdService;

    @Override
    public PageUtils findPage(Map<String, Object> params) {
        String classify = MapUtils.getString(params,"classify");
        String state =  MapUtils.getString(params,"state");
        IPage iPage = new Query<ExpertsPojo>().getPage(params);

        IPage<Map<String,Object>> page = appraisalDao.findPage(iPage,classify,state);

        return new PageUtils(page);
    }

    @Override
    public List<SysFileEntity> photoList(Long id) {
        AppraisalPojo appraisalPojo = this.getById(id);//获取鉴定记录主表
        List<SysFileEntity> files = null;
        if(appraisalPojo==null)
            return null;
        String method = appraisalPojo.getMethod();
        String classify = appraisalPojo.getClassify();//类别
        String  goodsCode = appraisalPojo.getGoodsCode();//对应详情表中的id
        if(method.equals("6") || method.equals("9")){
            KdPojo kdPojo = kdService.getOne(new QueryWrapper<KdPojo>().eq("goods_code",goodsCode));
            files = getFileList(kdPojo.getPhotos());
        }else {
            switch (classify) {
                case "01":
                    DiamondsPojo diamondsPojo = diamondsService.getOne(new QueryWrapper<DiamondsPojo>().eq("goods_code", goodsCode));
                    break;
                case "02":
                    GemstonePojo gemstonePojo = gemstoneService.getOne(new QueryWrapper<GemstonePojo>().eq("goods_code", goodsCode));
                    files = getFileList(gemstonePojo.getPhotos());
                    break;
                case "03":
                    WatchPojo watchPojo = watchService.getOne(new QueryWrapper<WatchPojo>().eq("goods_code", goodsCode));
                    files = getFileList(watchPojo.getPhotos());
                    break;
                case "04":
                    LuxuriesPojo luxuriesPojo = luxuriesService.getOne(new QueryWrapper<LuxuriesPojo>().eq("goods_code", goodsCode));
                    files = getFileList(luxuriesPojo.getPhotos());
                    break;
                case "05":
                    JadeitePojo jadeitePojo = jadeiteService.getOne(new QueryWrapper<JadeitePojo>().eq("goods_code", goodsCode));
                    files = getFileList(jadeitePojo.getPhotos());
                    break;
                case "06":
                    NephritePojo nephritePojo = nephriteService.getOne(new QueryWrapper<NephritePojo>().eq("goods_code", goodsCode));
                    files = getFileList(nephritePojo.getPhotos());
                    break;
                case "07":
                    MetalPojo metalPojo = metalService.getOne(new QueryWrapper<MetalPojo>().eq("goods_code", goodsCode));
                    break;
                case "08":
                    PorcelainPojo porcelainPojo = porcelainService.getOne(new QueryWrapper<PorcelainPojo>().eq("goods_code", goodsCode));
                    files = getFileList(porcelainPojo.getFrontImg(), porcelainPojo.getBackImg(),
                            porcelainPojo.getBottomImg(), porcelainPojo.getMouthImg(), porcelainPojo.getSideImg());
                    break;
                case "09":
                    CalligraphyPojo calligraphyPojo = calligraphyService.getOne(new QueryWrapper<CalligraphyPojo>().eq("goods_code", goodsCode));
                    files = getFileList(calligraphyPojo.getFrontImg(), calligraphyPojo.getAutographImg(),
                            calligraphyPojo.getLocalImg(), calligraphyPojo.getPostscriptImg(),
                            calligraphyPojo.getSettlementImg());
                    break;
                case "10":
                    PaintingPojo paintingPojo = paintingService.getOne(new QueryWrapper<PaintingPojo>().eq("goods_code", goodsCode));
                    files = getFileList(paintingPojo.getFrontImg(), paintingPojo.getAutographImg(),
                            paintingPojo.getLocalImg(), paintingPojo.getPostscriptImg(),
                            paintingPojo.getSettlementImg());
                    break;
                case "11":
                    WenwanPojo wenwanPojo = wenwanService.getOne(new QueryWrapper<WenwanPojo>().eq("goods_code", goodsCode));
                    files = getFileList(wenwanPojo.getWholeImg(), wenwanPojo.getAngleImg(), wenwanPojo.getBackImg(),
                            wenwanPojo.getBottomImg(), wenwanPojo.getLocalImg(), wenwanPojo.getSideImg());
                    break;
                case "12":
                    OtherPojo otherPojo = otherService.getOne(new QueryWrapper<OtherPojo>().eq("goods_code", goodsCode));
                    files = getFileList(otherPojo.getPhotos());
                    break;
            }
        }
        return files;
    }

    @Override
    public List<Map<String, Object>> getUnidentifiedList(String userCode) {
        return appraisalDao.getUnidentifiedList(userCode);
    }

    @Override
    public List<Map<String, Object>> getIdentifiedList(String userCode) {
        return appraisalDao.getIdentifiedList(userCode);
    }

    @Override
    public PageUtils getPage(Map<String, Object> params) {
        Long userId = MapUtils.getLongValue(params,"userId");
        String state = MapUtils.getString(params,"state");
        String[] stateArr = state.split(",");
        List<String> stateList = Arrays.asList(stateArr);

        IPage iPage = new Query<AppraisalPojo>().getPage(params);
        IPage<Map<String,Object>> page = appraisalDao.getPageAppraisal(iPage,userId,state,stateList);

//        Long userId = MapUtils.getLongValue(params,"userId");
//        String state = MapUtils.getString(params,"state");
//        String[] stateArr = state.split(",");
//        List<String> stateList = Arrays.asList(stateArr);
//        IPage<AppraisalPojo> page = this.page(
//                new Query<AppraisalPojo>().getPage(params),
//                new QueryWrapper<AppraisalPojo>()
//                        .eq("create_user",userId).in(StringUtils.isNotBlank(state),"state",stateList).orderByDesc("create_time"));
        return new PageUtils(page);
    }

    public List<SysFileEntity> getFileList(String... fileIds){
        List<Integer> ids = new ArrayList<>();
        for(String fileId : fileIds){
            String[] fileIdArr = fileId.split(",");
            for(String file : fileIdArr){
                ids.add(Integer.valueOf(file));
            }
        }
        return sysFileService.list(new QueryWrapper<SysFileEntity>().in("id",ids));
    }
}
