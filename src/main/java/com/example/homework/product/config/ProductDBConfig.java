package com.example.homework.product.config;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackages = "com.example.homework.product.repo")
public class ProductDBConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "product";
    }

    @Bean
    public MongoTransactionManager transactionManager(MongoDatabaseFactory databaseFactory){
        return new MongoTransactionManager(databaseFactory);
    }


    @Override
    public MongoClient mongoClient(){

        ConnectionString string = new ConnectionString("mongodb://localhost:27017/product");

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(string)
                .build();

        return MongoClients.create(settings);
    }


}
