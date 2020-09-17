package com.coroapi.mapper.mysql;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.coroapi.model.EpideSituDisplayModel;
import com.coroapi.model.EpideSituDisplayPerModel;


public interface   EpideSituDisplayPersionMapper {
	 	@Select("SELECT  SUM(IF(person_type ='1',1,0)) AS teachercount,SUM(IF(person_type ='0',1,0)) "
	 			+ "AS studentcount, SUM(IF(person_type ='2',1,0)) AS foreigner,SUM(IF(person_type ='3',1,0)) "
	 			+ "AS other FROM (SELECT * FROM yq_fxdata_collection  WHERE school =#{school} GROUP BY user_no)tmp1 "
	 			+ "LEFT JOIN  (SELECT * FROM yq_student_info GROUP BY user_no) tmp2 ON tmp1.user_no = tmp2.user_no ")
	    @Results({
//	        @Result(property = "name",  column = "name"),
//	        @Result(property = "industry", column = "industry")
	    })
	    List<EpideSituDisplayPerModel> getEntiCount(String school);
	 	
	 	@Select("SELECT  SUM(IF(fx_addr_country !='中国',1,0)) AS fromOtherCountry,"
	 			+ "SUM(IF(fx_addr_city ='武汉市',1,0)) AS fromWuHan, count(1) AS focusObservation  "
	 			+ "FROM (select * from yq_fxhealth_collection AS tmp1 WHERE tmp1.school =#{school} AND tmp1.heathinfo1!='00001'&& "
	 			+ "tmp1.id IN (select SUBSTRING_INDEX(group_concat(id order by `create_time` desc),',',1)"
	 			+ "from yq_fxhealth_collection group by user_no)) tmp2 LEFT JOIN (SELECT * FROM yq_fxdata_collection WHERE school =#{school} group by user_no)tmp3 ON tmp2.user_no"
	 			+ " = tmp3.user_no WHERE tmp2.school =#{school}")
	    @Results({
//	        @Result(property = "name",  column = "name"),
//	        @Result(property = "industry", column = "industry")
	    })
	    List<EpideSituDisplayPerModel> getZdsrCount(String school);
	 	
	
	 	@Select("SELECT DATE_FORMAT( time, '%H' ) AS timestamp,SUM(IF(person_type ='1',1,0)) AS teachercount ,"
	 			+ "SUM(IF(person_type ='0',1,0)) AS studentcount,SUM(IF(person_type ='3',1,0)) AS other  "
	 			+ "FROM `yq_wxgj_collection` LEFT JOIN yq_student_info ON  yq_wxgj_collection.user_no ="
	 			+ " yq_student_info.user_no WHERE yq_wxgj_collection.time> CURDATE() AND yq_wxgj_collection.school =#{school} AND addr like '%校门%' GROUP BY DATE_FORMAT( time, '%Y-%m-%d %H' )")
	    @Results({
//	        @Result(property = "name",  column = "name"),
//	        @Result(property = "industry", column = "industry")
	    })
	 	List<EpideSituDisplayPerModel> getXmcrCount(String school);
	 	
	 	/**
	 	 * 接口4 事件列表
	 	 * @return
	 	 */
	 	@Select("SELECT heathinfo1,timestamp FROM `yq_fxhealth_collection` WHERE school =#{school} and heathinfo1!='00001';")
	    @Results({
//	        @Result(property = "name",  column = "name"),
//	        @Result(property = "industry", column = "industry")
	    })
	    List<EpideSituDisplayPerModel> getYjxno(String school);
	 	/**
	 	 * 接口4 事件列表
	 	 * @return
	 	 */
	 	@Select("SELECT count(1) AS count FROM `yq_fxhealth_collection` "
	 			+ "WHERE school =#{school} and create_time>  date_sub(NOW(),interval 1 day) and heathinfo1!='00001';")
	    @Results({
//	        @Result(property = "name",  column = "name"),
//	        @Result(property = "industry", column = "industry")
	    })
	    List<EpideSituDisplayPerModel> getYjxnos(String school);
	 	
	 	
	 	
	 	/**
	 	 * 接口5事件列表
	 	 * @return
	 	 */
	 	@Select("SELECT user_name AS userName,user_no AS userNo,`timestamp`AS timestamp"
	 			+ " FROM `yq_fxhealth_collection` WHERE school =#{school} and is_quarantine=1;")
	    @Results({
	    })
	    List<EpideSituDisplayPerModel> getSysj(String school);
	 	
