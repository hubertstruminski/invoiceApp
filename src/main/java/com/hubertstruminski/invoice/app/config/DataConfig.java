package com.hubertstruminski.invoice.app.config;

import java.util.Objects;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.hubertstruminski.invoice.app.repository")
@PropertySource("classpath:application.properties")
public class DataConfig {

    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_DIALECT = "hibernate.dialect";

    Environment environment;

    @Autowired
    public DataConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean lfb = new LocalContainerEntityManagerFactoryBean();
        lfb.setDataSource(dataSource());
        lfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        lfb.setPackagesToScan("com.hubertstruminski.invoice.app.model");
        lfb.setJpaProperties(hibernateProps());
        return lfb;
    }

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(environment.getProperty(PROPERTY_URL));
        ds.setDriverClassName(Objects.requireNonNull(environment.getProperty(PROPERTY_DRIVER)));
        return ds;
    }

    Properties hibernateProps() {
        Properties properties = new Properties();
        properties.setProperty(PROPERTY_DIALECT, environment.getProperty(PROPERTY_DIALECT));
        properties.setProperty(PROPERTY_SHOW_SQL, environment.getProperty(PROPERTY_SHOW_SQL));
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        return properties;
    }

    @Bean
    JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
