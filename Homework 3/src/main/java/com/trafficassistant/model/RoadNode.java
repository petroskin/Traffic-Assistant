package com.trafficassistant.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Data
public class RoadNode
{
    private Double lat;
    private Double lon;

    public RoadNode(Double lon, Double lat)
    {
        /*
        Double truncatedDouble = BigDecimal.valueOf(toBeTruncated)
    .setScale(3, RoundingMode.HALF_UP)
    .doubleValue();
         */
        this.lat = BigDecimal.valueOf(lat).setScale(6, RoundingMode.HALF_UP).doubleValue();
        this.lon = BigDecimal.valueOf(lon).setScale(6, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadNode roadNode = (RoadNode) o;
//        return lat.equals(roadNode.lat) && lon.equals(roadNode.lon);
        // Accuracy
        return Math.abs(lat - roadNode.lat) < 0.00003 && Math.abs(lon - roadNode.lon) < 0.00003;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(lat, lon);
    }
}
