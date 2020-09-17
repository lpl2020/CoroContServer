package com.coroapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coroapi.mapper.mysql.EpideSituDisplayPersionMapper;
import com.coroapi.model.EpideSituDisplayModel;
import com.coroapi.model.EpideSituDisplayPerModel;

//解决跨域问题
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*",maxAge = 3600)
//注解的方式进行配置
@RestController//返回json字符串的数据，直接可以编写RESTFul的接口
@SpringBootApplication//@SpringBootApplication声明让spring boot自动给程序进行必要的配置
@RequestMapping("/root/getEpideSituDisplayPersion")  //配置系统拦截url

/**
 * 获取行为轨迹接口数据
 * @author 李磊
 *
 */
public class EpideSituDisplayPersonController {
	
	private static final Logger logger = LoggerFactory.getLogger(EpideSituDisplayPersonController.class);

	/**
	 * 数据库接口
	 */
	@Autowired
	private EpideSituDisplayPersionMapper EpideSituDisplayPersionMapper;
	
	private static final String DEFAULTSCHOOL ="山东英才学院";
	
	/**
	 * 1整体概况接口
	 * @param isStudent
	 * @param personNo
	 * @return
	 */
	@RequestMapping(value="/entiCount",method= RequestMethod.GET)
    public Object getEntiCount(
    		@RequestParam(value = "school", required = false) String school
    		) {
		
//    	logger.info("entiCount API START AND PARA isStudent IS "+isStudent+" personNo IS" + personNo);
		
		JSONObject resultJsonObject = new JSONObject();
		if(null==school ||"".equals(school)) school =DEFAULTSCHOOL;
		
		//校验输入参数合法性
//		if("".equals(isStudent)||"".equals(isStudent)) {
//			resultJsonObject.put("errorCode", "4000");//错误码4000参数为空 4001参数不正确， 4002认证失败
//			return resultJsonObject;
//		}
		try {
			List<EpideSituDisplayPerModel> baseInfoList=EpideSituDisplayPersionMapper.getEntiCount(school);
			List<EpideSituDisplayPerModel> zdsrInfoList=EpideSituDisplayPersionMapper.getZdsrCount(school);
			if(baseInfoList.size()<=0 ||zdsrInfoList.size()<=0) {
				resultJsonObject.put("errorCode", "4000");//错误码4000参数为空 4001参数不正确， 4002认证失败
				return resultJsonObject;
			}
			resultJsonObject.put("errorCode", "");//错误码4000参数为空 4001参数不正确， 4002认证失败
//			resultJsonObject.put("isStudent", isStudent);//输入值
//			resultJsonObject.put("personNo", personNo);//输入值
//			
			resultJsonObject.put("teachercount", baseInfoList.get(0).getTeachercount());//教师数量
			resultJsonObject.put("studentcount", baseInfoList.get(0).getStudentcount());//学生数量
			resultJsonObject.put("foreigner", baseInfoList.get(0).getForeigner());//留学生
			resultJsonObject.put("other", baseInfoList.get(0).getOther());//随访人
			resultJsonObject.put("fromOtherCountry", zdsrInfoList.get(0).getFromOtherCountry());//境外输入
			resultJsonObject.put("fromWuHan", zdsrInfoList.get(0).getFromWuHan());//武汉输入
			resultJsonObject.put("focusObservation", zdsrInfoList.get(0).getFocusObservation());//重点观察
		} catch (Exception e) {
			resultJsonObject.put("errorCode", "4003");//错误码4000参数为空 4001参数不正确， 4002认证失败
			// TODO: handle exception
		}

		logger.info("getRelationshipMap API  END");
        return resultJsonObject;
    }
	/**
	 *2 隔离人数饼状图
	 * @param isStudent
	 * @param personNo
	 * @return
	 */
	@RequestMapping(value="/glCount",method= RequestMethod.GET)
    public Object getGlCount(
    		@RequestParam(value = "isStudent", required = true) String isStudent,
    		@RequestParam(value = "personNo", required = true) String personNo
    		) {
		
    	logger.info("entiCount API START AND PARA isStudent IS "+isStudent+" personNo IS" + personNo);
		
		JSONObject resultJsonObject = new JSONObject();
		
		//校验输入参数合法性
		if("".equals(isStudent)||"".equals(isStudent)) {
			resultJsonObject.put("errorCode", "4000");//错误码4000参数为空 4001参数不正确， 4002认证失败
			return resultJsonObject;
		}
		try {
			//List<EpideSituDisplayPerModel> baseInfoList=EpideSituDisplayPersionMapper.getEntiCount();
//			if(baseInfoList.size()<=0) {
//				resultJsonObject.put("errorCode", "4000");//错误码4000参数为空 4001参数不正确， 4002认证失败
//				return resultJsonObject;
//			}
			resultJsonObject.put("errorCode", "");//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("isStudent", isStudent);//输入值
			resultJsonObject.put("personNo", personNo);//输入值
			JSONArray objectArray = new JSONArray();//Json数组
			
			JSONObject object1 = new JSONObject();
			object1.put("value",0);
			object1.put("name","正在14天隔离人数");
			objectArray.add(object1);
			JSONObject object2 = new JSONObject();
			object2.put("value",0);
			object2.put("name","结束14天隔离人数");
			objectArray.add(object2);
			resultJsonObject.put("data", objectArray);//输入值
			
//			resultJsonObject.put("teachercount", baseInfoList.get(0).getTeachercount());//教师数量
//			resultJsonObject.put("studentcount", baseInfoList.get(0).getStudentcount());//学生数量
//			resultJsonObject.put("foreigner", baseInfoList.get(0).getForeigner());//留学生
//			resultJsonObject.put("other", baseInfoList.get(0).getOther());//随访人
//			resultJsonObject.put("fromOtherCountry", baseInfoList.get(0).getFromOtherCountry());//境外输入
//			resultJsonObject.put("fromWuHan", baseInfoList.get(0).getFromWuHan());//武汉输入
//			resultJsonObject.put("focusObservation", baseInfoList.get(0).getFocusObservation());//重点观察
		} catch (Exception e) {
			resultJsonObject.put("errorCode", "4003");//错误码4000参数为空 4001参数不正确， 4002认证失败
			// TODO: handle exception
		}

		
		
		logger.info("getRelationshipMap API  END");
        return resultJsonObject;
    }
	
