package com.pawn.glave.app.modules.app.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.common.utils.RedisUtils;
import com.pawn.glave.app.modules.app.entity.ExpertTypePojo;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;
import com.pawn.glave.app.modules.app.entity.MiniProjectUser;
import com.pawn.glave.app.modules.app.service.ExpertTypeService;
import com.pawn.glave.app.modules.app.service.ExpertsService;
import com.pawn.glave.app.modules.app.service.MiniProjectService;
import com.pawn.glave.app.modules.app.service.MiniProjectUserService;
import com.pawn.glave.app.modules.app.utils.JwtUtils;
import com.pawn.glave.app.modules.app.utils.RedisCache;
import com.pawn.glave.app.modules.sys.entity.SysCaptchaEntity;
import com.pawn.glave.app.modules.sys.service.SysCaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class MiniProjectServiceImpl implements MiniProjectService {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private MiniProjectUserService miniProjectUserService;
    @Autowired
    private SysCaptchaService sysCaptchaService;
    @Autowired
    private ExpertsService expertsService;
    @Autowired
    private ExpertTypeService expertTypeService;

    @Override
    public R sendCode(String phone) {
        try {
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    @Override
    public R login(String phone, String code, String uuid) {
        R ret = new R();
        SysCaptchaEntity sysCaptchaEntity = sysCaptchaService.getOne(new QueryWrapper<SysCaptchaEntity>().eq("uuid", uuid)
                .eq("code", code).ge("expire_time", new Date()));
        if (sysCaptchaEntity == null) {
            ret.put("code", -1);
            ret.put("msg", "??????????????????");
        } else {
            //???????????????????????????????????????????????????????????????????????????????????????????????????
            ret.put("code", 0);
            MiniProjectUser miniProjectUser = miniProjectUserService.getOne(new QueryWrapper<MiniProjectUser>().
                    eq("phone", phone));
            Long userId;
            if (miniProjectUser == null) {//???????????????????????????
                MiniProjectUser insert = MiniProjectUser.builder().name(phone).wxNick(phone)
                        .wxImage("/image/default.png").state("0").phone(phone).build();
                miniProjectUserService.save(insert);
                ret.put("data", insert);
                userId = insert.getId();
            } else {
                ret.put("data", miniProjectUser);
                userId = miniProjectUser.getId();
            }
            ret.put("token", jwtUtils.generateToken(userId));
        }
        return ret;
    }

    @Resource
    private RedisUtils redisUtils;


    @Resource
    private RedisCache redisCache;

    @Override
    public R login1(String phone, String code, String openId) {
        String redisCode = redisCache.getCacheObject("login:" + phone);
        R ret = new R();
        if (com.pawn.glave.app.common.utils.StringUtils.isEmpty(redisCode) || com.pawn.glave.app.common.utils.StringUtils.isEmpty(code) || !redisCode.equals(code)) {
            ret.put("code", -1);
            ret.put("msg", "??????????????????");
        } else {
            redisCache.deleteObject("login:" + phone);
            //???????????????????????????????????????????????????????????????????????????????????????????????????
            ret.put("code", 0);
            MiniProjectUser miniProjectUser = miniProjectUserService.getOne(new QueryWrapper<MiniProjectUser>().
                    eq("phone", phone));
            Long userId;
            if (miniProjectUser == null) {//???????????????????????????
                MiniProjectUser insert = MiniProjectUser.builder().name(phone).wxNick(phone)
                        .wxImage("/image/default.png").state("0").phone(phone).miniOpenId(openId).build();
                miniProjectUserService.save(insert);
                ret.put("data", insert);
                userId = insert.getId();
            } else {
                ret.put("data", miniProjectUser);
                userId = miniProjectUser.getId();

                if (StringUtils.isNotBlank(openId)){
                    miniProjectUser.setMiniOpenId(openId);
                    miniProjectUserService.updateById(miniProjectUser);
                }
            }
            ret.put("token", jwtUtils.generateToken(userId));
        }
        return ret;
    }

    @Override
    public R login(String phone, String openId) {
        if (StringUtils.isBlank(phone)) {
            throw new RuntimeException("??????????????????");
        }
        if (StringUtils.isBlank(openId) && "undefined".equals(openId)) {
            throw new RuntimeException("openId??????");
        }
        R ret = new R();
        MiniProjectUser miniProjectUser = miniProjectUserService.getOne(new QueryWrapper<MiniProjectUser>()
                .eq("phone", phone));
        ret.put("code", 0);
        if (miniProjectUser == null) {//?????????????????????????????????
            MiniProjectUser insert = MiniProjectUser.builder().phone(phone).name(phone)
                    .wxImage("/image/default.png").state("0").wxNick(phone).miniOpenId(openId).build();
            miniProjectUserService.save(insert);
            ret.put("token", jwtUtils.generateToken(insert.getId()));
        } else {
            if (StringUtils.isBlank(miniProjectUser.getMiniOpenId()) || "undefined".equals(miniProjectUser.getMiniOpenId())) {
                miniProjectUser.setMiniOpenId(openId);
                miniProjectUserService.updateById(miniProjectUser);
            }
            ret.put("token", jwtUtils.generateToken(miniProjectUser.getId()));
        }
        ret.put("message", "????????????");
        return ret;
    }

    @Override
    public R expertsLogin(String phone, String password, String openId) {
        if (StringUtils.isBlank(phone)) {
            throw new RuntimeException("??????????????????");
        }
        if (StringUtils.isBlank(password)) {
            throw new RuntimeException("????????????");
        }
        R ret = new R();
        ExpertsPojo experts = expertsService.getOne(new QueryWrapper<ExpertsPojo>().eq("phone", phone));
        if (experts != null) {
            String pwd = experts.getPassword();
            if (pwd.equals(DigestUtil.md5Hex(password))) {
                if (StringUtils.isNotBlank(openId)) {
                    //openid????????? ????????????
                    experts.setOpenId(openId);
                    expertsService.updateById(experts);
                }
                ret.put("code", 0);
                ret.put("data", experts);
                //????????????
                List<ExpertTypePojo> expertTypePojos = expertTypeService.getExpertTypeByCode(experts.getCode());
                ret.put("typeList", expertTypePojos);
                ret.put("token", jwtUtils.generateToken(experts.getId()));
                ret.put("message", "????????????");
            } else {
                ret.put("code", -1);
                ret.put("message", "???????????????");
            }
        } else {
            ret.put("code", -1);
            ret.put("message", "???????????????");
        }
        return ret;
    }

    @Override
    public R expertsWxLogin(String openId) {
        if (StringUtils.isBlank(openId)) {
            throw new RuntimeException("openId??????");
        }
        R ret = new R();
        //??????openId????????????
        ExpertsPojo expertsPojo = expertsService.getOne(new QueryWrapper<ExpertsPojo>().eq("open_id", openId));
        if (expertsPojo != null) {
            ret.put("data", expertsPojo);
            ret.put("token", jwtUtils.generateToken(expertsPojo.getId()));
            //????????????
            List<ExpertTypePojo> expertTypePojos = expertTypeService.getExpertTypeByCode(expertsPojo.getCode());
            ret.put("typeList", expertTypePojos);
            ret.put("code", 0);
            ret.put("message", "????????????");
        } else {
            ret.put("code", -1);
            ret.put("message", "??????????????????");
        }
        return ret;
    }
}
