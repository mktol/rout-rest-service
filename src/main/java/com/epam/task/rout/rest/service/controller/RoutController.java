package com.epam.task.rout.rest.service.controller;

import com.epam.task.rout.rest.service.dto.City;
import com.epam.task.rout.rest.service.dto.Rout;
import com.epam.task.rout.rest.service.service.RoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class RoutController {

    private RoutService routService;

    @Autowired
    public RoutController(RoutService routService) {
        this.routService = routService;
    }

    @GetMapping("/routs/{id}")
    public Rout findRout(@PathVariable("id") Long id) {
        return routService.findById(id);
    }

    @GetMapping("/routs")
    public List<Rout> getAllRouts(){
        return routService.findAll();
    }

    @DeleteMapping("/routs/{id}")
    public ResponseEntity deleteRout(@PathVariable("id") Long id) {
        routService.remove(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @PostMapping("/routs")
    public Rout create(@RequestBody() Rout rout){
        return routService.create(rout);
    }

    @GetMapping("/temp/date")
    public Rout dummyRout(){
        Rout rout = new Rout();
        rout.setDeparture(LocalDateTime.of(2017,8, 27, 22, 30));
        rout.setArrival(LocalDateTime.of(2017,8, 28, 12, 30));
        rout.setStart(new City("Lviv"));
        rout.setFinish(new City("Poltava"));
        return rout;

    }




}
