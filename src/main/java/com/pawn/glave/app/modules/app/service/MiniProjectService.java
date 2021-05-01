package com.pawn.glave.app.modules.app.service;


import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.modules.app.entity.MiniProjectUser;

import java.util.Map;

public interface MiniProjectService {
    R sendCode(String phone);

    R login(String phone, String code, String uuid);
    R login1(String phone, String code,String openId);

    R login(String phone,String openId);

    R expertsLogin(String phone, String password, String openId);

    R expertsWxLogin(String openId);
}
