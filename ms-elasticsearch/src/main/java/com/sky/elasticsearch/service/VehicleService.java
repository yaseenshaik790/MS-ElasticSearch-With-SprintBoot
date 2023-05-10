package com.sky.elasticsearch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sky.elasticsearch.document.Person;
import com.sky.elasticsearch.document.Vehicle;
import com.sky.elasticsearch.indices.Indices;
import com.sky.elasticsearch.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VehicleService {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private RestHighLevelClient client;

    @Autowired
    public VehicleService(RestHighLevelClient client) {
        this.client = client;
    }

    public Boolean index(final Vehicle vehicle) {
        try {
            final String vehicleAsString = MAPPER.writeValueAsString(vehicle);

            final IndexRequest indexRequest = new IndexRequest(Indices.VEHICLE_INDEX);
            indexRequest.id(vehicle.getId());
            indexRequest.source(vehicleAsString, XContentType.JSON);

            final IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

            return response != null && response.status().equals(RestStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            exception.printStackTrace();
            return false;
        }
    }

    public Vehicle getById(final String vehicleId) {
        try {
            final GetResponse documentFields = client.get(
                    new GetRequest(Indices.VEHICLE_INDEX, vehicleId), RequestOptions.DEFAULT);

            if (documentFields == null) {
                log.info("No response");
                return null;
            }
            return MAPPER.readValue(documentFields.getSourceAsString(), Vehicle.class);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            exception.printStackTrace();
            return null;
        }

    }
}
