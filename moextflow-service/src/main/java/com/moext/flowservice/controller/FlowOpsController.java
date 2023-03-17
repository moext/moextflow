package com.moext.flowservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moext.flowservice.common.BaseResponse;
import com.moext.flowservice.common.PageResponse;
import com.moext.flowservice.common.RspUtils;
import com.moext.flowservice.data.gen.model.TodoTask;
import com.moext.flowservice.dto.req.ApproveProcessReq;
import com.moext.flowservice.dto.req.StartProcessReq;
import com.moext.flowservice.dto.req.TodoIdReq;
import com.moext.flowservice.dto.req.TodoListCountReq;
import com.moext.flowservice.dto.req.TodoListPageReq;
import com.moext.flowservice.service.TodoTaskService;

/**
 * 流程干预接口
 * @author PengPeng
 */
@RequestMapping("/flowops")
@RestController
public class FlowOpsController {

	//TODO
	
}
