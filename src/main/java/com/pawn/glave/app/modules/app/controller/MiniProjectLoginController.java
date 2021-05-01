package com.pawn.glave.app.modules.app.controller;


import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.pawn.glave.app.common.exception.RRException;
import com.pawn.glave.app.common.utils.LuoSiMaoUtil;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.common.utils.RedisUtils;
import com.pawn.glave.app.modules.app.annotation.Login;
import com.pawn.glave.app.modules.app.annotation.LoginUser;
import com.pawn.glave.app.modules.app.entity.ExpertTypePojo;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;
import com.pawn.glave.app.modules.app.entity.MiniProjectUser;
import com.pawn.glave.app.modules.app.service.*;
import com.pawn.glave.app.modules.app.utils.JwtUtils;
import com.pawn.glave.app.modules.app.utils.RedisCache;
import com.pawn.glave.app.modules.sys.service.SysCaptchaService;
import com.pawn.glave.app.modules.app.service.UserService;
import com.pawn.glave.app.modules.app.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/app/mini/project")
@Api("小程序相关接口")
@Slf4j
public class MiniProjectLoginController {
    @Resource
    private UserService userService;

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private MiniProjectService miniProjectService;

    @Resource
    private SysCaptchaService sysCaptchaService;

    @Resource
    private ExpertTypeService expertTypeService;


    @GetMapping("/getCode")
    @ApiOperation("获取手机验证码")
    public R login(@ApiParam(value = "uuid（与登录一致）") @RequestParam("uuid") String uuid,
                   @ApiParam(value = "手机号") @RequestParam("mobile") String mobile) {
        //表单校验
        String code = RandomUtil.randomNumbers(6);
        /*模拟发送短信，验证码为code*/
        System.out.println("成功发送验证码，验证码为:" + code);
        //保存验证码跟uuid
        sysCaptchaService.mobileCode(uuid, code);
        return R.ok().put("code", code);
    }

    @Resource
    private RedisCache redisCache;

    @GetMapping("/getCode1")
    @ApiOperation("获取手机验证码(用于登录)")
    public R getCode1(@ApiParam(value = "手机号") @RequestParam("mobile") String mobile) {
        //表单校验
        String code = RandomUtil.randomNumbers(6);
        /*模拟发送短信，验证码为code*/
        /**
         * 发送验证码
         */
        String redisCode = redisCache.getCacheObject("login:" + mobile);
        if (StringUtils.isNotEmpty(redisCode)) {
            throw new RRException("请勿重复发送");
        }
        String result = LuoSiMaoUtil.sendCode(mobile, code);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getInteger("error") != 0) {
            log.error(jsonObject.toString());
            throw new RRException("验证码发送失败");
        }

        redisCache.setCacheObject("login:" + mobile, code, 600, TimeUnit.SECONDS);

