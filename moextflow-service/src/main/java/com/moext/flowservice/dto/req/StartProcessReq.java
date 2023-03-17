package com.moext.flowservice.dto.req;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.moext.flowservice.common.BaseRequest;

/**
 * 发起流程审批请求
 * @author PengPeng
 */
public class StartProcessReq extends BaseRequest {

	private static final long serialVersionUID = -6342362040760994133L;
	
	@NotBlank(message = "流程标题不能为空")
	private String title;
	
	@NotBlank(message = "流程定义标识不能为空")
	private String procDefKey;
	
	@NotBlank(message = "表单实例ID不能为空")
	private String formInstanceId;
	
	@NotBlank(message = "发起人用户名不能为空")
	private String applyUserId;

	@NotNull(message = "公司标识不能为NULL")
	public String companyKey;
	
	@NotNull(message = "部门标识不能为NULL")
	public String departmentKey;
	
	//审批意见
	private String comment;
	
	//流程变量
	private Map<String, Object> variables = new HashMap<String, Object>();
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFormInstanceId() {
		return formInstanceId;
	}

	public void setFormInstanceId(String formInstanceId) {
		this.formInstanceId = formInstanceId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}
	
	public String getProcDefKey() {
		return procDefKey;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}
	
	public String getCompanyKey() {
		return companyKey;
	}

	public void setCompanyKey(String companyKey) {
		this.companyKey = companyKey;
	}

	public String getDepartmentKey() {
		return departmentKey;
	}

	public void setDepartmentKey(String departmentKey) {
		this.departmentKey = departmentKey;
	}
	
	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
}
