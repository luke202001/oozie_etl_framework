package com.altarmoss.oozie.client.utils;

public class WorkflowConfiguration {

	private String workUnitName;
	private String userName;
	private String password;
	private String targetLevel;
	private String[] hsqlFiles;
	private String propFile;
	private String srcFile;
	private String enabled;
	private String targetDB;
	private String workGroupName;
	private String frameworkDB;
	private String emailNotificationFile;

	public String getWorkUnitName() {
		return workUnitName;
	}
	public void setWorkUnitName(String workUnitName) {
		this.workUnitName = workUnitName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTargetLevel() {
		return targetLevel;
	}
	public void setTargetLevel(String targetLevel) {
		this.targetLevel = targetLevel;
	}
	public String[] getHsqlFiles() {
		return hsqlFiles;
	}
	public void setHsqlFiles(String[] hsqlFiles) {
		this.hsqlFiles = hsqlFiles;
	}
	public String getPropFile() {
		return propFile;
	}
	public void setPropFile(String propFile) {
		this.propFile = propFile;
	}
	public String getSrcFile() {
		return srcFile;
	}
	public void setSrcFile(String srcFile) {
		this.srcFile = srcFile;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getTargetDB() {
		return targetDB;
	}
	public void setTargetDB(String targetDB) {
		this.targetDB = targetDB;
	}
	public String getWorkGroupName() {
		return workGroupName;
	}
	public void setWorkGroupName(String workGroupName) {
		this.workGroupName = workGroupName;
	}
	public String getFrameworkDB() {
		return frameworkDB;
	}
	public void setFrameworkDB(String frameworkDB) {
		this.frameworkDB = frameworkDB;
	}
	public String getEmailNotificationFile() {
		return emailNotificationFile;
	}
	public void setEmailNotificationFile(String emailNotificationFile) {
		this.emailNotificationFile = emailNotificationFile;
	}
}
