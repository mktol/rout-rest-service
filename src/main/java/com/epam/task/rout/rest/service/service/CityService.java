package com.epam.task.rout.rest.service.service;

import com.epam.task.rout.rest.service.dto.City;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CityService {


    private static final String SHORTEST_BY_TIME = "Shortest path by time";
    private static final String SHORTEST_BY_CITY = "Shortest path by city";

    @Value("${city.default.url}")
    private String defaultCityUrl;

    @Value("${city.custom.url}")
    private String customCityUrl;

    private final DiscoveryClient discoveryClient;
    private final RestTemplate loadBalancedRestTemplate;
    private final RestTemplate justRestTemplate;

    private RestTemplate provideActualRestTemplate(){
        if(discoveryClient==null){
            return justRestTemplate;
        }
        return loadBalancedRestTemplate;
    }

    private String getActualCityUrl(){
        if(discoveryClient==null){
            return customCityUrl;
        }
        return defaultCityUrl;
    }

    @Autowired
    public CityService(@Qualifier(value = "lbRestTemplate") RestTemplate loadBalancedRestTemplate, DiscoveryClient discoveryClient, @Qualifier(value = "justRestTemplate") RestTemplate justRestTemplate) {
        this.loadBalancedRestTemplate = loadBalancedRestTemplate;
        this.discoveryClient = discoveryClient;
        this.justRestTemplate = justRestTemplate;
    }

    public boolean isDiscoveryServiceAlive(){
        com.netflix.discovery.DiscoveryClient discoveryClient = DiscoveryManager.getInstance()
                .getDiscoveryClient();
        discoveryClient.getApplications().getRegisteredApplications().forEach(app->
                System.out.println(app)
        );
        return  false;
    }

//    "http://city-rout-handler/cities/"

    private boolean isServiceDiscoveryPresent(){
        return null==discoveryClient;
    }

    public City getCity(String id) {

        System.out.println(discoveryClient.description());
        discoveryClient.getServices().forEach(service-> {
            System.out.println("*****Service: " + service+"******");
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            instances.forEach(instance->{
                System.out.println("Host: "+instance.getHost());
                System.out.println("Scheme: "+instance.getScheme());
                System.out.println("Service id: "+instance.getServiceId());
                System.out.println("Port: "+instance.getPort());
                System.out.println("---Meta data---");
                instance.getMetadata().forEach((key, val)->{
                    System.out.println(key+": "+val);
                });
            });
        });
        isDiscoveryServiceAlive();


        ResponseEntity<City> cityResponse = provideActualRestTemplate().exchange(getActualCityUrl()+"/" + id, HttpMethod.GET, null, new ParameterizedTypeReference<City>() {
        });
//        ResponseEntity<City> cityResponse = restTemplate.exchange(citiesUrl + id, HttpMethod.GET, null, new ParameterizedTypeReference<City>() {
//        });
        return cityResponse.getBody();
    }

    public City createCity(City city) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<City> entity = new HttpEntity<>(city, headers);
        ResponseEntity<City> cityResponseEntity = provideActualRestTemplate().postForEntity(getActualCityUrl(), entity, City.class);
        return cityResponseEntity.getBody();
    }

    public City findByName(String name) {
        ResponseEntity<City> cityResponseEntity = provideActualRestTemplate().exchange(getActualCityUrl()+"?name=" + name, HttpMethod.GET, null, new ParameterizedTypeReference<City>() {
        });
        return cityResponseEntity.getBody();
    }

    @HystrixCommand(fallbackMethod = "getEmptyBestRoad")
    public List<City> getBestRoutByTime(String start, String finish) {
        ResponseEntity<List<City>> cityResponseEntity = provideActualRestTemplate().exchange(getActualCityUrl()+"/path/time/?start=" + start + "&finish=" + finish, HttpMethod.GET, null, new ParameterizedTypeReference<List<City>>() {
        });
//        ResponseEntity<List<City>> cityResponseEntity = restTemplate.exchange("http://localhost:8080/cities/path/time/?start="+start+"&finish="+finish, HttpMethod.GET, null, new ParameterizedTypeReference<List<City>>() {});
        return cityResponseEntity.getBody();
    }

    @HystrixCommand(fallbackMethod = "getEmptyBestRoad")
    public List<City> getBestRoutByCities(String start, String finish) {
        ResponseEntity<List<City>> cityResponseEntity = provideActualRestTemplate().exchange(getActualCityUrl()+"/path/points/?start=" + start + "&finish=" + finish, HttpMethod.GET, null, new ParameterizedTypeReference<List<City>>() {
        });
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
    public List<City> getEmptyBestRoad() {
        return new ArrayList<>();
    }

    public List<City> getAllCities() {
        ResponseEntity<List<City>> cityResponseEntity = provideActualRestTemplate().exchange(getActualCityUrl(), HttpMethod.GET, null, new ParameterizedTypeReference<List<City>>() {
        });
        return cityResponseEntity.getBody();
    }
}
