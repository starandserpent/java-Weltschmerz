package com.ritualsoftheold.weltschmerz.core;

import com.ritualsoftheold.weltschmerz.geometry.misc.Configuration;
import com.ritualsoftheold.weltschmerz.geometry.misc.Shape;
import com.typesafe.config.Config;

import java.awt.*;
import java.util.HashMap;

public class ConfigParser {
    public static Configuration parseConfig(Config conf){
        Configuration configuration = new Configuration();
        configuration.latitude = conf.getInt("map.height");
        configuration.longitude = conf.getInt("map.width");

        configuration.seed = conf.getLong("noise.seed");
        configuration.octaves = conf.getInt("noise.octaves");
        configuration.frequency = conf.getDouble("noise.frequency");
        configuration.samples = conf.getInt("noise.samples");


        configuration.volcanoes = conf.getInt("elevation.volcanoes");
        configuration.tectonicPlates = conf.getInt("elevation.tectonicPlates");
        //configuration.islandSize = conf.getInt("elevation.mountainLenght");

        configuration.shapes = parseShape(conf);
        return configuration;
    }

    private static HashMap<String, Shape> parseShape(Config config){
        HashMap<String, Shape> shapes = new HashMap<>();
        Shape shape = new Shape(0, config.getDouble("level.OCEAN"), -3,
                false,"OCEAN", Color.BLUE);
        shapes.put("OCEAN", shape);

        shape = new Shape(shapes.get("OCEAN").max, config.getDouble("level.SEA"), -2,
                false,"SEA", Color.CYAN);
        shapes.put("SEA", shape);

        shape = new Shape(shapes.get("SEA").max, config.getDouble("level.SHORELINE"), -1,
                true,"SHORELINE", Color.YELLOW);
        shapes.put("SHORELINE", shape);

        shape = new Shape(shapes.get("SHORELINE").max, config.getDouble("level.PLAIN"), 0,
                true,"PLAIN", Color.GREEN);
        shapes.put("PLAIN", shape);

        shape = new Shape(shapes.get("PLAIN").max, config.getDouble("level.HILL"), 1,
                true,"HILL", Color.ORANGE);
        shapes.put("HILL", shape);

        shape = new  Shape(shapes.get("HILL").max, config.getDouble("level.MOUNTAIN"), 2,
                true,"MOUNTAIN", Color.GRAY);
        shapes.put("MOUNTAIN", shape);

        shape = new  Shape(shapes.get("HILL").max,  config.getDouble("level.MOUNTAIN"), 1,
                true,"VOLCANO", Color.RED);
        shapes.put("VOLCANO", shape);

        return shapes;
    }
}
