package com.altarmoss.oozie.client;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.altarmoss.oozie.client.utils.HiveConfig;
import com.altarmoss.oozie.client.utils.TestUtil;
import com.altarmoss.oozie.client.utils.WorkflowConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {AppConfig.class})
public class OozieWorkUnitTest {
	private OozieWorkUnit oozieWorkUnit;
	private Method parseParameters;
	@Autowired
	private HiveConfig hiveConfig;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void setUp() throws NoSuchMethodException {
		oozieWorkUnit = new OozieWorkUnit();
		parseParameters = TestUtil.getMethod(OozieWorkUnit.class,
										   "parseParameters",
										   String[].class);
		System.out.println(hiveConfig.getDbUrl());
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
	public void testParseParametersWithDifferentTargetLevels() 
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		
		String args = "myWorkUnit -u testUser -level LEVEL1";
		WorkflowConfiguration conf = (WorkflowConfiguration)parseParameters.invoke(oozieWorkUnit, new Object[] {args.split(" ")});
		assertEquals("HDW_L1", conf.getTargetLevel());
		
		args = "myWorkUnit -u testUser -level LEVEL2";
		conf = (WorkflowConfiguration)parseParameters.invoke(oozieWorkUnit, new Object[] {args.split(" ")});
		assertEquals("HDW_L2", conf.getTargetLevel());
		
		args = "myWorkUnit -u testUser -level LEVEL3";
		conf = (WorkflowConfiguration)parseParameters.invoke(oozieWorkUnit, new Object[] {args.split(" ")});
		assertEquals("HDW_L3", conf.getTargetLevel());
	}
	
	@Test
	public void testParseParametersWithInvalidTargetLevel() 
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		expectedEx.expect(InvocationTargetException.class);

		String args = "myWorkUnit -u testUser -level LEVEL4";
		parseParameters.invoke(oozieWorkUnit, new Object[] {args.split(" ")});
	}
	
	@Test
	public void testParseParametersWithDifferentEnableValues() 
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		
		String args = "myWorkUnit -u testUser -enable Y";
		WorkflowConfiguration conf = (WorkflowConfiguration)parseParameters.invoke(oozieWorkUnit, new Object[] {args.split(" ")});
		assertEquals("Y", conf.getEnabled());
		
		args = "myWorkUnit -u testUser -enable N";
		conf = (WorkflowConfiguration)parseParameters.invoke(oozieWorkUnit, new Object[] {args.split(" ")});
		assertEquals("N", conf.getEnabled());
	}
	
	@Test
	public void testParseParametersWithInvalidEnableValue() 
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		expectedEx.expect(InvocationTargetException.class);

		String args = "myWorkUnit -u testUser -enable O";
		parseParameters.invoke(oozieWorkUnit, new Object[] {args.split(" ")});
	}
	
	@Test
	public void testParseParametersFull() 
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		
		String args = "myWorkUnit -u testUser " + 
					  "-level LEVEL1 " + 
					  "-group TestGroup " +
					  "-enable yes " +
					  "-hsql /landing_zone/staging/scripts/oozie/samples/test.sql,test2.sql " + 
					  "-prop /configure/samples/job_properties " +
					  "-src mySrc " +
					  "-emailnotif myEmail " +
					  "-frameworkDb myFrameworkDB";
		WorkflowConfiguration conf = (WorkflowConfiguration)parseParameters.invoke(oozieWorkUnit, new Object[] {args.split(" ")});
		assertEquals("MYWORKUNIT", conf.getWorkUnitName());
		assertEquals("testUser", conf.getUserName());
		assertNull(conf.getPassword());
		assertEquals("HDW_L1", conf.getTargetLevel());
		assertEquals("TestGroup", conf.getWorkGroupName());
		assertEquals("yes", conf.getEnabled());
		assertArrayEquals(new String[] {"/landing_zone/staging/scripts/oozie/samples/test.sql", "test2.sql"}, conf.getHsqlFiles());
		assertEquals("/configure/samples/job_properties", conf.getPropFile());
		assertEquals("mySrc", conf.getSrcFile());
		assertEquals("myEmail", conf.getEmailNotificationFile());
		assertEquals("myFrameworkDB", conf.getFrameworkDB());
	}
	
	@Test
	public void testExecute() {
		
	}
}
