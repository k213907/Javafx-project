package com.example.game;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;

import java.io.*;

public class Gui2048 extends Application
{
    private String outputBoard;
    private Board board;
    private GridPane pane;
    private Rectangle[][] grid;
    private StackPane stack;
    private BorderPane borderPane;
    private Scene scene;



    @Override
    public void start(Stage primaryStage)
    {
        processArgs(getParameters().getRaw().toArray(new String[0]));

        stack = new StackPane();
        stack.setAlignment(Pos.CENTER);
        stack.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        stack.setStyle("-fx-background-color: rgb(187, 172, 160)");


        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setStyle("-fx-background-color: rgb(187, 172, 160)");

        pane.setHgap(15);
        pane.setVgap(15);

        this.borderPane = new BorderPane();
        this.borderPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        this.borderPane.setStyle("-fx-background-color: rgb(187, 173, 160)");


        makeHeader();
        this.grid = new Rectangle[board.GRID_SIZE][board.GRID_SIZE];
        makeNewPane();


        scene = new Scene(stack);
        drawBoard(getPane(), getBoard());
        scene.setOnKeyPressed(new myKeyHandler());


        primaryStage.setTitle("JavaFx_2048");
        primaryStage.setScene(scene);
        primaryStage.setHeight(700);
        primaryStage.setWidth(700);
        primaryStage.show();
    }

    private int getValue(int x, int y)
    {
        int[][] grid = this.board.getGrid();
        return grid[x][y];
    }

    private void makeHeader()
    {

        Text textTitle = new Text();
        textTitle.setText("2048");
        textTitle.setFont(Font.font("Times New Roman",FontWeight.BOLD, 30));
        textTitle.setFill(Constants2048.COLOR_VALUE_DARK);


        int score = board.getScore();
        Text scoreTitle = new Text();
        scoreTitle.setText("Score: " + Integer.toString(score));
        scoreTitle.setFont(Font.font("Times New Roman",FontWeight.BOLD,30));
        scoreTitle.setFill(Constants2048.COLOR_VALUE_DARK);


        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(100);
        hBox.setStyle("-fx-background-color: rgb(187, 173, 160)");
        hBox.getChildren().addAll(textTitle, scoreTitle);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefWidth(150);

        getBorder().setTop(hBox);
        getStack().getChildren().clear();
        getStack().getChildren().add(getBorder());
    }

    private BorderPane getBorder()
    {
        return this.borderPane;
    }

    private void makeNewPane()
    {
        this.pane.getChildren().clear();
        for(int row = 0; row < board.GRID_SIZE; row++)
        {
            for(int column = 0; column < board.GRID_SIZE; column++)
            {
                getGrid()[column][row] = new Rectangle();
                getGrid()[column][row].setFill(Constants2048.COLOR_EMPTY);
                getGrid()[column][row].widthProperty().bind(getStack().widthProperty().multiply(((double)(1/board.GRID_SIZE)) + 0.15));
                getGrid()[column][row].heightProperty().bind(getStack().heightProperty().multiply(((double)(1/board.GRID_SIZE)) + 0.15));
                this.pane.add(this.grid[column][row], row, column);
                getBorder().setCenter(this.pane);
            }
        }

        getStack().getChildren().clear();
        getStack().getChildren().add(getBorder());
    }

    private Rectangle[][] getGrid()
    {
        return this.grid;
    }
    private StackPane getStack()
    {
        return this.stack;
    }
    private GridPane getPane()
    {
        return this.pane;
    }

