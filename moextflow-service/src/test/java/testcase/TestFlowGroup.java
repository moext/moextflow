package testcase;

import static org.junit.jupiter.api.Assertions.*;

import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.junit.jupiter.api.Test;

import com.moext.flowservice.flow.model.FlowGroup;

public class TestFlowGroup {

	@Test
	public void testInstancesOf() {
		org.flowable.idm.api.Group group = new GroupEntityImpl();
		group.setId("role1");
		group.setName("测试组");
		group.setType("t1");
		FlowGroup record = FlowGroup.instancesOf(group);
		assertEquals(group.getId(), record.getId());
		assertEquals(group.getName(), record.getName());
		assertEquals(group.getType(), record.getType());
		
		org.flowable.idm.api.Group group2 = new GroupEntityImpl();
		FlowGroup record2 = FlowGroup.instancesOf(group2);
		assertNull(record2.getId());
		assertNull(record2.getName());
		assertNull(record2.getType());
	}
	
	@Test
	public void testInstancesOfNull() {	
		org.flowable.idm.api.Group group = null;
		FlowGroup record = FlowGroup.instancesOf(group);
		assertNull(record);
	}
}
