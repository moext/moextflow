package testcase;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

import com.moext.flowservice.data.gen.model.TodoTaskExample;
import com.moext.flowservice.dto.req.TodoListCountReq;

public class TestTodoListCountReq {

	//测试常用赋值
	@Test
	public void testGenExampleNormal() throws ParseException {
		TodoListCountReq req = new TodoListCountReq();
		req.setReceiveUserCode("peng");
		req.setStatus("TODO");
		req.setTaskName("我的");
		String beginDateStr = "2021-11-01 01:02:03";
		String endDateStr = "2022-10-31 23:59:59";
		Date beginDate = DateUtils.parseDate(beginDateStr, "yyyy-MM-dd HH:mm:ss");
		Date endDate = DateUtils.parseDate(endDateStr, "yyyy-MM-dd HH:mm:ss");
		req.setTaskCreateTimeBegin(beginDate);
		req.setTaskCreateTimeEnd(endDate);
		
		TodoTaskExample example = req.genExample();
		assertEquals("receive_user_code =", example.getOredCriteria().get(0).getAllCriteria().get(0).getCondition());
		assertEquals("peng", example.getOredCriteria().get(0).getAllCriteria().get(0).getValue());
		
	
		assertEquals("status =", example.getOredCriteria().get(0).getAllCriteria().get(1).getCondition());
		assertEquals("TODO", example.getOredCriteria().get(0).getAllCriteria().get(1).getValue());
		

		assertEquals("task_name like", example.getOredCriteria().get(0).getAllCriteria().get(2).getCondition());
		assertEquals("我的", example.getOredCriteria().get(0).getAllCriteria().get(2).getValue());
		
		assertEquals("task_create_time >=", example.getOredCriteria().get(0).getAllCriteria().get(3).getCondition());
		assertEquals(beginDate, example.getOredCriteria().get(0).getAllCriteria().get(3).getValue());
		
		assertEquals("task_create_time <=", example.getOredCriteria().get(0).getAllCriteria().get(4).getCondition());
		assertEquals(endDate, example.getOredCriteria().get(0).getAllCriteria().get(4).getValue());
		
		assertEquals("is_del =", example.getOredCriteria().get(0).getAllCriteria().get(5).getCondition());
		assertEquals(false, example.getOredCriteria().get(0).getAllCriteria().get(5).getValue());
	}
	
	//测试边界
	@Test
	public void testGenExampleEdge() {
		TodoListCountReq req = new TodoListCountReq();
		TodoTaskExample example = req.genExample();
		assertEquals(1, example.getOredCriteria().get(0).getAllCriteria().size());
	}
}
