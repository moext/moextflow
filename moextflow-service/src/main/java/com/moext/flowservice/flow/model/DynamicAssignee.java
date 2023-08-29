package com.moext.flowservice.flow.model;

import java.util.Map;

/**
 * 动态处理人，支持以json的形式扩展
 * 
 * @author PengPeng
 */
public class DynamicAssignee {

	/** 以下参数由json中指定，其中type、index支持默认值 **/
	// 机构编码
	private String orgCode;

	// 角色名称
	private String roleName;

	// 动态脚本
	private String script;

	// recursionLevel=0，递归层次：0=不限；1=1级，2=2级 ...
	private int recursionLevel = 0;

	// type=1 指定组织机构ID+角色得到处理人
	// type=2 根据applyUserId得到组织机构，再递归根据组织机构+角色得到处理人。注意：此角色下的人员必须在该组织架构下
	// type=4 动态脚本，此时在script字段传动态脚本
	// type=5 根据applyUserId得到部门负责人
	// type=6 根据applyUserId得到公司，再根据roleName得到处理人
	// type=7 根据applyUserId得到部门，再根据roleName得到处理人
	// type=8 根据${company}得到公司id，再根据roleName得到处理人
	// type=9 根据${department}得到部门id，再根据roleName得到处理人
	/*** 以下为更灵活的扩展模式 **/
	// type=11 根据${department}得到部门id，按visualType取对应部门分管配置
	// type=12 根据${company}得到公司id，按visualType取对应公司分管配置
	private int type = 1;

	// 如果条件条件的处理人有多个，取第几个，默认取第1个
	private int index = 1;

	/** 动态参数 **/
	// 任务发起人
	private String applyUserId;
	// 公司
	private String company;
	// 部门
	private String department;

	// 虚拟组织机构中的虚拟类型
	private int visualType = -1;

	/**
	 * 无法找到用户时，是否可跳过
	 */
	private boolean canSkip = false;

	// 扩展参数
	private Map<String, Object> variables;

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public boolean isCanSkip() {
		return canSkip;
	}

	public void setCanSkip(boolean canSkip) {
		this.canSkip = canSkip;
	}

	public int getVisualType() {
		return visualType;
	}

	public void setVisualType(int visualType) {
		this.visualType = visualType;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getRecursionLevel() {
		return recursionLevel;
	}

	public void setRecursionLevel(int recursionLevel) {
		this.recursionLevel = recursionLevel;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
}
