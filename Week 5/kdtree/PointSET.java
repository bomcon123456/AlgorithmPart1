/* *****************************************************************************
 *  Name: Termanteus
 *  Date: 01/06/2019
 *  Description: PointSET
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    final private SET<Point2D> m_set;

    public PointSET()                               // construct an empty set of points
    {
        m_set = new SET<>();
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return m_set.isEmpty();
    }

    public int size()                         // number of points in the set
    {
        return m_set.size();
    }

    public void insert(
            Point2D p)              // add the point to the set (if it is not already in the set)
    {
        m_set.add(p);
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        return m_set.contains(p);
    }

    public void draw()                         // draw all points to standard draw
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        m_set.forEach((p -> p.draw()));
    }

    public Iterable<Point2D> range(
            RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null) throw new IllegalArgumentException("call range() with null args");
        Stack<Point2D> res = new Stack<>();
        for (Point2D p : m_set) {
            if (rect.contains(p)) {
                res.push(p);
            }
        }
        return res;
    }


    public Point2D nearest(
            Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null) throw new IllegalArgumentException("call range() with null args");

        double res = Double.POSITIVE_INFINITY;
        Point2D ret = null;
        for (Point2D o : m_set) {
            double dist = p.distanceTo(o);
            if (dist < res) {
                res = dist;
                ret = o;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
    }
}
