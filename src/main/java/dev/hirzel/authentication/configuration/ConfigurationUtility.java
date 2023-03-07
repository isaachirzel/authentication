package dev.hirzel.authentication.configuration;

import java.io.File;

public class ConfigurationUtility {
	public static String getConfigurationFilepath() throws Exception {
		var rootDir = getOsSpecificConfigurationRootDir();
		var path = rootDir + File.separator + "authentication.json";

		return path;
	}

	private static String getOsSpecificConfigurationRootDir() throws Exception {
		var osName = System.getProperty("os.name").toLowerCase();

		switch (osName) {
			case "linux":
				return System.getProperty("user.home") + File.separator + ".config" + File.separator + "hirzel";
			case "windows":
				return System.getenv("APPDATA") + File.separator + "hirzel";
			default:
				break;
		}

		throw new Exception(String.format("OS `%s` is not supported.", osName));
	}
}
