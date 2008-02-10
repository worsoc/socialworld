package org.socialworld.core.test;

import junit.framework.TestCase;

import org.socialworld.core.ActionHandler;
import org.socialworld.objects.Human;

public class ActionHandlerTest extends TestCase {

	private ActionHandler actionHandler;

	public ActionHandlerTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		actionHandler = new ActionHandler(new Human());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testDoAction() {
		fail("Not yet implemented");
	}

	public void testDoActualAction() {
		assertTrue(true);
	}

	public void testInsertAction() {
		fail("Not yet implemented");
	}

	public void testNewAction() {
		fail("Not yet implemented");
	}

	public void testChangeAction() {
		fail("Not yet implemented");
	}

}
