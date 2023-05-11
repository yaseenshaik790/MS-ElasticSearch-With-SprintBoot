package com.sky.elasticsearch.search.utils;


import com.sky.elasticsearch.search.SearchRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.CollectionUtils;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchUtils {

    public static SearchRequest buildSearchRequest(final String indexName, final SearchRequestDto dto) {

        try {
            final SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                    .postFilter(getQueryBuilder(dto));
            SearchRequest searchRequest = new SearchRequest(indexName);
            searchRequest.source(searchSourceBuilder);
            return searchRequest;
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    public static QueryBuilder getQueryBuilder(final SearchRequestDto dto) {

        if (dto == null || CollectionUtils.isEmpty(dto.getFields())) {
            return null;
        }
        List<String> fields = dto.getFields();
        if (fields.size() > 1) {
            MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(dto.getSearchTerm())
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
                    .operator(Operator.AND);
            fields.forEach(queryBuilder::field);
            return queryBuilder;
        }

        return fields.stream().findFirst().map(field ->
                        QueryBuilders.matchQuery(field, dto.getSearchTerm()).operator(Operator.AND))
                .orElse(null);
    }
}
