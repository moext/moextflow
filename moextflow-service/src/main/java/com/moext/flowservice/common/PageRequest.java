package com.moext.flowservice.common;

import org.apache.ibatis.session.RowBounds;

/**
 * 分页请求
 * @author PengPeng
 */
public class PageRequest extends BaseRequest {
	
	private static final long serialVersionUID = 7414967733027868142L;
	
	//当前页：pageNum，从1开始
	public int pageNum = 0;
	
	//每页行数：pageSize
	public int pageSize = 10;
	
	public PageRequest(){
	}
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public PageRequest(int pageNum, int pageSize){
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}
	
	public int getFirstIndex() {
		return Math.max(0, (pageNum - 1) * pageSize);
	}
	
	public RowBounds getRowBounds() {
		int offset = (this.getPageNum()-1) * this.getPageSize();
		RowBounds rowBounds = new RowBounds(offset, this.getPageSize());
		return rowBounds;
	}
}
