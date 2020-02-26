package com.sparknetworks.exercise.filteringmatches;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
  private String database;
  private String uri;

  public MongoConfig(
      @Value("${spring.data.mongodb.database}") String database,
      @Value("${spring.data.mongodb.uri}") String uri
  ) {
    this.database = database;
    this.uri = uri;
  }

  @Override
  protected String getDatabaseName() {
    return database;
  }

  public MongoClient mongoClient() {
    return MongoClients.create(uri);
  }

  @Bean
  public MongoDbFactory mongoDbFactory() {
    return new SimpleMongoClientDbFactory(mongoClient(), getDatabaseName());
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(mongoDbFactory());
  }
}
