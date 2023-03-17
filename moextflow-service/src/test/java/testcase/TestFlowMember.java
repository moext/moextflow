package testcase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.junit.jupiter.api.Test;

import com.moext.flowservice.flow.model.FlowUser;

public class TestFlowMember {

	@Test
	public void testInstanceOf() {
		User user = new UserEntityImpl();
		user.setId("peng");
		user.setDisplayName("测试用户");
		user.setEmail("peng@moext.com");
		
		FlowUser flowUser = FlowUser.instancesOf(user);
		assertEquals(user.getId(), flowUser.getUserCode());
		assertEquals(user.getDisplayName(), flowUser.getDisplayName());
		assertEquals(user.getEmail(), flowUser.getEmail());
		
		User user2 = new UserEntityImpl();
		FlowUser flowUser2 = FlowUser.instancesOf(user2);
		assertNull(flowUser2.getUserCode());
		assertNull(flowUser2.getDisplayName());
		assertNull(flowUser2.getEmail());
		
	}
	
	@Test
	public void testInstanceOfNull() {
		User user = null;
		FlowUser flowUser = FlowUser.instancesOf(user);
		assertNull(flowUser);
	}
	
	@Test
	public void testInstanceOfList() {
		List<User> list = new ArrayList<User>();
		User user = new UserEntityImpl();
		user.setId("peng");
		user.setDisplayName("测试用户");
		user.setEmail("peng@moext.com");
		list.add(user);
		
		list.add(null);
		
		User user2 = new UserEntityImpl();
		list.add(user2);
		
		List<FlowUser> memberList = FlowUser.instancesOf(list);
		assertEquals(2, memberList.size());
		assertNull(memberList.get(1).getUserCode());
		assertNull(memberList.get(1).getDisplayName());
		assertNull(memberList.get(1).getEmail());
		
	}
	
	@Test
	public void testInstanceOfListNull() {
		List<User> list = null;
		List<FlowUser> memberList = FlowUser.instancesOf(list);
		assertNull(memberList);
	}
	
	@Test
	public void testInstanceOfListBlank() {
		List<User> list = new ArrayList<User>();
		List<FlowUser> memberList = FlowUser.instancesOf(list);
		assertEquals(0, memberList.size());
	}
}
