package com.moext.flowservice.component;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.stream.XMLStreamException;

import org.flowable.engine.repository.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moext.flowservice.dto.req.FlowModelPageRequest;
import com.moext.flowservice.flow.model.FlowModel;

/**
 * 流程模型组件
 * 
 * @author PengPeng
 *
 */
public interface ProcessModelComponent {

	/**
	 * 分页查询
	 * 
	 * @param pageRequest
	 * @return
	 */
	public List<FlowModel> list(FlowModelPageRequest pageRequest);

	/**
	 * 按条件统计数量
	 * 
	 * @param pageRequest
	 * @return
	 */
	public long count(FlowModelPageRequest pageRequest);

	/**
	 * 创建或更新模型
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
	 * 导出Model文件
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
	public void updateCategory(String id, String category);

	/**
	 * 删除模型
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 按Model文件导入多个模型
	 * 
	 * @param file
	 */
	public boolean importListByModelFile(Part file) throws Exception;

	/**
	 * 部署更新的流程
	 */
	public boolean deployUpdated();

	/**
	 * 按modelId获取model
	 * 
	 * @param modelId
	 * @return
	 */
	public Model getModel(String modelId);

	/**
	 * 按key获取model
	 * 
	 * @param key
	 * @return
	 */
	public Model getModelByKey(String key);
}
