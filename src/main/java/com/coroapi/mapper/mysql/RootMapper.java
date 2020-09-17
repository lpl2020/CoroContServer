package com.coroapi.mapper.mysql;

import java.util.List;

import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.coroapi.model.RootModel;
import com.coroapi.model.WxApiModel;


public interface   RootMapper {
	
	
		//全局接口获取学校参数
	 	@Select("SELECT school,bigscreen_url as bigscreenUrl FROM `yq_url_token` "
	 			+ "WHERE school=#{school} AND user = #{user} AND password=#{password};")
	    @Results({
	    })
	    List<RootModel> login(RootModel rootModel);
	 	
	 	
	 	@Select("SELECT school FROM `yq_collage_configure` group by school")
	    @Results({

	    })
	    List<RootModel> getSchoolList();

}
