package com.epam.task.rout.rest.service.controller;

import com.epam.task.rout.rest.service.dto.City;
import com.epam.task.rout.rest.service.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public City findById(@PathVariable("id")String id){
        return cityService.getCity(id);
    }
    @GetMapping
    public List<City> getAll(){
        return cityService.getAllCities();
    }

    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public City create(@RequestBody City city) {
        return cityService.createCity(city);
    }

    @RequestMapping(value = "/path", method = RequestMethod.GET)
    public Map<String, List<City>> getBestPath(@RequestParam("start") String start, @RequestParam("finish") String finish){
        return cityService.findBestWays(start, finish);
    }
}
