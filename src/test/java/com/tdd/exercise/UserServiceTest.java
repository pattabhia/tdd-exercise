package com.tdd.exercise;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import mockito.demo.UserManager;
import mockito.demo.UserService;

public class UserServiceTest {

	@Test
	public void testThenReturn() {
		UserManager manager = mock(UserManager.class);
		when(manager.getUserCount()).thenReturn(50);
		UserService userService = new UserService(manager);
		assertEquals(50, userService.getUserCount());
	}
	
	
	@Test
	public void testThenThrow() {
		UserManager manager = mock(UserManager.class);
		when(manager.getUserCount()).thenThrow(new RuntimeException());
		UserService userService = new UserService(manager);
		assertEquals(-1,userService.getUserCount());
	}
	
	
	@Test
	public void testThenAnswer() {
		UserManager manager = mock(UserManager.class);
		when(manager.getUserCount()).then(new Answer<Integer>() {
			public int count = 0;
			public Integer answer(InvocationOnMock invocation) throws Throwable {
				return ++count;
			}
			
		}); 
		UserService userService = new UserService(manager);
		assertEquals(1,userService.getUserCount());
		assertEquals(2,userService.getUserCount());
		assertEquals(3,userService.getUserCount());
		assertEquals(4,userService.getUserCount());
	}
	
	@Test
	public void testTestSave() {
		UserManager manager = mock(UserManager.class);
		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
		UserService userService = new UserService(manager);
		userService.save("hamlet");
		verify(manager,times(1)).save("hamlet");
		verify(manager,times(0)).getUserCount();
		verify(manager,times(1)).save(argumentCaptor.capture());
		assertEquals("hamlet", argumentCaptor.getValue());
	}
}
