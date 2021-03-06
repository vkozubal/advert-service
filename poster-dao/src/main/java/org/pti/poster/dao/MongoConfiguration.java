package org.pti.poster.dao;


import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import org.pti.poster.dao.Constants.DATABASE;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@Import(DaoConfiguration.class)
@Profile("live")
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