	/**
	 * 3校门出入折线图
	 * @param isStudent
	 * @param personNo
	 * @return
	 */
	@RequestMapping(value="/xmcrCount",method= RequestMethod.GET)
    public Object getXmcrCount(
//    		@RequestParam(value = "isStudent", required = true) String isStudent,
//    		@RequestParam(value = "personNo", required = true) String personNo
    		@RequestParam(value = "school", required = false) String school
    		) {
		
    	logger.info("entiCount API START AND PARA isStudent IS  personNo IS");
		
		JSONObject resultJsonObject = new JSONObject();
		if(null==school ||"".equals(school)) school =DEFAULTSCHOOL;
		//校验输入参数合法性
//		if("".equals(isStudent)||"".equals(isStudent)) {
//			resultJsonObject.put("errorCode", "4000");//错误码4000参数为空 4001参数不正确， 4002认证失败
//			return resultJsonObject;
//		}
		try {
			List<EpideSituDisplayPerModel> baseInfoList=EpideSituDisplayPersionMapper.getXmcrCount(school);
//			if(baseInfoList.size()<=0) {
//				resultJsonObject.put("errorCode", "4000");//错误码4000参数为空 4001参数不正确， 4002认证失败
//				return resultJsonObject;
//			}
			resultJsonObject.put("errorCode", "");//错误码4000参数为空 4001参数不正确， 4002认证失败
//			resultJsonObject.put("isStudent", isStudent);//输入值
//			resultJsonObject.put("personNo", personNo);//输入值
			
			String[] xData = {"01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00"
					,"13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00","24:00"};
			Integer[] yDataStudent = new Integer[24];
			Integer[] yDataTeacher = new Integer[24];
			Integer[] yDataOther = new Integer[24];
			
			for(int i=0; i<24;i++) {
				yDataStudent[i] =0 ;
				yDataTeacher[i] =0 ;
				yDataOther[i] =0 ;
				for(EpideSituDisplayPerModel object:baseInfoList) {
					if(Integer.valueOf(object.getTimestamp())==i+1) {
						
						yDataStudent[i]=yDataStudent[i]+object.getStudentcount();
						yDataTeacher[i]=yDataTeacher[i]+object.getTeachercount();
						yDataOther[i]=yDataOther[i]+object.getOther();
					}
				}
			}
			resultJsonObject.put("xData", xData);//教师数量
			resultJsonObject.put("yDataStudent", yDataStudent);//学生数量
			resultJsonObject.put("yDataTeacher", yDataTeacher);//留学生
			resultJsonObject.put("yDataOther", yDataOther);//随访人

		} catch (Exception e) {
			resultJsonObject.put("errorCode", "4003");//错误码4000参数为空 4001参数不正确， 4002认证失败
			// TODO: handle exception
			logger.error(e.getMessage());
		}
		logger.info("getRelationshipMap API  END");
        return resultJsonObject;
    }

