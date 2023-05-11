package com.sky.elasticsearch.controller;

import com.sky.elasticsearch.service.IndicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/indices")
public class IndexController {

    private IndicesService indicesService;

    @Autowired
    public IndexController(IndicesService indicesService) {
        this.indicesService = indicesService;
    }

    @PostMapping("/recreate")
    public void reCreateIndex(){
        indicesService.reCreateIndex(true);
    }
}
