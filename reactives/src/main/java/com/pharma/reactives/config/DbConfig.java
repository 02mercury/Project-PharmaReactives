package com.pharma.reactives.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
 * Clasa de configurare a bazei de date.
 * @author : Bodiu Dumitru
 */
@Configuration
@PropertySource("classpath:/application.properties")
@EnableTransactionManagement
@EnableJpaRepositories("com.pharma.reactives.repositories")
public class DbConfig {

    @Autowired
    private Environment env;

    /**
     * Metoda care creaza si returneaza obiectul DataSource configurat.
     * @return obiectul DataSource configurat
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("hibernate.driver_class"));
        dataSource.setUrl(env.getProperty("hibernate.connection.url"));
        dataSource.setUsername(env.getProperty("hibernate.connection.username"));
        dataSource.setPassword(env.getProperty("hibernate.connection.password"));
        return dataSource;
    }

    /**
     * Metoda care configureaza proprietatile Hibernate.
     * @return obiectul Properties configurat
     */
    private Properties hibernateProperties(){
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));

        return properties;
    }

    /**
     * Metoda care creaza si returneaza obiectul EntityManagerFactory configurat.
     * @return obiectul EntityManagerFactory configurat
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.pharma.reactives.models");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());

        return em;
    }

    /**
     * Metoda care creaza si returneaza obiectul PlatformTransactionManager configurat.
     * @return obiectul PlatformTransactionManager configurat
     */
    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

}
