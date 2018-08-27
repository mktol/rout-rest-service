package com.epam.task.rout.rest.service.service;

import com.epam.task.rout.rest.service.dto.InternalRout;
import com.epam.task.rout.rest.service.dto.Rout;
import com.epam.task.rout.rest.service.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class RoutService {
    private final RestTemplate restTemplate;


    @Autowired
    public RoutService(@Qualifier(value = "lbRestTemplate")RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Rout findById(Long id) {
        ResponseEntity<InternalRout> cityResponse = restTemplate.exchange("http://city-rout-handler/rest/snippets/"+id, HttpMethod.GET, null, new ParameterizedTypeReference<InternalRout>() {});
//        ResponseEntity<InternalRout> cityResponse = restTemplate.exchange("http://localhost:8080/rest/snippets/"+id, HttpMethod.GET, null, new ParameterizedTypeReference<InternalRout>() {});
        return Converter.toRout(Objects.requireNonNull(cityResponse.getBody()));
    }

    public void remove(Long id) {
        restTemplate.execute("http://localhost:8080/rest/snippets/"+id, HttpMethod.DELETE, null, null);
    }

    public Rout create(Rout rout) {
        InternalRout internalRout = Converter.toInternal(rout);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InternalRout> entity = new HttpEntity<>(internalRout, headers);
//      for configured eureka cloud  ResponseEntity<City> cityResponseEntity = restTemplate.postForEntity("http://city-rout-handler/cities", entity, City.class);
        ResponseEntity<InternalRout> routResponseEntity = restTemplate.postForEntity("http://city-rout-handler/rest/snippets", entity, InternalRout.class);
//        ResponseEntity<InternalRout> routResponseEntity = restTemplate.postForEntity("http://localhost:8080/rest/snippets", entity, InternalRout.class);
        System.out.println(routResponseEntity.getStatusCode());
        return Converter.toRout(Objects.requireNonNull(routResponseEntity.getBody()));
    }

    public List<Rout> findAll() {
        ResponseEntity<List<InternalRout>> routResponseEntity = restTemplate.exchange("http://city-rout-handler/rest/snippets", HttpMethod.GET, null, new ParameterizedTypeReference<List<InternalRout>>() {});
//        ResponseEntity<List<InternalRout>> routResponseEntity = restTemplate.exchange("http://localhost:8080/rest/snippets", HttpMethod.GET, null, new ParameterizedTypeReference<List<InternalRout>>() {});
        return Converter.toRouts(Objects.requireNonNull(routResponseEntity.getBody()));
    }

}
