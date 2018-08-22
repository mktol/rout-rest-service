package com.epam.task.rout.rest.service.util;

import com.epam.task.rout.rest.service.dto.InternalRout;
import com.epam.task.rout.rest.service.dto.Rout;


import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class Converter {


    public static InternalRout toInternal(Rout rout){
        InternalRout internalRout = new InternalRout();
        internalRout.setId(rout.getId());
        internalRout.setStart(rout.getStart());
        internalRout.setFinish(rout.getFinish());
        internalRout.setArrival(rout.getArrival().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        internalRout.setDeparture(rout.getDeparture().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        return internalRout;
    }

    public static Rout toRout(InternalRout internalRout){
        Rout rout = new Rout();
        rout.setArrival(new Timestamp(internalRout.getArrival()).toLocalDateTime());
        rout.setDeparture(new Timestamp(internalRout.getDeparture()).toLocalDateTime());
        rout.setStart(internalRout.getStart());
        rout.setFinish(internalRout.getFinish());
        rout.setId(internalRout.getId());
        return rout;
    }

    public static List<Rout> toRouts(List<InternalRout> routs){
        return routs.stream().map(Converter::toRout).collect(Collectors.toList());
    }
}
