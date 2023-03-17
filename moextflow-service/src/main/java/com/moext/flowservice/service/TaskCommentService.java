package com.moext.flowservice.service;

import java.util.List;

import com.moext.flowservice.data.gen.model.TaskComment;

/**
 * 任务审批意见Service
 * @author PengPeng
 */
public interface TaskCommentService {

    public List<TaskComment> listComments(String processInstId);
}
