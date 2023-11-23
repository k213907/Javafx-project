package com.example.game;
import java.util.*;
import java.io.*;

public class Board {
    public final int NUM_START_TILES = 2;
    public final int TWO_PROBABILITY = 86;
    public final int GRID_SIZE;


    private final Random random;
    private int[][] grid;
    private int score;

    public Board(Random random, int boardSize) {
        GRID_SIZE = boardSize;
        this.grid = new int[GRID_SIZE][GRID_SIZE];
        this.random = random;
        addRandomTile();
        addRandomTile();
    }

    public Board(Random random, String inputBoard) throws IOException {
        File myFile = new File(inputBoard);
        Scanner myScanner = new Scanner(myFile);
        this.random = random;
        GRID_SIZE = Integer.parseInt(myScanner.next());
        this.score = Integer.parseInt(myScanner.next());
        this.grid = new int[GRID_SIZE][GRID_SIZE];
        for ( int i = 0; i < GRID_SIZE; i++)
        {
            for ( int j = 0; j < GRID_SIZE; j++)
            {
                this.grid[i][j] = Integer.parseInt(myScanner.next());
            }
        }
    }

    public void saveBoard(String outputBoard) throws IOException {
        File myFile = new File(outputBoard);
        PrintWriter myWriter = new PrintWriter(myFile);

        int gridSize = this.GRID_SIZE;
        myWriter.print(gridSize);
        myWriter.println();
        int score = this.score;
        myWriter.print(score);
        myWriter.println();
        for(int x = 0; x < this.GRID_SIZE; x++)
        {
            for(int y = 0; y < this.GRID_SIZE; y++)
            {
                myWriter.print(this.grid[x][y]);
                myWriter.print(" ");
            }
            myWriter.println();
        }

        myWriter.close();


    }
    public void addRandomTile() {
        int count = 0;
        for (int x = 0; x < GRID_SIZE; x++)
        {
            for (int y = 0; y < GRID_SIZE; y++)
            {
                if( this.grid[x][y] == 0 )
                {
                    count++;
                }
            }
        }

        if(count == 0)
            return;

        int location = random.nextInt(count);
        int value = random.nextInt(100);
        int counter = 0;

        for(int x = 0; x < GRID_SIZE; x++)
        {
            for(int y = 0; y < GRID_SIZE; y++)
            {
                if( this.grid[x][y] == 0)
                {
                    if( counter == location )
                    {

                        if( value < TWO_PROBABILITY )
                        {
                            this.grid[x][y] = 2;
                        }
                        else
                            this.grid[x][y] = 4;
                    }
                    counter++;
                }
            }
        }


    }

