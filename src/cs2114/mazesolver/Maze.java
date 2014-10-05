package cs2114.mazesolver;

import cs2114.mazesolver.ILocation;
import cs2114.mazesolver.IMaze;
import cs2114.mazesolver.MazeCell;
import java.util.*;
import sofia.util.Observable;

// -------------------------------------------------------------------------
/**
 *  Maze class is the back end code for the MazeSolver app
 *
 *  @author Vaillancourt
 *  @version Mar 23, 2014
 */
public class Maze
    extends Observable
    implements IMaze
{
    private MazeCell[][]     board;

    private int              size;


    private ILocation        goalLocation;
    private ILocation        startLocation;

    private Stack<ILocation> solveStack;


    /**
     * Constructor of Maze.
     *
     * @param size
     *            indicates size of square maze.
     */
    public Maze(int size)
    {

        this.size = size;
        board = new MazeCell[size][size];
        goalLocation = new Location(size - 1, size - 1);
        startLocation = new Location(0, 0);

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
    @Override
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
     * Gets goal location.
     *
     * @return Returns goal location of the maze.
     */
    @Override
    public ILocation getGoalLocation()
    {

        return goalLocation;
    }


    /**
     * Gets the start location of maze.
     *
     * @return the start location of the maze.
     */
    @Override
    public ILocation getStartLocation()
    {

        return startLocation;
    }


    /**
     * Sets the cell.
     *
     * @param location
     *            indicates the location to set the new cell to.
     * @param cellType
     *            indicates the cell you want to change.
     */
    @Override
    public void setCell(ILocation location, MazeCell cellType)
    {
        if ((location.equals(startLocation) || location.equals(goalLocation))
            && cellType.equals(MazeCell.WALL))
        {
            return;
        }

        if (location.x() < size && location.y() < size
            && (location.x() >= 0 && location.y() >= 0))
        {
            board[location.x()][location.y()] = cellType;
        }
        notifyObservers(location);

    }


    /**
     * Sets the goal location.
     *
     * @param loc
     *            is the new goal location you want to be set to.
     */
    @Override
    public void setGoalLocation(ILocation loc)
    {

        goalLocation = loc;

        if (getCell(goalLocation) == MazeCell.WALL)
        {
            setCell(goalLocation, MazeCell.UNEXPLORED);
        }
    }


    /**
     * Sets the start location.
     *
     * @param loc
     *            is the new start location you want to be set to.
     */
    @Override
    public void setStartLocation(ILocation loc)
    {

        startLocation = loc;
        if (getCell(startLocation) == MazeCell.WALL)
        {
            setCell(startLocation, MazeCell.UNEXPLORED);
        }
    }


    /**
     * Returns the size.
     *
     * @return size of the maze.
     */
    @Override
    public int size()
    {
        return board.length;
    }


    /**
     * Solves the maze, returning a string representation of the solved
     * coordinates.
     *
     * @return Returns the string version of coordinates to solve the maze.
     *         public String solve() { // return new
     *         MazeSolver(this).checkLocation(); }
     */

    public String solution()

    {
        StringBuilder stringbuilder = new StringBuilder();
        Stack<ILocation> stack2 = new Stack<ILocation>();
        while (!solveStack.isEmpty())
        {
            stack2.push(solveStack.pop());
        }
        while (!stack2.isEmpty())
        {
            if (stack2.size() == 1)
            {
                stringbuilder.append(stack2.pop().toString());
            }
            else
            {
                stringbuilder.append(stack2.pop().toString() + " ");
            }
        }
        return stringbuilder.toString();
    }


    @Override
    public String solve()
    {
        solveStack = new Stack<ILocation>();
        solveStack.push(startLocation);
        ILocation current = startLocation;
        setCell(current, MazeCell.CURRENT_PATH);

        while (!solveStack.empty())
        {
            current = solveStack.peek();
            setCell(current, MazeCell.CURRENT_PATH);

            if (current.equals(goalLocation))
            {
                return solution();
            }
            else
            {
                if (getCell(current.east()).equals(
                        MazeCell.UNEXPLORED))
                {
                    solveStack.push(current.east());
                }
                else if (getCell(current.west()).equals(
                            MazeCell.UNEXPLORED))
                {
                    solveStack.push(current.west());
                }
                else if (getCell(current.south()).equals(
                            MazeCell.UNEXPLORED))
                {
                    solveStack.push(current.south());
                }
                else if (getCell(current.north()).equals(
                            MazeCell.UNEXPLORED))
                {
                    solveStack.push(current.north());
                }
                else
                {
                    setCell(current, MazeCell.FAILED_PATH);
                    solveStack.pop();
                }
            }

            System.out.println(current);

        }
        return null;
    }
}
