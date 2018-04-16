package com.altarmoss.oozie.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.altarmoss.oozie.client.utils.TestUtil;
import com.altarmoss.oozie.client.utils.WorkflowConfiguration;

public class OozieWorkUnitTest {
	private OozieWorkUnit oozieWorkUnit;
	private Method parseParameters;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void setUp() throws NoSuchMethodException {
		oozieWorkUnit = new OozieWorkUnit();
		parseParameters = TestUtil.getMethod(OozieWorkUnit.class,
										   "parseParameters",
										   String[].class);
	}

	@Test
	public void testParseParametersShouldThrowExceptionWhenTheNumberOfParametersIsSmallerThan3() 
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
		expectedEx.expect(InvocationTargetException.class);
//		expectedEx.expectMessage(EXPECTED_INCORRECT_NUM_OF_PARAMS_MSG);

		String[] args = new String[] {"arg1", "arg2"};
		parseParameters.invoke(oozieWorkUnit, new Object[] {args});
	}

	@Test
	public void testParseParametersWithUserNameAndPassword() 
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		
		String args = "myWorkUnit -u testUser:testPwd";
		WorkflowConfiguration conf = (WorkflowConfiguration)parseParameters.invoke(oozieWorkUnit, new Object[] {args.split(" ")});
		assertEquals(conf.getWorkUnitName(), "MYWORKUNIT");
		assertEquals(conf.getUserName(), "testUser");
		assertEquals(conf.getPassword(), "testPwd");
	}
	
	@Test
	public void testParseParametersWithUserNameOnly() 
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		
		String args = "myWorkUnit -u testUser";
		WorkflowConfiguration conf = (WorkflowConfiguration)parseParameters.invoke(oozieWorkUnit, new Object[] {args.split(" ")});
		assertEquals(conf.getWorkUnitName(), "MYWORKUNIT");
		assertEquals(conf.getUserName(), "testUser");
		assertNull(conf.getPassword());
	}
	
	@Test
	public void testParseParametersFull() 
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		
		String args = "myWorkUnit -u testUser " + 
					  "-level LEVEL1 " + 
					  "-hsql /landing_zone/staging/scripts/oozie/samples/test.sql " + 
					  "-prop /configure/samples/job_properties";
		WorkflowConfiguration conf = (WorkflowConfiguration)parseParameters.invoke(oozieWorkUnit, new Object[] {args.split(" ")});
		assertEquals(conf.getWorkUnitName(), "MYWORKUNIT");
		assertEquals(conf.getUserName(), "testUser");
		assertNull(conf.getPassword());

	}
}
