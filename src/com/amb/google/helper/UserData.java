package com.amb.google.helper;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;

public class UserData {

	private Properties userProperties = new Properties();
	private String filePath = "user.properties";
	private String user, password;
	
	public UserData() {
		loadUserProperites();
	}
	
	public UserData(String filePath) {
		this.filePath = filePath;
		loadUserProperites();
	}

	public String getUser() {
		return user;
	}
	
	public String getPassword() {
		return password;
	}
	
	private void loadUserProperites() {
		try {
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(filePath));
			userProperties.load(stream);
			stream.close();
			user = userProperties.getProperty("user");
			password = userProperties.getProperty("password");
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
}
