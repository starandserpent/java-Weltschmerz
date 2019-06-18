package com.ritualsoftheold.weltschmerz.geometry.fortune.events;

import com.ritualsoftheold.weltschmerz.geometry.fortune.nodes.DataNode;
import com.ritualsoftheold.weltschmerz.geometry.misc.PrecisionMath;
import com.ritualsoftheold.weltschmerz.geometry.units.Point;
import com.ritualsoftheold.weltschmerz.geometry.fortune.nodes.BorderNode;
import com.ritualsoftheold.weltschmerz.geometry.fortune.nodes.Node;
import com.ritualsoftheold.weltschmerz.geometry.units.Vertex;
import com.ritualsoftheold.weltschmerz.geometry.units.VoronoiBorder;

import java.util.ArrayList;
import java.util.HashSet;

public class DataEvent extends Event {

    private Point DataPoint;

    public DataEvent(Point Point) {
        this.DataPoint = Point;
    }

    //Will return the new root (unchanged except in start-up)
    @Override
    public Node process(Node root, double ys,
                        HashSet<VoronoiBorder> edgeList,
                        ArrayList<DataNode> CircleCheckList)
    {
        if (root == null) {
            root = new DataNode(DataPoint);
            CircleCheckList.add((DataNode) root);
            return root;
        }

        // 1. Find the node to be replaced
        DataNode C = Node.findDataNode(root, ys, DataPoint.x);

        // 2. Create the subtree (ONE Edge, but two VEdgeNodes)
        VoronoiBorder VE = new VoronoiBorder();
        VE.LeftData = C.point;
        VE.RightData = DataPoint;
        VE.VVertexA = Vertex.UNKNOWN;
        VE.VVertexB = Vertex.UNKNOWN;
        edgeList.add(VE);

        Node SubRoot;
        if (PrecisionMath.eq(VE.LeftData.y, VE.RightData.y)) {
            DataNode l, r;
            if (VE.LeftData.x < VE.RightData.x) {
                l = new DataNode(VE.LeftData);
                r = new DataNode(VE.RightData);
                SubRoot = new BorderNode(VE, false, l, r);
            } else {
                l = new DataNode(VE.RightData);
                r = new DataNode(VE.LeftData);
                SubRoot = new BorderNode(VE, true, l, r);
            }
            CircleCheckList.add(l);
            CircleCheckList.add(r);
        } else {
            DataNode l = new DataNode(VE.LeftData);
            DataNode rl = new DataNode(VE.RightData);
            DataNode rr = new DataNode(VE.LeftData);
            BorderNode r = new BorderNode(VE, true, rl, rr);
            SubRoot = new BorderNode(VE, false, l, r);
            CircleCheckList.add(l);
            CircleCheckList.add(rl);
            CircleCheckList.add(rr);
        }

        // 3. Apply subtree
        Node parent = C.getParent();
        if (parent == null)
            return SubRoot;
        parent.Replace(C, SubRoot);
        return root;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof DataEvent))
            return false;

        final DataEvent o = (DataEvent) obj;
        return DataPoint.equals(o.DataPoint);
    }

    @Override
    public double getX() {
        return DataPoint.x;
    }

    @Override
    public double getY() {
        return DataPoint.y;
    }

    public Point getDatum() {
        return DataPoint;
    }
}