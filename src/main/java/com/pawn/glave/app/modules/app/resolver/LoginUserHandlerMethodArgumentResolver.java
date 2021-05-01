package com.pawn.glave.app.modules.app.resolver;

import com.pawn.glave.app.common.utils.Constant;
import com.pawn.glave.app.modules.app.annotation.LoginUser;
import com.pawn.glave.app.modules.app.entity.ExpertsPojo;
import com.pawn.glave.app.modules.app.entity.MiniProjectUser;
import com.pawn.glave.app.modules.app.entity.UserEntity;
import com.pawn.glave.app.modules.app.interceptor.AuthorizationInterceptor;
import com.pawn.glave.app.modules.app.service.ExpertsService;
import com.pawn.glave.app.modules.app.service.MiniProjectUserService;
import com.pawn.glave.app.modules.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 *
 */
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private UserService userService;
    @Autowired
    private MiniProjectUserService miniProjectUserService;
    @Autowired
    private ExpertsService expertsService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return (parameter.getParameterType().isAssignableFrom(UserEntity.class) ||
                parameter.getParameterType().isAssignableFrom(MiniProjectUser.class)||
                parameter.getParameterType().isAssignableFrom(ExpertsPojo.class))
                && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        //获取用户ID
        Object object = request.getAttribute(Constant.USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if(object == null){
            return null;
        }

        if(parameter.getParameterType().isAssignableFrom(UserEntity.class)){
            //获取用户信息
            UserEntity user = userService.getById((Long)object);

            return user;
        }else if(parameter.getParameterType().isAssignableFrom(MiniProjectUser.class)){
            MiniProjectUser miniProjectUser = miniProjectUserService.getById((Long)object);
            return miniProjectUser;
        }else if(parameter.getParameterType().isAssignableFrom(ExpertsPojo.class)){
            ExpertsPojo expertsPojo = expertsService.getById((Long)object);
            return expertsPojo;
        }
        return null;
    }
}
