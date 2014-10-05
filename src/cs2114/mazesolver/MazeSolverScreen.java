package cs2114.mazesolver;

import cs2114.mazesolver.ILocation;
import cs2114.mazesolver.MazeCell;
import android.widget.TextView;
import sofia.graphics.RectangleShape;
import sofia.graphics.Color;
import sofia.app.ShapeScreen;

// -------------------------------------------------------------------------
/**
 * MazeSolverScreen is the
 *
 * @author Kara Vaillancourt kvail94
 * @version 2014.03.23
 */
public class MazeSolverScreen
    extends ShapeScreen
    {
        // ~ Fields ................................................................
        private LifeBase           earth;
        private RectangleShape[][] grid;
        private float              cellSize;
        private mode               setting;


        // ----------------------------------------------------------

        /**
         * Initialize the visual shape grid and the lifeBase object Then populates it.
         */
        public void initialize()
        {
            int size = 30;
            earth = new LifeBase(size); // Create a new earth
            grid = new RectangleShape[size][size];

            cellSize = Math.min(getHeight(), getWidth()) / size;

            RectangleShape c = null;

            for (int i = 0; i < size; i++)
            {
                for (int j = 0; j < size; j++)
                {
                    float y = cellSize * j;
                    float x = cellSize * i;
                    c = new RectangleShape(x, y, x + cellSize, y + cellSize);
                    grid[i][j] = c;
                    c.setColor(Color.black);
                    c.setFillColor(Color.white);

                    add(c);

                }

            }
            earth.addObserver(this);
        }


        /**
         * Updates the colors of the screen
         *
         * @param changingGrid
         *            grid to edit
         * @param location
         *            location to edit
         */
        public void changeWasObserved(LifeBase changingGrid, ILocation location)
        {
            switch (earth.getCell(location))
            {
                case WALL:
                    grid[location.x()][location.y()].setFillColor(Color.black);
                    break;
                case UNEXPLORED:
                    grid[location.x()][location.y()].setFillColor(Color.white);
                    break;
                default:
                    break;
            }

        }


        // }

        // -------------------------------------------------------------------------
        /**
         * Button enum keeps track of which button is pressed.
         */
        public enum mode
        {

            /**
             * Signals that the stop button was pressed
             */
            Stop,
            /**
             * signals that the erase button was pressed
             */
            Erase,

            /**
             * signals that the draw button was pressed
             */
            Draw

        }


        // ----------------------------------------------------------
        /**
         * Checks to see if the EraseMode button is on.
         */
        public void eraseClicked()
        {

            setting = mode.Erase;
        }


        // ----------------------------------------------------------
        /**
         * Set Draw Wall Mode
         */
        public void drawClicked()
        {

            setting = mode.Draw;
        }


        // ----------------------------------------------------------
        /**
         * Plays the grid when the solve button is clicked
         */
        public void playClicked()
        {
            int x = 0;
            while (x < 50)
            {
                earth.play();
                x++;
            }
        }


        // ----------------------------------------------------------
        /**
         * Stops the earth ToBe implemented
         */
        public void stopClicked()
        {
            setting = mode.Stop;
        }


        /**
         * Changes the tiles of the grid when the user touches each cell. If the
         * eraseMode is on, the cell is destroyed, otherwise one is created.
         *
         * @param x
         *            coordinate
         * @param y
         *            coordinate
         */
        public void onTouchDown(float x, float y)
        {
            int xCell = (int)(x / cellSize);
            int yCell = (int)(y / cellSize);
            ILocation loc = new Location(xCell, yCell);

            RectangleShape shape =
                getShapes().locatedAt(x, y).withClass(RectangleShape.class).front();

            if (shape != null)
            {
                if (setting == mode.Draw)
                {
                    earth.setCell(loc, MazeCell.WALL);
                    grid[xCell][yCell].setFillColor(Color.black);
                }
                else if (setting == mode.Erase)// the erase mode is on
                {
                    earth.setCell(loc, MazeCell.UNEXPLORED);
                    grid[xCell][yCell].setFillColor(Color.white);
                }
            }

        }


        // ----------------------------------------------------------
        /**
         * Calls the onTouchDown method when the user slides his/her finger across
         * the earth.
         *
         * @param x
         *            coordinate
         * @param y
         *            coordinate
         */
        public void onTouchMove(float x, float y)
        {
            onTouchDown(x, y);
        }


        // ----------------------------------------------------------
        /**
         * Returns the current grid
         *
         * @return the grid
         */
        public LifeBase getGrid()
        {
            return earth;
        }


        // ----------------------------------------------------------
        /**
         * Returns the shape at the corresponding x and y position
         *
         * @param x
         *            coordinate
         * @param y
         *            coordinate
         * @return rectangleShape at that position
         */
        public RectangleShape getCell(int x, int y)
        {
            return grid[x][y];
        }

    }
