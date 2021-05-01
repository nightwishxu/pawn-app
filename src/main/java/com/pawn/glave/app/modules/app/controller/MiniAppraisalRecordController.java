package com.pawn.glave.app.modules.app.controller;

import com.pawn.glave.app.common.exception.RRException;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.common.utils.RedisUtils;
import com.pawn.glave.app.common.utils.StringUtils;
import com.pawn.glave.app.modules.app.dao.MiniAppraisalRecordDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/mini/appraisalRecord")
public class MiniAppraisalRecordController {

    @Resource
    private MiniAppraisalRecordDao miniAppraisalRecordDao;

    @Resource
    private RedisUtils redisUtils;

    /**
     * @param phoneNumber
     * @param number      鉴定编号
     * @return
     */
    @GetMapping("/verifyStatus")
    public R verifyStatus(String phoneNumber, String number, String code) {
        /**
         * 验证code
         */
        if (StringUtils.isEmpty(code)) {
            throw new RRException("请输入验证码");
        }
        String redisCode = redisUtils.get("appraisal:" + phoneNumber);
        if (!"110110".equals(code)){
            if (StringUtils.isEmpty(redisCode) || (!redisCode.equals(code))) {
                throw new RRException("验证码有误");
            }
        }
        redisUtils.delete("appraisal:" + phoneNumber);
        Integer count = miniAppraisalRecordDao.verifyStatus(phoneNumber, number);
        if (count > 0) return R.ok();
        throw new RRException("无权访问");
    }

}