        //保存验证码跟uuid
        sysCaptchaService.mobileCode(com.pawn.glave.app.common.utils.StringUtils.getUUID(), code);
        return R.ok();
    }

    @Resource
    private RedisUtils redisUtils;

    @GetMapping("/verifyLogin")
    @ApiOperation("获取手机验证码(扫描证书码用)")
    public R verifyLogin(@ApiParam(value = "手机号") @RequestParam("mobile") String mobile) {
        //表单校验
        String code = RandomUtil.randomNumbers(6);
        /*模拟发送短信，验证码为code*/
        /**
         * 发送验证码
         */
        String redisCode = redisUtils.get("appraisal:" + mobile);
        if (StringUtils.isNotEmpty(redisCode)) {
            throw new RRException("请勿重复发送");
        }
        String result = LuoSiMaoUtil.sendCode(mobile, code);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getInteger("error") != 0) {
            log.error(jsonObject.toString());
            throw new RRException("验证码发送失败");
        }
        redisUtils.set("appraisal:" + mobile, code, 600);

        //保存验证码跟uuid
        sysCaptchaService.mobileCode(com.pawn.glave.app.common.utils.StringUtils.getUUID(), code);
        return R.ok();
    }

    /**
     * 小程序手机验证码登陆接口
     *
     * @param phone
     * @param code
     * @return
     */
    @GetMapping("/mobile/login")
    @ApiOperation("手机号码登录")
    public R login(@ApiParam(value = "手机号") @RequestParam("phone") String phone,
                   @ApiParam(value = "验证码") @RequestParam("code") String code,
                   @ApiParam(value = "uuid（与获取验证码一致）") @RequestParam("uuid") String uuid) {
        return miniProjectService.login(phone, code, uuid);
    }

    @GetMapping("/mobile/login1")
    @ApiOperation("手机号码登录(修改后)")
    public R login1(@ApiParam(value = "手机号") @RequestParam("phone") String phone,
                    @ApiParam(value = "验证码") @RequestParam("code") String code,
                    @ApiParam(value = "openId") @RequestParam("openId") String openId) {
        return miniProjectService.login1(phone, code,openId);
    }


    /**
     * 小程序 微信登陆
     *
     * @param phone
     * @param openId
     * @return
     */
    @GetMapping("/wx/login")
    @ApiOperation("微信授权手机号码登录")
    public R wxLogin(@ApiParam(value = "手机号") @RequestParam("phone") String phone,
                     @ApiParam(value = "openId") @RequestParam("openId") String openId) {
        return miniProjectService.login(phone, openId);
    }

    /**
     * 根据token获取人員信息
     *
     * @param miniProjectUser
     * @return
     */
    @GetMapping("/getUserInfo")
    @ApiOperation("获取登录人信息")
    @Login
    public R getUserInfo(@LoginUser MiniProjectUser miniProjectUser) {
        if (miniProjectUser == null) {
            throw new RRException("不存在当前登录人员,请重新登录");
        }
        return R.ok().put("data", miniProjectUser);
    }

    /**
     * 小程序 专家账号密码登陆
     *
     * @param phone
     * @param password
     * @return
     */
    @GetMapping("/experts/login")
    @ApiOperation("专家手机号密码登录")
    public R expertsLogin(@ApiParam(value = "手机号") @RequestParam("phone") String phone,
                          @ApiParam(value = "密码") @RequestParam("password") String password) {
        return miniProjectService.expertsLogin(phone, password, "");
    }

    /**
     * 小程序 专家微信直接登录
     *
     * @param openId
     * @return
     */
    @GetMapping("/experts/wxLogin")
    @ApiOperation("专家微信直接登录")
    public R expertsWxLogin(@ApiParam(value = "openId") @RequestParam("openId") String openId) {
        return miniProjectService.expertsWxLogin(openId);
    }

    /**
     * 小程序 专家账号密码登陆
     *
     * @param openId
     * @return
     */
    @GetMapping("/experts/wxBind")
    @ApiOperation("专家微信首次登录绑定账号")
    public R expertsWxBind(@ApiParam(value = "openId") @RequestParam("openId") String openId,
                           @ApiParam(value = "手机号") @RequestParam("phone") String phone,
                           @ApiParam(value = "密码") @RequestParam("password") String password) {
        if (StringUtils.isBlank(openId)) {
            throw new RuntimeException("openId为空");
        }
        return miniProjectService.expertsLogin(phone, password, openId);
    }

    /**
     * 根据token获取专家人員信息
     *
     * @param expertsPojo
     * @return
     */
    @GetMapping("/getExpertsInfo")
    @ApiOperation("获取专家登录人信息")
    @Login
    public R getUserInfo(@LoginUser ExpertsPojo expertsPojo) {
        if (expertsPojo == null) {
            throw new RRException("不存在当前登录人员,请重新登录");
        }
        //获取专家
        List<ExpertTypePojo> expertTypePojos = expertTypeService.getExpertTypeByCode(expertsPojo.getCode());
        return R.ok().put("data", expertsPojo).put("typeList", expertTypePojos);
    }

    @PostMapping("/getToken")
    public R getToken(Long uid){
        return R.ok().put("token",jwtUtils.generateToken(uid));
    }
}
