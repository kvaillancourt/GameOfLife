package cs2114.mazesolver;

import cs2114.mazesolver.ILocation;
import cs2114.mazesolver.MazeCell;
import java.util.*;
import sofia.util.Observable;

// -------------------------------------------------------------------------
/**
 * Life Base Class
 *
 * @author Vaillancourt
 * @version Mar 23, 2014
 */
public class LifeBase
    extends Observable
{
    private MazeCell[][] board;

    private int          size;


    /**
     * Constructor of Life grid.
     *
     * @param size
     *            indicates size of square maze.
     */
    public LifeBase(int size)
    {

        this.size = size;
        board = new MazeCell[size][size];
// goalLocation = new Location(size - 1, size - 1);
// startLocation = new Location(0, 0);

        for (int m = 0; m < size; m++)
        {
            for (int n = 0; n < size; n++)
            {
                board[m][n] = MazeCell.UNEXPLORED;

            }
        }
    }


    /**
     * Returns the cell at the location.
     *
     * @return returns specific cell.
     * @param loc
     *            indicates the location concerned.
     */
    public MazeCell getCell(ILocation loc)
    {
        if (loc.x() < size() && loc.x() >= 0 && loc.y() >= 0
            && loc.y() < size())
        {
            return board[loc.x()][loc.y()];
        }
        else
        {
            return MazeCell.INVALID_CELL;
        }
    }


    /**
     * Sets the cell.
     *
     * @param location
     *            indicates the location to set the new cell to.
     * @param cellType
     *            indicates the cell you want to change.
     */
    public void setCell(ILocation location, MazeCell cellType)
    {
        if (location.x() < size && location.y() < size
            && (location.x() >= 0 && location.y() >= 0))
        {
            board[location.x()][location.y()] = cellType;
        }
        notifyObservers(location);

    }


    /**
     * Returns the size.
     *
     * @return size of the grid.
     */
    public int size()
    {
        return board.length;
    }


    /**
     * Finds the number of neighbors to that location
     * @param loc is the specified location
     *
     * @return Returns the number of neighbors
     */
    public int getNeighbors(ILocation loc)
    {
        ILocation locCount = new Location(0, 0);
        int count = 0;
        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++)
            {
                locCount = new Location(loc.x() + i, loc.y() + j);

                if (getCell(locCount).equals(MazeCell.WALL)
                    && !locCount.equals(loc))
                    count++;

            }
        return count;
    }


    // ----------------------------------------------------------
    /**
     * Plays the world
     */
    public void play()
    {
        ILocation loc = new Location(0, 0);
        int[][] states = new int[size][size];

        for (int k = 0; k < size; k++)
            for (int j = 0; j < size; j++)
            {
                loc = new Location(k, j);
                // if neighbors < 2 or > 3 then die
                if (getNeighbors(loc) < 2 || getNeighbors(loc) > 3)
                    states[k][j] = 0;
                //
                else if (getCell(loc).equals(MazeCell.UNEXPLORED)
                    && getNeighbors(loc) == 3)
                    states[k][j] = 1;
                else if (getCell(loc).equals(MazeCell.WALL)
                    && (getNeighbors(loc) == 2 || getNeighbors(loc) == 3))
                    states[k][j] = 1;

            }

        for (int k = 0; k < size; k++)
            for (int j = 0; j < size; j++)
            {
                loc = new Location(k, j);
                // if neighbors < 2 or > 3 then die
                if (states[k][j] == 0)
                    setCell(loc, MazeCell.UNEXPLORED);
                //
                else if (states[k][j] == 1)
                    setCell(loc, MazeCell.WALL);

            }

    }
}
