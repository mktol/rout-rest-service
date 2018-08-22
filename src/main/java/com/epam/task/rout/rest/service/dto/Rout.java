package com.epam.task.rout.rest.service.dto;

import com.epam.task.rout.rest.service.service.LocalDateDeserializer;
import com.epam.task.rout.rest.service.service.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class Rout {

    private Long id;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime departure;
//    @JsonFormat(pattern = "dd::MM::yyyy HH:mm")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
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
