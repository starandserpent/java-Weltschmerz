package com.ritualsoftheold.weltschmerz.maps.noise;

import com.ritualsoftheold.weltschmerz.noise.Configuration;
import com.ritualsoftheold.weltschmerz.core.MapIO;
import com.ritualsoftheold.weltschmerz.noise.WeltschmerzNoise;

import javax.swing.*;
import java.awt.*;


public class TestHeightMapNoise {
    public static void main(String... args) {
        Configuration configuration = MapIO.loadMapConfig();
        int width = configuration.width;
        int height = configuration.height;
        //Creates frame for heigh map
        JFrame frame = new JFrame("Joise Example 01");
        frame.setPreferredSize(new Dimension(width, height));

        Canvas canvas = new Canvas(width, height);
        frame.add(canvas);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        WeltschmerzNoise noise = new WeltschmerzNoise(configuration);
        canvas.updateImage(noise);

        frame.pack();
        frame.setLocationRelativeTo(null);
    }
}