	 	/**
	 	 * 
	 	 * @param userNo
	 	 * @return
	 	 */
	 	@Select("SELECT TMP1.user_name AS userName, sex AS sex, age AS age, classes AS classes, phone, parents_phone AS parentPhone, "
	 			+ "fx_time AS fxTime,TMP2.fx_vehicl AS fxVehicl,TMP2.fxjt_sm AS fxjtSm ,TMP2.fx_addr_city AS addr FROM (SELECT * FROM yq_student_info "
	 			+ "WHERE school =#{school} AND user_no = #{userNo}) TMP1 LEFT JOIN (select * from "
	 			+ "yq_fxdata_collection AS tmp1 WHERE school = #{school} AND user_no = #{userNo} AND "
	 			+ "tmp1.id IN (select SUBSTRING_INDEX(group_concat(id order by `create_time` desc),',',1) "
	 			+ "from yq_fxdata_collection WHERE school = #{school} AND user_no = #{userNo} group by user_no))"
	 			+ "TMP2 ON TMP1.user_no = TMP2.user_no;" + 
	 			"")
	    @Results({
//	        @Result(property = "name",  column = "name"),
//	        @Result(property = "industry", column = "industry")
	    })
	    List<EpideSituDisplayPerModel> getpersionInfoDetail(String school,String userNo);
	 	/**
	 	 * 
	 	 * @param userNo
	 	 * @return
	 	 */
	 	@Select("SELECT create_time AS timestamp FROM `yq_fxhealth_collection` WHERE school =#{school} AND user_no=#{userNo} and heathinfo1!='00001';")
	    @Results({
//	        @Result(property = "name",  column = "name"),
//	        @Result(property = "industry", column = "industry")
	    })
	    List<EpideSituDisplayPerModel> getpersionInfoSDetail(String school,String userNo);
	 	
//	 	@Select("SELECT parents_phone AS phone FROM `yq_parents_phone` WHERE school =#{school} AND user_no=#{userNo};")
//	    @Results({
////	        @Result(property = "name",  column = "name"),
////	        @Result(property = "industry", column = "industry")
//	    })
//	    List<EpideSituDisplayPerModel> getparentsInfoSDetail(String school,String userNo);
	 	
	 	//接口8 、个人轨迹图：
	 	@Select("SELECT center_longitude AS centerLongitude,center_dimension AS centerDimension"
	 			+ " FROM `yq_school_configure` WHERE school=#{school};")
	    @Results({
//	        @Result(property = "name",  column = "name"),
//	        @Result(property = "industry", column = "industry")
	    })
	    List<EpideSituDisplayPerModel> getSyGrgjt(String school);
	 	//接口8 3、个人轨迹图：
	 	@Select("SELECT addr, longitude AS longitude ,dimension AS dimension,time FROM `yq_wxgj_collection` "
	 			+ "WHERE school=#{school} and user_no=#{personNo} limit 20;")
	    @Results({
//	        @Result(property = "name",  column = "name"),
//	        @Result(property = "industry", column = "industry")
	    })
	 	List<EpideSituDisplayPerModel> getSyGrgjtLD(String school,String personNo);
	 	
	 	//接口9  个人相应事件：
	 	@Select("SELECT user_name AS userName ,user_no AS userNo, addr,time FROM `yq_wxgj_collection` WHERE school=#{school} and user_no=#{personNo};")
	    @Results({
//	        @Result(property = "name",  column = "name"),
//	        @Result(property = "industry", column = "industry")
	    })
	 	List<EpideSituDisplayPerModel> getSyGrxysj(String school,String personNo);
	 	
	 	
	 	/**
	 	 * 
	 	 * @param userNo
	 	 * @return
	 	 */
	 	@Select("SELECT user_name AS userName FROM `yq_student_info` GROUP BY user_no ORDER BY id  DESC LIMIT 10  ;")
	    @Results({
//	        @Result(property = "name",  column = "name"),
//	        @Result(property = "industry", column = "industry")
	    })
	    List<EpideSituDisplayPerModel> getRelationPersionInfo(String school,String userNo);

}
