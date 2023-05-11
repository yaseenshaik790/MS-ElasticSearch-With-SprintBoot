package com.sky.elasticsearch.controller;

import com.sky.elasticsearch.document.Vehicle;
import com.sky.elasticsearch.search.SearchRequestDto;
import com.sky.elasticsearch.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    private VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public void index(@RequestBody Vehicle vehicle){
        vehicleService.index(vehicle);
    }

    @GetMapping("/{id}")
    public Object getById(@PathVariable String id){
        return vehicleService.getById(id);
    }

    @PostMapping("/search")
    public List<Vehicle> search(@RequestBody SearchRequestDto requestDto){
        return vehicleService.search(requestDto);
    }
}