	/**
	 * 接口4、预警信息
	 * @return
	 */
	@RequestMapping(value="/yjxn",method= RequestMethod.GET)
    public Object getYjxno (
//    		@RequestParam(value = "isStudent", required = true) String isStudent,
//    		@RequestParam(value = "personNo", required = true) String personNo
    		@RequestParam(value = "school", required = false) String school
    		) {
		
    	logger.info("sy_ejxyfxinfo API START AND PARA isStudent IS NULL");
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("errorCode", "");//错误码4000参数为空 4001参数不正确， 4002认证失败
		if(null==school ||"".equals(school)) school =DEFAULTSCHOOL;
		
        List <String> result = new ArrayList<>();
        List<EpideSituDisplayPerModel> epideSituInfoList=EpideSituDisplayPersionMapper.getYjxno(school);
        List<EpideSituDisplayPerModel> epidesSituInfoList=EpideSituDisplayPersionMapper.getYjxnos(school);
        
        Integer[] count = new Integer[] {0,0,0,0,0};
		for(EpideSituDisplayPerModel epideSituInfo:epideSituInfoList) {
			String heathinfo1 = epideSituInfo.getHeathinfo1();
			String timestamp = epideSituInfo.getTimestamp();
			if(heathinfo1.length()!=5) break;
			for(int i =0 ;i<4;i++) {
				if("1".equals(heathinfo1.substring(i, i+1))) {
					count[i]++;
				}
			}
		
		}
		result.add("至目前累计前出现发热"+count[0]+"位");
		result.add("至目前累计前出现咳嗽"+count[1]+"位");
		result.add("至目前累计前出现气喘"+count[2]+"位");
		result.add("至目前累计前出现腹泻"+count[3]+"位");
		//result.add("至目前累计确诊0例");
		result.add("今日新增发热、咳嗽、气喘、腹泻"+epidesSituInfoList.get(0).getCount()+"位");
		resultJsonObject.put("result", result);
			
		logger.info("sy_ejxyfxinfo API  END");
        return resultJsonObject;
    }
	/**
	 * 接口5、响应事件
	 * @return
	 */
	@RequestMapping(value="/sysj",method= RequestMethod.GET)
    public Object getSysj (
//    		@RequestParam(value = "isStudent", required = true) String isStudent,
//    		@RequestParam(value = "personNo", required = true) String personNo
    		@RequestParam(value = "school", required = false) String school
    		) {
		
    	logger.info("sy_ejxyfxinfo API START AND PARA isStudent IS NULL");
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("errorCode", "");//错误码4000参数为空 4001参数不正确， 4002认证失败
		if(null==school ||"".equals(school)) school =DEFAULTSCHOOL;
		
		JSONArray result = new JSONArray();
		List<EpideSituDisplayPerModel> epideSituInfoList=EpideSituDisplayPersionMapper.getSysj(school);
		for(EpideSituDisplayPerModel epideSituInfo:epideSituInfoList) {
			JSONObject jsonObj1= new JSONObject();
			jsonObj1.put("name", epideSituInfo.getUserName());
			jsonObj1.put("content", epideSituInfo.getTimestamp()+"隔离");
			jsonObj1.put("level", "一级");
			result.add(jsonObj1);
		}
		
		resultJsonObject.put("result", result);
			
		logger.info("sy_ejxyfxinfo API  END");
        return resultJsonObject;
    }
	
	
	/**
	 * 接口6、响应事件
	 * @return
	 */
	@RequestMapping(value="/syrelationship",method= RequestMethod.GET)
    public Object getSySelationship (
    		@RequestParam(value = "isStudent", required = true) String isStudent,
    		@RequestParam(value = "personNo", required = true) String personNo,
    		@RequestParam(value = "school", required = false) String school
    		) {
		
    	logger.info("sy_ejxyfxinfo API START AND PARA isStudent IS NULL");
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("errorCode", "");//错误码4000参数为空 4001参数不正确， 4002认证失败
		if(null==school ||"".equals(school)) school =DEFAULTSCHOOL;
		JSONArray categoryArray = new JSONArray();
		JSONArray linksArray = new JSONArray();
		JSONArray nodesList = new JSONArray();
		
		List<EpideSituDisplayPerModel> baseInfoList=EpideSituDisplayPersionMapper.getpersionInfoDetail(school,personNo);
		List<EpideSituDisplayPerModel> relationList=EpideSituDisplayPersionMapper.getRelationPersionInfo(school,personNo);
		if(baseInfoList.size()==0 || relationList.size() ==0) {
			resultJsonObject.put("errorCode", "4001");//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("msg", "查询不到人");//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("categories", categoryArray);
			resultJsonObject.put("nodes", nodesList);
			resultJsonObject.put("links", linksArray);
			return resultJsonObject;
		}
		
	
		
		JSONObject jsonObj1= new JSONObject();
		jsonObj1.put("name", "核心人物");
		categoryArray.add(jsonObj1);
		JSONObject jsonObj2= new JSONObject();
		jsonObj2.put("name", "舍友");
		categoryArray.add(jsonObj2);
//		JSONObject jsonObj3= new JSONObject();
//		jsonObj3.put("name", "密切同学");
//		categoryArray.add(jsonObj3);
		JSONObject jsonObj4= new JSONObject();
		jsonObj4.put("name", "轨迹接触");
		categoryArray.add(jsonObj4);
		
		
		JSONObject nodesObj1= new JSONObject();
		nodesObj1.put("category", 0);nodesObj1.put("name", baseInfoList.get(0).getUserName());nodesObj1.put("value", 25);nodesObj1.put("label",baseInfoList.get(0).getUserName());
		nodesList.add(nodesObj1);
		JSONObject nodesObj2= new JSONObject();
		nodesObj2.put("category", 1);nodesObj2.put("name", "彭岚承");nodesObj2.put("value", 25);nodesObj2.put("label", "彭岚承");
		nodesList.add(nodesObj2);
		JSONObject nodesObj3= new JSONObject();
		nodesObj3.put("category", 1);nodesObj3.put("name", "董会风");nodesObj3.put("value", 15);nodesObj3.put("label", "董会风");
		nodesList.add(nodesObj3);
		
		for(EpideSituDisplayPerModel relationObject:relationList) {
			JSONObject nodesObj4= new JSONObject();
			nodesObj4.put("category", 2);nodesObj4.put("name", relationObject.getUserName());nodesObj4.put("value", 23);nodesObj4.put("label", relationObject.getUserName());
			nodesList.add(nodesObj4);		
			}
		
		
		for(int i =1 ;i< nodesList.size();i++) {
			JSONObject linkObj1= new JSONObject();
			linkObj1.put("source", nodesList.getJSONObject(0).getString("name"));linkObj1.put("target", nodesList.getJSONObject(i).getString("name"));linkObj1.put("weight ", 1);
			linksArray.add(linkObj1);
		}
		for(int i =2 ;i< nodesList.size();i++) {
			JSONObject linkObj1= new JSONObject();
			linkObj1.put("source", nodesList.getJSONObject(1).getString("name"));linkObj1.put("target", nodesList.getJSONObject(i).getString("name"));linkObj1.put("weight ", 1);
			linksArray.add(linkObj1);
		}
		
		
		resultJsonObject.put("categories", categoryArray);
		resultJsonObject.put("nodes", nodesList);
		resultJsonObject.put("links", linksArray);
			
		logger.info("sy_ejxyfxinfo API  END");
        return resultJsonObject;
    }
	
	
	/**
	 * 17人员详情信息
	 * @param isStudent
	 * @param personNo
	 * @return
	 */
	@RequestMapping(value="/persionInfo",method= RequestMethod.GET)
    public Object getpersionInfoDetail(
    		@RequestParam(value = "isStudent", required = true) String isStudent,
    		@RequestParam(value = "personNo", required = true) String personNo,
    		@RequestParam(value = "school", required = false) String school
    		) {
		
    	logger.info("entiCount API START AND PARA isStudent IS "+isStudent+" personNo IS" + personNo);
		
		JSONObject resultJsonObject = new JSONObject();
		if(null==school ||"".equals(school)) school =DEFAULTSCHOOL;
		//校验输入参数合法性
		if("".equals(isStudent)||"".equals(isStudent)) {
			resultJsonObject.put("errorCode", "4000");//错误码4000参数为空 4001参数不正确， 4002认证失败
			return resultJsonObject;
		}
		
		List<EpideSituDisplayPerModel> baseInfoList=EpideSituDisplayPersionMapper.getpersionInfoDetail(school,personNo);
		List<EpideSituDisplayPerModel> baseInfoSList=EpideSituDisplayPersionMapper.getpersionInfoSDetail(school,personNo);
//		List<EpideSituDisplayPerModel> parentsInfoSList=EpideSituDisplayPersionMapper.getparentsInfoSDetail(school,personNo);
		if(baseInfoList.size()<=0) {
			resultJsonObject.put("errorCode", "4003");//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("Mgss", "无数据");//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("name", "");//姓名
			resultJsonObject.put("age", "");//年龄
			resultJsonObject.put("class", "");//班级
			resultJsonObject.put("sex", "");//性别
			resultJsonObject.put("returnTime", "");//返校时间
			resultJsonObject.put("segregateTime", "");//随访人
			resultJsonObject.put("from", "");//输入值
			resultJsonObject.put("transport", "");//输入值
			
			resultJsonObject.put("phone", "");//输入值
			resultJsonObject.put("parentPhone", "");//输入值
			
			return resultJsonObject;
		}
		resultJsonObject.put("errorCode", "");//错误码4000参数为空 4001参数不正确， 4002认证失败
		resultJsonObject.put("isStudent", isStudent);//输入值
		resultJsonObject.put("personNo", personNo);//输入值
		
		resultJsonObject.put("from", null==baseInfoList.get(0).getAddr()?"":baseInfoList.get(0).getAddr());//输入值
		
		StringBuffer transport = new StringBuffer("");
		if(null != baseInfoList.get(0).getFxVehicl() && baseInfoList.get(0).getFxVehicl().length()==5) {
			String fxVehicl = baseInfoList.get(0).getFxVehicl();
			if(fxVehicl.substring(0, 1).equals("1")) {
				transport.append(" 汽车");
			}
			if(fxVehicl.substring(1, 2).equals("1")) {
				transport.append(" 火车");
			}
			if(fxVehicl.substring(2, 3).equals("1")) {
				transport.append(" 高铁");
			}
			if(fxVehicl.substring(3, 4).equals("1")) {
				transport.append(" 飞机");
			}
			if(fxVehicl.substring(4, 5).equals("1")) {
				transport.append(" 私家车");
			}
		}
		
		resultJsonObject.put("transport", transport+" "+ (baseInfoList.get(0).getFxjtSm()==null?"":baseInfoList.get(0).getFxjtSm()));//输入值
		
		resultJsonObject.put("name", baseInfoList.get(0).getUserName());//姓名
		resultJsonObject.put("age", baseInfoList.get(0).getAge());//年龄
		resultJsonObject.put("class", baseInfoList.get(0).getClasses());//班级
		resultJsonObject.put("sex", baseInfoList.get(0).getSex());//性别
		resultJsonObject.put("returnTime", baseInfoList.get(0).getFxTime());//返校时间
		resultJsonObject.put("segregateTime", baseInfoSList.size()==0?baseInfoList.get(0).getFxTime():baseInfoSList.get(0).getTimestamp());//随访人
		resultJsonObject.put("phone", baseInfoList.get(0).getPhone());//输入值
		resultJsonObject.put("parentPhone", baseInfoList.get(0).getParentPhone());//输入值
		
		try {
			
			
		} catch (Exception e) {
			resultJsonObject.put("errorCode", "4003");//错误码4000参数为空 4001参数不正确， 4002认证失败
			
			// TODO: handle exception
		}

		
		
		logger.info("getRelationshipMap API  END");
        return resultJsonObject;
    }
	
