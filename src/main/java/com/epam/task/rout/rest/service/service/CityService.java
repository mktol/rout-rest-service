package com.epam.task.rout.rest.service.service;

import com.epam.task.rout.rest.service.dto.City;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CityService {

    private final RestTemplate restTemplate;

    private static final String SHORTEST_BY_TIME= "Shortest path by time";
    private static final String SHORTEST_BY_CITY= "Shortest path by city";

    @Autowired
    public CityService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public  City getCity(String id) {
        ResponseEntity<City> cityResponse = restTemplate.exchange("http://city-rout-handler/cities/"+id, HttpMethod.GET, null, new ParameterizedTypeReference<City>() {});
//        ResponseEntity<City> cityResponse = restTemplate.exchange("http://localhost:8080/cities/"+id, HttpMethod.GET, null, new ParameterizedTypeReference<City>() {});
        return cityResponse.getBody();
    }

    public City createCity(City city){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

//        String cityJson = objectMapper.writeValueAsString(city);
        HttpEntity<City> entity = new HttpEntity<>(city, headers);
        ResponseEntity<City> cityResponseEntity = restTemplate.postForEntity("http://city-rout-handler/cities", entity, City.class);
//        ResponseEntity<City> cityResponseEntity = restTemplate.postForEntity("http://localhost:8080/cities", entity, City.class);
        return cityResponseEntity.getBody();
    }

    public City findByName(String name){
        ResponseEntity<City> cityResponseEntity = restTemplate.exchange("http://city-rout-handler/cities/?name=" + name, HttpMethod.GET, null, new ParameterizedTypeReference<City>() {});
//        ResponseEntity<City> cityResponseEntity = restTemplate.exchange("http://localhost:8080/cities/?name=" + name, HttpMethod.GET, null, new ParameterizedTypeReference<City>() {});
        return cityResponseEntity.getBody();
    }

    @HystrixCommand(fallbackMethod = "getEmptyBestRoad")
    public List<City> getBestRoutByTime(String start, String finish){
        ResponseEntity<List<City>> cityResponseEntity = restTemplate.exchange("http://city-rout-handler/cities/path/time/?start="+start+"&finish="+finish, HttpMethod.GET, null, new ParameterizedTypeReference<List<City>>() {});
//        ResponseEntity<List<City>> cityResponseEntity = restTemplate.exchange("http://localhost:8080/cities/path/time/?start="+start+"&finish="+finish, HttpMethod.GET, null, new ParameterizedTypeReference<List<City>>() {});
        return cityResponseEntity.getBody();
    }
    @HystrixCommand(fallbackMethod = "getEmptyBestRoad")
    public List<City> getBestRoutByCities(String start, String finish){
        ResponseEntity<List<City>> cityResponseEntity = restTemplate.exchange("http://city-rout-handler/cities/path/points/?start="+start+"&finish="+finish, HttpMethod.GET, null, new ParameterizedTypeReference<List<City>>() {});
//        ResponseEntity<List<City>> cityResponseEntity = restTemplate.exchange("http://localhost:8080/cities/path/points/?start="+start+"&finish="+finish, HttpMethod.GET, null, new ParameterizedTypeReference<List<City>>() {});
        return cityResponseEntity.getBody();
    }

    public Map<String, List<City>> findBestWays(String start, String finish) {

        Map<String, List<City>> resultMap = new HashMap<>();

        List<City> bestRoutByCities = getBestRoutByCities(start, finish);
        List<City> bestRoutByTime = getBestRoutByTime(start, finish);
        resultMap.put(SHORTEST_BY_CITY, bestRoutByCities);
        resultMap.put(SHORTEST_BY_TIME, bestRoutByTime);
        return resultMap;
    }

    // dummy method for hystrix
    public List<City> getEmptyBestRoad(){
        return new ArrayList<>();
    }

    public List<City> getAllCities() {
        ResponseEntity<List<City>> cityResponseEntity = restTemplate.exchange("http://city-rout-handler/cities", HttpMethod.GET, null, new ParameterizedTypeReference<List<City>>() {});
        return cityResponseEntity.getBody();
    }
}
