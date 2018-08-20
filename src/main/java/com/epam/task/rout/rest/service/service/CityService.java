package com.epam.task.rout.rest.service.service;

import com.epam.task.rout.rest.service.dto.City;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class CityService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ObjectMapper objectMapper;

    public  City getCity(String id) {
        ResponseEntity<City> cityResponse = restTemplate.exchange("http://city-rout-handler/cities/"+id, HttpMethod.GET, null, new ParameterizedTypeReference<City>() {});
        return cityResponse.getBody();
    }

    public City createCity(City city){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

//        String cityJson = objectMapper.writeValueAsString(city);
        HttpEntity<City> entity = new HttpEntity<>(city, headers);
        ResponseEntity<City> cityResponseEntity = restTemplate.postForEntity("http://city-rout-handler/citie", entity, City.class);
        return cityResponseEntity.getBody();
    }

}
