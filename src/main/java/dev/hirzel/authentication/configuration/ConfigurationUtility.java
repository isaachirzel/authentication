package dev.hirzel.authentication.configuration;

import java.io.File;

public class ConfigurationUtility {
	public static String getConfigurationFilepath() throws Exception {
		var rootDir = getOsSpecificConfigurationRootDir();
		var path = rootDir + File.separator + "config.json";

		return path;
	}

	private static String getOsSpecificConfigurationRootDir() throws Exception {
		var osName = System.getProperty("os.name").toLowerCase();

		switch (osName) {
			case "linux":
				return System.getProperty("user.home") + "/.config/hirzel/authentication";
			case "windows":
				return System.getenv("APPDATA") + "\\hirzel\\authentication";
			default:
				break;
		}

		throw new Exception(String.format("OS `%s` is not supported.", osName));
	}
}