    private Board getBoard()
    {
        return this.board;
    }
    public void drawBoard(GridPane pane, Board board)
    {
        for(int row = 0; row < board.GRID_SIZE; row++)
        {
            for(int column = 0; column < board.GRID_SIZE; column++)
            {

                if(getValue(column, row) > 0)
                {
                    Rectangle tile = new Rectangle();
                    tile.widthProperty().bind(getStack().widthProperty().multiply(              ((double)(1/board.GRID_SIZE)) + 0.15));
                    tile.heightProperty().bind(getStack().heightProperty().multiply             (((double)(1/board.GRID_SIZE))+ 0.15));

                    if(getValue(column, row) == 2)
                        tile.setFill(Constants2048.COLOR_2);
                    else if(getValue(column, row) == 4)
                        tile.setFill(Constants2048.COLOR_4);
                    else if(getValue(column, row) == 8)
                        tile.setFill(Constants2048.COLOR_8);
                    else if(getValue(column, row) == 16)
                        tile.setFill(Constants2048.COLOR_16);
                    else if(getValue(column, row) == 32)
                        tile.setFill(Constants2048.COLOR_32);
                    else if(getValue(column, row) == 64)
                        tile.setFill(Constants2048.COLOR_64);
                    else if(getValue(column, row) == 128)
                        tile.setFill(Constants2048.COLOR_128);
                    else if(getValue(column, row) == 256)
                        tile.setFill(Constants2048.COLOR_256);
                    else if(getValue(column, row) == 512)
                        tile.setFill(Constants2048.COLOR_512);
                    else if(getValue(column, row) == 1024)
                        tile.setFill(Constants2048.COLOR_1024);
                    else if (getValue(column, row) == 2048)
                        tile.setFill(Constants2048.COLOR_2048);
                    else if(getValue(column, row) > 2048)
                        tile.setFill(Constants2048.COLOR_OTHER);

                    Text tileValue = new Text();
                    int value = getValue(column, row);
                    String tileNumber = Integer.toString(value);
                    tileValue.setText(tileNumber);

                    if( (value > 0 ) && (value < 128))
                    {
                        tileValue.setFont(Font.font("Times New Roman",
                                FontWeight.BOLD, Constants2048.TEXT_SIZE_LOW));
                    }

                    else if( (value >= 128) && (value <  1024))
                    {
                        tileValue.setFont(Font.font("Times New Roman",
                                FontWeight.BOLD, Constants2048.TEXT_SIZE_MID));
                    }

                    else if( (value >= 1024) )
                    {
                        tileValue.setFont(Font.font("Times New Roman",
                                FontWeight.BOLD, Constants2048.TEXT_SIZE_HIGH));
                    }


                    if(value >= 8 )
                    {
                        tileValue.setFill(Constants2048.COLOR_VALUE_LIGHT);
                    }

                    else
                        tileValue.setFill(Constants2048.COLOR_VALUE_DARK);

                    pane.add(tile, row, column );
                    pane.add(tileValue, row, column);

                    GridPane.setHalignment(tileValue, HPos.CENTER);

                    getBorder().setCenter(pane);

                    getStack().getChildren().clear();
                    getStack().getChildren().add(getBorder());
                }
            }
        }
    }

    private class myKeyHandler implements EventHandler<KeyEvent>{

