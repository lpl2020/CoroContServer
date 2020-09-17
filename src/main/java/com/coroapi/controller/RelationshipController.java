package com.coroapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

//解决跨域问题
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*",maxAge = 3600)
//注解的方式进行配置
@RestController//返回json字符串的数据，直接可以编写RESTFul的接口
@SpringBootApplication//@SpringBootApplication声明让spring boot自动给程序进行必要的配置
@RequestMapping("/corocon/relatpMap")  //配置系统拦截url

/**
 * 获取行为轨迹接口数据
 * @author 李磊
 *
 */
public class RelationshipController {
	
	private static final Logger logger = LoggerFactory.getLogger(RelationshipController.class);

	@RequestMapping(value="/getMainRelat/{userId}",method= RequestMethod.GET)
    public Object getRelationshipMap(@PathVariable("userId") String userId) {
		
    	logger.info("getRelationshipMap API START AND PARA userId IS "+userId);
		
		JSONObject resultJsonObject = new JSONObject();
		//校验输入参数合法性
		if("".equals(userId)) {
			resultJsonObject.put("errorCode", "4000");//错误码4000参数为空 4001参数不正确， 4002认证失败
			return resultJsonObject;
		}
		resultJsonObject.put("errorCode", "");//错误码4000参数为空 4001参数不正确， 4002认证失败
		resultJsonObject.put("userId", userId);//输入值
		
		//categoryArray JSON数据封装
		JSONArray categoryArray = new JSONArray();//Json数组
		//------------------------------------------------
		JSONObject categoryArrayObject1 = new JSONObject();
		categoryArrayObject1.put("name", "核心人物");
		categoryArrayObject1.put("category", 0);
		categoryArray.add(categoryArrayObject1);
		JSONObject categoryArrayObject2 = new JSONObject();
		categoryArrayObject2.put("name", "同宿舍");
		categoryArrayObject2.put("category", 1);
		categoryArray.add(categoryArrayObject2);
		JSONObject categoryArrayObject3 = new JSONObject();
		categoryArrayObject3.put("name", "同班级");
		categoryArrayObject3.put("category", 2);
		categoryArray.add(categoryArrayObject3);
		JSONObject categoryArrayObject4 = new JSONObject();
		categoryArrayObject4.put("name", "朋友");
		categoryArrayObject4.put("category", 3);
		categoryArray.add(categoryArrayObject4);
		//------------------------------------------------
		
		
		//nodesArray JSON数据封装
		JSONArray linksArray = new JSONArray();//Json数组
		//------------------------------------------------
		JSONObject linksArrayObject1 = new JSONObject();
		linksArrayObject1.put("source", "曹政凝");linksArrayObject1.put("target", "朱世琼");linksArrayObject1.put("weight",3);
		linksArray.add(linksArrayObject1);
		JSONObject linksArrayObject2 = new JSONObject();
		linksArrayObject2.put("source", "冯壮贵");linksArrayObject2.put("target", "朱世琼");linksArrayObject2.put("weight",4);
		linksArray.add(linksArrayObject2);
		JSONObject linksArrayObject3 = new JSONObject();
		linksArrayObject3.put("source", "龙功");linksArrayObject3.put("target", "朱世琼");linksArrayObject3.put("weight",2);
		linksArray.add(linksArrayObject3);
		JSONObject linksArrayObject4 = new JSONObject();
		linksArrayObject4.put("source", "宋刚");linksArrayObject4.put("target", "朱世琼");linksArrayObject4.put("weight",5);
		linksArray.add(linksArrayObject4);
		JSONObject linksArrayObject5 = new JSONObject();
		linksArrayObject5.put("source", "马荷梦");linksArrayObject5.put("target", "朱世琼");linksArrayObject5.put("weight",3);
		linksArray.add(linksArrayObject5);
		JSONObject linksArrayObject6 = new JSONObject();
		linksArrayObject6.put("source", "严黛");linksArrayObject6.put("target", "朱世琼");linksArrayObject6.put("weight",3);
		linksArray.add(linksArrayObject6);
		JSONObject linksArrayObject7 = new JSONObject();
		linksArrayObject7.put("source", "马荷梦");linksArrayObject7.put("target", "严黛");linksArrayObject7.put("weight",5);
		linksArray.add(linksArrayObject7);
		JSONObject linksArrayObject8 = new JSONObject();
		linksArrayObject8.put("source", "冯壮贵");linksArrayObject8.put("target", "宋刚");linksArrayObject8.put("weight",3);
		linksArray.add(linksArrayObject8);
		JSONObject linksArrayObject9 = new JSONObject();
		linksArrayObject9.put("source", "宋刚");linksArrayObject9.put("target", "龙功");linksArrayObject9.put("weight",3);
		linksArray.add(linksArrayObject9);
		//------------------------------------------------
		
		JSONArray nodesArray = new JSONArray();//Json数组
		//------------------------------------------------
		JSONObject nodesArrayObject1 = new JSONObject();
		nodesArrayObject1.put("category", 0);nodesArrayObject1.put("name", "朱世琼");nodesArrayObject1.put("value",15);nodesArrayObject1.put("label", "朱世琼");
		nodesArray.add(nodesArrayObject1);
		JSONObject nodesArrayObject2 = new JSONObject();
		nodesArrayObject2.put("category", 1);nodesArrayObject2.put("name", "曹政凝");nodesArrayObject2.put("value", 12);nodesArrayObject2.put("label", "曹政凝");
		nodesArray.add(nodesArrayObject2);
		JSONObject nodesArrayObject3 = new JSONObject();
		nodesArrayObject3.put("category", 1);nodesArrayObject3.put("name", "冯壮贵");nodesArrayObject3.put("value", 10);nodesArrayObject3.put("label", "冯壮贵");
		nodesArray.add(nodesArrayObject3);
		JSONObject nodesArrayObject4 = new JSONObject();
		nodesArrayObject4.put("category", 2);nodesArrayObject4.put("name", "龙功");nodesArrayObject4.put("value", 9);nodesArrayObject4.put("label", "龙功");
		nodesArray.add(nodesArrayObject4);
		JSONObject nodesArrayObject5 = new JSONObject();
		nodesArrayObject5.put("category", 4);nodesArrayObject5.put("name", "宋刚");nodesArrayObject5.put("value", 9);nodesArrayObject5.put("label", "宋刚");
		nodesArray.add(nodesArrayObject5);
		JSONObject nodesArrayObject6 = new JSONObject();
		nodesArrayObject6.put("category", 3);nodesArrayObject6.put("name", "马荷梦");nodesArrayObject6.put("value", 8);nodesArrayObject6.put("label", "马荷梦");
		nodesArray.add(nodesArrayObject6);
		JSONObject nodesArrayObject7 = new JSONObject();
		nodesArrayObject7.put("category", 3);nodesArrayObject7.put("name", "严黛");nodesArrayObject7.put("value", 10);nodesArrayObject7.put("label", "严黛");
		nodesArray.add(nodesArrayObject7);
		
		resultJsonObject.put("categoryArray", categoryArray);
		resultJsonObject.put("nodesArray", nodesArray);
		resultJsonObject.put("linksArray", linksArray);
		logger.info("getRelationshipMap API  END");
        return resultJsonObject;
    }
    
    
	@RequestMapping(value="/postMainRelat",method= RequestMethod.POST)
    public Object postRelationshipMap(
    		@RequestParam(value = "userId", required = true) String userId,
    		@RequestParam(value = "acessToken", required = true) String acessToken
    		) {
		
    	logger.info("getRelationshipMap API START AND PARA userId IS "+userId);
		
		JSONObject resultJsonObject = new JSONObject();
		//校验输入参数合法性
		if("".equals(userId)||"".equals(acessToken)) {
			resultJsonObject.put("errorCode", "4000");//错误码4000参数为空 4001参数不正确， 4002认证失败
			return resultJsonObject;
		}
		if(!"ZEZV0wZwnqA3oJYyocg45WGKcGN".equals(acessToken)) {
			resultJsonObject.put("errorCode", "4001");//错误码4000参数为空 4001参数不正确， 4002认证失败
			return resultJsonObject;
		}
		resultJsonObject.put("errorCode", "");//错误码4000参数为空 4001参数不正确， 4002认证失败
		resultJsonObject.put("userId", userId);//输入值
		
		//categoryArray JSON数据封装
		JSONArray categoryArray = new JSONArray();//Json数组
		//------------------------------------------------
		JSONObject categoryArrayObject1 = new JSONObject();
		categoryArrayObject1.put("name", "核心人物");
		categoryArrayObject1.put("category", 0);
		categoryArray.add(categoryArrayObject1);
		JSONObject categoryArrayObject2 = new JSONObject();
		categoryArrayObject2.put("name", "同宿舍");
		categoryArrayObject2.put("category", 1);
		categoryArray.add(categoryArrayObject2);
		JSONObject categoryArrayObject3 = new JSONObject();
		categoryArrayObject3.put("name", "同班级");
		categoryArrayObject3.put("category", 2);
		categoryArray.add(categoryArrayObject3);
		JSONObject categoryArrayObject4 = new JSONObject();
		categoryArrayObject4.put("name", "朋友");
		categoryArrayObject4.put("category", 3);
		categoryArray.add(categoryArrayObject4);
		//------------------------------------------------
		
		
		//nodesArray JSON数据封装
		JSONArray linksArray = new JSONArray();//Json数组
		//------------------------------------------------
		JSONObject linksArrayObject1 = new JSONObject();
		linksArrayObject1.put("source", "曹政凝");linksArrayObject1.put("target ", "朱世琼");linksArrayObject1.put("weight  ",3);
		linksArray.add(linksArrayObject1);
		JSONObject linksArrayObject2 = new JSONObject();
		linksArrayObject2.put("source", "冯壮贵");linksArrayObject2.put("target ", "朱世琼");linksArrayObject2.put("weight  ",4);
		linksArray.add(linksArrayObject2);
		JSONObject linksArrayObject3 = new JSONObject();
		linksArrayObject3.put("source", "龙功");linksArrayObject3.put("target ", "朱世琼");linksArrayObject3.put("weight  ",2);
		linksArray.add(linksArrayObject3);
		JSONObject linksArrayObject4 = new JSONObject();
		linksArrayObject4.put("source", "宋刚");linksArrayObject4.put("target ", "朱世琼");linksArrayObject4.put("weight  ",5);
		linksArray.add(linksArrayObject4);
		JSONObject linksArrayObject5 = new JSONObject();
		linksArrayObject5.put("source", "马荷梦");linksArrayObject5.put("target ", "朱世琼");linksArrayObject5.put("weight  ",3);
		linksArray.add(linksArrayObject5);
		JSONObject linksArrayObject6 = new JSONObject();
		linksArrayObject6.put("source", "严黛");linksArrayObject6.put("target ", "朱世琼");linksArrayObject6.put("weight  ",3);
		linksArray.add(linksArrayObject6);
		JSONObject linksArrayObject7 = new JSONObject();
		linksArrayObject7.put("source", "马荷梦");linksArrayObject7.put("target ", "严黛");linksArrayObject7.put("weight  ",5);
		linksArray.add(linksArrayObject7);
		JSONObject linksArrayObject8 = new JSONObject();
		linksArrayObject8.put("source", "冯壮贵");linksArrayObject8.put("target ", "宋刚");linksArrayObject8.put("weight  ",3);
		linksArray.add(linksArrayObject8);
		JSONObject linksArrayObject9 = new JSONObject();
		linksArrayObject9.put("source", "宋刚");linksArrayObject9.put("target ", "龙功");linksArrayObject9.put("weight  ",3);
		linksArray.add(linksArrayObject9);
		//------------------------------------------------
		
		JSONArray nodesArray = new JSONArray();//Json数组
		//------------------------------------------------
		JSONObject nodesArrayObject1 = new JSONObject();
		nodesArrayObject1.put("category", 0);nodesArrayObject1.put("userName", "朱世琼");nodesArrayObject1.put("value ",3);nodesArrayObject1.put("label ", "朱世琼");
		nodesArray.add(nodesArrayObject1);
		JSONObject nodesArrayObject2 = new JSONObject();
		nodesArrayObject2.put("category", 1);nodesArrayObject2.put("userName", "曹政凝");nodesArrayObject2.put("value ", 3);nodesArrayObject2.put("label ", "曹政凝");
		nodesArray.add(nodesArrayObject2);
		JSONObject nodesArrayObject3 = new JSONObject();
		nodesArrayObject3.put("category", 1);nodesArrayObject3.put("userName", "冯壮贵");nodesArrayObject3.put("value ", 3);nodesArrayObject3.put("label ", "冯壮贵");
		nodesArray.add(nodesArrayObject3);
		JSONObject nodesArrayObject4 = new JSONObject();
		nodesArrayObject4.put("category", 2);nodesArrayObject4.put("userName", "龙功");nodesArrayObject4.put("value ", 3);nodesArrayObject4.put("label ", "龙功");
		nodesArray.add(nodesArrayObject4);
		JSONObject nodesArrayObject5 = new JSONObject();
		nodesArrayObject5.put("category", 4);nodesArrayObject5.put("userName", "宋刚");nodesArrayObject5.put("value ", 5);nodesArrayObject5.put("label ", "宋刚");
		nodesArray.add(nodesArrayObject5);
		JSONObject nodesArrayObject6 = new JSONObject();
		nodesArrayObject6.put("category", 3);nodesArrayObject6.put("userName", "马荷梦");nodesArrayObject6.put("value ", 8);nodesArrayObject6.put("label ", "马荷梦");
		nodesArray.add(nodesArrayObject6);
		JSONObject nodesArrayObject7 = new JSONObject();
		nodesArrayObject7.put("category", 3);nodesArrayObject7.put("userName", "严黛");nodesArrayObject7.put("value ", 10);nodesArrayObject7.put("label ", "严黛");
		nodesArray.add(nodesArrayObject7);
		
		resultJsonObject.put("categoryArray", categoryArray);
		resultJsonObject.put("nodesArray", nodesArray);
		resultJsonObject.put("linksArray", linksArray);
		logger.info("getRelationshipMap API  END");
        return resultJsonObject;
    }
    
    
    

}
