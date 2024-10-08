package com.xworkz.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class DBConfiguration {

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.driver}")
    private String driver;


    public DBConfiguration(){
        System.out.println("DB Configuration initiated.");
    }

    @Bean
    public DataSource dataSource(){
        System.out.println("DataSource configured");
//        DriverManagerDataSource dataSource= new DriverManagerDataSource();
//        dataSource.setUrl(this.url);
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(this.url);
        config.setUsername(this.username);
        config.setPassword(this.password);
        config.setDriverClassName(this.driver);
        config.setConnectionTimeout(10000);
        config.setPoolName("HikariCP");
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(100);
        config.setLeakDetectionThreshold(30000); // 30 seconds


        return new HikariDataSource(config);
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean= new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.xworkz");

        Properties properties = new Properties();
        properties.put("hibernate.show_sql",false);
        properties.put("hibernate.format_sql",false);
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        entityManagerFactoryBean.setJpaProperties(properties);

        System.out.println("Entity manager Factory configured.");

        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean  localContainerEntityManagerFactoryBean){
        System.out.println("Transactional Manager is configured ");
        return new JpaTransactionManager(localContainerEntityManagerFactoryBean.getObject());
    }

}