        @Override
        public void handle(KeyEvent e)
        {
            if(getBoard().isGameOver() == false)
            {

                if(e.getCode()==KeyCode.UP)
                {

                    if(getBoard().canMove(Directions.UP))
                    {
                        System.out.println("Moving Up");
                        getBoard().move(Directions.UP);
                        getBoard().addRandomTile();
                        makeNewPane();
                        drawBoard(getPane(), getBoard());
                        makeHeader();
                        getBorder().setCenter(getPane());
                        getStack().getChildren().clear();
                        getStack().getChildren().add(getBorder());
                    }
                }


                else if(e.getCode()==KeyCode.DOWN)
                {

                    if(getBoard().canMove(Directions.DOWN))
                    {
                        System.out.println("Moving Down");

                        getBoard().move(Directions.DOWN);
                        getBoard().addRandomTile();
                        makeNewPane();
                        drawBoard(getPane(), getBoard());

                        makeHeader();

                        getBorder().setCenter(getPane());
                        getStack().getChildren().clear();
                        getStack().getChildren().add(getBorder());
                    }
                }


                else if(e.getCode()==KeyCode.RIGHT)
                {

                    if(getBoard().canMove(Directions.RIGHT))
                    {
                        System.out.println("Moving Right");

                        getBoard().move(Directions.RIGHT);
                        getBoard().addRandomTile();
                        makeNewPane();
                        drawBoard(getPane(), getBoard());

                        makeHeader();

                        getBorder().setCenter(getPane());
                        getStack().getChildren().clear();
                        getStack().getChildren().add(getBorder());
                    }
                }


                else if(e.getCode()==KeyCode.LEFT)
                {

                    if(getBoard().canMove(Directions.LEFT))
                    {
                        System.out.println("Moving Left");
                        getBoard().move(Directions.LEFT);
                        getBoard().addRandomTile();
                        makeNewPane();

                        drawBoard(getPane(), getBoard());

                        makeHeader();

                        getBorder().setCenter(getPane());
                        getStack().getChildren().clear();
                        getStack().getChildren().add(getBorder());
                    }
                }


                else if(e.getCode() == KeyCode.S)
                {
                    String outputFileName = "outputFileName";
                    try
                    {
                        getBoard().saveBoard(outputFileName);
                    }
                    catch (IOException s)
                    {
                        System.out.println("saveBoard threw an exception");
                    }
                    System.out.println("Saving Board to " + outputFileName);
                }

            }


            else if( getBoard().isGameOver() == true)
            {
                Rectangle blur = new Rectangle();
                blur.widthProperty().bind(getStack().widthProperty());
                blur.heightProperty().bind(getStack().heightProperty());
                blur.setFill(Constants2048.COLOR_GAME_OVER);
                blur.setOpacity(0.5);
                Text gameOver = new Text();
                gameOver.setText("Game Over!");
                gameOver.setFont(Font.font("Times New Roman", FontWeight.BOLD, 70));
                gameOver.setFill(Color.BLACK);
                getBorder().setCenter(getPane());
                getStack().getChildren().clear();
                getStack().getChildren().add(getBorder());
                getStack().getChildren().add(blur);
                getStack().getChildren().add(gameOver);

            }
        }
    }

    private void processArgs(String[] args)
    {
        String inputBoard = null;
        int boardSize = 0;

        if((args.length % 2) != 0)
        {
            printUsage();
            System.exit(-1);
        }

        for(int i = 0; i < args.length; i += 2)
        {
            if(args[i].equals("-i"))
            {
                inputBoard = args[i + 1];
            }
            else if(args[i].equals("-o"))
            {
                outputBoard = args[i + 1];
            }
            else if(args[i].equals("-s"))
            {
                boardSize = Integer.parseInt(args[i + 1]);
            }
            else
            {
                printUsage();
                System.exit(-1);
            }
        }


        if(outputBoard == null)
            outputBoard = "JavaFX_2048.board";

        if(boardSize < 2)
            boardSize = 4;

        try{
            if(inputBoard != null)
                board = new Board(new Random(), inputBoard);
            else
                board = new Board(new Random(), boardSize);
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getName() +
                    " was thrown while creating a " +
                    "Board from file " + inputBoard);
            System.out.println("Either your Board(String, Random) " +
                    "Constructor is broken or the file isn't " +
                    "formated correctly");
            System.exit(-1);
        }
    }

    // Print the Usage Message
    private static void printUsage()
    {
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the "+
                "form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that " +
                "should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be " +
                "used to save the 2048 board");
        System.out.println("                If none specified then the " +
                "default \"2048.board\" file will be used");
        System.out.println("  -s [size]  -> Specifies the size of the 2048" +
                "board if an input file hasn't been");
        System.out.println("                specified.  If both -s and -i" +
                "are used, then the size of the board");
        System.out.println("                will be determined by the input" +
                " file. The default size is 4.");
    }


}





