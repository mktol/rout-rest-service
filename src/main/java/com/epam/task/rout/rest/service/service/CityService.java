package com.epam.task.rout.rest.service.service;

import com.epam.task.rout.rest.service.dto.City;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CityService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ObjectMapper objectMapper;

    public  City getCity(String id) {
//        ResponseEntity<City> cityResponse = restTemplate.exchange("http://city-rout-handler/cities/"+id, HttpMethod.GET, null, new ParameterizedTypeReference<City>() {});
        ResponseEntity<City> cityResponse = restTemplate.exchange("http://localhost:8080/cities/"+id, HttpMethod.GET, null, new ParameterizedTypeReference<City>() {});
        return cityResponse.getBody();
    }

    public City createCity(City city){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

//        String cityJson = objectMapper.writeValueAsString(city);
        HttpEntity<City> entity = new HttpEntity<>(city, headers);
//        ResponseEntity<City> cityResponseEntity = restTemplate.postForEntity("http://city-rout-handler/cities", entity, City.class);
        ResponseEntity<City> cityResponseEntity = restTemplate.postForEntity("http://localhost:8080/cities", entity, City.class);
        return cityResponseEntity.getBody();
    }

    public City findByName(String name){
        ResponseEntity<City> cityResponseEntity = restTemplate.exchange("http://localhost:8080/cities/?name=" + name, HttpMethod.GET, null, new ParameterizedTypeReference<City>() {});
        return cityResponseEntity.getBody();
    }

    public List<City> getBestRoutByTime(String start, String finish){
        ResponseEntity<List<City>> cityResponseEntity = restTemplate.exchange("http://localhost:8080/cities/path/time/?start="+start+"&finish="+finish, HttpMethod.GET, null, new ParameterizedTypeReference<List<City>>() {});
        return cityResponseEntity.getBody();
    }
    public List<City> getBestRoutByCities(String start, String finish){
        ResponseEntity<List<City>> cityResponseEntity = restTemplate.exchange("http://localhost:8080/cities/path/points/?start="+start+"&finish="+finish, HttpMethod.GET, null, new ParameterizedTypeReference<List<City>>() {});
        return cityResponseEntity.getBody();
    }

}
