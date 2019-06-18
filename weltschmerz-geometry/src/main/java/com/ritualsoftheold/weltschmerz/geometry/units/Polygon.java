package com.ritualsoftheold.weltschmerz.geometry.units;

import com.google.common.collect.HashMultimap;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Polygon {
    public final Point centroid;
    public final Point center;
    private java.awt.Polygon polygon;
    private HashMultimap<Vertex, Border> borders;
    Set<Point> neighborPoints;


    public Polygon(Point centroid, Point center) {
        this.centroid = centroid;
        this.center = center;
        polygon = new java.awt.Polygon();
        borders = HashMultimap.create();
        neighborPoints = new HashSet<>();
    }

    public boolean contains(double x, double y) {
        return polygon.contains(x, y);
    }

    public java.awt.Polygon getSwingPolygon() {
        return polygon;
    }

    public void addBorder(Set<Border> borders) {
        for (Border border : borders) {
            if (border.datumA == centroid || border.datumB == centroid) {
                this.borders.put(border.vertexA, border);
                this.borders.put(border.vertexB, border);
            }
        }
    }

    public HashMap<Point, Border> getBorders() {
        HashMap<Point, Border> cloneBorders = new HashMap<>();
        for(Border border : borders.values()){
            if(border.datumA != centroid) {
                cloneBorders.put(border.datumA, border);
            }else if (border.datumB != centroid){
                cloneBorders.put(border.datumB, border);
            }
        }
        return cloneBorders;
    }

    public Rectangle getBounds() {
        return polygon.getBounds();
    }

    public void createPolygon() {
        Vertex vertex = null;
        Vertex previousVertex;
        HashSet<Border> removeBorders = new HashSet<>(borders.values());
        for (Vertex key : borders.keySet()) {
            if (borders.containsKey(key)) {
                Border border = (Border) borders.get(key).toArray()[0];
                polygon.addPoint((int) Math.round(border.vertexB.x), (int) Math.round(border.vertexB.y));
                polygon.addPoint((int) Math.round(border.vertexA.x), (int) Math.round(border.vertexA.y));
                addPoints(border.datumA, border.datumB);
                removeBorders.remove(border);
                vertex = border.vertexA;
                break;
            }
        }

        while (!removeBorders.isEmpty()) {
            previousVertex = vertex;
            for (Border anotherBorder : borders.get(vertex)) {
                if (removeBorders.contains(anotherBorder)) {
                    if (anotherBorder.vertexA != vertex && borders.containsKey(vertex)) {
                        removeBorders.remove(anotherBorder);
                        vertex = anotherBorder.vertexA;
                        polygon.addPoint((int) Math.round(vertex.x), (int) Math.round(vertex.y));
                        addPoints(anotherBorder.datumA, anotherBorder.datumB);
                        break;
                    } else if (anotherBorder.vertexB != vertex) {
                        removeBorders.remove(anotherBorder);
                        vertex = anotherBorder.vertexB;
                        polygon.addPoint((int) Math.round(vertex.x), (int) Math.round(vertex.y));
                        addPoints(anotherBorder.datumA, anotherBorder.datumB);
                        break;
                    }
                }
            }

            if (vertex == previousVertex) {
                for (Border border : new ArrayList<>(removeBorders)) {
                    if (border.vertexA == vertex) {
                        vertex = border.vertexB;
                    } else if (border.vertexB == vertex) {
                        vertex = border.vertexA;
                    } else {
                        removeBorders.remove(border);
                    }
                }
            }
        }
    }

    private void addPoints(Point a, Point b){
        if(a != centroid){
            neighborPoints.add(a);
        }

        if (b != centroid){
            neighborPoints.add(b);
        }
    }

    public Set<Point> getNeighborPoints() {
        return neighborPoints;
    }
}