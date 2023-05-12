package com.sky.elasticsearch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sky.elasticsearch.document.Person;
import com.sky.elasticsearch.document.Vehicle;
import com.sky.elasticsearch.indices.Indices;
import com.sky.elasticsearch.repository.PersonRepository;
import com.sky.elasticsearch.search.SearchRequestDto;
import com.sky.elasticsearch.search.utils.SearchUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class VehicleService {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private RestHighLevelClient client;

    @Autowired
    public VehicleService(RestHighLevelClient client) {
        this.client = client;
    }

    public List<Vehicle> search(final SearchRequestDto requestDto) {
        SearchRequest searchRequest = SearchUtils.buildSearchRequest(Indices.VEHICLE_INDEX, requestDto);

        if (searchRequest == null) {
            return Collections.emptyList();
        }

        try {
            final SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            final SearchHit[] searchHits = response.getHits().getHits();
            final List<Vehicle> vehicles = new ArrayList<>(searchHits.length);
            for (SearchHit searchHit : searchHits) {
                vehicles.add(MAPPER.readValue(searchHit.getSourceAsString(), Vehicle.class));
            }
            return vehicles;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
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
