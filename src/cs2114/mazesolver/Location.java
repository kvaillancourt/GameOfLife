package cs2114.mazesolver;

import cs2114.mazesolver.ILocation;

// -------------------------------------------------------------------------
/**
 * Location is the cells for the Maze class. They describe the x, y coordinates
 * of the location.
 *
 * @author Vaillancourt
 * @version Feb 23, 2014
 */
public class Location
    implements ILocation
{
    private int      x;
    private int      y;
    private Location loc;


    // ----------------------------------------------------------
    /**
     * Create a new Location object.
     *
     * @param x
     *            coordinate
     * @param y
     *            coordinate
     */
    public Location(int x, int y)
    {
        this.x = x;
        this.y = y;
    }


    // ----------------------------------------------------------
    /**
     * Equals method
     *
     * @return if the two are equal
     * @override
     * @param obj is the object used for comparison.
     */
    public boolean equals(Object obj)
    {

        Location test;

        if (obj instanceof Location)
        {
            test = (Location)obj;
            if (this.x() == (test.x()) && this.y() == (test.y()))
            {
                return true;
            }
        }

        return false;
    }


    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }


    @Override
    public ILocation east()
    {
        loc = new Location(x() + 1, y());
        return loc;
    }


    @Override
    public ILocation north()
    {
        loc = new Location(x(), y() - 1);
        return loc;
    }


    @Override
    public ILocation south()
    {
        loc = new Location(x(), y() + 1);
        return loc;
    }


    @Override
    public ILocation west()
    {
        loc = new Location(x() - 1, y());
        return loc;
    }


    @Override
    public int x()
    {
        return x;
    }


    @Override
    public int y()
    {
        return y;
    }
}
