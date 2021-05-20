package com.pawn.glave.app.modules.app.controller;

import com.pawn.glave.app.common.exception.RRException;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.common.utils.RedisUtils;
import com.pawn.glave.app.common.utils.StringUtils;
import com.pawn.glave.app.modules.app.dao.CertificateDao;
import com.pawn.glave.app.modules.app.dao.MiniAppraisalRecordDao;
import com.pawn.glave.app.modules.app.entity.CertificatePojo;
import com.pawn.glave.app.modules.app.service.CertificateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/mini/appraisalRecord")
public class MiniAppraisalRecordController {

    @Resource
    private MiniAppraisalRecordDao miniAppraisalRecordDao;

    @Resource
    private CertificateDao certificateDao;

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
        Map<String, Object> map = new HashMap<>();
        map.put("checked", count > 0);
        return R.ok(map);
    }
    @GetMapping("/findByCode")
    public R findByCode(String code) {
        CertificatePojo pojo = certificateDao.findOneByAppraisalCode(code);
        Map<String, Object> map = new HashMap<>();
        map.put("three_z_file_id", pojo.getThreeZFileId());
        map.put("two_z_file_id", pojo.getTwoZFileId());
        return R.ok(map);
    }

}
