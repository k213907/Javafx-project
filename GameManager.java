package com.example.game;
import java.util.*;
import java.io.*;

public class GameManager {

    private Board board;
    private String outputBoard;


    private String outputRecord;
    StringBuilder history = new StringBuilder();

    public GameManager(String outputBoard, int boardSize, Random random) {
        this.outputBoard = outputBoard;
        this.board = new Board(random, boardSize);
    }

    public GameManager(String inputBoard, String outputBoard, Random random) throws IOException {
        this.outputBoard = outputBoard;
        this.board = new Board(random, inputBoard);

    }

    public void play() throws Exception {
        this.printControls();
        System.out.print(this.board);
        Scanner reader = new Scanner(System.in);


        while(this.board.isGameOver() == false)
        {
            String temp = reader.next();
            if(temp.equals("w"))
            {
                if(this.board.move(Directions.UP))
                {
                    this.board.addRandomTile();
                    System.out.print(this.board);
                }

                else if(this.board.move(Directions.UP) == false)
                {
                    System.out.println("Can't move in this direction!");
                    System.out.print(this.board);
                }
            }


            else if(temp.equals("s"))
            {
                if(this.board.move(Directions.DOWN))
                {
                    this.board.addRandomTile();
                    System.out.print(this.board);
                }
                else if(this.board.move(Directions.DOWN) == false)
                {
                    System.out.println("Can't move in this direction!");
                    System.out.print(this.board);
                }
            }


            else if(temp.equals("d"))
            {
                if(this.board.move(Directions.RIGHT))
                {
                    this.board.addRandomTile();
                    System.out.print(this.board);
                }
                else if(this.board.move(Directions.RIGHT) == false)
                {
                    System.out.println("Can't move in this direction!");
                    System.out.print(this.board);
                }
            }


            else if(temp.equals("a"))
            {
                if(this.board.move(Directions.LEFT))
                {
                    this.board.addRandomTile();
                    System.out.print(this.board);
                }
                else if(this.board.move(Directions.LEFT) == false)
                {
                    System.out.println("Can't move in this direction!");
                    System.out.print(this.board);
                }
            }

            else if(temp.equals("q"))
            {
                this.board.saveBoard(this.outputBoard);
                return;
            }

            else
            {
                this.printControls();
            }
        }

        System.out.println("GAME OVER!");

        this.board.saveBoard(this.outputBoard);

    }

    private void printControls() {
        System.out.println("  Controls:");
        System.out.println("    w - Move the tiles Up");
        System.out.println("    s - Move the tiles Down");
        System.out.println("    a - Move  the tiles Left");
        System.out.println("    d - Move the tiles Right");
        System.out.println("    q - Quit the game and Save Board");
        System.out.println();
    }
}

