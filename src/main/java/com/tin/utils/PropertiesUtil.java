package com.tin.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {

	private PropertiesUtil() {
	}

	private static class PropertiesUtil_ {

		private static final Properties properties = getProperties();

		private static Properties getProperties() {
			Properties prop = new Properties();

			String file = System.getProperty("user.dir") + "\\config\\jdbc.properties";
			InputStreamReader is = null;
			try {
				// is = new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(file), "UTF-8");

				// ResourceBundle.clearCache();
				is = new InputStreamReader(new FileInputStream(file), "utf-8");
				prop.load(is);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return prop;
		}

	}

	public static Properties getProperties() {
		return PropertiesUtil_.properties;
	}

	public static Object get(String key) {
		return getProperties().get(key);
	}

	public static String getProperty(String key) {
		return getProperties().getProperty(key);
	}

	public static String getProperty(String key, String defaultValue) {
		return getProperties().getProperty(key, defaultValue);
	}

}
