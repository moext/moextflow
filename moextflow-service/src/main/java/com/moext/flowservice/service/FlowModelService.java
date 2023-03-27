package com.moext.flowservice.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.stream.XMLStreamException;

import org.flowable.engine.repository.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moext.flowservice.common.PageResponse;
import com.moext.flowservice.dto.req.FlowModelPageRequest;
import com.moext.flowservice.flow.model.FlowModel;

/**
 * 流程模型服务
 * 
 * @author PengPeng
 */
public interface FlowModelService {

	/**
	 * 按条件分页查询
	 * 
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<FlowModel> listPage(FlowModelPageRequest pageRequest);

	/**
	 * 创建模型
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void saveOrUpdate(String name, String key, String description, String category)
			throws UnsupportedEncodingException;

	/**
	 * 根据Model部署流程
	 * 
	 * @param id
	 * @return
	 */
	public String deploy(String id);

	/**
	 * 导入Model
	 * 
	 * @param
	 * @throws UnsupportedEncodingException
	 * @throws XMLStreamException
	 */
	public void importModel(Model data, InputStream bpmnStream) throws UnsupportedEncodingException, XMLStreamException;

	/**
	 * 导出Model的XML文件
	 * 
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public void export(String id, HttpServletResponse response);

	/**
	 * 导出all Model的文件
	 * 
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public void exportAll(HttpServletResponse response);

	/**
	 * 更新Model分类
	 * 
	 * @param id
	 * @param category
	 */
	public boolean updateCategory(String id, String category);

	/**
	 * 删除模型
	 * 
	 * @param id
	 */
	public boolean delete(String id);

	/**
	 * 按Model文件导入多个模型
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public boolean importListByModelFile(Part file) throws Exception;

	/**
	 * 部署更新的流程
	 */
	public boolean deployUpdated();

}
