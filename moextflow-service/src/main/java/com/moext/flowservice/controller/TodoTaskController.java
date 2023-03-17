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
import com.moext.flowservice.data.gen.model.TodoTask;
import com.moext.flowservice.dto.req.TodoIdReq;
import com.moext.flowservice.dto.req.TodoListCountReq;
import com.moext.flowservice.dto.req.TodoListPageReq;
import com.moext.flowservice.service.TodoTaskService;

/**
 * 待办接口
 * @author PengPeng
 */
@RequestMapping("/todo")
@RestController
public class TodoTaskController {

	@Autowired
	private TodoTaskService todoTaskService;

	/**
	 * 分页按条件查询待办
	 * @param request
	 * @param todoListPageReq
	 * @return
	 */
	@RequestMapping(value="/listPage", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<PageResponse<TodoTask>> listPage(HttpServletRequest request, @RequestBody TodoListPageReq todoListPageReq) {
		String validateMsg = RspUtils.validate(todoListPageReq, TodoListPageReq.class);
		if(StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}
		
		PageResponse<TodoTask> response = todoTaskService.listPage(todoListPageReq);
		return RspUtils.success(response);
	}
	
	/**
	 * 按条件查询待办数量
	 * @param request
	 * @param todoListPageReq
	 * @return
	 */
	@RequestMapping(value="/count", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Long> count(HttpServletRequest request, @RequestBody TodoListCountReq todoListCountReq) {
		String validateMsg = RspUtils.validate(todoListCountReq, TodoListCountReq.class);
		if(StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}
		
		Long total = todoTaskService.count(todoListCountReq);
		return RspUtils.success(total);
	}
	
	/**
	 * 待办任务详细信息
	 * @param request
	 * @param todoIdReq
	 * @return
	 */
	@RequestMapping(value="/detail", method = RequestMethod.POST)
    @ResponseBody
	public BaseResponse<TodoTask> detail(HttpServletRequest request, @RequestBody TodoIdReq todoIdReq) {
		String validateMsg = RspUtils.validate(todoIdReq, TodoIdReq.class);
		if(StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}
		
		return RspUtils.success(todoTaskService.getByTodoId(todoIdReq.getTodoId()));
	}

    /**
     * 删除待办任务（标记删除）
     * @param request
     * @param todoIdReq
     */
    @RequestMapping(value="/delete", method = RequestMethod.POST)
    @ResponseBody
	public BaseResponse<Boolean> delete(HttpServletRequest request, @RequestBody TodoIdReq todoIdReq) {
    	String validateMsg = RspUtils.validate(todoIdReq, TodoIdReq.class);
		if(StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}
		
	    return RspUtils.success(todoTaskService.deleteByTodoId(todoIdReq.getTodoId()));
	}	
}
