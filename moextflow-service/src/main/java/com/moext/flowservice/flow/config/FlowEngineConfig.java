package com.moext.flowservice.flow.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.stereotype.Component;

import com.moext.flowservice.flow.listener.ProcessCompletedListener;
import com.moext.flowservice.flow.listener.TaskAssignedListener;
import com.moext.flowservice.flow.listener.TaskBackListener;
import com.moext.flowservice.flow.listener.TaskCompletedListener;
import com.moext.flowservice.flow.listener.TaskCreateListener;
import com.moext.flowservice.flow.listener.TaskDeleteListener;

@Component
public class FlowEngineConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

	private SpringProcessEngineConfiguration processEngineConfiguration;
	
	public SpringProcessEngineConfiguration getProcessEngineConfiguration() {
		return processEngineConfiguration;
	}

	@Override
	public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
		processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");        

        processEngineConfiguration.setDatabaseSchemaUpdate("false");
        
        Map<String, List<FlowableEventListener>> typedListeners = new HashMap<String, List<FlowableEventListener>>();
        
        //任务创建监听
		List<FlowableEventListener> taskCreatedListenerList = new ArrayList<FlowableEventListener>();
		TaskCreateListener taskCreatedListener = new TaskCreateListener();
		TaskBackListener taskBackListener = new TaskBackListener();
		taskCreatedListenerList.add(taskCreatedListener);
		taskCreatedListenerList.add(taskBackListener);
	    typedListeners.put("TASK_CREATED", taskCreatedListenerList);
      	    
        //任务指定处理人监听
		List<FlowableEventListener> taskAssignedListenerList = new ArrayList<FlowableEventListener>();
		TaskAssignedListener taskAssignedListener = new TaskAssignedListener();
		taskAssignedListenerList.add(taskAssignedListener);//扩展assignee
	    typedListeners.put("TASK_ASSIGNED", taskAssignedListenerList);
	    
	    //任务删除监听
		List<FlowableEventListener> taskDeletedListenerList = new ArrayList<FlowableEventListener>();
		TaskDeleteListener taskDeletedListener = new TaskDeleteListener();
		taskDeletedListenerList.add(taskDeletedListener);
	    typedListeners.put("ENTITY_DELETED", taskDeletedListenerList);
	    
	    //任务完成监听
  		List<FlowableEventListener> taskCompletedListenerList = new ArrayList<FlowableEventListener>();
  		TaskCompletedListener taskCompletedListener = new TaskCompletedListener();
  		taskCompletedListenerList.add(taskCompletedListener);
  	    typedListeners.put("TASK_COMPLETED", taskCompletedListenerList);

	    //流程完成监听
	    ProcessCompletedListener processCompleteListener = new ProcessCompletedListener();
	    List<FlowableEventListener> processCompleteListenerList = new ArrayList<FlowableEventListener>();
	    processCompleteListenerList.add(processCompleteListener);
	    typedListeners.put("PROCESS_COMPLETED", processCompleteListenerList);
	    
	    
	    processEngineConfiguration.setTypedEventListeners(typedListeners);
	}
}
