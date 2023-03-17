package com.moext.flowservice.flow.model;

import java.io.Serializable;

/**
 * 批量操作结果
 * @author PengPeng
 */
public class BatchOperResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//总量
	private int totalCount;
	
	//保持数量
	private int keepCount = 0;
	
	//新增数量
	private int addCount = 0;
	
	//删除数量
	private int deleteCount = 0;
	
	public int getKeepCount() {
		return keepCount;
	}
	public void setKeepCount(int keepCount) {
		this.keepCount = keepCount;
	}
	public int getAddCount() {
		return addCount;
	}
	public void setAddCount(int addCount) {
		this.addCount = addCount;
	}
	public int getDeleteCount() {
		return deleteCount;
	}
	public void setDeleteCount(int deleteCount) {
		this.deleteCount = deleteCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
