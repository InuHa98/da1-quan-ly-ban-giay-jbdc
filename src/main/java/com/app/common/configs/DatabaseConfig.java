package com.app.common.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories({"com.app.repositories", "com.app.core"})
@ComponentScan(value = "com.app")
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySource("classpath:application.properties")
public class DatabaseConfig {

    @Value("${spring.jpa.packagesToScan}")
    private String packagesToScan;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String hibernateDdlAuto;

    @Value("${spring.jpa.hibernate.dialect}")
    private String hibernateDialect;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.driverClassName);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setUrl(this.url);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(this.dataSource());
        entityManager.setPackagesToScan(this.packagesToScan);
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(adapter);
        entityManager.setJpaProperties(this.additionalProperties());
        return entityManager;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", this.hibernateDdlAuto);
        properties.setProperty("hibernate.dialect", this.hibernateDialect);
        return properties;
    }
}
