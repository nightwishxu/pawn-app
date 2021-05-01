package com.pawn.glave.app.common.utils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MediaType;

/**
 * 螺丝帽工具类
 *
 * @author Wang Kang
 * @create 2020-04-29 4:59 下午
 **/
public class LuoSiMaoUtil {

    public static String sendCode(String mob, String code) {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(
                "api", "key-09e6d8569a9f211e94b642ff46a7218c"));
        WebResource webResource = client.resource(
                "http://sms-api.luosimao.com/v1/send.json");
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("mobile", mob);
        formData.add("message", "您的验证码是" + code + "。不要告诉别人哦~【蚌蚌拍当】");
        ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
                post(ClientResponse.class, formData);
        String textEntity = response.getEntity(String.class);
        return textEntity;
    }
}
