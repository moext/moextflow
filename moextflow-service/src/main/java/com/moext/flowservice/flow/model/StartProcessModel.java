package com.moext.flowservice.flow.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 流程发起模型
 * 
 * @author PengPeng
 */
public class StartProcessModel implements Serializable {

	private static final long serialVersionUID = -863844389513626342L;

	// 流程标题不能
	private String title;

	// 流程定义标识
	private String procDefKey;

	// 表单实例ID
	private String formInstanceId;

	// 发起人用户名
	private String applyUserId;

	// 公司标识
	public String companyKey;

	// "部门标识
	public String departmentKey;

	// 审批意见
	private String comment;

	// 流程变量
	private Map<String, Object> variables;

	private StartProcessModel(String title, String procDefKey, String formInstanceId, String applyUserId,
			String companyKey, String departmentKey, String comment, Map<String, Object> variables) {
		this.title = title;
		this.procDefKey = procDefKey;
		this.formInstanceId = formInstanceId;
		this.applyUserId = applyUserId;
		this.companyKey = companyKey;
		this.departmentKey = departmentKey;
		this.comment = comment;
		this.variables = variables;
	}

	/**
	 * 使用Builder模式让客户端调用时方便形成链式调用
	 * 
	 * @author pengPeng
	 */
	public static class Builder {
		private String title;
		private String procDefKey;
		private String formInstanceId;
		private String applyUserId;
		public String companyKey;
		public String departmentKey;
		private String comment;
		private Map<String, Object> variables;

		public String getTitle() {
			return title;
		}

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public String getProcDefKey() {
			return procDefKey;
		}

		public Builder setProcDefKey(String procDefKey) {
			this.procDefKey = procDefKey;
			return this;
		}

		public String getFormInstanceId() {
			return formInstanceId;
		}

		public Builder setFormInstanceId(String formInstanceId) {
			this.formInstanceId = formInstanceId;
			return this;
		}

		public String getApplyUserId() {
			return applyUserId;
		}

		public Builder setApplyUserId(String applyUserId) {
			this.applyUserId = applyUserId;
			return this;
		}

		public String getCompanyKey() {
			return companyKey;
		}

		public Builder setCompanyKey(String companyKey) {
			this.companyKey = companyKey;
			return this;
		}

		public String getDepartmentKey() {
			return departmentKey;
		}

		public Builder setDepartmentKey(String departmentKey) {
			this.departmentKey = departmentKey;
			return this;
		}

		public String getComment() {
			return comment;
		}

		public Builder setComment(String comment) {
			this.comment = comment;
			return this;
		}

		public Map<String, Object> getVariables() {
			return variables;
		}

		public Builder setVariables(Map<String, Object> variables) {
			this.variables = variables;
			return this;
		}

		public StartProcessModel build() {
			return new StartProcessModel(this.title, this.procDefKey, this.formInstanceId, this.applyUserId,
					this.companyKey, this.departmentKey, this.comment, this.variables);
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProcDefKey() {
		return procDefKey;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}

	public String getFormInstanceId() {
		return formInstanceId;
	}

	public void setFormInstanceId(String formInstanceId) {
		this.formInstanceId = formInstanceId;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
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
}