	/**
	 * 接口8、个人轨迹图
	 * @return
	 */
	@RequestMapping(value="/sy_grgjt",method= RequestMethod.GET)
    public Object getSyGrgjt(
    		@RequestParam(value = "isStudent", required = true) String isStudent,
    		@RequestParam(value = "personNo", required = true) String personNo,
    		@RequestParam(value = "school", required = false) String school
    		) {
		
    	logger.info("sy_grgjt API START AND PARA isStudent IS NULL");
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("errorCode", "");//错误码4000参数为空 4001参数不正确， 4002认证失败
		if(null==school ||"".equals(school)) school =DEFAULTSCHOOL;
		
		List<EpideSituDisplayPerModel> epideSituInfoList=EpideSituDisplayPersionMapper.getSyGrgjt(school);
		List<EpideSituDisplayPerModel> epideSituInfoLDList=EpideSituDisplayPersionMapper.getSyGrgjtLD(school,personNo);
		if(epideSituInfoList.size()==0) {
			resultJsonObject.put("errorCode", "4003");//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("Mgss", "无数据");//错误码4000参数为空 4001参数不正确， 4002认证失败
			return resultJsonObject;
		}
		/**
		 * 取学校中心
		 */
		Double[] center = new Double[] {Double.parseDouble(epideSituInfoList.get(0).getCenterLongitude().toString()),
				Double.parseDouble(epideSituInfoList.get(0).getCenterDimension())};
		epideSituInfoList.get(0).getCenterLongitude();
		epideSituInfoList.get(0).getCenterDimension();
		JSONObject result = new JSONObject();
		resultJsonObject.put("center", center);
		
		//设置地图点
		JSONObject geoCoordMap = new JSONObject();
		JSONArray targetData = new JSONArray();
		for(EpideSituDisplayPerModel epideSituInfoLD:epideSituInfoLDList) {
			
			Double[] gpsInfo = new Double[] {epideSituInfoLD.getLongitude(),epideSituInfoLD.getDimension()};
			geoCoordMap.put(epideSituInfoLD.getAddr(),gpsInfo);
			
		}
		
		//只有一次地址记录位置轨迹为空
		resultJsonObject.put("geoCoordMap", geoCoordMap);
		if(epideSituInfoLDList.size()==1) {
			
			JSONArray targetDataObj = new JSONArray();
			JSONObject jsonObjOr = new JSONObject();
			jsonObjOr.put("name",epideSituInfoLDList.get(0).getAddr());
			jsonObjOr.put("value", epideSituInfoLDList.get(0).getTime());
			targetDataObj.add(jsonObjOr);
			
			JSONObject jsonObjTr = new JSONObject();
			jsonObjTr.put("name",epideSituInfoLDList.get(0).getAddr());
			jsonObjTr.put("value", epideSituInfoLDList.get(0).getTime());
			
			targetDataObj.add(jsonObjTr);
			targetDataObj.add(1);
			
			targetData.add(targetDataObj);
			JSONObject geometry = new JSONObject();
			
			resultJsonObject.put("targetData", targetData);
			return resultJsonObject;
		}
		for(int i = 1;i < epideSituInfoLDList.size();i++) {
			
			JSONArray targetDataObj = new JSONArray();
			JSONObject jsonObjOr = new JSONObject();
			jsonObjOr.put("name",epideSituInfoLDList.get(i-1).getAddr());
			jsonObjOr.put("value", epideSituInfoLDList.get(i-1).getTime());
			targetDataObj.add(jsonObjOr);
			
			JSONObject jsonObjTr = new JSONObject();
			jsonObjTr.put("name",epideSituInfoLDList.get(i).getAddr());
			jsonObjTr.put("value", epideSituInfoLDList.get(i).getTime());
			
			targetDataObj.add(jsonObjTr);
			targetDataObj.add(1);
			
			targetData.add(targetDataObj);
			JSONObject geometry = new JSONObject();
		}
				
		resultJsonObject.put("geoCoordMap", geoCoordMap);
		resultJsonObject.put("targetData", targetData);
		
		logger.info("sy_grgjt API  END");
        return resultJsonObject;
    }
	
