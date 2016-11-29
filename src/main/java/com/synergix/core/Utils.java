package com.synergix.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * @Author Charlie
 * @Date Oct 3, 2016
 *
 */

public class Utils {

	public static Properties loadProperties(String fileName) {
		Properties configProperties = null;

		FileInputStream in;
		try {
			in = new FileInputStream(Utils.getAbsoluteFilePath(fileName));
			configProperties = new Properties();
			configProperties.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return configProperties;
	}

	public static String getAbsoluteFilePath(String relativeFilePath) {
		String curDir = System.getProperty("user.dir");
		String absolutePath = curDir + Constants.RESOURCE_PATH + relativeFilePath;
		return absolutePath;
	}
	
	public static void pause(long timeInMillis) {
		try {
			Thread.sleep(timeInMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
