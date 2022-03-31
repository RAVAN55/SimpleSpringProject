package com.example.homework.product.config;


import com.example.homework.product.data.Product;
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
@EnableJpaRepositories(entityManagerFactoryRef = "productEntityManagerFactory",
        transactionManagerRef = "productTransactionManager", basePackages = "com.example.homework.product.repo")
public class ProductDBConfig {

    @Autowired
    private Environment env;

    @Bean(name = "productDatasource")
    @ConfigurationProperties(prefix = "spring.product.datasource")
    public DataSource dataSource(){
//        return DataSourceBuilder.create().build();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl(env.getProperty("spring.product.datasource.jdbc-url"));
        dataSource.setUsername(env.getProperty("spring.product.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.product.datasource.password"));
        dataSource.setDriverClassName(env.getProperty("spring.product.datasource.driver-class-name","org.postgresql.Driver"));

        return dataSource;
    }


    @Bean(name = "productEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("productDatasource") DataSource dataSource){
        return builder.dataSource(dataSource)
                .packages(Product.class)
                .persistenceUnit("product")
                .build();
    }

    @Bean(name = "productTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("productEntityManagerFactory") EntityManagerFactory factory){
        return new JpaTransactionManager(factory);
    }
}
