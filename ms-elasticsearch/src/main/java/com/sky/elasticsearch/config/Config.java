package com.sky.elasticsearch.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.HttpHeaders;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.sky.elasticsearch.repository")
@ComponentScan(basePackages = { "com.sky.elasticsearch" })
public class Config extends AbstractElasticsearchConfiguration {

    @Value("${elasticsearch.url}")
    private String elasticSearchUrl;

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        HttpHeaders defaultHeaders = new HttpHeaders();
        defaultHeaders.add("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
        defaultHeaders.add("Content-Type", "application/vnd.elasticsearch+json;compatible-with=7");
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticSearchUrl)
                .withConnectTimeout(500000)
                .withSocketTimeout(900000)
                .withDefaultHeaders(defaultHeaders)
                .build();

        return RestClients.create(clientConfiguration)
                .rest();

        /*RestClientBuilder builder = RestClient.builder(
                        new HttpHost("localhost", 9200))
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                        .setConnectTimeout(500000)
                        .setSocketTimeout(60000000));
        return new RestHighLevelClient(builder);*/
    }

}
