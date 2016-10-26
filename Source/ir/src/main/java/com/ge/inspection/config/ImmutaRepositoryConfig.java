package com.ge.inspection.config;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "immutaEntityManagerFactory",
        transactionManagerRef = "immutaTransactionManager",
        basePackages = {"com.ge.inspection.ir.repository.immuta"})
public class ImmutaRepositoryConfig {
    @Autowired
    JpaVendorAdapter jpaVendorAdapter;

    @Value("${immuta.datasource.url}")
    private String databaseUrl;

    @Value("${immuta.datasource.username}")
    private String username;

    @Value("${immuta.datasource.password}")
    private String password;

    @Value("${immuta.dataource.driverClassName}")
    private String driverClassName;

    @Value("${immuta.datasource.hibernate.dialect}")
    private String dialect;

    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(databaseUrl, username, password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    @Bean(name = "immutaEntityManager")
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

    @Bean(name = "immutaEntityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan("com.ge.inspection.ir.domain.immuta");   
        emf.setPersistenceUnitName("immutaPersistenceUnit");
        emf.setJpaProperties(properties);
        emf.afterPropertiesSet();
        return emf.getObject();
    }

    @Bean(name = "immutaTransactionManager")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }
}