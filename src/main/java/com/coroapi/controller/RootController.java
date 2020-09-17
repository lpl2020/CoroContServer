package com.coroapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coroapi.mapper.mysql.RootMapper;
import com.coroapi.model.EpideSituDisplayModel;
import com.coroapi.model.RootModel;
import com.coroapi.model.SchoolPara;


//解决跨域问题
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*",maxAge = 3600)
//注解的方式进行配置
@RestController//返回json字符串的数据，直接可以编写RESTFul的接口
@SpringBootApplication//@SpringBootApplication声明让spring boot自动给程序进行必要的配置
@RequestMapping("/root")  //配置系统拦截url

/**
 * 获取行为轨迹接口数据
 * @author 李磊
 *
 */
public class RootController {
	
	@Autowired
	private RootMapper RootMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(RootController.class);

	private static final String DEFAULTSCHOOL ="山东英才学院";
	/**
	 * 接口1大屏登录接口
	 * @return
	 */
	@RequestMapping(value="/login",method= RequestMethod.GET)
    public Object login(
    		@RequestParam(value = "school", required = true) String school,
    		@RequestParam(value = "usr", required = true) String user,
    		@RequestParam(value = "psw", required = true) String password
    		) {
    	logger.info("login API START AND PARA isStudent IS NULL");
    	if(null==school ||"".equals(school)) school =DEFAULTSCHOOL;
    	
    	RootModel rootModel= new RootModel();
    	rootModel.setSchool(school);
    	rootModel.setUser(user);
    	rootModel.setPassword(password);
    	
    	List<RootModel> schoolInfo=RootMapper.login(rootModel);
    	
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("errorCode", 0);//错误码4000参数为空 4001参数不正确， 4002认证失败
		resultJsonObject.put("MGSS", "登录成功");//
		
		if(schoolInfo.size()==0) {
			resultJsonObject.put("errorCode", "4002");//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("MGSS", "登录失败");//
			return resultJsonObject;
		}
		
		resultJsonObject.put("school", schoolInfo.get(0).getSchool());
		resultJsonObject.put("url", schoolInfo.get(0).getBigscreenUrl());
			
		logger.info("login API  END");
        return resultJsonObject;
    }
	/**
	 * 接口2
	 * @return
	 */
	@RequestMapping(value="/getSchoolList",method= RequestMethod.GET)
    public Object getSchoolList(

    		) {
    	logger.info("getSchoolList API START AND PARA isStudent IS NULL");
    	List<RootModel> schoolInfo=RootMapper.getSchoolList();
    	
    	JSONArray schoolListArray = new JSONArray();
    	for(RootModel school:schoolInfo) {
    		schoolListArray.add(school.getSchool());
    	}
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("errorCode", "");//错误码4000参数为空 4001参数不正确， 4002认证失败
		resultJsonObject.put("MGSS", "获取学校列表成功");//
		
		resultJsonObject.put("school", schoolListArray);
			
		logger.info("getSchoolList API  END");
        return resultJsonObject;
    }
}
