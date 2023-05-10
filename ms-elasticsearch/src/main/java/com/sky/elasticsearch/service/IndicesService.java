package com.sky.elasticsearch.service;

import com.sky.elasticsearch.indices.Indices;
import com.sky.elasticsearch.indices.Utils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class IndicesService {

    private final List<String> INDICES_TO_CREATE = Arrays.asList(Indices.VEHICLE_INDEX);

    private RestHighLevelClient client;

    @Autowired
    public IndicesService(RestHighLevelClient client) {
        this.client = client;
    }

    @PostConstruct
    public void tryToCreateIndex() {
        final String settings = Utils.loadAsString("static/es-settings.json");
        for (final String indexName : INDICES_TO_CREATE) {
            try {
                boolean indexExist = client.indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
                if (indexExist) {
                    continue;
                }
                final String mappings = Utils.loadAsString("static/mappings/" + indexName + ".json");
                if (StringUtils.isEmpty(settings) || StringUtils.isEmpty(mappings)) {
                    log.error("Failed to create index with IndexName {}" + indexName);
                    continue;
                }
                final CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
                createIndexRequest.settings(settings, XContentType.JSON);
                createIndexRequest.mapping(mappings, XContentType.JSON);
                client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            } catch (Exception exception) {
                log.error("Error occurred while parsing the File");
                exception.printStackTrace();
            }
        }

    }
}
