package com.trafficassistant.repository;

import com.trafficassistant.model.RoadNode;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Repository
public class RoadRepository
{
    public Collection<RoadNode> getNodes() throws FileNotFoundException
    {
        List<RoadNode> ret = new ArrayList<>();
        String currentPath = new File("").getAbsolutePath();
        Scanner in;
        in = new Scanner(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResourceAsStream("nodes.csv")));
        in.nextLine();
        while (in.hasNextLine())
        {
            String [] line = in.nextLine().split(",");
            ret.add(new RoadNode(Double.parseDouble(line[1]), Double.parseDouble(line[2])));
        }
        return ret;
    }
}
