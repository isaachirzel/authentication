package dev.hirzel.sso.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

public class ApplicationConfiguration {
	private static ApplicationConfiguration instance = null;
	private String databaseUrl;
	private String databaseUsername;
	private String databasePassword;

	private static ApplicationConfiguration readFromFile() throws Exception {
		var path = Path.of(ConfigurationUtility.getConfigurationFilepath());
		var configText = Files.readString(path);
		var mapper = new ObjectMapper();
		var config = mapper.readValue(configText, ApplicationConfiguration.class);

		if (config.getDatabaseUrl() == null)
			throw new Exception("Configuration field `databaseUrl` is required.");

		return config;
	}

	public static ApplicationConfiguration refresh() throws Exception {
		instance = readFromFile();

		return instance;
	}

	public static ApplicationConfiguration getInstance() throws Exception {
		if (instance == null)
			instance = readFromFile();

		return instance;
	}

	public String getDatabaseUrl() {
		return databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	public String getDatabaseUsername() {
		return databaseUsername;
	}

	public void setDatabaseUsername(String databaseUsername) {
		this.databaseUsername = databaseUsername;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}

	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}
}
