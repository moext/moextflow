package com.moext.flowservice.data.gen.model;

import java.io.Serializable;
import java.util.Date;

public class TaskComment implements Serializable {
    private Long id;

    private String formInstanceId;

    private String processInstanceId;

    private String stepNodeName;

    private Date taskCompleteTime;

    private Date taskCreateTime;

    private String taskId;

    private String taskName;

    private Date taskReceiveTime;

    private String todoId;

    private String userCode;

    private String userName;

    private Boolean isDel;

    private String operType;

    private Date createTime;

    private Date updateTime;

    private String comment;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormInstanceId() {
        return formInstanceId;
    }

    public void setFormInstanceId(String formInstanceId) {
        this.formInstanceId = formInstanceId == null ? null : formInstanceId.trim();
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId == null ? null : processInstanceId.trim();
    }

    public String getStepNodeName() {
        return stepNodeName;
    }

    public void setStepNodeName(String stepNodeName) {
        this.stepNodeName = stepNodeName == null ? null : stepNodeName.trim();
    }

    public Date getTaskCompleteTime() {
        return taskCompleteTime;
    }

    public void setTaskCompleteTime(Date taskCompleteTime) {
        this.taskCompleteTime = taskCompleteTime;
    }

    public Date getTaskCreateTime() {
        return taskCreateTime;
    }

    public void setTaskCreateTime(Date taskCreateTime) {
        this.taskCreateTime = taskCreateTime;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public Date getTaskReceiveTime() {
        return taskReceiveTime;
    }

    public void setTaskReceiveTime(Date taskReceiveTime) {
        this.taskReceiveTime = taskReceiveTime;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId == null ? null : todoId.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType == null ? null : operType.trim();
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", formInstanceId=").append(formInstanceId);
        sb.append(", processInstanceId=").append(processInstanceId);
        sb.append(", stepNodeName=").append(stepNodeName);
        sb.append(", taskCompleteTime=").append(taskCompleteTime);
        sb.append(", taskCreateTime=").append(taskCreateTime);
        sb.append(", taskId=").append(taskId);
        sb.append(", taskName=").append(taskName);
        sb.append(", taskReceiveTime=").append(taskReceiveTime);
        sb.append(", todoId=").append(todoId);
        sb.append(", userCode=").append(userCode);
        sb.append(", userName=").append(userName);
        sb.append(", isDel=").append(isDel);
        sb.append(", operType=").append(operType);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", comment=").append(comment);
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
        TaskComment other = (TaskComment) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFormInstanceId() == null ? other.getFormInstanceId() == null : this.getFormInstanceId().equals(other.getFormInstanceId()))
            && (this.getProcessInstanceId() == null ? other.getProcessInstanceId() == null : this.getProcessInstanceId().equals(other.getProcessInstanceId()))
            && (this.getStepNodeName() == null ? other.getStepNodeName() == null : this.getStepNodeName().equals(other.getStepNodeName()))
            && (this.getTaskCompleteTime() == null ? other.getTaskCompleteTime() == null : this.getTaskCompleteTime().equals(other.getTaskCompleteTime()))
            && (this.getTaskCreateTime() == null ? other.getTaskCreateTime() == null : this.getTaskCreateTime().equals(other.getTaskCreateTime()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getTaskName() == null ? other.getTaskName() == null : this.getTaskName().equals(other.getTaskName()))
            && (this.getTaskReceiveTime() == null ? other.getTaskReceiveTime() == null : this.getTaskReceiveTime().equals(other.getTaskReceiveTime()))
            && (this.getTodoId() == null ? other.getTodoId() == null : this.getTodoId().equals(other.getTodoId()))
            && (this.getUserCode() == null ? other.getUserCode() == null : this.getUserCode().equals(other.getUserCode()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.getOperType() == null ? other.getOperType() == null : this.getOperType().equals(other.getOperType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getComment() == null ? other.getComment() == null : this.getComment().equals(other.getComment()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFormInstanceId() == null) ? 0 : getFormInstanceId().hashCode());
        result = prime * result + ((getProcessInstanceId() == null) ? 0 : getProcessInstanceId().hashCode());
        result = prime * result + ((getStepNodeName() == null) ? 0 : getStepNodeName().hashCode());
        result = prime * result + ((getTaskCompleteTime() == null) ? 0 : getTaskCompleteTime().hashCode());
        result = prime * result + ((getTaskCreateTime() == null) ? 0 : getTaskCreateTime().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getTaskName() == null) ? 0 : getTaskName().hashCode());
        result = prime * result + ((getTaskReceiveTime() == null) ? 0 : getTaskReceiveTime().hashCode());
        result = prime * result + ((getTodoId() == null) ? 0 : getTodoId().hashCode());
        result = prime * result + ((getUserCode() == null) ? 0 : getUserCode().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((getOperType() == null) ? 0 : getOperType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getComment() == null) ? 0 : getComment().hashCode());
        return result;
    }
}