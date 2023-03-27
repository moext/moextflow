package com.moext.flowservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moext.flowservice.common.BaseResponse;
import com.moext.flowservice.common.PageResponse;
import com.moext.flowservice.common.RspUtils;
import com.moext.flowservice.dto.req.FlowModelPageRequest;
import com.moext.flowservice.dto.req.IdReq;
import com.moext.flowservice.dto.req.ModelCreateReq;
import com.moext.flowservice.dto.req.UpdateModelReq;
import com.moext.flowservice.flow.model.FlowModel;
import com.moext.flowservice.service.FlowModelService;

/**
 * 流程管理接口
 * 
 * @author PengPeng
 */
@RequestMapping("/flowadmin/model")
@RestController
public class FlowModelAdminController {

	@Autowired
	private FlowModelService flowModelService;

	/**
	 * 模型管理-分页条件查询
	 * 
	 * @param request
	 * @param flowModelPageRequest
	 * @return
	 */
	@RequestMapping(value = "listPage", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<PageResponse<FlowModel>> listPage(HttpServletRequest request,
			@RequestBody FlowModelPageRequest flowModelPageRequest) {
		String validateMsg = RspUtils.validate(flowModelPageRequest, FlowModelPageRequest.class);
		if (StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}

		PageResponse<FlowModel> response = flowModelService.listPage(flowModelPageRequest);
		return RspUtils.success(response);
	}

	/**
	 * 创建或更新模型
	 * 
	 * @param request
	 * @param modelCreateReq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Boolean> saveOrUpdate(HttpServletRequest request, @RequestBody ModelCreateReq modelCreateReq) throws Exception {
		String validateMsg = RspUtils.validate(modelCreateReq, ModelCreateReq.class);
		if (StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}
		
		flowModelService.saveOrUpdate(modelCreateReq.getName(), modelCreateReq.getKey(), modelCreateReq.getDescription(),
				modelCreateReq.getCategory());
		return RspUtils.success(true);
	}

	/**
	 * 部署流程
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deploy", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Boolean> deploy(HttpServletRequest request, @RequestBody IdReq idReq) {
		String validateMsg = RspUtils.validate(idReq, IdReq.class);
		if (StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}
		
		flowModelService.deploy(idReq.getId());
		return RspUtils.success(true);
	}

	/**
	 * 导出流程
	 * 
	 * @param request
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "/export/{id}", method = RequestMethod.GET)
	public void export(HttpServletRequest request, @PathVariable("id") String id, HttpServletResponse response) {
		flowModelService.export(id, response);
	}

	/**
	 * 导出所有model文件
	 */
	@RequestMapping(value = "exportAll", method = RequestMethod.GET)
	public void exportAll(HttpServletRequest request, HttpServletResponse response) {
		flowModelService.exportAll(response);
	}

	// 更新Model分类
	@RequestMapping(value = "updateCategory", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Boolean> updateCategory(HttpServletRequest request,
			@RequestBody UpdateModelReq updateModelReq) {
		String validateMsg = RspUtils.validate(updateModelReq, UpdateModelReq.class);
		if (StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}
		
		return RspUtils.success(flowModelService.updateCategory(updateModelReq.getId(), updateModelReq.getCategory()));
	}

	/**
	 * 删除模型
	 * 
	 * @param request
	 * @param idReq
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Boolean> delete(HttpServletRequest request, @RequestBody IdReq idReq) {
		String validateMsg = RspUtils.validate(idReq, IdReq.class);
		if (StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}
		
		return RspUtils.success(flowModelService.delete(idReq.getId()));
	}

	/**
	 * 一键部署所有更新的模型
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deployUpdated", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Boolean> deployUpdated(HttpServletRequest request) {
		return RspUtils.success(flowModelService.deployUpdated());
	}

	/**
	 * 导入model文件
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public BaseResponse<Boolean> importFile(HttpServletRequest request) throws Exception {
		Part file = request.getPart("file");
		return RspUtils.success(flowModelService.importListByModelFile(file));
	}
	
}
