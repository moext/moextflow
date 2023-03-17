package testcase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.moext.flowservice.data.gen.model.TodoTask;
import com.moext.flowservice.data.gen.model.TodoTaskExample;
import com.moext.flowservice.service.TodoTaskService;

@ExtendWith(MockitoExtension.class)
public class TestTodoService {

//	@InjectMocks
//	private TodoTaskService todoTaskService;
//	
//	@Test
//	public void testGetOne() {
//		try {
//			todoTaskService.getOne(null);
//		}catch(IllegalArgumentException e) {
//			Assertions.assertTrue(true);
//		}
//		
//		List<TodoTask> list = new ArrayList<TodoTask>();
//		try {
//			todoTaskService.getOne(list);
//		}catch(IllegalArgumentException e) {
//			Assertions.assertTrue(true);
//		}
//		
//		TodoTask task1 = new TodoTask();
//		task1.setId(1L);
//		list.add(task1);
//		Assertions.assertEquals(1L, todoTaskService.getOne(list).getId());
//		
//		TodoTask task2 = new TodoTask();
//		task2.setId(2L);
//		list.add(task2);
//		Assertions.assertEquals(1L, todoTaskService.getOne(list).getId());
//	}
//	
//	@Test
//	public void testToExampleByTodoId() {
//		TodoTaskExample example = todoTaskService.toExampleByTodoId("123");
//		assertEquals("todo_id =", example.getOredCriteria().get(0).getAllCriteria().get(0).getCondition());
//		assertEquals("123", example.getOredCriteria().get(0).getAllCriteria().get(0).getValue());
//		
//		assertEquals("is_del =", example.getOredCriteria().get(0).getAllCriteria().get(1).getCondition());
//		assertEquals(false, example.getOredCriteria().get(0).getAllCriteria().get(1).getValue());
//	}
//	
//	@Test
//	public void testToExampleByByProcIdAndTaskId() {
//		TodoTaskExample example = todoTaskService.toExampleByByProcIdAndTaskId("1234", "5678");
//		assertEquals("process_instance_id =", example.getOredCriteria().get(0).getAllCriteria().get(0).getCondition());
//		assertEquals("1234", example.getOredCriteria().get(0).getAllCriteria().get(0).getValue());
//		
//		assertEquals("task_id =", example.getOredCriteria().get(0).getAllCriteria().get(1).getCondition());
//		assertEquals("5678", example.getOredCriteria().get(0).getAllCriteria().get(1).getValue());
//		
//		assertEquals("is_del =", example.getOredCriteria().get(0).getAllCriteria().get(2).getCondition());
//		assertEquals(false, example.getOredCriteria().get(0).getAllCriteria().get(2).getValue());
//	}
}
