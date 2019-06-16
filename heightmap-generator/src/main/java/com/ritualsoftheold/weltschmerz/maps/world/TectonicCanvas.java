package com.ritualsoftheold.weltschmerz.maps.world;

import com.ritualsoftheold.weltschmerz.core.World;
import com.ritualsoftheold.weltschmerz.landmass.land.Location;
import com.ritualsoftheold.weltschmerz.landmass.land.Plate;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class TectonicCanvas extends JPanel {
    private BufferedImage image;
    private World world;
    private int width;
    private int height;

    public TectonicCanvas(int width, int height, World world) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.world = world;
        this.width = width;
        this.height = height;
    }

    public void drawWorld() {
        Graphics g = image.getGraphics();

        g.setColor(Color.BLACK);

        for (Plate plate : world.getPlates()) {
            for (Location location : plate) {
                g.fillPolygon(location.position.getSwingPolygon());
            }
        }

        this.repaint();
    }

    public void drawPart(int index) {
        Graphics g = image.getGraphics();

        g.setColor(Color.BLACK);

        Plate plate = world.getPlates()[0];
        Location location = plate.get(index);

        g.fillPolygon(location.position.getSwingPolygon());

        this.repaint();
    }


    public void fill() {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();

        Random rand = new Random();

        for (Plate plate : world.getPlates()) {
            float r = rand.nextFloat();
            float z = rand.nextFloat();
            float b = rand.nextFloat();
            g.setColor(new Color(r, z, b));
            for (Location location : plate) {
                g.fillPolygon(location.position.getSwingPolygon());
            }
        }
        repaint();
    }

    public void fillOnce(int index) {

        Graphics g = image.getGraphics();

        g.setColor(Color.BLACK);

        Plate plate = world.getPlates()[0];
        g.setColor(Color.GREEN);
        Location location = plate.get(index);

        g.fillPolygon(location.position.getSwingPolygon());

        drawWorld();

        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.image, null, null);
        g2.dispose();
    }
}