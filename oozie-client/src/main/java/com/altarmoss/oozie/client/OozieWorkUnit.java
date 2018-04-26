package com.altarmoss.oozie.client;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import com.altarmoss.oozie.action.InitialWorkflowAction;
import com.altarmoss.oozie.client.utils.HiveConfig;
import com.altarmoss.oozie.client.utils.WorkflowConfiguration;
import com.altarmoss.oozie.database.IConfigDataHandler;

@ComponentScan
public class OozieWorkUnit {

	private static Logger logger = LoggerFactory.getLogger(InitialWorkflowAction.class);
	private static String KEY_FILE = "keyFile.txt";
	protected static final SimpleDateFormat DF_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private IConfigDataHandler dataHandler;
	@Autowired
	private HiveConfig hiveConfig;
	
	public void execute(WorkflowConfiguration conf) {
		initDataHandler();
//		getHiveHandler();
//		loadWorkUnitProperties();
//		getWorkUnit();
//		executeWorkUnit();
//		executeProp0();
//		executeSource();
//		executeEmailNotification();
//		executeHsql();
	}
	
	private static WorkflowConfiguration parseParameters(String[] args) {
		if (args.length < 3) {
			String errorMessage = "Incorrect number of parameters. " +
								  "Usage: [Work Unit Name]" +
								  " -u [Username]" +
								  " -keyfile [keyFile]" +
								  " -level [Target Data Level (LEVEL1, LEVEL2, LEVEL3)" +
								  " -group [Work Group name]" +
								  " -enable [Enabled (Y or N)), default Y]" +
								  " -hsql [HSQL File]" +
								  " -prop [Property File]" +
								  " -src [Source File]" +
								  " -frameworkDb [Framework Database]" +
								  " -emailnotif [Email Notification File]";
			throw new RuntimeException(errorMessage);
		}
		WorkflowConfiguration result = new WorkflowConfiguration();
		result.setWorkUnitName(args[0].toUpperCase());
		
		for (int i = 1; i < args.length; i++) {
			switch (args[i]) {
				case "-u":
					String[] userTokens = args[++i].split(":");
					result.setUserName(userTokens[0]);
					if (userTokens.length > 1) {
						result.setPassword(userTokens[1]);
					}
					break;
				case "-level":
					String targetLevel = args[++i];
					switch (targetLevel) {
					case "LEVEL1":
						result.setTargetLevel("HDW_L1");
						break;
					case "LEVEL2":
						result.setTargetLevel("HDW_L2");
						break;
					case "LEVEL3":
						result.setTargetLevel("HDW_L3");
						break;
					default:
						throw new RuntimeException("Invalid target data level: " + targetLevel + ".");
					}
					break;
				case "-group":
					result.setWorkGroupName(args[++i]);
					break;
				case "-enable":
					result.setEnabled(args[++i]);
					if (!result.getEnabled().equals("Y") &&
						!result.getEnabled().equals("N")) {
						throw new RuntimeException("Invalid enable value: " + result.getEnabled() + ".");
					}
					break;
				case "-hsql":
					String[] hsqlFiles = args[++i].split(",");
					result.setHsqlFiles(hsqlFiles);
					break;
				case "-prop":
					result.setPropFile(args[++i]);
					break;
				case "-src":
					result.setSrcFile(args[++i]);
					break;
				case "-emailnotif":
					result.setEmailNotificationFile(args[++i]);
					break;
				case "-frameworkDb":
					result.setFrameworkDB(args[++i]);
					break;
			}
		}
		
		return result;
	}
	
	private void initDataHandler() {
	}
	
	public static void main(String[] args) throws Exception {
		WorkflowConfiguration conf = parseParameters(args);
		
		OozieWorkUnit action = new OozieWorkUnit();
		action.execute(conf);
	}
}
