package com.trafficassistant.model;

import java.util.ArrayList;
import java.util.List;

public class EventType {

    static class Type{
        static List<String> eventTypes = new ArrayList<>();

    }

    public EventType(){
        Type.eventTypes.add("Police patrol");
        Type.eventTypes.add("Accident");
        Type.eventTypes.add("Heavy Fog");
        Type.eventTypes.add("Slippery road");
        Type.eventTypes.add("Closed road");
    }

    public String getEventType(int i){
        return Type.eventTypes.get(i);
    }
}