	/**
	 * 接口9、个人响应事件
	 * @return
	 */
	@RequestMapping(value="/sy_grxysj",method= RequestMethod.GET)
    public Object getSyGrxysj(
    		@RequestParam(value = "isStudent", required = true) String isStudent,
    		@RequestParam(value = "personNo", required = true) String personNo,
    		@RequestParam(value = "school", required = false) String school
    		) {
		
    	logger.info("sy_grgjt API START AND PARA isStudent IS NULL");
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("errorCode", "");//错误码4000参数为空 4001参数不正确， 4002认证失败
		if(null==school ||"".equals(school)) school =DEFAULTSCHOOL;
		
		List<EpideSituDisplayPerModel> epideSituInfoList=EpideSituDisplayPersionMapper.getSyGrxysj(school,personNo);
		if(epideSituInfoList.size()==0) {
			resultJsonObject.put("errorCode", "4003");//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("Mgss", "无数据");//错误码4000参数为空 4001参数不正确， 4002认证失败
			return resultJsonObject;
		}
	
		JSONArray arryObj = new JSONArray();
		for(EpideSituDisplayPerModel epideSituInfo:epideSituInfoList) {
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("name",epideSituInfo.getUserName());
			jsonObj.put("content",epideSituInfo.getTime()+"到达"+epideSituInfo.getAddr());
			jsonObj.put("level","一级");
			arryObj.add(jsonObj);
		}
				
		resultJsonObject.put("arryObj", arryObj);		
		logger.info("sy_grgjt API  END");
        return resultJsonObject;
    }
}
