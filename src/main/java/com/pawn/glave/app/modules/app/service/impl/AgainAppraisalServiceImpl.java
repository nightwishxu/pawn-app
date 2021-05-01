package com.pawn.glave.app.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.pawn.glave.app.common.exception.RRException;
import com.pawn.glave.app.common.utils.StringUtils;
import com.pawn.glave.app.modules.app.AgainConstant;
import com.pawn.glave.app.modules.app.dao.AppraisalAgainDao;
import com.pawn.glave.app.modules.app.entity.AppraisalAgain;
import com.pawn.glave.app.modules.app.entity.AppraisalPojo;
import com.pawn.glave.app.modules.app.entity.ExpertAppraisalPojo;
import com.pawn.glave.app.modules.app.param.AgainAppraisalSaveParam;
import com.pawn.glave.app.modules.app.service.AgainAppraisalService;
import com.pawn.glave.app.modules.app.service.AppraisalService;
import com.pawn.glave.app.modules.app.service.ExpertAppraisalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
@Transactional
public class AgainAppraisalServiceImpl implements AgainAppraisalService {
    @Resource
    private AppraisalService appraisalService;

    @Resource
    private AppraisalAgainDao appraisalAgainDao;

    @Resource
    private ExpertAppraisalService expertAppraisalService;

    private static final int MAX_UPLOAD = 9;

    /**
     * @param param
     */
    @Override
    public void save(AgainAppraisalSaveParam param) {
        /**
         * 获取用户ID
         */
        long userId = StringUtils.getWechatUserId();
//        long userId = 2;

        //鉴定申请ID
        long appraisalId = param.getAppraisalId();

        //检查当前鉴定状态，只有状态为3的时候才能提交
        LambdaQueryWrapper<AppraisalPojo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppraisalPojo::getId, appraisalId)
                .eq(AppraisalPojo::getCreateUser, userId);
        AppraisalPojo appraisalPojo = appraisalService.getOne(queryWrapper);
        if (appraisalPojo == null) {
            throw new RRException("当前鉴定申请不存在");
        } else if (!AgainConstant.AgainEnum.UN_APPRAISAL.getValue().equals(appraisalPojo.getState())) {
            throw new RRException("鉴定状态异常");
        }

        /**
         * 检查图片ID格式
         */
        String imgStr = param.getImgStr();
        if (StringUtils.isNotEmpty(imgStr)) {
            //去掉最后的逗号
            if (",".equals(imgStr.substring(imgStr.length() - 1))) {
                imgStr = imgStr.substring(0, imgStr.length() - 1);
            }

            /**
             * 最多只能上传9张图片
             */
            if (imgStr.split(",").length > MAX_UPLOAD) {
                throw new RRException("最多只能上传9张图片");
            }
        }

        //再次鉴定申请ID
        String againId = StringUtils.getUUID();

        /**
         * 修改鉴定申请状态
         */
        LambdaUpdateWrapper<AppraisalPojo> appraisalPojoUpdateWrapper = new LambdaUpdateWrapper<>();
        appraisalPojoUpdateWrapper.eq(AppraisalPojo::getId, appraisalId)
                .set(AppraisalPojo::getState, AgainConstant.AgainEnum.AGAIN_APPRAISAL.getValue())
                .set(AppraisalPojo::getAgainId, againId);
        appraisalService.update(appraisalPojoUpdateWrapper);

        /**
         * 讲选择的专家状态 改为待鉴定
         */
        LambdaUpdateWrapper<ExpertAppraisalPojo> expertUpdateMapper = new LambdaUpdateWrapper<>();
        expertUpdateMapper.eq(ExpertAppraisalPojo::getAppraisalId,appraisalId)
                .eq(ExpertAppraisalPojo::getIsSelection,1)
                .set(ExpertAppraisalPojo::getState,1);//todo:
        expertAppraisalService.update(expertUpdateMapper);

        /**
         * 保存-再次提交鉴定申请
         */
        AppraisalAgain appraisalAgain = new AppraisalAgain();
        appraisalAgain.setAgainReason(param.getReason());
        appraisalAgain.setUserId(userId);
        appraisalAgain.setAppraisalId(appraisalId);
        appraisalAgain.setAgainImg(imgStr);
        appraisalAgain.setAgainId(againId);
        appraisalAgain.setCreatedTime(new Date());
        appraisalAgainDao.insert(appraisalAgain);
    }

    /**
     * 根据再次申请ID 获取再次申请的详细信息
     *
     * @param againId
     * @return
     */
    @Override
    public AppraisalAgain infoByAgainId(String againId) {
        LambdaQueryWrapper<AppraisalAgain> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppraisalAgain::getAgainId, againId);
        return appraisalAgainDao.selectOne(queryWrapper);
    }

}
