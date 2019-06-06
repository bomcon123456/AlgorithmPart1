/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;


public class KdTree {
    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;

        public Node(Point2D mp, RectHV rec, Node l, Node r) {
            p = mp;
            rect = rec;
            lb = l;
            rt = r;
        }
    }

    private Node root;
    private int size;

    public KdTree()                               // construct an empty set of points
    {
        root = null;
        size = 0;
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return size == 0;
    }

    public int size()                         // number of points in the set
    {
        return size;
    }

    private Node insert(Node node, double xmin, double ymin, double xmax, double ymax, boolean isX,
                        Point2D p) {
        if (node == null) {
            size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax), null, null);
        }
        if (node.p.equals(p)) {
            return node;
        }
        int cmp;
        double thisKey, thatKey;
        if (isX) {
            thatKey = p.x();
            thisKey = node.p.x();
        }
        else {
            thatKey = p.y();
            thisKey = node.p.y();
        }
        // going left
        if (thisKey > thatKey) {
            if (isX) {
                node.lb = insert(node.lb,
                                 xmin, ymin, node.p.x(), ymax,
                                 !isX, p);
            }
            else {
                node.lb = insert(node.lb,
                                 xmin, ymin, xmax, node.p.y(),
                                 !isX, p);
            }
        }
        // going right
        else if (thisKey < thatKey || thisKey == thatKey) {
            if (isX) {
                node.rt = insert(node.rt,
                                 node.p.x(), ymin, xmax, ymax,
                                 !isX, p);
            }
            else {
                node.rt = insert(node.rt,
                                 xmin, node.p.y(), xmax, ymax,
                                 !isX, p);
            }
        }
        return node;
    }

    public void insert(
            Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null) throw new IllegalArgumentException("calls insert() with a null point");
        root = insert(root, 0, 0, 1, 1, true, p);
    }

    private Point2D get(Node node, boolean isX, Point2D p) {
        if (node == null) return null;
        if (node.p.x() == p.x() && node.p.y() == p.y())
            return node.p;
        int cmp;
        double thisKey, thatKey;
        if (isX) {
            thatKey = p.x();
            thisKey = node.p.x();
        }
        else {
            thatKey = p.y();
            thisKey = node.p.y();
        }
        if (thisKey > thatKey) {
            return get(node.lb, !isX, p);
        }
        else if (thisKey <= thatKey) {
            return get(node.rt, !isX, p);
        }
        return null;
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null) throw new IllegalArgumentException("calls contains() with a null point");
        return get(root, true, p) != null;
    }

    // isX => vertical, !isX => horizontal
    private void draw(Node node, boolean isX) {
        if (node == null) return;
        draw(node.lb, !isX);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.p.x(), node.p.y());
        StdDraw.setPenRadius();
        if (isX) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        draw(node.rt, !isX);
    }

    public void draw()                         // draw all points to standard draw
    {
        draw(root, true);
    }


    private Iterable<Point2D> range(Node node, RectHV rect) {
        SET<Point2D> result = new SET<>();
        if (node == null || !rect.intersects(node.rect)) {
            return result;
        }
        if (rect.contains(node.p)) {
            result.add(node.p);
        }
        Iterable<Point2D> left = range(node.lb, rect);
        Iterable<Point2D> right = range(node.rt, rect);
        left.forEach(result::add);
        right.forEach(result::add);

        return result;
    }

    public Iterable<Point2D> range(
            RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null)
            throw new IllegalArgumentException("calls range() with a null point");

        return range(root, rect);
    }

    private Node nearest(Node node, Node currentChampion, Point2D p) {
        if (isEmpty()) return null;
        double currentChampionDistance = p.distanceTo(currentChampion.p);
        if (node == null || currentChampionDistance < node.rect.distanceTo(p)) {
            return currentChampion;
        }
        double currentDistance = p.distanceTo(node.p);
        if (currentDistance < currentChampionDistance) {
            currentChampion = node;
        }
        if (node.lb != null && node.rt != null) {
            if (node.lb.rect.distanceSquaredTo(p) < node.rt.rect.distanceSquaredTo(p)) {
                //go to left tree first
                currentChampion = nearest(node.lb, currentChampion, p);
                currentChampion = nearest(node.rt, currentChampion, p);
            }
            else {
                // go to right tree first
                currentChampion = nearest(node.rt, currentChampion, p);
                currentChampion = nearest(node.lb, currentChampion, p);
            }
        }
        else {
            currentChampion = nearest(node.lb, currentChampion, p);
            currentChampion = nearest(node.rt, currentChampion, p);
        }
        return currentChampion;
    }

    public Point2D nearest(
            Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null)
            throw new IllegalArgumentException("calls nearest() with a null point");

        Node resNode = nearest(root, root, p);
        if (resNode == null)
            return null;
        return resNode.p;
    }

    public static void main(String[] args) {
        KdTree test = new KdTree();
        // Point2D point = new Point2D(0.7, 0.2);
        test.insert(new Point2D(0.7, 0.2));
        test.insert(new Point2D(0.5, 0.4));
        test.insert(new Point2D(0.2, 0.3));
        test.insert(new Point2D(0.4, 0.7));
        test.insert(new Point2D(0.9, 0.6));
        test.nearest(new Point2D(0.65, 0.53));
    }
}
