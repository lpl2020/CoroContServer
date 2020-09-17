package com.coroapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
//import org.eclipse.jdt.internal.compiler.ast.FalseLiteral;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
import com.coroapi.mapper.mysql.EpideSituDisplayEntiMapper;
import com.coroapi.model.EpideSituDisplayModel;
import com.coroapi.model.SchoolPara;

//解决跨域问题
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", maxAge = 3600)
//注解的方式进行配置
@RestController // 返回json字符串的数据，直接可以编写RESTFul的接口
@SpringBootApplication // @SpringBootApplication声明让spring boot自动给程序进行必要的配置
@RequestMapping("/root/getEpideSituDisplayEnti") // 配置系统拦截url

/**
 * 获取行为轨迹接口数据
 * 
 * @author 李磊
 *
 */
public class EpideSituDisplayEntiController {

	@Autowired
	private EpideSituDisplayEntiMapper EpideSituDisplayEntiMapper;

	private static final Logger logger = LoggerFactory.getLogger(EpideSituDisplayEntiController.class);

	private static final String DEFAULTSCHOOL = "山东英才学院";

	/**
	 * 接口11、大屏最顶端信息：返校人数 外省返校人数 境外返校人数 重点观察人数
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sy_baseinfo", method = RequestMethod.GET)
	public Object getSyBaseinfo(@RequestParam(value = "school", required = false) String school) {
		logger.info("sy_baseinfo API START AND PARA isStudent IS NULL");
		if (null == school || "".equals(school))
			school = DEFAULTSCHOOL;

		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("errorCode", "");// 错误码4000参数为空 4001参数不正确， 4002认证失败

		List<SchoolPara> schoolInfo = EpideSituDisplayEntiMapper.getSchoolInfo(school);
		if (schoolInfo.size() == 0 || null == schoolInfo.get(0).getProvince() || null == schoolInfo.get(0).getCity()) {
			resultJsonObject.put("errorCode", "4001");// 错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("MGSS", "没有学校配置信息");// 错误码4000参数为空 4001参数不正确， 4002认证失败
			return resultJsonObject;
		}
		List<EpideSituDisplayModel> baseInfo = EpideSituDisplayEntiMapper.getBaseInfo(school,
				schoolInfo.get(0).getProvince());
		List<EpideSituDisplayModel> zdgcrsInfo = EpideSituDisplayEntiMapper.getZdgcrsInfo(school);
		if (baseInfo.size() == 0) {
			resultJsonObject.put("errorCode", "4001");// 错误码4000参数为空 4001参数不正确， 4002认证失败
			return resultJsonObject;
		}
		JSONObject result = new JSONObject();
		result.put("fx_num", baseInfo.get(0).getFxNum());
		result.put("wsfx_num", baseInfo.get(0).getWsfxNum());
		result.put("jwfx_num", baseInfo.get(0).getJwfxNum());
		result.put("zdgc_num", zdgcrsInfo.get(0).getZdgcNum());
		resultJsonObject.put("result", result);

		logger.info("sy_baseinfo API  END");
		return resultJsonObject;
	}

	/**
	 * 接口2、2、疫情监控数据：(全国、全省、全市、全校)累计确诊、境外输入确诊、现有确诊、现有疑似、累计治愈、累计死亡 调用公网用友接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sy_yqjkinfo", method = RequestMethod.GET)
	public Object getSyYqjkinfo(@RequestParam(value = "school", required = false) String school) {

		logger.info("sy_yqjkinfo API START AND PARA isStudent IS NULL");
		JSONObject resultJsonObject = new JSONObject();

		if (null == school || "".equals(school))
			school = DEFAULTSCHOOL;
		List<SchoolPara> schoolInfo = EpideSituDisplayEntiMapper.getSchoolInfo(school);
		if (schoolInfo.size() == 0 || null == schoolInfo.get(0).getProvince() || null == schoolInfo.get(0).getCity()) {
			resultJsonObject.put("errorCode", "4000");// 错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("MGSS", "没有学校配置信息");// 错误码4000参数为空 4001参数不正确， 4002认证失败
			return resultJsonObject;
		}

		String province = schoolInfo.get(0).getProvince();
		String city = schoolInfo.get(0).getCity();

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("authoration", "apicode");
		headers.add("apicode", "65f2157f4fff4426b83519770ec5bd9b");

//		//解决转发问题
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();

		factory.setHttpClient(httpClient);
		restTemplate.setRequestFactory(factory);
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity(null, headers);
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("http://api.yonyoucloud.com/apis/dst/ncov/country");
		String resEntity = restTemplate.exchange(builder.build().toString(), HttpMethod.GET, request, String.class)
				.getBody();

		UriComponentsBuilder builder2 = UriComponentsBuilder
				.fromHttpUrl("https://api.yonyoucloud.com/apis/dst/ncov/spreadQuery");
		String resEntity2 = restTemplate.exchange(builder2.build().toString(), HttpMethod.GET, request, String.class)
				.getBody();

		JSONObject jsonResult = JSONObject.parseObject(resEntity);
		JSONObject jsonResult2 = JSONObject.parseObject(resEntity2);
		if (!"200".equals(jsonResult.getString("code")) && !"200".equals(jsonResult2.getString("code"))) {
			resultJsonObject.put("errorCode", "4000");
			return resultJsonObject;
		}
		JSONObject dataResult = JSONObject.parseObject(jsonResult.get("data").toString());

		// ----------------------------------接口2信息处理------------------------------------------------
		Integer[] qx = { 0, 0, 0, 0, 0, 0 };
		Integer[] qshi = { 0, 0, 0, 0, 0, 0 };
		Integer[] qshen = { 0, 0, 0, 0, 0, 0 };
		Integer[] qg = { 0, 0, 0, 0, 0, 0 };
		JSONArray mapResultList = JSONArray.parseArray(jsonResult2.get("newslist").toString());
		for (int i = 0; i < mapResultList.size(); i++) {
			String provinceName = JSONObject.parseObject(mapResultList.get(i).toString()).get("provinceName")
					.toString();
			if (provinceName.contains(province)) {
				JSONObject sfInfo = JSONObject.parseObject(mapResultList.get(i).toString());
				qshen[0] = sfInfo.getInteger("confirmedCount");
//				qshen[1] = sfInfo.getInteger("");
				qshen[2] = sfInfo.getInteger("currentConfirmedCount");
				qshen[3] = sfInfo.getInteger("suspectedCount");
				qshen[4] = sfInfo.getInteger("curedCount");
				qshen[5] = sfInfo.getInteger("deadCount");
				JSONArray cityResultList = JSONArray.parseArray(sfInfo.get("cities").toString());
				for (int j = 0; j < cityResultList.size(); j++) {
					JSONObject cityInfo = cityResultList.getJSONObject(j);
					if (city.equals(cityInfo.getString("cityName"))) {
						qshi[0] = cityInfo.getInteger("confirmedCount");
//						qshi[1] = cityInfo.getInteger("");
						qshi[2] = cityInfo.getInteger("currentConfirmedCount");
						qshi[3] = cityInfo.getInteger("suspectedCount");
						qshi[4] = cityInfo.getInteger("curedCount");
						qshi[5] = cityInfo.getInteger("deadCount");
					}
				}
//				logger.info("----------------"+mapResultList.get(i).toString());
			}
		}
		qg[0] = dataResult.getInteger("confirmedCount");
//		qg[1] = dataResult.getInteger("");
		qg[2] = dataResult.getInteger("currentConfirmedCount");
		qg[3] = dataResult.getInteger("suspectedCount");
		qg[4] = dataResult.getInteger("curedCount");
		qg[5] = dataResult.getInteger("deadCount");

		resultJsonObject.put("errorCode", "");// 错误码4000参数为空 4001参数不正确， 4002认证失败
		JSONObject result = new JSONObject();
		result.put("qg", qg);
		result.put("qx", qx);
		result.put("qshen", qshen);
		result.put("qshi", qshi);
		resultJsonObject.put("result", result);// 错误码4000参数为空 4001参数不正确， 4002认证失败

		logger.info("sy_yqjkinfo API  END");
		return resultJsonObject;
	}

	/**
	 * 接口3、3、重点观察人员：姓名、学院、来源地、来源地确诊人数
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sy_zdgcryinfo", method = RequestMethod.GET)
	public Object getSyZdgcryinfo(@RequestParam(value = "school", required = false) String school) {

		logger.info("sy_zdgcryinfo API START AND PARA isStudent IS NULL");
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("errorCode", "");// 错误码4000参数为空 4001参数不正确， 4002认证失败
		if (null == school || "".equals(school))
			school = DEFAULTSCHOOL;

		List<EpideSituDisplayModel> syZdgcryInfo = EpideSituDisplayEntiMapper.getSyZdgcryinfo(school);

		JSONArray zdgcryyArrayObject = new JSONArray();
		for (EpideSituDisplayModel syZdgcry : syZdgcryInfo) {
			JSONObject Object = new JSONObject();
			Object.put("name", syZdgcry.getUserName());
			Object.put("xh", syZdgcry.getUserNum());
			Object.put("xy", syZdgcry.getCollege());
			Object.put("from", syZdgcry.getOriginCity());
			Object.put("from_num", 0);
			zdgcryyArrayObject.add(Object);
		}

		resultJsonObject.put("result", zdgcryyArrayObject);

		logger.info("sy_zdgcryinfo API  END");
		return resultJsonObject;
	}

	/**
	 * 接口4、校园人员分布：校园中心gps（百度地图）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sy_xyryfbinfo", method = RequestMethod.GET)
	public Object getSyXyryfbinfo(@RequestParam(value = "school", required = false) String school) {

		logger.info("sy_xyryfbinfo API START AND PARA isStudent IS NULL");
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("errorCode", "");// 错误码4000参数为空 4001参数不正确， 4002认证失败

		if (null == school || "".equals(school))
			school = DEFAULTSCHOOL;

		List<EpideSituDisplayModel> epideSituInfoList = EpideSituDisplayEntiMapper.getSyXyryfbinfo(school);
		List<EpideSituDisplayModel> epideSituInfoLDList = EpideSituDisplayEntiMapper.getSyXyryfbinfoLD(school);
		if (epideSituInfoList.size() == 0) {
			resultJsonObject.put("errorCode", "4001");// 错误码4000参数为空 4001参数不正确， 4002认证失败
			return resultJsonObject;
		}

		Double[] center = new Double[] { Double.parseDouble(epideSituInfoList.get(0).getCenterLongitude().toString()),
				Double.parseDouble(epideSituInfoList.get(0).getCenterDimension()) };
		epideSituInfoList.get(0).getCenterLongitude();
		epideSituInfoList.get(0).getCenterDimension();
		JSONObject result = new JSONObject();
		result.put("center", center);

		JSONArray poits = new JSONArray();
		for (EpideSituDisplayModel epideSituInfoLD : epideSituInfoLDList) {

			JSONObject jsonObj = new JSONObject();
			JSONObject geometry = new JSONObject();
			Double[] gpsInfo = new Double[] { epideSituInfoLD.getLongitude(), epideSituInfoLD.getDimension() };
			geometry.put("type", "Point");
			geometry.put("coordinates", gpsInfo);
			jsonObj.put("geometry", geometry);
			poits.add(jsonObj);
		}

		resultJsonObject.put("result", result);
		resultJsonObject.put("poits", poits);

		logger.info("sy_xyryfbinfo API  END");
		return resultJsonObject;
	}

	/**
	 * 接口5、5、预警信息:学生或老师扫码事件
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sy_yjinfo", method = RequestMethod.GET)
	public Object getSyYjinfo(@RequestParam(value = "school", required = false) String school) {

		logger.info("sy_yjinfo API START AND PARA isStudent IS NULL");
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("errorCode", "");// 错误码4000参数为空 4001参数不正确， 4002认证失败
		if (null == school || "".equals(school))
			school = DEFAULTSCHOOL;

		List<EpideSituDisplayModel> syYjinfo = EpideSituDisplayEntiMapper.getSyYjinfo(school);
		JSONArray syYjinfoArrayObject = new JSONArray();
		for (EpideSituDisplayModel syYjinfoL : syYjinfo) {
			JSONObject Object = new JSONObject();
			if (syYjinfoL.getFlag() == 1) {
				Object.put("isWarning", true);
			} else {
				Object.put("isWarning", false);
			}
			Object.put("name", syYjinfoL.getUserName());
			Object.put("xh", syYjinfoL.getUserNum());
			Object.put("event", syYjinfoL.getLymc() + "扫码通过" + " " + syYjinfoL.getCreateTime().substring(0, 19));
			syYjinfoArrayObject.add(Object);
		}

		resultJsonObject.put("result", syYjinfoArrayObject);
		logger.info("sy_yjinfo API  END");
		return resultJsonObject;
	}

	/**
	 * 接口6、热门来源
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sy_rmlyinfo", method = RequestMethod.GET)
	public Object getSyRmlyinfo(@RequestParam(value = "school", required = false) String school) {

		logger.info("sy_rmlyinfo API START AND PARA isStudent IS NULL");
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("errorCode", "");// 错误码4000参数为空 4001参数不正确， 4002认证失败
		if (null == school || "".equals(school))
			school = DEFAULTSCHOOL;

		List<EpideSituDisplayModel> syinfo = EpideSituDisplayEntiMapper.getSyRmlyinfo(school);

		JSONArray syinfoArrayObject = new JSONArray();
		for (EpideSituDisplayModel syinfoValue : syinfo) {
			JSONObject Object = new JSONObject();
			Object.put("name", syinfoValue.getOriginCity());
			Object.put("value", syinfoValue.getCount());
			syinfoArrayObject.add(Object);
		}

		resultJsonObject.put("result", syinfoArrayObject);
		logger.info("sy_rmlyinfo API  END");
		return resultJsonObject;
	}

	/**
	 * 接口7、返校交通
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sy_fxjtinfo", method = RequestMethod.GET)
	public Object getSyFxjtinfo(@RequestParam(value = "school", required = false) String school) {

		logger.info("sy_rmlyinfo API START AND PARA isStudent IS NULL");
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("errorCode", "");// 错误码4000参数为空 4001参数不正确， 4002认证失败
		if (null == school || "".equals(school))
			school = DEFAULTSCHOOL;

		List<EpideSituDisplayModel> syinfo = EpideSituDisplayEntiMapper.getSyFxjtinfo(school);
//		if(baseInfo.size()==0) {
//			resultJsonObject.put("errorCode", "4000");//错误码4000参数为空 4001参数不正确， 4002认证失败
//			return resultJsonObject;
//		}
		Integer[] count = new Integer[] { 0, 0, 0, 0, 0 };
		JSONArray syinfoArrayObject = new JSONArray();
		for (EpideSituDisplayModel syinfoValue : syinfo) {
			String jtfs = syinfoValue.getName();
			if (jtfs.length() != 5) {
				break;
			}
			for (int i = 0; i < 5; i++) {
				if (jtfs.substring(i, i + 1).equals("1")) {
					count[i]++;
				}
			}
		}
		// A.汽车", "B.火车", "C.飞机", "D.私家车
		JSONObject Object1 = new JSONObject();
		Object1.put("name", "汽车");
		Object1.put("value", count[0]);
		syinfoArrayObject.add(Object1);
		JSONObject Object2 = new JSONObject();
		Object2.put("name", "火车");
		Object2.put("value", count[1]);
		syinfoArrayObject.add(Object2);
		JSONObject Object3 = new JSONObject();
		Object3.put("name", "高铁");
		Object3.put("value", count[2]);
		syinfoArrayObject.add(Object3);
		JSONObject Object4 = new JSONObject();
		Object4.put("name", "飞机");
		Object4.put("value", count[3]);
		syinfoArrayObject.add(Object4);
		JSONObject Object5 = new JSONObject();
		Object5.put("name", "私家车");
		Object5.put("value", count[4]);
		syinfoArrayObject.add(Object5);

		resultJsonObject.put("result", syinfoArrayObject);
		logger.info("sy_rmlyinfo API  END");
		return resultJsonObject;
	}

	/**
	 * 接口8、8、返校人员来源：学校gps（百度地图）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sy_fxrylyinfo", method = RequestMethod.GET)
	public Object getSyFxrylyinfo(@RequestParam(value = "school", required = false) String school) {

		logger.info("sy_rmlyinfo API START AND PARA isStudent IS NULL");
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("errorCode", "");// 错误码4000参数为空 4001参数不正确， 4002认证失败
		if (null == school || "".equals(school))
			school = DEFAULTSCHOOL;

		List<EpideSituDisplayModel> epideSituInfoList = EpideSituDisplayEntiMapper.getSyXyryfbinfo(school);
		if (epideSituInfoList.size() == 0) {
			resultJsonObject.put("errorCode", "4001");// 错误码4000参数为空 4001参数不正确， 4002认证失败
			resultJsonObject.put("MGSS", "没有学校配置信息");// 错误码4000参数为空 4001参数不正确， 4002认证失败
			return resultJsonObject;
		}

		Double[] center = new Double[] { Double.parseDouble(epideSituInfoList.get(0).getCenterLongitude().toString()),
				Double.parseDouble(epideSituInfoList.get(0).getCenterDimension()) };
		epideSituInfoList.get(0).getCenterLongitude();
		epideSituInfoList.get(0).getCenterDimension();

		List<EpideSituDisplayModel> syinfo = EpideSituDisplayEntiMapper.getSyFxrylyinfo(school);

		JSONArray syinfoArrayObject = new JSONArray();
		for (EpideSituDisplayModel syinfoValue : syinfo) {

			JSONObject Object = new JSONObject();
			Object.put("name", syinfoValue.getOriginCity());
			String originCity = syinfoValue.getOriginCity();

			if (null == syinfoValue.getLongitude() || null == syinfoValue.getDimension()) {

				RestTemplate restTemplate = new RestTemplate();
				String URL = "http://api.map.baidu.com/geocoder?address=%E5%9F%8E%E5%B8%82%E5%90%8D%E7%A7%B0"
						+ "&output=json&key=37492c0ee6f924cb5e934fa08c6b1676&city=" + originCity;
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
				HttpEntity<String> entity = new HttpEntity<String>(headers);
				String strbody = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class).getBody();
				JSONObject apiResult = JSONObject.parseObject(strbody);
				if (null != apiResult.getJSONObject("result")) {
					JSONObject location = apiResult.getJSONObject("result").getJSONObject("location");
					Double[] gps = new Double[] { location.getDouble("lng"), location.getDouble("lat") };
					EpideSituDisplayEntiMapper.insertgps(originCity, location.getDouble("lng"),
							location.getDouble("lat"));
					Object.put("gps", gps);
				}
				;
				Object.put("value", syinfoValue.getCount());
				syinfoArrayObject.add(Object);

			} else {

				Double[] gps = new Double[] { syinfoValue.getLongitude(), syinfoValue.getDimension() };
				Object.put("gps", gps);
				Object.put("value", syinfoValue.getCount());
				syinfoArrayObject.add(Object);
			}
		}

		JSONObject result = new JSONObject();
		result.put("center", center);
		result.put("from", syinfoArrayObject);
		result.put("school", school);
		resultJsonObject.put("result", result);

		logger.info("sy_rmlyinfo API  END");
		return resultJsonObject;
	}

	/**
	 * 接口9、疫情动态接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sy_yqdtinfo", method = RequestMethod.GET)
	public Object getSyYqdtinfo(@RequestParam(value = "school", required = false) String school) {

		logger.info("sy_ejxyfxinfo API START AND PARA isStudent IS NULL");
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("errorCode", "");// 错误码4000参数为空 4001参数不正确， 4002认证失败
		if (null == school || "".equals(school))
			school = DEFAULTSCHOOL;

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("authoration", "apicode");
		headers.add("apicode", "65f2157f4fff4426b83519770ec5bd9b");

//		//解决转发问题
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();

		factory.setHttpClient(httpClient);
		restTemplate.setRequestFactory(factory);
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity(null, headers);
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("https://api.yonyoucloud.com/apis/dst/ncov/query");
		String resEntity = restTemplate.exchange(builder.build().toString(), HttpMethod.GET, request, String.class)
				.getBody();
		JSONObject jsonResult = JSONObject.parseObject(resEntity);
		if (!"200".equals(jsonResult.getString("code"))) {
			resultJsonObject.put("errorCode", "4000");
			return resultJsonObject;
		}

		List<String> result = new ArrayList<>();
		JSONArray newsList = jsonResult.getJSONArray("newslist");
		JSONArray newList = newsList.getJSONObject(0).getJSONArray("news");
		for (int i = 0; i < newList.size(); i++) {
			String summary = newList.getJSONObject(i).getString("summary");
			result.add(summary);
			logger.info("-------------------" + newList.get(i));
		}

		resultJsonObject.put("result", result);

		logger.info("sy_ejxyfxinfo API  END");
		return resultJsonObject;
	}

	/**
	 * 接口10、10、各个学院返校信息：
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sy_ejxyfxinfo", method = RequestMethod.GET)
	public Object getSyEjxyfxinfo(@RequestParam(value = "school", required = false) String school) {

		logger.info("sy_ejxyfxinfo API START AND PARA isStudent IS NULL");
		JSONObject resultJsonObject = new JSONObject();
		resultJsonObject.put("errorCode", "");// 错误码4000参数为空 4001参数不正确， 4002认证失败
		if (null == school || "".equals(school))
			school = DEFAULTSCHOOL;

		List<EpideSituDisplayModel> syinfo = EpideSituDisplayEntiMapper.getSyEjxyfxinfo(school);
//		if(baseInfo.size()==0) {
//			resultJsonObject.put("errorCode", "4000");//错误码4000参数为空 4001参数不正确， 4002认证失败
//			return resultJsonObject;
//		}
		JSONArray syinfoArrayObject = new JSONArray();
		for (EpideSituDisplayModel syinfoValue : syinfo) {
			JSONObject Object = new JSONObject();
			Object.put("name", syinfoValue.getName());
			Object.put("value", syinfoValue.getCount());
			syinfoArrayObject.add(Object);
		}

		resultJsonObject.put("result", syinfoArrayObject);

		logger.info("sy_ejxyfxinfo API  END");
		return resultJsonObject;
	}
}
