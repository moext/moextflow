package com.moext.flowservice.flow.model;

import java.io.Serializable;

import org.flowable.idm.api.Group;

import com.moext.flowservice.dto.req.GroupReq;

/**
 * 工作流用户分组
 * @author PengPeng
 *
 */
public class FlowGroup implements Serializable {

	private static final long serialVersionUID = 7097955008411954017L;
	
	//角色标识
	private String id;
	
	//角色类型
	private String type;
	
	//角色名称
	private String name;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static FlowGroup instancesOf(Group group) {
		if(group == null) {
			return null;
		}
		FlowGroup item = new FlowGroup();
		item.setId(group.getId());
		item.setType(group.getType());
		item.setName(group.getName());
		return item;
	}
	
	public static FlowGroup instanceOf(GroupReq groupReq) {
		if(groupReq == null) {
			throw new IllegalArgumentException("参数groupReq不能为空");
		}
		FlowGroup item = new FlowGroup();
		item.setId(groupReq.getId());
		item.setType(groupReq.getType());
		item.setName(groupReq.getName());
		return item;
	}
}
