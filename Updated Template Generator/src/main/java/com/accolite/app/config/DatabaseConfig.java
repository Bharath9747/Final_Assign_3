package com.accolite.app.config;


import java.util.HashMap;


import javax.sql.DataSource;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "createEntityManagerFactory",
        basePackages 	 = {"com.accolite.app.repo1"},
        transactionManagerRef = "createTransactionManager"
)
public class DatabaseConfig {

    @Autowired
    Environment env;

    @Primary
    @Bean(name= "createDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(env.getProperty("create.datasource.url"));
        ds.setUsername(env.getProperty("create.datasource.username"));
        ds.setPassword(env.getProperty("create.datasource.password"));
        ds.setDriverClassName(env.getProperty("create.datasource.driver-class-name"));
        return ds;
    }


    @Primary
    @Bean(name= "createEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource());
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);
        HashMap<String,Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", "create");
        properties.put("hibernate.show_sql",true);
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        bean.setJpaPropertyMap(properties);
        bean.setPackagesToScan("com.accolite.app.entity1");
        return bean;

    }

    @Primary
    @Bean("createTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("createEntityManagerFactory") EntityManagerFactory entityManagerFactory ) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}