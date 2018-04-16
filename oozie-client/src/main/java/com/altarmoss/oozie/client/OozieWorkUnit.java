package com.altarmoss.oozie.client;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.altarmoss.oozie.action.InitialWorkflowAction;
import com.altarmoss.oozie.client.utils.WorkflowConfiguration;

public class OozieWorkUnit {

	private static Logger logger = LoggerFactory.getLogger(InitialWorkflowAction.class);
	
	private static String KEY_FILE = "keyFile.txt";
	
	protected static final SimpleDateFormat DF_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public void execute(WorkflowConfiguration conf) {
		
	}
	
	private static WorkflowConfiguration parseParameters(String[] args) {
		if (args.length < 3) {
			String errorMessage = "Incorrect number of parameters. " +
								  "Usage: [Work Unit Name]";
			throw new RuntimeException(errorMessage);
		}
		WorkflowConfiguration result = new WorkflowConfiguration();
		result.setWorkUnitName(args[0].toUpperCase());
		
		for (int i = 1; i < args.length; i++) {
			if (args[i].equals("-u")) {
				i++;
				String[] userTokens = args[i].split(":");
				result.setUserName(userTokens[0]);
				if (userTokens.length > 1) {
					result.setPassword(userTokens[1]);
				}
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		WorkflowConfiguration conf = parseParameters(args);
		
		OozieWorkUnit action = new OozieWorkUnit();
		action.execute(conf);
	}
}
