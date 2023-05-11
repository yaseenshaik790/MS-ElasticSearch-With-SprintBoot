package com.sky.elasticsearch.service;

import com.sky.elasticsearch.indices.Indices;
import com.sky.elasticsearch.indices.Utils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
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

    private final List<String> INDICES = Arrays.asList(Indices.VEHICLE_INDEX);

    private RestHighLevelClient client;

    @Autowired
    public IndicesService(RestHighLevelClient client) {
        this.client = client;
    }

    @PostConstruct
    public void tryToCreateIndex() {
        reCreateIndex(false);
    }

    public void reCreateIndex(boolean deleteExisting){
        final String settings = Utils.loadAsString("static/es-settings.json");

        if (settings == null){
            log.error("Failed to load setting");
            return;
        }
        for (final String indexName : INDICES) {
            try {
                boolean indexExist = client.indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
                if (indexExist) {
                    if (!deleteExisting){
                        continue;
                    }
                    client.indices().delete(new DeleteIndexRequest(indexName), RequestOptions.DEFAULT);
                }

                final String mappings = loadMappings(indexName);
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

    private String loadMappings(String indexName){
        final String mappings = Utils.loadAsString("static/mappings/" + indexName + ".json");
        if (StringUtils.isEmpty(mappings)) {
            log.error("Failed to create index with IndexName {}" + indexName);
            return null;
        }
        return mappings;
    }
}
