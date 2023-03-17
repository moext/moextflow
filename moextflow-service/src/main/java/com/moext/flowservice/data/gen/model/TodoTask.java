package com.moext.flowservice.data.gen.model;

import java.io.Serializable;
import java.util.Date;

public class TodoTask implements Serializable {
    private Long id;

    private String applyUserCode;

    private String applyUserName;

    private String receiveUserCode;

    private String receiveUserName;

    private String status;

    private String processInstanceId;

    private String taskName;

    private String taskId;

    private Date taskCreateTime;

    private Date taskReceiveTime;

    private Date taskCompleteTime;

    private String todoId;

    private String formInstanceId;

    private String stepNodeName;

    private Boolean isDel;

    private String procDefKey;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplyUserCode() {
        return applyUserCode;
    }

    public void setApplyUserCode(String applyUserCode) {
        this.applyUserCode = applyUserCode == null ? null : applyUserCode.trim();
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName == null ? null : applyUserName.trim();
    }

    public String getReceiveUserCode() {
        return receiveUserCode;
    }

    public void setReceiveUserCode(String receiveUserCode) {
        this.receiveUserCode = receiveUserCode == null ? null : receiveUserCode.trim();
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName == null ? null : receiveUserName.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId == null ? null : processInstanceId.trim();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public Date getTaskCreateTime() {
        return taskCreateTime;
    }

    public void setTaskCreateTime(Date taskCreateTime) {
        this.taskCreateTime = taskCreateTime;
    }

    public Date getTaskReceiveTime() {
        return taskReceiveTime;
    }

    public void setTaskReceiveTime(Date taskReceiveTime) {
        this.taskReceiveTime = taskReceiveTime;
    }

    public Date getTaskCompleteTime() {
        return taskCompleteTime;
    }

    public void setTaskCompleteTime(Date taskCompleteTime) {
        this.taskCompleteTime = taskCompleteTime;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId == null ? null : todoId.trim();
    }

    public String getFormInstanceId() {
        return formInstanceId;
    }

    public void setFormInstanceId(String formInstanceId) {
        this.formInstanceId = formInstanceId == null ? null : formInstanceId.trim();
    }

    public String getStepNodeName() {
        return stepNodeName;
    }

    public void setStepNodeName(String stepNodeName) {
        this.stepNodeName = stepNodeName == null ? null : stepNodeName.trim();
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public String getProcDefKey() {
        return procDefKey;
    }

    public void setProcDefKey(String procDefKey) {
        this.procDefKey = procDefKey == null ? null : procDefKey.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", applyUserCode=").append(applyUserCode);
        sb.append(", applyUserName=").append(applyUserName);
        sb.append(", receiveUserCode=").append(receiveUserCode);
        sb.append(", receiveUserName=").append(receiveUserName);
        sb.append(", status=").append(status);
        sb.append(", processInstanceId=").append(processInstanceId);
        sb.append(", taskName=").append(taskName);
        sb.append(", taskId=").append(taskId);
        sb.append(", taskCreateTime=").append(taskCreateTime);
        sb.append(", taskReceiveTime=").append(taskReceiveTime);
        sb.append(", taskCompleteTime=").append(taskCompleteTime);
        sb.append(", todoId=").append(todoId);
        sb.append(", formInstanceId=").append(formInstanceId);
        sb.append(", stepNodeName=").append(stepNodeName);
        sb.append(", isDel=").append(isDel);
        sb.append(", procDefKey=").append(procDefKey);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TodoTask other = (TodoTask) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplyUserCode() == null ? other.getApplyUserCode() == null : this.getApplyUserCode().equals(other.getApplyUserCode()))
            && (this.getApplyUserName() == null ? other.getApplyUserName() == null : this.getApplyUserName().equals(other.getApplyUserName()))
            && (this.getReceiveUserCode() == null ? other.getReceiveUserCode() == null : this.getReceiveUserCode().equals(other.getReceiveUserCode()))
            && (this.getReceiveUserName() == null ? other.getReceiveUserName() == null : this.getReceiveUserName().equals(other.getReceiveUserName()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getProcessInstanceId() == null ? other.getProcessInstanceId() == null : this.getProcessInstanceId().equals(other.getProcessInstanceId()))
            && (this.getTaskName() == null ? other.getTaskName() == null : this.getTaskName().equals(other.getTaskName()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getTaskCreateTime() == null ? other.getTaskCreateTime() == null : this.getTaskCreateTime().equals(other.getTaskCreateTime()))
            && (this.getTaskReceiveTime() == null ? other.getTaskReceiveTime() == null : this.getTaskReceiveTime().equals(other.getTaskReceiveTime()))
            && (this.getTaskCompleteTime() == null ? other.getTaskCompleteTime() == null : this.getTaskCompleteTime().equals(other.getTaskCompleteTime()))
            && (this.getTodoId() == null ? other.getTodoId() == null : this.getTodoId().equals(other.getTodoId()))
            && (this.getFormInstanceId() == null ? other.getFormInstanceId() == null : this.getFormInstanceId().equals(other.getFormInstanceId()))
            && (this.getStepNodeName() == null ? other.getStepNodeName() == null : this.getStepNodeName().equals(other.getStepNodeName()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.getProcDefKey() == null ? other.getProcDefKey() == null : this.getProcDefKey().equals(other.getProcDefKey()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplyUserCode() == null) ? 0 : getApplyUserCode().hashCode());
        result = prime * result + ((getApplyUserName() == null) ? 0 : getApplyUserName().hashCode());
        result = prime * result + ((getReceiveUserCode() == null) ? 0 : getReceiveUserCode().hashCode());
        result = prime * result + ((getReceiveUserName() == null) ? 0 : getReceiveUserName().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getProcessInstanceId() == null) ? 0 : getProcessInstanceId().hashCode());
        result = prime * result + ((getTaskName() == null) ? 0 : getTaskName().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getTaskCreateTime() == null) ? 0 : getTaskCreateTime().hashCode());
        result = prime * result + ((getTaskReceiveTime() == null) ? 0 : getTaskReceiveTime().hashCode());
        result = prime * result + ((getTaskCompleteTime() == null) ? 0 : getTaskCompleteTime().hashCode());
        result = prime * result + ((getTodoId() == null) ? 0 : getTodoId().hashCode());
        result = prime * result + ((getFormInstanceId() == null) ? 0 : getFormInstanceId().hashCode());
        result = prime * result + ((getStepNodeName() == null) ? 0 : getStepNodeName().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((getProcDefKey() == null) ? 0 : getProcDefKey().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}