package com.moext.flowservice.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 分页响应
 * 
 * @author PengPeng
 *
 * @param <T>
 */
public class PageResponse<T> implements Serializable {

	private static final long serialVersionUID = 415796938866078858L;

	// 每页行数
	private int size;
	// 当前页元素个数【冗余字段】
	private int numberOfElements;
	// 当前页码，从1开始
	private int number = 0;
	// 总页数【冗余字段】
	private int totalPages = 10;
	// 总元素个数
	private long totalElements = -1;
	// 内容列表
	private List<T> content;
	// 是否第一页【冗余字段】
	private Boolean first;
	// 是否最后一页【冗余字段】
	private Boolean last;

	// 额外参数
	private Map<String, Object> extraMap = new HashMap<String, Object>();

	private PageResponse(int number, int size, long totalElements, List<T> content, Map<String, Object> extraMap) {
		this.number = number;
		this.size = size;
		this.totalElements = totalElements;
		this.content = content;
		this.extraMap = extraMap;
		// 计算总页码
		this.totalPages = getRowCount(totalElements, size);
		// 计算当前页元素个数
		this.numberOfElements = CollectionUtils.size(this.content);
		// 计算是否第一页
		this.first = this.number < 2;
		// 计算是否最后一页
		this.last = this.number == totalPages;
	}

	private int getRowCount(long total, int pagePerSize) {
		if (total % pagePerSize == 0) {
			return (int) (total / pagePerSize);
		} else {
			return (int) (total / pagePerSize + 1);
		}
	}

	/****
	 * 使用Builder模型让客户端调用时方便形成链式调用
	 * 
	 * @author peng
	 */
	public static class Builder<T> {
		private int number;
		private int size;
		private long totalElements;
		private List<T> content;
		private Map<String, Object> extraMap = new HashMap<String, Object>();
		private PageRequest pageRequest;

		public Builder<T> setPageRequest(PageRequest pageRequest) {
			this.pageRequest = pageRequest;
			return this;
		}

		public Builder<T> setContent(List<T> content) {
			this.content = content;
			return this;
		}

		public Builder<T> setTotalElements(long totalElements) {
			this.totalElements = totalElements;
			return this;
		}

		public Builder<T> setExtraMap(Map<String, Object> extraMap) {
			this.extraMap = extraMap;
			return this;
		}

		public PageResponse<T> build() {
			this.number = pageRequest.getPageNum();
			this.size = pageRequest.getPageSize();
			return new PageResponse<T>(number, size, totalElements, content, extraMap);
		}
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public int getSize() {
		return size;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public int getNumber() {
		return number;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public List<T> getContent() {
		return content;
	}

	public Boolean getFirst() {
		return first;
	}

	public Boolean getLast() {
		return last;
	}

	public Map<String, Object> getExtraMap() {
		return extraMap;
	}
}
