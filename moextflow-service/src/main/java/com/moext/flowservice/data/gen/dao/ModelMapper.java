package com.moext.flowservice.data.gen.dao;

import org.apache.ibatis.annotations.Param;

import com.moext.flowservice.modeler.ui.modeler.domain.Model;

public interface ModelMapper {

	public Model selectModel(@Param("id") String id);
}
