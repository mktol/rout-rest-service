package com.epam.task.rout.rest.service.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class InternalRout {

    private Long id;
    private Long departure;
    private Long arrival;
    private City start;
    private City finish;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeparture() {
        return departure;
    }

    public void setDeparture(Long departure) {
        this.departure = departure;
    }

    public Long getArrival() {
        return arrival;
    }

    public void setArrival(Long arrival) {
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
        InternalRout that = (InternalRout) o;
        return Objects.equals(getDeparture(), that.getDeparture()) &&
                Objects.equals(getArrival(), that.getArrival()) &&
                Objects.equals(getStart(), that.getStart()) &&
                Objects.equals(getFinish(), that.getFinish());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDeparture(), getArrival(), getStart(), getFinish());
    }
}
