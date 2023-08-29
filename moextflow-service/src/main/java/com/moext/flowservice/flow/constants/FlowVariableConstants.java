package com.moext.flowservice.flow.constants;

/**
 * 流程变量和变量值常量定义
 * 
 * @author PengPeng
 *
 */
public class FlowVariableConstants {

	/**
	 * 流程变量：流程标题
	 */
	public static final String VARIABLE_TITLE = "title";

	/**
	 * 流程变量：发起人用户名
	 */
	public static final String VARIABLE_APPLY_USERID = "applyUserId";

	/**
	 * 流程变量：发起人姓名
	 */
	public static final String VARIABLE_APPLY_USERNAME = "applyUserName";

	/**
	 * 流程变量：发起时间
	 */
	public static final String VARIABLE_APPLY_DATE = "applyDate";

	/**
	 * 流程变量：审批结果
	 */
	public static final String VARIABLE_RESULT = "result";

	/**
	 * 流程变量：公司标识
	 */
	public static final String VARIABLE_COMPANYKEY = "companyKey";

	/**
	 * 流程变量：部门标识
	 */
	public static final String VARIABLE_DEPARTMENTKEY = "departmentKey";

	/**
	 * 流程变量值：审批通过
	 */
	public static final Integer VARIABLE_RESULT_VALUE_PASS = 1;

	/**
	 * 流程变量值：审批拒绝不通过
	 */
	public static final Integer VARIABLE_RESULT_VALUE_REJECT = 0;
}
