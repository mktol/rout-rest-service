package com.epam.task.rout.rest.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class Rout {

    private Long id;

    @JsonFormat(pattern = "dd::MM::yyyy")
    private LocalDateTime departure;
    @JsonFormat(pattern = "dd::MM::yyyy")
    private LocalDateTime arrival;
    private City start;
    private City finish;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

    public City getStart() {
        return start;
    }

    public void setStart(City start) {
        this.start = start;
    }

    public City getFinish() {
        return finish;
    }

    public void setFinish(City finish) {
        this.finish = finish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rout rout = (Rout) o;
        return Objects.equals(getDeparture(), rout.getDeparture()) &&
                Objects.equals(getArrival(), rout.getArrival()) &&
                Objects.equals(getStart(), rout.getStart()) &&
                Objects.equals(getFinish(), rout.getFinish());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDeparture(), getArrival(), getStart(), getFinish());
    }
}
