package com.moext.flowservice.flow.constants;

/**
 * 审批操作类型常量
 * @author PengPeng
 */
public class ApproveOperTypeConstants {
		
	/**
	 * 操作类型：申请
	 */
	public static String OPER_TYPE_APPLY = "0";
	
	/**
	 * 操作类型：通过
	 */
	public static String OPER_TYPE_OK = "1";
	
	/**
	 * 操作类型：退回
	 */
	public static String OPER_TYPE_BACK = "2";
	
	/**
	 * 操作类型：否决
	 */
	public static String OPER_TYPE_REJECT = "3";
	
	/**
	 * 操作类型：跳转
	 */
	public static String OPER_TYPE_JUMP = "4";
	
	/**
	 * 操作类型：签收
	 */
	public static String OPER_TYPE_CLAIM = "5";
	
	/**
	 * 操作类型：重新申请
	 */
	public static String OPER_TYPE_REAPPLY = "6";
	
	/**
	 * 操作类型：销毁申请
	 */
	public static String OPER_TYPE_CANCEL = "7";
	
}
