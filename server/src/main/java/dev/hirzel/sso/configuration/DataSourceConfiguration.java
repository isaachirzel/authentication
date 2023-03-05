package dev.hirzel.sso.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Bean
    public DataSource getDataSource() throws Exception {
        var appConfig = ApplicationConfiguration.getInstance();
        var builder = DataSourceBuilder
            .create()
            .url("jdbc:" + appConfig.getDatabaseUrl())
            .username(appConfig.getDatabaseUsername())
            .password(appConfig.getDatabasePassword());

        return builder.build();
    }
}
