package com.coroapi.mapper.mysql;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.coroapi.model.EpideSituDisplayModel;
import com.coroapi.model.SchoolPara;
import com.coroapi.model.WxApiModel;


public interface   EpideSituDisplayEntiMapper {
	
	
		//全局接口获取学校参数
	 	@Select("SELECT province,city FROM `yq_school_configure` WHERE school=#{school}")
	    @Results({
	//        @Result(property = "name",  column = "name"),
	//        @Result(property = "industry", column = "industry")
	    })
	    List<SchoolPara> getSchoolInfo(String school);
	
	    //接口1 查询返校人数
	 	@Select("SELECT COUNT(1) AS fxNum,SUM(IF(fx_addr_country !='中国',1,0)) AS jwfxNum, SUM(IF(fx_addr_province !=#{province},1,0)) AS wsfxNum"
	 			+ " FROM (SELECT * FROM `yq_fxdata_collection` WHERE school=#{school} GROUP BY user_no) FXRY;")
	    @Results({
	    })
	    List<EpideSituDisplayModel> getBaseInfo(String school,String province);
	 	@Select("select count(1) AS zdgcNum from yq_fxhealth_collection AS tmp1 WHERE school=#{school} && tmp1.heathinfo1!='00001'&& tmp1.id IN "
	 			+ "(select SUBSTRING_INDEX(group_concat(id order by `create_time` desc),',',1) from yq_fxhealth_collection group by user_no)")
	    @Results({
	    })
	    List<EpideSituDisplayModel> getZdgcrsInfo(String school);
	 	
	 	
	 	
	 	//接口3 查询重点观察人员信息   体温大于37.3的温度
	 	@Select(" SELECT tmp3.user_name AS userName,tmp3.user_no AS userNum,college,fx_addr_province AS originCity FROM "
	 			+ "(SELECT tmp2.user_name,tmp2.user_no,college FROM (select user_name,user_no from yq_fxhealth_collection "
	 			+ "AS tmp1 WHERE school = #{school} && tmp1.heathinfo1!='00001'&& tmp1.id IN "
	 			+ "(select SUBSTRING_INDEX(group_concat(id order by `create_time` desc),',',1) "
	 			+ "from yq_fxhealth_collection WHERE school = #{school} group by user_no)) tmp2 LEFT JOIN yq_student_info ON tmp2.user_no=yq_student_info.user_no)"
	 			+ " tmp3 LEFT JOIN yq_fxdata_collection ON tmp3.user_no=yq_fxdata_collection.user_no GROUP BY tmp3.user_no")
	    @Results({
//	        @Result(property = "name",  column = "name"),
//	        @Result(property = "industry", column = "industry")
	    })
	    List<EpideSituDisplayModel> getSyZdgcryinfo(String school);
	 	
	 	//接口4 校园人员分布：校园中心gps（百度地图）
	 	@Select("SELECT center_longitude AS centerLongitude,center_dimension AS centerDimension"
	 			+ " FROM `yq_school_configure` WHERE school=#{school};")
	    @Results({
	    })
	    List<EpideSituDisplayModel> getSyXyryfbinfo(String school);
	 	//接口4 校园人员分布：校园中心gps（百度地图）
	 	@Select("SELECT longitude AS longitude ,dimension AS dimension FROM `yq_wxgj_collection` WHERE school=#{school} "
	 			+ " AND id IN (select SUBSTRING_INDEX(group_concat(id order by `time` desc),',',1) from yq_wxgj_collection group by user_no)")
	    @Results({
	    })
	    List<EpideSituDisplayModel> getSyXyryfbinfoLD(String school);
	 	
	 	
	 	//接口5 扫码事件
	 	@Select("SELECT * from ((SELECT  0 AS flag, user_name AS userName,user_no AS userNum, addr AS lymc,creat_time AS createTime "
	 			+ "FROM yq_wxgj_collection WHERE  school=#{school} AND user_no  NOT IN (select user_no from yq_fxhealth_collection AS tmp1 "
	 			+ "WHERE school = #{school} && tmp1.heathinfo1!='00001'&& tmp1.id IN "
	 			+ "(select SUBSTRING_INDEX(group_concat(id order by `create_time` desc),',',1) "
	 			+ "from yq_fxhealth_collection WHERE school = #{school} group by user_no)) ORDER BY creat_time DESC LIMIT 10) "
	 			+ "union all "
	 			+ "(SELECT  1 AS flag,user_name AS userName,user_no AS userNum, addr AS lymc,creat_time AS createTime FROM yq_wxgj_collection "
	 			+ "WHERE  school=#{school} AND user_no  IN (select user_no from yq_fxhealth_collection AS tmp1 "
	 			+ "WHERE school = #{school} && tmp1.heathinfo1!='00001'&& tmp1.id IN "
	 			+ "(select SUBSTRING_INDEX(group_concat(id order by `create_time` desc),',',1) from yq_fxhealth_collection "
	 			+ "WHERE school = #{school} group by user_no)) ORDER BY creat_time DESC LIMIT 5 )) tmp ORDER BY createTime ASC")
	    @Results({
	    })
	    List<EpideSituDisplayModel> getSyYjinfo(String school);
	 	
	 	//接口6 热门来源：
	 	@Select("SELECT fx_addr_province AS originCity,count(1) AS count "
	 			+ "FROM `yq_fxdata_collection` WHERE school=#{school} GROUP BY fx_addr_province ORDER BY count DESC LIMIT 5;")
	    @Results({
	    })
	    List<EpideSituDisplayModel> getSyRmlyinfo(String school);
	 	
	 	
	 	
	 	//返校交通
	 	//接口7
	 	@Select("select fx_vehicl as name from yq_fxdata_collection AS tmp1 WHERE school=#{school} "
	 			+ "AND tmp1.id IN (select SUBSTRING_INDEX(group_concat(id order by `create_time` desc),',',1) "
	 			+ "from yq_fxdata_collection WHERE school = #{school} group by user_no)")
	    @Results({
//	        @Result(property = "name",  column = "name"),
//	        @Result(property = "industry", column = "industry")
	    })
	    List<EpideSituDisplayModel> getSyFxjtinfo(String school);
	 	
	 	
	 	//接口8源：返校人员来源：学校gps（百度地图）
	 	@Select("SELECT originCity, count, longitude,dimension FROM (SELECT fx_addr_city AS originCity,count(1) "
	 			+ "AS count FROM `yq_fxdata_collection` WHERE school= #{school} GROUP BY fx_addr_city)tmp "
	 			+ "LEFT JOIN yq_city_gps ON tmp.originCity= yq_city_gps.city;")
	    @Results({
	    })
	    List<EpideSituDisplayModel> getSyFxrylyinfo(String school);
	 	
	 	//返校交通
	 	//接口10、10、各个学院返校信息：
	 	@Select("SELECT college AS name, count(1) AS count FROM `yq_fxdata_collection`"
	 			+ " LEFT JOIN yq_student_info ON yq_fxdata_collection.user_no =  yq_student_info.user_no"
	 			+ " WHERE yq_fxdata_collection.school = #{school} AND college <>'' AND college IS NOT NULL GROUP BY college;")
	    @Results({
//	        @Result(property = "name",  column = "name"),
//	        @Result(property = "industry", column = "industry")
	    })
	    List<EpideSituDisplayModel> getSyEjxyfxinfo(String school);
	 	
	 	//--------------------------------------------------
	 	@Insert("INSERT INTO yq_city_gps(city,longitude,dimension) VALUES(#{city}, #{lng}, #{lat})")
	    void insertgps(String city, Double lng, Double lat);

}
