package com.pawn.glave.app.modules.app.config;

import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.common.utils.Ret;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@RestControllerAdvice(basePackages = "com.pawn.glave.app.modules.app.controller")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseAdvisor implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(o == null) {
            return Ret.builder().code(0).build();
        }else if(R.class.isAssignableFrom(o.getClass())){
            return o;
        }else{
            return Ret.builder().code(0).data(o).build();
        }
    }
}
