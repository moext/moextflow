package com.moext.flowservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moext.flowservice.common.BaseResponse;
import com.moext.flowservice.common.PageResponse;
import com.moext.flowservice.common.RspUtils;
import com.moext.flowservice.dto.req.FlowModelPageRequest;
import com.moext.flowservice.flow.model.FlowModel;
import com.moext.flowservice.service.FlowModelService;

/**
 * 流程管理接口
 * @author PengPeng
 */
@RequestMapping("/flowadmin")
@RestController
public class FlowAdminController {

	@Autowired
	private FlowModelService flowModelService;
	
	/**
	 * 模型管理-分页条件查询
	 * @param request
	 * @param flowModelPageRequest
	 * @return
	 */
	@RequestMapping(value="/model/listPage", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<PageResponse<FlowModel>> modelListPage(HttpServletRequest request, @RequestBody FlowModelPageRequest flowModelPageRequest) {
		String validateMsg = RspUtils.validate(flowModelPageRequest, FlowModelPageRequest.class);
		if(StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}
		
		PageResponse<FlowModel> response = flowModelService.listPage(flowModelPageRequest);
		return RspUtils.success(response);
	}
}