    private boolean canMoveLeft()
    {
        for(int x = 0; x < this.GRID_SIZE; x++)
        {
            for(int y = 0; y < this.GRID_SIZE-1; y++)
            {
                if( (this.grid[x][y+1] != 0 && this.grid[x][y] == 0)||
                        ((this.grid[x][y] == this.grid[x][y+1])
                                && this.grid[x][y] > 0) )
                {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canMoveRight()
    {
        for(int x = 0; x < this.GRID_SIZE; x++)
        {
            for(int y = this.GRID_SIZE-1; y >= 1; y--)
            {
                if((this.grid[x][y-1] != 0 && this.grid[x][y] == 0) ||
                        ((this.grid[x][y] == this.grid[x][y-1]) &&
                                this.grid[x][y] > 0))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canMoveDown()
    {
        for(int y = 0; y < this.GRID_SIZE; y++)
        {
            for(int x = this.GRID_SIZE-1; x >= 1; x--)
            {
                if((this.grid[x-1][y] != 0 && this.grid[x][y] == 0) ||
                        ((this.grid[x][y] == this.grid[x-1][y])
                                && this.grid[x][y] > 0))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canMoveUp()
    {
        for(int y = 0; y < this.GRID_SIZE; y++)
        {
            for(int x = 0; x < this.GRID_SIZE -1; x++)
            {
                if((this.grid[x+1][y] != 0 && this.grid[x][y] == 0)||
                        ((this.grid[x][y] == this.grid[x+1][y])
                                && this.grid[x][y] > 0))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean canMove(Directions direction){

        if(direction.equals(Directions.UP))
        {
            if(canMoveUp())
            {
                return true;
            }
        }

        if(direction.equals(Directions.DOWN))
        {
            if(canMoveDown())
            {
                return true;
            }
        }

        if(direction.equals(Directions.RIGHT))
        {
            if(canMoveRight())
            {
                return true;
            }
        }

        if(direction.equals(Directions.LEFT))
        {
            if(canMoveLeft())
            {
                return true;
            }
        }
        return false;
    }

    private boolean moveRight()
    {

        for(int maxMoves = 0; maxMoves < this.GRID_SIZE; maxMoves++)
        {
            for(int column = 0; column < this.GRID_SIZE; column++)
            {
                for(int row = this.GRID_SIZE-1; row >= 1; row--)
                {
                    if(this.grid[column][row-1]!=0 && this.grid[column][row]==0)
                    {
                        this.grid[column][row] = this.grid[column][row-1];
                        this.grid[column][row-1] = 0;
                    }
                }
            }
        }

        for(int column = 0; column < this.GRID_SIZE; column++)
        {
            for(int row = this.GRID_SIZE-1; row >= 1; row--)
            {
                if((this.grid[column][row] == this.grid[column][row-1])
                        && this.grid[column][row]>0)
                {
                    this.grid[column][row] = this.grid[column][row] +
                            this.grid[column][row-1];
                    this.grid[column][row-1] = 0;
                    this.score += this.grid[column][row];
                }
            }
        }


        for(int maxMoves = 0; maxMoves < this.GRID_SIZE; maxMoves++)
        {
            for(int column = 0; column < this.GRID_SIZE; column++)
            {
                for(int row = this.GRID_SIZE-1; row >= 1; row--)
                {
                    if(this.grid[column][row-1]!=0 && this.grid[column][row]==0)
                    {
                        this.grid[column][row] = this.grid[column][row-1];
                        this.grid[column][row-1] = 0;
                    }
                }
            }
        }


        return true;
    }

    private boolean moveLeft()
    {

        for(int maxMoves = 0; maxMoves < this.GRID_SIZE; maxMoves++)
        {
            for(int column = 0; column < this.GRID_SIZE; column++)
            {
                for(int row = 0; row < this.GRID_SIZE -1; row++)
                {
                    if(this.grid[column][row+1]!=0 && this.grid[column][row]==0)
                    {
                        this.grid[column][row] = this.grid[column][row+1];
                        this.grid[column][row+1] = 0;
                    }
                }
            }
        }

        for(int column = 0; column < this.GRID_SIZE; column++)
        {
            for(int row = 0; row < this.GRID_SIZE - 1; row++)
            {
                if((this.grid[column][row] == this.grid[column][row+1])
                        && this.grid[column][row]>0)
                {
                    this.grid[column][row] = this.grid[column][row] +
                            this.grid[column][row+1];
                    this.grid[column][row+1] = 0;
                    this.score += this.grid[column][row];
                }
            }
        }

        for(int maxMoves = 0; maxMoves < this.GRID_SIZE; maxMoves++)
        {
            for(int column = 0; column < this.GRID_SIZE; column++)
            {
                for(int row = 0; row < this.GRID_SIZE -1; row++)
                {
                    if(this.grid[column][row+1]!=0 && this.grid[column][row]==0)
                    {
                        this.grid[column][row] = this.grid[column][row+1];
                        this.grid[column][row+1] = 0;
                    }
                }
            }
        }


        return true;
    }

    private boolean moveUp()
    {


        for(int maxMoves = 0; maxMoves < this.GRID_SIZE; maxMoves++)
        {
            for(int row = 0; row < this.GRID_SIZE; row++)
            {
                for(int column = 0; column < this.GRID_SIZE -1; column++)
                {
                    if(this.grid[column+1][row]!=0 && this.grid[column][row]==0)
                    {
                        this.grid[column][row] = this.grid[column+1][row];
                        this.grid[column+1][row] = 0;
                    }
                }
            }
        }

        for(int row = 0; row < this.GRID_SIZE; row++)
        {
            for(int column = 0; column < this.GRID_SIZE-1; column++)
            {
                if((this.grid[column][row] == this.grid[column+1][row])
                        && this.grid[column][row]>0)
                {
                    this.grid[column][row] = this.grid[column][row] +
                            this.grid[column+1][row];
                    this.grid[column+1][row] = 0;
                    this.score += this.grid[column][row];
                }
            }
        }


        for(int maxMoves = 0; maxMoves < this.GRID_SIZE; maxMoves++)
        {
            for(int row = 0; row < this.GRID_SIZE; row++)
            {
                for(int column = 0; column < this.GRID_SIZE -1; column++)
                {
                    if(this.grid[column+1][row]!=0 && this.grid[column][row]==0)
                    {
                        this.grid[column][row] = this.grid[column+1][row];
                        this.grid[column+1][row] = 0;
                    }
                }
            }
        }


        return true;
    }

    private boolean moveDown()
    {

        for(int maxMoves = 0; maxMoves < this.GRID_SIZE; maxMoves++)
        {
            for(int row = 0; row < this.GRID_SIZE; row++)
            {
                for(int column = this.GRID_SIZE-1; column >= 1; column--)
                {
                    if(this.grid[column-1][row]!=0 && this.grid[column][row]==0)
                    {
                        this.grid[column][row] = this.grid[column-1][row];
                        this.grid[column-1][row] = 0;
                    }
                }
            }
        }
        for(int row = 0; row < this.GRID_SIZE; row++)
        {
            for(int column = this.GRID_SIZE-1; column >= 1; column--)
            {
                if((this.grid[column][row] == this.grid[column-1][row])
                        && this.grid[column][row]>0)
                {
                    this.grid[column][row] = this.grid[column][row] +
                            this.grid[column-1][row];
                    this.grid[column-1][row] = 0;
                    this.score += this.grid[column][row];
                }
            }
        }

        for(int maxMoves = 0; maxMoves < this.GRID_SIZE; maxMoves++)
        {
            for(int row = 0; row < this.GRID_SIZE; row++)
            {
                for(int column = this.GRID_SIZE-1; column >= 1; column--)
                {
                    if(this.grid[column-1][row]!=0 && this.grid[column][row]==0)
                    {
                        this.grid[column][row] = this.grid[column-1][row];
                        this.grid[column-1][row] = 0;
                    }
                }
            }
        }


        return true;
    }
    public boolean move(Directions direction) {
        if(canMove(direction))
        {

            if(direction.equals(direction.RIGHT))
            {
                moveRight();
                return true;
            }

            if(direction.equals(direction.LEFT))
            {
                moveLeft();
                return true;
            }

            if(direction.equals(direction.UP))
            {
                moveUp();
                return true;
            }

            if(direction.equals(direction.DOWN))
            {
                moveDown();
                return true;
            }
        }
        return false;
    }
    public boolean isGameOver() {
        if(canMove(Directions.UP))
        {return false;}

        if(canMove(Directions.DOWN))
        {return false;}

        if(canMove(Directions.RIGHT))
        {return false;}

        if(canMove(Directions.LEFT))
        {return false;}

        return true;
    }


    public int[][] getGrid() {
        return grid;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -" :
                        String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }
}

