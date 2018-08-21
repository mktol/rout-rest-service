package com.epam.task.rout.rest.service.controller;

import com.epam.task.rout.rest.service.dto.City;
import com.epam.task.rout.rest.service.service.CityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("cities")
public class CityController {

    @Autowired
    private CityService cityService;



    @GetMapping
    @RequestMapping("/{id}")
    public City findById(@PathVariable("id")String id){

        City city = cityService.getCity(id);
        return city;
    }


    @PostMapping
    @RequestMapping(produces = "application/json")
    public City create(@RequestBody City city) {
        return cityService.createCity(city);
    }
}
