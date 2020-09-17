package com.coroapi.controller;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ObjectArrayCodec;
import com.coroapi.mapper.mysql.WxApiMapper;
import com.coroapi.model.EpideSituDisplayModel;
import com.coroapi.model.WxApiModel;


//解决跨域问题
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*",maxAge = 3600)
//注解的方式进行配置
@RestController//返回json字符串的数据，直接可以编写RESTFul的接口
@SpringBootApplication//@SpringBootApplication声明让spring boot自动给程序进行必要的配置
@RequestMapping("/corocon/postWxApi")  //配置系统拦截url

/**
 * 微信小程序post接口
 * @author 李磊
 *
 */
public class WxApiController<T> {
	
	@Autowired
	private WxApiMapper WxApiMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(WxApiController.class);

	/**
	 * 接口1、1、每天健康数据采集：
	 * @return
	 */
	 @RequestMapping(value = "/yq_mtjkinfo",method = RequestMethod.POST)
    public Object postYqMtjkinfo(
    		@RequestBody Map params
    		) {
		
    	logger.info("yq_mtjkinfo API START AND PARA isStudent IS NULL");
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("errorCode", 0);//错误码4000参数为空 4001参数不正确， 4002认证失败
		resultJsonObject.put("msg", "写入数据库成功");
		
		//校验参数
		if(null == params.get("school") || null == params.get("name")|| null == params.get("no")
				|| null == params.get("date")|| null == params.get("heathinfo")) {
			resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("msg", "参数为空或不完整");
			logger.info("yq_mtjkinfo API  END");
	        return resultJsonObject;
		}
		//解析参数
		String school = params.get("school").toString();
		String name = params.get("name").toString();
		String no = params.get("no").toString();
		
		String heathinfoList = params.get("heathinfo").toString();
		String heathinfoList2 = heathinfoList.substring(1,heathinfoList.length()-1);
		String[]  heathinfo = heathinfoList2.split(",");
		//List<Object> heathinfo = JSONArray.parseArray(params.get("heathinfo").toString(), Object.class);
		logger.info("-----------------"+heathinfoList);
		String  heathinfo1 = heathinfo[0];
		Integer heathinfo2 = Integer.valueOf(heathinfo[1].trim());
		Integer heathinfo3 = Integer.valueOf(heathinfo[2].trim());
//		
		String  temperature = heathinfo[4].trim();
		String  isQuarantine = heathinfo[3].trim();
		String  notes = heathinfo[5].trim();
				
		String date = params.get("date").toString();
		
//		logger.info("***************"+heathinfo1+heathinfo2+heathinfo3);
		
		WxApiModel wxApiData = new WxApiModel();
		wxApiData.setSchool(school);
		wxApiData.setUserName(name);
		wxApiData.setUserNo(no);
		wxApiData.setHeathinfo1(heathinfo1);
		wxApiData.setHeathinfo2(heathinfo2);
		wxApiData.setHeathinfo3(heathinfo3);
		wxApiData.setTimestamp(date);
		wxApiData.setIsQuarantine(isQuarantine);
		wxApiData.setTemperature(temperature);
		wxApiData.setNotes(notes);
		WxApiMapper.insert(wxApiData);
		
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
			resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("msg", "解析或写入数据库失败");
			logger.info("yq_mtjkinfo API  END");
	        return resultJsonObject;
		}	
		
		
		logger.info("yq_mtjkinfo API  END");
        return resultJsonObject;
    }
	 
	 
	 /**
		 * 接口2、返校报到数据采集：
		 * @return
		 */
		 @RequestMapping(value = "/yq_fxbdinfo",method = RequestMethod.POST)
	    public Object postYqFxbdinfo(
	    		@RequestBody Map params
	    		) {
			
	    	logger.info("yq_fxbdinfo API START AND PARA isStudent IS NULL");
			JSONObject resultJsonObject = new JSONObject();
			resultJsonObject.put("errorCode", 0);//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("msg", "写入数据库成功");
			
			//校验参数
			if(null == params.get("school") || null == params.get("name")|| null == params.get("no")
					|| null == params.get("fxdate")|| null == params.get("fxaddr")
					|| null == params.get("fxjt")|| null == params.get("is_thSeriousarea")
					|| null == params.get("fxjt_sm")|| null == params.get("fxjk")
					) {
				resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
				resultJsonObject.put("msg", "参数为空或不完整");
				logger.info("yq_fxbdinfo API  END");
		        return resultJsonObject;
			}
			
			try {
				//解析参数
				String school = params.get("school").toString();
				String name = params.get("name").toString();
				String no = params.get("no").toString();
//				logger.info("***************"+((Map)params.get("fxaddr")).get("country"));
				String fxAddrCountry = ((Map)params.get("fxaddr")).get("country").toString();
				String fxAddrProvince = ((Map)params.get("fxaddr")).get("province").toString();
				String fxAddrCity = ((Map)params.get("fxaddr")).get("city").toString();
				String fxAddrTown = ((Map)params.get("fxaddr")).get("town").toString();
				
				String fxjtSm = params.get("fxjt_sm").toString();
				String fxjk = params.get("fxjk").toString();
				
				String fxVehicl = params.get("fxjt").toString();
				Integer isThseriousarea = Integer.valueOf(params.get("is_thSeriousarea").toString());
				String fxTime = params.get("fxdate").toString();
								
				WxApiModel wxApiData = new WxApiModel();
				wxApiData.setSchool(school);
				wxApiData.setUserName(name);
				wxApiData.setUserNo(no);
				wxApiData.setFxTime(fxTime);
				wxApiData.setFxAddrCountry(fxAddrCountry);
				wxApiData.setFxAddrProvince(fxAddrProvince);
				wxApiData.setFxAddrCity(fxAddrCity);
				wxApiData.setIsThseriousarea(isThseriousarea);
				wxApiData.setFxVehicl(fxVehicl);
				wxApiData.setFxAddrTown(fxAddrTown);
				
				wxApiData.setFxjtSm(fxjtSm);
				wxApiData.setFxjk(fxjk);
				
				WxApiMapper.postYqFxbdinfo(wxApiData);
				
				WxApiMapper.postYqjkinfo(wxApiData);
			} catch (Exception e) {
				// TODO: handle exception
				resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
				resultJsonObject.put("msg", "解析或写入数据库失败");
				logger.info("yq_fxbdinfo API  END");
		        return resultJsonObject;
			}	
			
			logger.info("yq_fxbdinfo API  END");
	        return resultJsonObject;
	    }
	
		 /**
		 * 3、学生扫码数据采集
		 * @return
		 */
		 @RequestMapping(value = "/yq_saomainfo",method = RequestMethod.POST)
	    public Object postYqSaomainfo(
	    		@RequestBody Map params
	    		) {
			
	    	logger.info("yq_saomainfo API START AND PARA isStudent IS NULL");
			JSONObject resultJsonObject = new JSONObject();
			resultJsonObject.put("errorCode", 0);//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("msg", "写入数据库成功");
			
			//校验参数
			if(null == params.get("school") || null == params.get("name")|| null == params.get("no")
					|| null == params.get("addr")|| null == params.get("time")
					|| null == params.get("fxaddr")) {
				resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
				resultJsonObject.put("msg", "参数为空或不完整");
				logger.info("yq_saomainfo API  END");
		        return resultJsonObject;
			}
			
			
			try {
				//解析参数
				String school = params.get("school").toString();
				String name = params.get("name").toString();
				String no = params.get("no").toString();
				String addr = params.get("addr").toString();
				String time = params.get("time").toString();
				
		        JSONObject json = new JSONObject(params);
				Double longitude = json.getJSONArray("fxaddr").getDouble(0);
				Double dimension = json.getJSONArray("fxaddr").getDouble(1);
				
				WxApiModel wxApiData = new WxApiModel();
				wxApiData.setSchool(school);
				wxApiData.setUserName(name);
				wxApiData.setUserNo(no);
				wxApiData.setAddr(addr);
				wxApiData.setLongitude(longitude);
				wxApiData.setDimension(dimension);
				wxApiData.setTimestamp(time);
			
				WxApiMapper.postYqSaomainfo(wxApiData);
			} catch (Exception e) {
				// TODO: handle exception
				resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
				resultJsonObject.put("msg", "解析或写入数据库失败");
				logger.info("yq_saomainfo API  END");
		        return resultJsonObject;
			}	
			
			logger.info("yq_saomainfo API  END");
	        return resultJsonObject;
	    }
			 
		 /**
		 * 4、学生注册信息
		 * @return
		 */
		 @RequestMapping(value = "/yq_zhuceinfo",method = RequestMethod.POST)
	    public Object postYqZhuceinfo(
	    		@RequestBody Map params
	    		) {
			
	    	logger.info("yq_fxbdinfo API START AND PARA isStudent IS NULL");
			JSONObject resultJsonObject = new JSONObject();
			resultJsonObject.put("errorCode",0);//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("msg", "注册成功");
			
			//校验参数
			if(null == params.get("college") || null == params.get("school")|| null == params.get("name")
					|| null == params.get("no")|| null == params.get("banji")
					|| null == params.get("phone")|| null == params.get("password")|| null == params.get("role")|| null == params.get("parentPhone")) {
				resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
				resultJsonObject.put("msg", "参数为空或不完整");
				logger.info("yq_fxbdinfo API  END");
		        return resultJsonObject;
			}
			
			
			try {
				//解析参数
				String college = params.get("college").toString();
				String school = params.get("school").toString();
				String name = params.get("name").toString();
				String password = params.get("password").toString();
				String depart = "";
				Integer personType =Integer.parseInt(params.get("role").toString());
				
				String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
//						logger.info("***************"+((Map)params.get("fxaddr")).get("country"));
				
				String no = params.get("no").toString();
				String banji = params.get("banji").toString();
				String phone = params.get("phone").toString();
				String parentPhone = params.get("parentPhone").toString();
				
				if(null != params.get("depart")) {
					depart = params.get("depart").toString();
				}
				
				
				WxApiModel wxApiData = new WxApiModel();
				wxApiData.setSchool(school);
				wxApiData.setUserName(name);
				wxApiData.setUserNo(no);
				wxApiData.setPhone(phone);
				wxApiData.setClasses(banji);
				wxApiData.setCollege(college);
				wxApiData.setMd5Password(password);
				wxApiData.setPersonType(personType);
				wxApiData.setDepartment(depart);
				wxApiData.setParentPhone(parentPhone);
				
				List<WxApiModel> userInfolist = WxApiMapper.getYqZhuceinfo(no);
				if(userInfolist.size()>0) {
					resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
					resultJsonObject.put("msg", "该学号已经注册过请勿重复注册");
					logger.info("yq_fxbdinfo API  END");
			        return resultJsonObject;
				}
				WxApiMapper.postYqZhuceinfo(wxApiData);
			} catch (Exception e) {
				// TODO: handle exception
				resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
				resultJsonObject.put("msg", "解析或写入数据库失败");
				logger.info("yq_fxbdinfo API  END");
		        return resultJsonObject;
			}	
			
			logger.info("yq_fxbdinfo API  END");
	        return resultJsonObject;
	    }
		 
		 /**
		 * 5学生登陆接口
		 * @return
		 */
		 @RequestMapping(value = "/yq_login",method = RequestMethod.POST)
	    public Object postYqLogin(
		    		@RequestBody Map params
	    		) {
			
	    	logger.info("yq_login API START AND PARA isStudent IS NULL");
			JSONObject resultJsonObject = new JSONObject();
			resultJsonObject.put("errorCode", 0);//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("msg", "登录成功");
			
			
			//校验参数
			if(null == params.get("no") || null == params.get("password")) {
				resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
				resultJsonObject.put("msg", "参数为空或不完整");
				logger.info("yq_login API  END");
		        return resultJsonObject;
			}
			
			try {
				String no = params.get("no").toString();
				String password = params.get("password").toString();
				List<WxApiModel> userInfolist  =  WxApiMapper.getYqLogin(no,password);
				if(userInfolist.size()==0) {
					resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
					resultJsonObject.put("msg", "账号密码不匹配");
					return resultJsonObject;
				}
				
				JSONObject userinfo = new JSONObject();
				userinfo.put("college", userInfolist.get(0).getSchool());
				userinfo.put("school", userInfolist.get(0).getCollege());
				userinfo.put("name", userInfolist.get(0).getUserName());
				userinfo.put("no", userInfolist.get(0).getUserNo());
				userinfo.put("banji", userInfolist.get(0).getClasses());
				userinfo.put("phone", userInfolist.get(0).getPhone());
				userinfo.put("role", userInfolist.get(0).getPersonType());
				userinfo.put("depart", userInfolist.get(0).getDepartment());
				userinfo.put("parentPhone", userInfolist.get(0).getParentPhone());
				
				List<WxApiModel> jkSubmit  =  WxApiMapper.jkSubmit(userInfolist.get(0).getSchool(),userInfolist.get(0).getUserNo());
				List<WxApiModel> jtSubmit  =  WxApiMapper.jtSubmit(userInfolist.get(0).getSchool(),userInfolist.get(0).getUserNo());
				if(jkSubmit.size()==0) {
					userinfo.put("is_submit_mrjk", 0);
				}else {
					userinfo.put("is_submit_mrjk", 1);
				}
				if(jtSubmit.size()==0) {
					userinfo.put("is_submit_kxbd", 0);
				}else {
					userinfo.put("is_submit_kxbd", 1);
				}
				
				resultJsonObject.put("userinfo",userinfo);
				
			} catch (Exception e) {
				// TODO: handle exception
				resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
				resultJsonObject.put("msg", "解析或读取数据库失败");
				logger.info("yq_login API  END");
		        return resultJsonObject;
			}	
			
			logger.info("yq_login API  END");
	        return resultJsonObject;
	    }
			 
			 
		 /**
		 * 6、6、获取学校列表：
		 * @return
		 */
		 @RequestMapping(value = "/yq_getcollagelist",method = RequestMethod.POST)
	    public Object postGetcollagelist(
//			    		@RequestBody Map params
	    		) {
			
	    	logger.info("yq_getcollagelist API START AND PARA isStudent IS NULL");
			JSONObject resultJsonObject = new JSONObject();
			resultJsonObject.put("errorCode", 0);//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("msg", "获取成功");
			
			try {
				List<WxApiModel> schoollist  =  WxApiMapper.postGetschoollist();
				JSONObject result = new JSONObject();
				for(WxApiModel school:schoollist) {
					String schools= school.getSchool();
					List<WxApiModel> collagelist  =  WxApiMapper.postGetcollagelist(schools);
					JSONObject collageObject = new JSONObject();
					for(WxApiModel collage:collagelist) {
						String collages = collage.getCollege();
						List<WxApiModel> personallitylist  =  WxApiMapper.postpersonallitylist(schools,collages);
						JSONArray personnallity= new JSONArray();
						for(WxApiModel personallity:personallitylist) {
							personnallity.add(personallity.getClasses());
						}
						collageObject.put(collages,personnallity);
					}
					result.put(schools,collageObject);
				}
				resultJsonObject.put("result",result);
			} catch (Exception e) {
				// TODO: handle exception
				resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
				resultJsonObject.put("msg", "解析或写入数据库失败");
				logger.info("yq_getcollagelist API  END");
		        return resultJsonObject;
			}	
			logger.info("yq_getcollagelist API  END");
	        return resultJsonObject;
	    }
		 
		 
		 /**
		 * 7、获取扫码历史列表
		 * @return
		 */
		 @RequestMapping(value = "/yq_getsaomalist",method = RequestMethod.POST)
	    public Object postGetSaomalist(
				    		@RequestBody Map params
	    		) {
			
	    	logger.info("yq_getsaomalist API START AND PARA isStudent IS NULL");
			JSONObject resultJsonObject = new JSONObject();
			resultJsonObject.put("errorCode", 0);//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("msg", "获取扫码历史列表成功");
			
			//校验参数
			if(null == params.get("school") || null == params.get("no")) {
				resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
				resultJsonObject.put("msg", "参数为空或不完整");
				logger.info("yq_getsaomalist API  END");
		        return resultJsonObject;
			}
			
			
			try {
				
				//解析参数
				JSONArray result= new JSONArray();
				String school = params.get("school").toString();
				String no = params.get("no").toString();
				List<WxApiModel> saomaolist  =  WxApiMapper.postGetSaomalist(school,no);
				for(WxApiModel saomao:saomaolist) {
					result.add(saomao.getTimestamp().substring(0, 19)+" "+saomao.getAddr()+"扫码");
				}
				resultJsonObject.put("result",result);
				
			} catch (Exception e) {
				// TODO: handle exception
				resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
				resultJsonObject.put("msg", "解析或写入数据库失败");
				logger.info("yq_getcollagelist API  END");
		        return resultJsonObject;
			}	
			logger.info("yq_getsaomalist API  END");
	        return resultJsonObject;
	    }
		 
		 /**
		 * 8、提交来访人员信息：
		 * @return
		 */
		 @RequestMapping(value = "/yq_savevisitorinfo",method = RequestMethod.POST)
	    public Object postSavevisitorinfo  (
				    		@RequestBody Map params
	    		) {
			
	    	logger.info("yq_savevisitorinfo API START AND PARA isStudent IS NULL");
			JSONObject resultJsonObject = new JSONObject();
			resultJsonObject.put("errorCode", 0);//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("msg", "提交成功");
			
			//校验参数
			if(null == params.get("school") || null == params.get("name")|| null == params.get("phone")
					|| null == params.get("toname")|| null == params.get("tophone")|| null == params.get("visitinfo")) {
				resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
				resultJsonObject.put("msg", "参数为空或不完整");
				logger.info("yq_savevisitorinfo API  END");
		        return resultJsonObject;
			}
			
			
			try {
				
				//解析参数
				String school = params.get("school").toString();
				String name = params.get("name").toString();
				String phone = params.get("phone").toString();
				String toname = params.get("toname").toString();
				String tophone = params.get("tophone").toString();
				String visitinfo = params.get("visitinfo").toString();
				
				WxApiModel wxApiData = new WxApiModel();
				wxApiData.setSchool(school);
				wxApiData.setUserName(name);
				wxApiData.setPhone(phone);
				wxApiData.setToname(toname);
				wxApiData.setTophone(tophone);
				wxApiData.setVisitinfo(visitinfo);
				WxApiMapper.postSavevisitorinfo(wxApiData);
				
			} catch (Exception e) {
				// TODO: handle exception
				resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
				resultJsonObject.put("msg", "解析或写入数据库失败");
				logger.info("yq_getcollagelist API  END");
		        return resultJsonObject;
			}	
			logger.info("yq_getsaomalist API  END");
	        return resultJsonObject;
	    }
		 
		 /**
		 * 9、获取行政部门信息：
		 * @return
		 */
		 @RequestMapping(value = "/yq_getdepartlist",method = RequestMethod.POST)
	    public Object postGetdepartlist    (
				    		@RequestBody Map params
	    		) {
			
	    	logger.info("yq_getdepartlist API START AND PARA isStudent IS NULL");
			JSONObject resultJsonObject = new JSONObject();
			resultJsonObject.put("errorCode", 0);//错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("msg", "获取行政部门信息成功");
			
			//校验参数
			
			List<WxApiModel> departlist = WxApiMapper.getdepartlist();
			
			JSONObject result = new JSONObject();
			JSONArray schoolList= new JSONArray();
			for(WxApiModel schoolInfo: departlist) {
				String school = schoolInfo.getSchool();
				
				boolean flag = false;
				for(int i = 0 ;i<schoolList.size();i++) {
					if(school.equals(schoolList.get(i))) {
						flag= true;
					}
				}
				if(flag) {
					continue;
				}
				JSONArray departmentList= new JSONArray();
				for(WxApiModel departInfo: departlist) {
					if(school.equals(departInfo.getSchool())) {
						departmentList.add(departInfo.getDepartment());
					}
					
				}
				schoolList.add(school);
				
				result.put(school, departmentList);
			}
			resultJsonObject.put("result", result);
			try {
				
				
			} catch (Exception e) {
				// TODO: handle exception
				resultJsonObject.put("errorCode", 1);//错误码4000参数为空 4001参数不正确， 4002认证失败
				resultJsonObject.put("msg", "获取失败");
				logger.info("yq_getcollagelist API  END");
		        return resultJsonObject;
			}	
			logger.info("yq_getdepartlist API  END");
	        return resultJsonObject;
	    }
}
