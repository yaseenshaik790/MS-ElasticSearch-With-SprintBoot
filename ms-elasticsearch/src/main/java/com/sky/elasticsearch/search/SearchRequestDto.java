package com.sky.elasticsearch.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor@NoArgsConstructor
public class SearchRequestDto {
    private List<String> fields;
    private String searchTerm;

}
