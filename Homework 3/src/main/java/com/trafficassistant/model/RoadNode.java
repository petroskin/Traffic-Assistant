package com.trafficassistant.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class RoadNode
{
    @Id
    private Long id;
    private Double lat;
    private Double lon;

    public RoadNode(Long id, Double lat, Double lon)
    {
        this.id=id;
        this.lat = lat;
        this.lon = lon;
    }

    public RoadNode()
    {
    }
}
