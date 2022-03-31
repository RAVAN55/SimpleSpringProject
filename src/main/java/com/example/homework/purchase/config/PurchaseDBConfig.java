package com.example.homework.purchase.config;


import com.example.homework.purchase.data.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "purchaseEntityManagerFactory",
        transactionManagerRef = "purchaseTransactionManager",
        basePackages = {"com.example.homework.purchase.repo"})
public class PurchaseDBConfig {

    @Autowired
    private Environment env;

    @Bean(name = "purchaseDatasource")
    @ConfigurationProperties(prefix = "spring.purchase.datasource")
    public DataSource dataSource(){
//        return DataSourceBuilder.create().build();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl(env.getProperty("spring.purchase.datasource.jdbc-url"));
        dataSource.setUsername(env.getProperty("spring.purchase.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.purchase.datasource.password"));
        dataSource.setDriverClassName(env.getProperty("spring.purchase.datasource.driver-class-name","org.postgresql.Driver"));

        return dataSource;

    }

    @Bean(name = "purchaseEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("purchaseDatasource") DataSource dataSource, EntityManagerFactoryBuilder builder){

        return builder.dataSource(dataSource)
                .packages(Purchase.class)
                .persistenceUnit("purchase")
                .build();
    }


    @Bean(name = "purchaseTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("purchaseEntityManagerFactory") EntityManagerFactory factory){
        return new JpaTransactionManager(factory);
    }

}
