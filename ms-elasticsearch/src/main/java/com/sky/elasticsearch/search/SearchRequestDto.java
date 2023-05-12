package com.sky.elasticsearch.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

@Data
@AllArgsConstructor@NoArgsConstructor
public class SearchRequestDto {
    private List<String> fields;
    private String searchTerm;
    private String sortBy;
    private SortOrder sortOrder;
}
