package com.project.splitexp.config.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostgresDBConfiguration {
  
  @Value("${spring.datasource.driverClassName}")
  private String driverClass;

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Value("${spring.datasource.username}")
  private String dbUser;

  @Value("${spring.datasource.password:#{null}}")
  private String dbPassword;

  @Bean
  public DataSource getLocalDataSource() {
    @SuppressWarnings("unchecked")
    DataSourceBuilder<DataSource> dataSourceBuilder = (DataSourceBuilder<DataSource>) DataSourceBuilder.create();
    dataSourceBuilder.driverClassName(driverClass);
    dataSourceBuilder.url(dbUrl);
    dataSourceBuilder.username(dbUser);
    dataSourceBuilder.password(dbPassword);
    return dataSourceBuilder.build();
  }

}
