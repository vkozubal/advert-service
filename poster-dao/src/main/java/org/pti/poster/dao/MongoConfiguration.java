package org.pti.poster.dao;


import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import org.pti.poster.dao.Constants.DATABASE;
import org.pti.poster.dao.repository.RepositoryPackage;
import org.pti.poster.model.SpringConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses = RepositoryPackage.class)
@ComponentScan
@Import(value = SpringConfiguration.class)
public class MongoConfiguration extends AbstractMongoConfiguration {

    // ---------------------------------------------------- mongodb config

    @Override
    protected String getDatabaseName() {
        return DATABASE.NAME;
    }

    @Bean
    @Override
    public Mongo mongo() throws Exception {
        MongoClient client = new MongoClient(DATABASE.HOST, DATABASE.PORT);
        client.setWriteConcern(WriteConcern.SAFE);
        return client;
    }

    @Override
    protected String getMappingBasePackage() {
        return DATABASE.BASE_MAPPING_PACKAGE;
    }

    // ---------------------------------------------------- MongoTemplate

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), getDatabaseName());
    }
}
