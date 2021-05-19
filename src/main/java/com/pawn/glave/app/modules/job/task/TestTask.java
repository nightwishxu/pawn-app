package com.pawn.glave.app.modules.job.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pawn.glave.app.common.utils.HttpUtils;
import com.pawn.glave.app.modules.app.entity.SendPojo;
import com.pawn.glave.app.modules.app.service.SendService;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试定时任务(演示Demo，可删除)
 *
 * testTask为spring bean的名称
 **/
@Component("testTask")
public class TestTask implements ITask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SendService sendService;

	@Override
	public void run(String params){
		List<SendPojo> sendPojos = sendService.list(new QueryWrapper<SendPojo>().eq("state","1").last("limit 0,10"));
		for(SendPojo sendPojo : sendPojos){
			getConvertResult(sendPojo);
		}
	}
	public void getConvertResult(SendPojo sendPojo){
		String host = "https://api.9yuntu.cn";
		String path = "/execute/GetOutputResult";
		String method = "GET";
		String appcode = "ef1ed3bef5a94a5a90129760008bafe5";
		Map<String, String> headers = new HashMap();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap();
		querys.put("docID", sendPojo.getDocId());
		querys.put("outputType", "pdf");
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
			if(response.getStatusLine().getStatusCode() != 200){
				sendPojo.setState("-1");
			}else{
				String retCode = jsonObject.getString("retCode");
				if(retCode.equals("0")){//转换成功
					sendPojo.setState("3");
					JSONArray arr = jsonObject.getJSONArray("outputURLs");
					sendPojo.setPdfUrl(String.valueOf(arr.get(0)));
				}else{//转换失败
					sendPojo.setState(retCode);
				}
			}
			sendService.updateById(sendPojo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception{
		String host = "https://api.9yuntu.cn";
		String path = "/execute/GetOutputResult";
		String method = "GET";
		String appcode = "ef1ed3bef5a94a5a90129760008bafe5";
		Map<String, String> headers = new HashMap();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap();
		querys.put("docID", "bsae28BKGHueSUMIaUlffY");
		querys.put("outputType", "pdf");

			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
		System.out.println(jsonObject.toJSONString());
	}
}
