package com.intuit.vivek.config;

import com.intuit.vivek.services.ProductService;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by vvenugopal on 6/16/16.
 */
@ComponentScan(basePackages = "com.intuit.vivek",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = DbConfig.class)})
@Configuration
@EnableJpaRepositories(basePackages = {"com.intuit.vivek.persistence.dao"})
@EnableTransactionManagement
public class TestDbConfig {

    @Value("${db.url}")
    private String dbUrl;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.driverClass}")
    private String driverClass;


    @Value("${db.schemas.location}")
    private String schemaLocation;


    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.hbm2ddl.auto}")
    private String ddlAction;

//    @Value("${hibernate.show_sql}")
//    private boolean logSql;
//
//    @Value("${hibernate.format_sql}")
//    private boolean prettyPrintSql;

    @Bean
    public DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:~/intuit;MODE=MySQL;DB_CLOSE_DELAY=-1");
        return dataSource;
    }

    @Bean(name = "flyway", initMethod = "migrate")
    public Flyway getFlyway() {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setDataSource(getDataSource());
        flyway.setLocations(schemaLocation);
        System.out.println("Created tables");
        return flyway;
    }

    @Bean(name = "entityManagerFactory") @DependsOn("flyway")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(getDataSource());
        entityManagerFactoryBean.setPackagesToScan("com.intuit.vivek.persistence.model");
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", hibernateDialect);
        jpaProperties.put("hibernate.hbm2ddl.auto", ddlAction);
        jpaProperties.put("hibernate.id.new_generator_mappings", true);
        jpaProperties.put("hibernate.show_sql", true);
        jpaProperties.put("hibernate.format_sql", true);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(getEntityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public ProductService productService() {
        return new ProductService();
    }

}
