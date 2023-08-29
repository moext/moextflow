package com.moext.flowservice.data.gen.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskCommentExample {
	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public TaskCommentExample() {
		oredCriteria = new ArrayList<>();
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andIdIsNull() {
			addCriterion("id is null");
			return (Criteria) this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("id is not null");
			return (Criteria) this;
		}

		public Criteria andIdEqualTo(Long value) {
			addCriterion("id =", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotEqualTo(Long value) {
			addCriterion("id <>", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThan(Long value) {
			addCriterion("id >", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThanOrEqualTo(Long value) {
			addCriterion("id >=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThan(Long value) {
			addCriterion("id <", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThanOrEqualTo(Long value) {
			addCriterion("id <=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdIn(List<Long> values) {
			addCriterion("id in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotIn(List<Long> values) {
			addCriterion("id not in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdBetween(Long value1, Long value2) {
			addCriterion("id between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotBetween(Long value1, Long value2) {
			addCriterion("id not between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andFormInstanceIdIsNull() {
			addCriterion("form_instance_id is null");
			return (Criteria) this;
		}

		public Criteria andFormInstanceIdIsNotNull() {
			addCriterion("form_instance_id is not null");
			return (Criteria) this;
		}

		public Criteria andFormInstanceIdEqualTo(String value) {
			addCriterion("form_instance_id =", value, "formInstanceId");
			return (Criteria) this;
		}

		public Criteria andFormInstanceIdNotEqualTo(String value) {
			addCriterion("form_instance_id <>", value, "formInstanceId");
			return (Criteria) this;
		}

		public Criteria andFormInstanceIdGreaterThan(String value) {
			addCriterion("form_instance_id >", value, "formInstanceId");
			return (Criteria) this;
		}

		public Criteria andFormInstanceIdGreaterThanOrEqualTo(String value) {
			addCriterion("form_instance_id >=", value, "formInstanceId");
			return (Criteria) this;
		}

		public Criteria andFormInstanceIdLessThan(String value) {
			addCriterion("form_instance_id <", value, "formInstanceId");
			return (Criteria) this;
		}

		public Criteria andFormInstanceIdLessThanOrEqualTo(String value) {
			addCriterion("form_instance_id <=", value, "formInstanceId");
			return (Criteria) this;
		}

		public Criteria andFormInstanceIdLike(String value) {
			addCriterion("form_instance_id like", value, "formInstanceId");
			return (Criteria) this;
		}

		public Criteria andFormInstanceIdNotLike(String value) {
			addCriterion("form_instance_id not like", value, "formInstanceId");
			return (Criteria) this;
		}

		public Criteria andFormInstanceIdIn(List<String> values) {
			addCriterion("form_instance_id in", values, "formInstanceId");
			return (Criteria) this;
		}

		public Criteria andFormInstanceIdNotIn(List<String> values) {
			addCriterion("form_instance_id not in", values, "formInstanceId");
			return (Criteria) this;
		}

		public Criteria andFormInstanceIdBetween(String value1, String value2) {
			addCriterion("form_instance_id between", value1, value2, "formInstanceId");
			return (Criteria) this;
		}

		public Criteria andFormInstanceIdNotBetween(String value1, String value2) {
			addCriterion("form_instance_id not between", value1, value2, "formInstanceId");
			return (Criteria) this;
		}

		public Criteria andProcessInstanceIdIsNull() {
			addCriterion("process_instance_id is null");
			return (Criteria) this;
		}

		public Criteria andProcessInstanceIdIsNotNull() {
			addCriterion("process_instance_id is not null");
			return (Criteria) this;
		}

		public Criteria andProcessInstanceIdEqualTo(String value) {
			addCriterion("process_instance_id =", value, "processInstanceId");
			return (Criteria) this;
		}

		public Criteria andProcessInstanceIdNotEqualTo(String value) {
			addCriterion("process_instance_id <>", value, "processInstanceId");
			return (Criteria) this;
		}

		public Criteria andProcessInstanceIdGreaterThan(String value) {
			addCriterion("process_instance_id >", value, "processInstanceId");
			return (Criteria) this;
		}

		public Criteria andProcessInstanceIdGreaterThanOrEqualTo(String value) {
			addCriterion("process_instance_id >=", value, "processInstanceId");
			return (Criteria) this;
		}

		public Criteria andProcessInstanceIdLessThan(String value) {
			addCriterion("process_instance_id <", value, "processInstanceId");
			return (Criteria) this;
		}

		public Criteria andProcessInstanceIdLessThanOrEqualTo(String value) {
			addCriterion("process_instance_id <=", value, "processInstanceId");
			return (Criteria) this;
		}

		public Criteria andProcessInstanceIdLike(String value) {
			addCriterion("process_instance_id like", value, "processInstanceId");
			return (Criteria) this;
		}

		public Criteria andProcessInstanceIdNotLike(String value) {
			addCriterion("process_instance_id not like", value, "processInstanceId");
			return (Criteria) this;
		}

		public Criteria andProcessInstanceIdIn(List<String> values) {
			addCriterion("process_instance_id in", values, "processInstanceId");
			return (Criteria) this;
		}

		public Criteria andProcessInstanceIdNotIn(List<String> values) {
			addCriterion("process_instance_id not in", values, "processInstanceId");
			return (Criteria) this;
		}

		public Criteria andProcessInstanceIdBetween(String value1, String value2) {
			addCriterion("process_instance_id between", value1, value2, "processInstanceId");
			return (Criteria) this;
		}

		public Criteria andProcessInstanceIdNotBetween(String value1, String value2) {
			addCriterion("process_instance_id not between", value1, value2, "processInstanceId");
			return (Criteria) this;
		}

		public Criteria andStepNodeNameIsNull() {
			addCriterion("step_node_name is null");
			return (Criteria) this;
		}

		public Criteria andStepNodeNameIsNotNull() {
			addCriterion("step_node_name is not null");
			return (Criteria) this;
		}

		public Criteria andStepNodeNameEqualTo(String value) {
			addCriterion("step_node_name =", value, "stepNodeName");
			return (Criteria) this;
		}

		public Criteria andStepNodeNameNotEqualTo(String value) {
			addCriterion("step_node_name <>", value, "stepNodeName");
			return (Criteria) this;
		}

		public Criteria andStepNodeNameGreaterThan(String value) {
			addCriterion("step_node_name >", value, "stepNodeName");
			return (Criteria) this;
		}

		public Criteria andStepNodeNameGreaterThanOrEqualTo(String value) {
			addCriterion("step_node_name >=", value, "stepNodeName");
			return (Criteria) this;
		}

		public Criteria andStepNodeNameLessThan(String value) {
			addCriterion("step_node_name <", value, "stepNodeName");
			return (Criteria) this;
		}

		public Criteria andStepNodeNameLessThanOrEqualTo(String value) {
			addCriterion("step_node_name <=", value, "stepNodeName");
			return (Criteria) this;
		}

		public Criteria andStepNodeNameLike(String value) {
			addCriterion("step_node_name like", value, "stepNodeName");
			return (Criteria) this;
		}

		public Criteria andStepNodeNameNotLike(String value) {
			addCriterion("step_node_name not like", value, "stepNodeName");
			return (Criteria) this;
		}

		public Criteria andStepNodeNameIn(List<String> values) {
			addCriterion("step_node_name in", values, "stepNodeName");
			return (Criteria) this;
		}

		public Criteria andStepNodeNameNotIn(List<String> values) {
			addCriterion("step_node_name not in", values, "stepNodeName");
			return (Criteria) this;
		}

		public Criteria andStepNodeNameBetween(String value1, String value2) {
			addCriterion("step_node_name between", value1, value2, "stepNodeName");
			return (Criteria) this;
		}

		public Criteria andStepNodeNameNotBetween(String value1, String value2) {
			addCriterion("step_node_name not between", value1, value2, "stepNodeName");
			return (Criteria) this;
		}

		public Criteria andTaskCompleteTimeIsNull() {
			addCriterion("task_complete_time is null");
			return (Criteria) this;
		}

		public Criteria andTaskCompleteTimeIsNotNull() {
			addCriterion("task_complete_time is not null");
			return (Criteria) this;
		}

		public Criteria andTaskCompleteTimeEqualTo(Date value) {
			addCriterion("task_complete_time =", value, "taskCompleteTime");
			return (Criteria) this;
		}

		public Criteria andTaskCompleteTimeNotEqualTo(Date value) {
			addCriterion("task_complete_time <>", value, "taskCompleteTime");
			return (Criteria) this;
		}

		public Criteria andTaskCompleteTimeGreaterThan(Date value) {
			addCriterion("task_complete_time >", value, "taskCompleteTime");
			return (Criteria) this;
		}

		public Criteria andTaskCompleteTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("task_complete_time >=", value, "taskCompleteTime");
			return (Criteria) this;
		}

		public Criteria andTaskCompleteTimeLessThan(Date value) {
			addCriterion("task_complete_time <", value, "taskCompleteTime");
			return (Criteria) this;
		}

		public Criteria andTaskCompleteTimeLessThanOrEqualTo(Date value) {
			addCriterion("task_complete_time <=", value, "taskCompleteTime");
			return (Criteria) this;
		}

		public Criteria andTaskCompleteTimeIn(List<Date> values) {
			addCriterion("task_complete_time in", values, "taskCompleteTime");
			return (Criteria) this;
		}

		public Criteria andTaskCompleteTimeNotIn(List<Date> values) {
			addCriterion("task_complete_time not in", values, "taskCompleteTime");
			return (Criteria) this;
		}

		public Criteria andTaskCompleteTimeBetween(Date value1, Date value2) {
			addCriterion("task_complete_time between", value1, value2, "taskCompleteTime");
			return (Criteria) this;
		}

		public Criteria andTaskCompleteTimeNotBetween(Date value1, Date value2) {
			addCriterion("task_complete_time not between", value1, value2, "taskCompleteTime");
			return (Criteria) this;
		}

		public Criteria andTaskCreateTimeIsNull() {
			addCriterion("task_create_time is null");
			return (Criteria) this;
		}

		public Criteria andTaskCreateTimeIsNotNull() {
			addCriterion("task_create_time is not null");
			return (Criteria) this;
		}

		public Criteria andTaskCreateTimeEqualTo(Date value) {
			addCriterion("task_create_time =", value, "taskCreateTime");
			return (Criteria) this;
		}

		public Criteria andTaskCreateTimeNotEqualTo(Date value) {
			addCriterion("task_create_time <>", value, "taskCreateTime");
			return (Criteria) this;
		}

		public Criteria andTaskCreateTimeGreaterThan(Date value) {
			addCriterion("task_create_time >", value, "taskCreateTime");
			return (Criteria) this;
		}

		public Criteria andTaskCreateTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("task_create_time >=", value, "taskCreateTime");
			return (Criteria) this;
		}

		public Criteria andTaskCreateTimeLessThan(Date value) {
			addCriterion("task_create_time <", value, "taskCreateTime");
			return (Criteria) this;
		}

		public Criteria andTaskCreateTimeLessThanOrEqualTo(Date value) {
			addCriterion("task_create_time <=", value, "taskCreateTime");
			return (Criteria) this;
		}

		public Criteria andTaskCreateTimeIn(List<Date> values) {
			addCriterion("task_create_time in", values, "taskCreateTime");
			return (Criteria) this;
		}

		public Criteria andTaskCreateTimeNotIn(List<Date> values) {
			addCriterion("task_create_time not in", values, "taskCreateTime");
			return (Criteria) this;
		}

		public Criteria andTaskCreateTimeBetween(Date value1, Date value2) {
			addCriterion("task_create_time between", value1, value2, "taskCreateTime");
			return (Criteria) this;
		}

		public Criteria andTaskCreateTimeNotBetween(Date value1, Date value2) {
			addCriterion("task_create_time not between", value1, value2, "taskCreateTime");
			return (Criteria) this;
		}

		public Criteria andTaskIdIsNull() {
			addCriterion("task_id is null");
			return (Criteria) this;
		}

		public Criteria andTaskIdIsNotNull() {
			addCriterion("task_id is not null");
			return (Criteria) this;
		}

		public Criteria andTaskIdEqualTo(String value) {
			addCriterion("task_id =", value, "taskId");
			return (Criteria) this;
		}

		public Criteria andTaskIdNotEqualTo(String value) {
			addCriterion("task_id <>", value, "taskId");
			return (Criteria) this;
		}

		public Criteria andTaskIdGreaterThan(String value) {
			addCriterion("task_id >", value, "taskId");
			return (Criteria) this;
		}

		public Criteria andTaskIdGreaterThanOrEqualTo(String value) {
			addCriterion("task_id >=", value, "taskId");
			return (Criteria) this;
		}

		public Criteria andTaskIdLessThan(String value) {
			addCriterion("task_id <", value, "taskId");
			return (Criteria) this;
		}

		public Criteria andTaskIdLessThanOrEqualTo(String value) {
			addCriterion("task_id <=", value, "taskId");
			return (Criteria) this;
		}

		public Criteria andTaskIdLike(String value) {
			addCriterion("task_id like", value, "taskId");
			return (Criteria) this;
		}

		public Criteria andTaskIdNotLike(String value) {
			addCriterion("task_id not like", value, "taskId");
			return (Criteria) this;
		}

		public Criteria andTaskIdIn(List<String> values) {
			addCriterion("task_id in", values, "taskId");
			return (Criteria) this;
		}

		public Criteria andTaskIdNotIn(List<String> values) {
			addCriterion("task_id not in", values, "taskId");
			return (Criteria) this;
		}

		public Criteria andTaskIdBetween(String value1, String value2) {
			addCriterion("task_id between", value1, value2, "taskId");
			return (Criteria) this;
		}

		public Criteria andTaskIdNotBetween(String value1, String value2) {
			addCriterion("task_id not between", value1, value2, "taskId");
			return (Criteria) this;
		}

		public Criteria andTaskNameIsNull() {
			addCriterion("task_name is null");
			return (Criteria) this;
		}

		public Criteria andTaskNameIsNotNull() {
			addCriterion("task_name is not null");
			return (Criteria) this;
		}

		public Criteria andTaskNameEqualTo(String value) {
			addCriterion("task_name =", value, "taskName");
			return (Criteria) this;
		}

		public Criteria andTaskNameNotEqualTo(String value) {
			addCriterion("task_name <>", value, "taskName");
			return (Criteria) this;
		}

		public Criteria andTaskNameGreaterThan(String value) {
			addCriterion("task_name >", value, "taskName");
			return (Criteria) this;
		}

		public Criteria andTaskNameGreaterThanOrEqualTo(String value) {
			addCriterion("task_name >=", value, "taskName");
			return (Criteria) this;
		}

		public Criteria andTaskNameLessThan(String value) {
			addCriterion("task_name <", value, "taskName");
			return (Criteria) this;
		}

		public Criteria andTaskNameLessThanOrEqualTo(String value) {
			addCriterion("task_name <=", value, "taskName");
			return (Criteria) this;
		}

		public Criteria andTaskNameLike(String value) {
			addCriterion("task_name like", value, "taskName");
			return (Criteria) this;
		}

		public Criteria andTaskNameNotLike(String value) {
			addCriterion("task_name not like", value, "taskName");
			return (Criteria) this;
		}

		public Criteria andTaskNameIn(List<String> values) {
			addCriterion("task_name in", values, "taskName");
			return (Criteria) this;
		}

		public Criteria andTaskNameNotIn(List<String> values) {
			addCriterion("task_name not in", values, "taskName");
			return (Criteria) this;
		}

		public Criteria andTaskNameBetween(String value1, String value2) {
			addCriterion("task_name between", value1, value2, "taskName");
			return (Criteria) this;
		}

		public Criteria andTaskNameNotBetween(String value1, String value2) {
			addCriterion("task_name not between", value1, value2, "taskName");
			return (Criteria) this;
		}

		public Criteria andTaskReceiveTimeIsNull() {
			addCriterion("task_receive_time is null");
			return (Criteria) this;
		}

		public Criteria andTaskReceiveTimeIsNotNull() {
			addCriterion("task_receive_time is not null");
			return (Criteria) this;
		}

		public Criteria andTaskReceiveTimeEqualTo(Date value) {
			addCriterion("task_receive_time =", value, "taskReceiveTime");
			return (Criteria) this;
		}

		public Criteria andTaskReceiveTimeNotEqualTo(Date value) {
			addCriterion("task_receive_time <>", value, "taskReceiveTime");
			return (Criteria) this;
		}

		public Criteria andTaskReceiveTimeGreaterThan(Date value) {
			addCriterion("task_receive_time >", value, "taskReceiveTime");
			return (Criteria) this;
		}

		public Criteria andTaskReceiveTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("task_receive_time >=", value, "taskReceiveTime");
			return (Criteria) this;
		}

		public Criteria andTaskReceiveTimeLessThan(Date value) {
			addCriterion("task_receive_time <", value, "taskReceiveTime");
			return (Criteria) this;
		}

		public Criteria andTaskReceiveTimeLessThanOrEqualTo(Date value) {
			addCriterion("task_receive_time <=", value, "taskReceiveTime");
			return (Criteria) this;
		}

		public Criteria andTaskReceiveTimeIn(List<Date> values) {
			addCriterion("task_receive_time in", values, "taskReceiveTime");
			return (Criteria) this;
		}

		public Criteria andTaskReceiveTimeNotIn(List<Date> values) {
			addCriterion("task_receive_time not in", values, "taskReceiveTime");
			return (Criteria) this;
		}

		public Criteria andTaskReceiveTimeBetween(Date value1, Date value2) {
			addCriterion("task_receive_time between", value1, value2, "taskReceiveTime");
			return (Criteria) this;
		}

		public Criteria andTaskReceiveTimeNotBetween(Date value1, Date value2) {
			addCriterion("task_receive_time not between", value1, value2, "taskReceiveTime");
			return (Criteria) this;
		}

		public Criteria andTodoIdIsNull() {
			addCriterion("todo_id is null");
			return (Criteria) this;
		}

		public Criteria andTodoIdIsNotNull() {
			addCriterion("todo_id is not null");
			return (Criteria) this;
		}

		public Criteria andTodoIdEqualTo(String value) {
			addCriterion("todo_id =", value, "todoId");
			return (Criteria) this;
		}

		public Criteria andTodoIdNotEqualTo(String value) {
			addCriterion("todo_id <>", value, "todoId");
			return (Criteria) this;
		}

		public Criteria andTodoIdGreaterThan(String value) {
			addCriterion("todo_id >", value, "todoId");
			return (Criteria) this;
		}

		public Criteria andTodoIdGreaterThanOrEqualTo(String value) {
			addCriterion("todo_id >=", value, "todoId");
			return (Criteria) this;
		}

		public Criteria andTodoIdLessThan(String value) {
			addCriterion("todo_id <", value, "todoId");
			return (Criteria) this;
		}

		public Criteria andTodoIdLessThanOrEqualTo(String value) {
			addCriterion("todo_id <=", value, "todoId");
			return (Criteria) this;
		}

		public Criteria andTodoIdLike(String value) {
			addCriterion("todo_id like", value, "todoId");
			return (Criteria) this;
		}

		public Criteria andTodoIdNotLike(String value) {
			addCriterion("todo_id not like", value, "todoId");
			return (Criteria) this;
		}

		public Criteria andTodoIdIn(List<String> values) {
			addCriterion("todo_id in", values, "todoId");
			return (Criteria) this;
		}

		public Criteria andTodoIdNotIn(List<String> values) {
			addCriterion("todo_id not in", values, "todoId");
			return (Criteria) this;
		}

		public Criteria andTodoIdBetween(String value1, String value2) {
			addCriterion("todo_id between", value1, value2, "todoId");
			return (Criteria) this;
		}

		public Criteria andTodoIdNotBetween(String value1, String value2) {
			addCriterion("todo_id not between", value1, value2, "todoId");
			return (Criteria) this;
		}

		public Criteria andUserCodeIsNull() {
			addCriterion("user_code is null");
			return (Criteria) this;
		}

		public Criteria andUserCodeIsNotNull() {
			addCriterion("user_code is not null");
			return (Criteria) this;
		}

		public Criteria andUserCodeEqualTo(String value) {
			addCriterion("user_code =", value, "userCode");
			return (Criteria) this;
		}

		public Criteria andUserCodeNotEqualTo(String value) {
			addCriterion("user_code <>", value, "userCode");
			return (Criteria) this;
		}

		public Criteria andUserCodeGreaterThan(String value) {
			addCriterion("user_code >", value, "userCode");
			return (Criteria) this;
		}

		public Criteria andUserCodeGreaterThanOrEqualTo(String value) {
			addCriterion("user_code >=", value, "userCode");
			return (Criteria) this;
		}

		public Criteria andUserCodeLessThan(String value) {
			addCriterion("user_code <", value, "userCode");
			return (Criteria) this;
		}

		public Criteria andUserCodeLessThanOrEqualTo(String value) {
			addCriterion("user_code <=", value, "userCode");
			return (Criteria) this;
		}

		public Criteria andUserCodeLike(String value) {
			addCriterion("user_code like", value, "userCode");
			return (Criteria) this;
		}

		public Criteria andUserCodeNotLike(String value) {
			addCriterion("user_code not like", value, "userCode");
			return (Criteria) this;
		}

		public Criteria andUserCodeIn(List<String> values) {
			addCriterion("user_code in", values, "userCode");
			return (Criteria) this;
		}

		public Criteria andUserCodeNotIn(List<String> values) {
			addCriterion("user_code not in", values, "userCode");
			return (Criteria) this;
		}

		public Criteria andUserCodeBetween(String value1, String value2) {
			addCriterion("user_code between", value1, value2, "userCode");
			return (Criteria) this;
		}

		public Criteria andUserCodeNotBetween(String value1, String value2) {
			addCriterion("user_code not between", value1, value2, "userCode");
			return (Criteria) this;
		}

		public Criteria andUserNameIsNull() {
			addCriterion("user_name is null");
			return (Criteria) this;
		}

		public Criteria andUserNameIsNotNull() {
			addCriterion("user_name is not null");
			return (Criteria) this;
		}

		public Criteria andUserNameEqualTo(String value) {
			addCriterion("user_name =", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotEqualTo(String value) {
			addCriterion("user_name <>", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameGreaterThan(String value) {
			addCriterion("user_name >", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameGreaterThanOrEqualTo(String value) {
			addCriterion("user_name >=", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameLessThan(String value) {
			addCriterion("user_name <", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameLessThanOrEqualTo(String value) {
			addCriterion("user_name <=", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameLike(String value) {
			addCriterion("user_name like", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotLike(String value) {
			addCriterion("user_name not like", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameIn(List<String> values) {
			addCriterion("user_name in", values, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotIn(List<String> values) {
			addCriterion("user_name not in", values, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameBetween(String value1, String value2) {
			addCriterion("user_name between", value1, value2, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotBetween(String value1, String value2) {
			addCriterion("user_name not between", value1, value2, "userName");
			return (Criteria) this;
		}

		public Criteria andIsDelIsNull() {
			addCriterion("is_del is null");
			return (Criteria) this;
		}

		public Criteria andIsDelIsNotNull() {
			addCriterion("is_del is not null");
			return (Criteria) this;
		}

		public Criteria andIsDelEqualTo(Boolean value) {
			addCriterion("is_del =", value, "isDel");
			return (Criteria) this;
		}

		public Criteria andIsDelNotEqualTo(Boolean value) {
			addCriterion("is_del <>", value, "isDel");
			return (Criteria) this;
		}

		public Criteria andIsDelGreaterThan(Boolean value) {
			addCriterion("is_del >", value, "isDel");
			return (Criteria) this;
		}

		public Criteria andIsDelGreaterThanOrEqualTo(Boolean value) {
			addCriterion("is_del >=", value, "isDel");
			return (Criteria) this;
		}

		public Criteria andIsDelLessThan(Boolean value) {
			addCriterion("is_del <", value, "isDel");
			return (Criteria) this;
		}

		public Criteria andIsDelLessThanOrEqualTo(Boolean value) {
			addCriterion("is_del <=", value, "isDel");
			return (Criteria) this;
		}

		public Criteria andIsDelIn(List<Boolean> values) {
			addCriterion("is_del in", values, "isDel");
			return (Criteria) this;
		}

		public Criteria andIsDelNotIn(List<Boolean> values) {
			addCriterion("is_del not in", values, "isDel");
			return (Criteria) this;
		}

		public Criteria andIsDelBetween(Boolean value1, Boolean value2) {
			addCriterion("is_del between", value1, value2, "isDel");
			return (Criteria) this;
		}

		public Criteria andIsDelNotBetween(Boolean value1, Boolean value2) {
			addCriterion("is_del not between", value1, value2, "isDel");
			return (Criteria) this;
		}

		public Criteria andOperTypeIsNull() {
			addCriterion("oper_type is null");
			return (Criteria) this;
		}

		public Criteria andOperTypeIsNotNull() {
			addCriterion("oper_type is not null");
			return (Criteria) this;
		}

		public Criteria andOperTypeEqualTo(String value) {
			addCriterion("oper_type =", value, "operType");
			return (Criteria) this;
		}

		public Criteria andOperTypeNotEqualTo(String value) {
			addCriterion("oper_type <>", value, "operType");
			return (Criteria) this;
		}

		public Criteria andOperTypeGreaterThan(String value) {
			addCriterion("oper_type >", value, "operType");
			return (Criteria) this;
		}

		public Criteria andOperTypeGreaterThanOrEqualTo(String value) {
			addCriterion("oper_type >=", value, "operType");
			return (Criteria) this;
		}

		public Criteria andOperTypeLessThan(String value) {
			addCriterion("oper_type <", value, "operType");
			return (Criteria) this;
		}

		public Criteria andOperTypeLessThanOrEqualTo(String value) {
			addCriterion("oper_type <=", value, "operType");
			return (Criteria) this;
		}

		public Criteria andOperTypeLike(String value) {
			addCriterion("oper_type like", value, "operType");
			return (Criteria) this;
		}

		public Criteria andOperTypeNotLike(String value) {
			addCriterion("oper_type not like", value, "operType");
			return (Criteria) this;
		}

		public Criteria andOperTypeIn(List<String> values) {
			addCriterion("oper_type in", values, "operType");
			return (Criteria) this;
		}

		public Criteria andOperTypeNotIn(List<String> values) {
			addCriterion("oper_type not in", values, "operType");
			return (Criteria) this;
		}

		public Criteria andOperTypeBetween(String value1, String value2) {
			addCriterion("oper_type between", value1, value2, "operType");
			return (Criteria) this;
		}

		public Criteria andOperTypeNotBetween(String value1, String value2) {
			addCriterion("oper_type not between", value1, value2, "operType");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIsNull() {
			addCriterion("create_time is null");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIsNotNull() {
			addCriterion("create_time is not null");
			return (Criteria) this;
		}

		public Criteria andCreateTimeEqualTo(Date value) {
			addCriterion("create_time =", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotEqualTo(Date value) {
			addCriterion("create_time <>", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeGreaterThan(Date value) {
			addCriterion("create_time >", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("create_time >=", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeLessThan(Date value) {
			addCriterion("create_time <", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
			addCriterion("create_time <=", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIn(List<Date> values) {
			addCriterion("create_time in", values, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotIn(List<Date> values) {
			addCriterion("create_time not in", values, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeBetween(Date value1, Date value2) {
			addCriterion("create_time between", value1, value2, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
			addCriterion("create_time not between", value1, value2, "createTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIsNull() {
			addCriterion("update_time is null");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIsNotNull() {
			addCriterion("update_time is not null");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeEqualTo(Date value) {
			addCriterion("update_time =", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotEqualTo(Date value) {
			addCriterion("update_time <>", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeGreaterThan(Date value) {
			addCriterion("update_time >", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("update_time >=", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeLessThan(Date value) {
			addCriterion("update_time <", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
			addCriterion("update_time <=", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIn(List<Date> values) {
			addCriterion("update_time in", values, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotIn(List<Date> values) {
			addCriterion("update_time not in", values, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeBetween(Date value1, Date value2) {
			addCriterion("update_time between", value1, value2, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
			addCriterion("update_time not between", value1, value2, "updateTime");
			return (Criteria) this;
		}
	}

	public static class Criteria extends GeneratedCriteria {
		protected Criteria() {
			super();
		}
	}

	public static class Criterion {
		private String condition;

		private Object value;

		private Object secondValue;

		private boolean noValue;

		private boolean singleValue;

		private boolean betweenValue;

		private boolean listValue;

		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}
}