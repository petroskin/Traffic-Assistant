package com.trafficassistant.repository;

import com.trafficassistant.model.RoadNode;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Repository
public class RoadRepository
{
    public Collection<RoadNode> getNodes() throws FileNotFoundException
    {
        List<RoadNode> ret = new ArrayList<>();
        // TODO Configure path to nodes file
        Scanner in = new Scanner(new File(new File("").getAbsolutePath() + "\\Homework 3\\src\\main\\resources\\nodes.csv"));
        System.out.println(in.nextLine());
        while (in.hasNextLine())
        {
            String [] line = in.nextLine().split(",");
            ret.add(new RoadNode(Double.parseDouble(line[1]), Double.parseDouble(line[2])));
        }
        return ret;
    }
}
