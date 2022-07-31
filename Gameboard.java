 

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Gameboard extends Application {

    // the board and position of ships
    private String[][] CPU_Board;

    // dimensions of the board

    private int dim_x;
    private int dim_y;

    // number of shots misses
    private int num_misses;
    // num of total hits
    private int num_hits;
    // number of ships sunk
    private int ships_remaining = 5;

    // number of missiles available
    private int missiles_used = 0;
    private int max_missiles;

    // whether or not the game is won

    private Boolean Won_the_Game = false;

    protected ArrayList<Ship> ShipList = new ArrayList<>();

    protected Tile[][] board;

    protected Pane root = new Pane();


    // allows ActionGame to inherit method and launch the GUI


    protected Parent createContent() {
        // inherited the board and ship positions from action game

        // creating a Top Label To Display Messages to the User While Playing the Game

        Label topLabel = new Label();
        topLabel.setAlignment(Pos.CENTER_RIGHT);
        topLabel.setFont(Font.font(24));
        root.getChildren().add(topLabel);


        topLabel.setText("Welcome to the BattleShip Game" + "\nSelect your Difficulty: ");

        // creating Buttons to select the difficulty

        Button easy = new Button("Beginner");
        easy.relocate(275, 35);
        easy.getOnAction();
        // setting the radio button text

        Button medium = new Button("Standard");
        medium.relocate(350, 35);

        Button hard = new Button("Advanced");
        hard.relocate(425,35);

        // creating the Listeners for the Buttons

        // Upon selecting Beginner, sets the size and number of missiles, resets the Label Text, Removes, the buttons, and displays the gameboard

        EventHandler<ActionEvent> easyClick = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dim_x = 6;
                dim_y = 6;
                max_missiles = 30;
                easy.setVisible(false);
                medium.setVisible(false);
                hard.setVisible(false);
                topLabel.setText("Select a Row and Column to Attack");
                setUpGame(topLabel);

            }
        };

        // Upon selecting Standard, sets the size and number of missiles, resets the Label Text, Removes, the buttons, and displays the gameboard

        EventHandler<ActionEvent> mediumClick = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dim_x = 9;
                dim_y = 9;
                max_missiles = 50;
                easy.setVisible(false);
                medium.setVisible(false);
                hard.setVisible(false);
                topLabel.setText("Select a Row and Column to Attack");
                setUpGame(topLabel);

            }
        };

        // Upon selecting Advanced, sets the size and number of missiles, resets the Label Text, Removes, the buttons, and displays the gameboard

        EventHandler<ActionEvent> hardClick = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dim_x = 12;
                dim_y = 12;
                max_missiles = 75;
                easy.setVisible(false);
                medium.setVisible(false);
                hard.setVisible(false);
                topLabel.setText("Select a Row and Column to Attack");
                setUpGame(topLabel);
            }
        };

        easy.setOnAction(easyClick);
        medium.setOnAction(mediumClick);
        hard.setOnAction(hardClick);
        
        // adds buttons to frame

        root.getChildren().add(easy);
        root.getChildren().add(medium);
        root.getChildren().add(hard);
        
        // sets preferred size of frame

        root.setPrefSize(12 * 50 + 100, 12 * 50 + 250);
        return root;
    }


    public void setUpGame(Label topLabel) {

        // creating a new Tile instance for the squares in the board

        this.board = new Tile[dim_x][dim_y];

        ActionGame game1 = new ActionGame();
        // setting the board size
        game1.setUpBoard(dim_x-1, dim_y-1);
        // randomly placing the ships
        game1.PlaceShips();
        // returning the ship positions and board to the GUI
        this.CPU_Board = game1.getBoard();
        this.ShipList = game1.getShipList();

        // verifying the contents

        System.out.println("Printing Ships");
        printShips();
        System.out.println("Printing Ship Codes");
        printShipCodes();
        printShipPositions();
        printBoard(dim_x-1, dim_y-1);


        // sets preferred size


        // creates Tile Grid from private Class Tile based on the size of the board

       for (int i = 0; i < dim_x; i++) {
          for (int j = 0; j < dim_y; j++) {
               Tile tile = new Tile(i,j, topLabel);
              // sets spacing between tiles
               tile.setTranslateX(i * 50 + 50);
              tile.setTranslateY(j * 50 + 200);
             // adds Tile objects to root Pane
              root.getChildren().add(tile);
               // Assigns tile to each element in Array
              board[i][j] = tile;

          }
       }
    }

    // individual tiles that will display hit or miss

    public class Tile extends StackPane {
        // creates empty text in Tiles
        private Text text = new Text();

        // positions for the ships
        private int posX;
        private int posY;

        public Tile(int x, int y, Label topLabel) {

            // the x and y position for the ship

            this.posX = x - 1;
            this.posY = y - 1;




            // size for each individual Tile for BattleShip


            Rectangle border = new Rectangle(50, 50);
            // sets fill to transparent
            border.setFill(null);
            // border set to black
            border.setStroke(Color.BLACK);

            // sets the width of the border lines
            border.setStrokeWidth(2);

            // font size is 50
            text.setFont(Font.font(35));
            // sets elements to center alignment within Tile
            setAlignment(Pos.CENTER);
            // adds Border objects to StackPane and Text
            getChildren().addAll(border, text);

            // drawing numbers on columns

            if(y == 0){
                drawShip(String.valueOf(x+1));
                border.setStrokeWidth(5);
            }

            // drawing A-Z chars on Rows

            if(x == 0 && y != 0){
                char tmp = (char) ('A' + y-1);
                String cell_name = Character.toString(tmp);
                drawShip(cell_name);
                border.setStrokeWidth(5);
            }

            // drawing the ship positions if it is not empty on the CPU_Board
            // condition to prevent from drawing off the board
            if(x != dim_x && x != 0 && y != dim_y && y != 0){
                if(CPU_Board[x-1][y-1] != "e")
                    drawShip(CPU_Board[x-1][y-1]);
            }

            // inherited from Node Class
            // events that occur if mouse is Clicked
            setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {

                    // for verification

                    // only allows to continue playing if all missiles have not been used

                    if(missiles_used < max_missiles && Won_the_Game == false) {

                        System.out.println("Clicked");
                        System.out.println(x);
                        System.out.println(y);

                        // passing the topLabel for the Bozoo Display Text
                        boolean hit = checkSpot(x-1, y-1, topLabel, text);

                        int missiles_remaining = max_missiles - missiles_used;
                        System.out.println("Missiles Remaining: " + missiles_remaining);
                        if (max_missiles == missiles_used) {
                            topLabel.setText("You Lose!!! CPU Wins!!! \nYou have exceeded the maximum number of missiles!!!");
                            return;
                        }

                    }


                }

            });


        }
        public void drawShip(String ship_code){
            text.setText(ship_code);
        }

    }


    public boolean checkSpot(int x, int y, Label topLabel, Text boxText) {

        // if it is not empty or has a previous hit

        // e for empty, h for hit, m for miss

        if (CPU_Board[x][y] != "e" && CPU_Board[x][y] != "h" && CPU_Board[x][y] != "m") {

            // loops through each ship to determine name of Type of Ship Hit, and number of Hits

            for (Ship ship : ShipList) {

                boolean hit = ship.checkForHit(x, y);

                if (hit == true) {
                    // adding hit to ship object and returning type
                    ship.Hit();
                    String type = ship.getType();

                    int hits = ship.getHits();
                    int total_life = ship.getSize();

                    // num of total hits
                    num_hits++;
                    // number of missiles available
                    missiles_used++;

                    if(total_life - hits > 0) {
                        topLabel.setText("Congratulations!!! You hit the " + type + "\nIt has " + (total_life - hits) + " hits remaining" +
                        "\nRemaining Missiles: " + (max_missiles-missiles_used) +
                        "\nRemaining Ships: " + ships_remaining +
                        "\nUser Accuracy: " + num_hits + "/" + missiles_used);

                        boxText.setText("H");
                        CPU_Board[x][y] = "h";
                    }
                    if(total_life - hits == 0){
                        ships_remaining--;
                        topLabel.setText("Congratulations!!! You Sunk the " + type + "!!!" +
                                "\nRemaining Missiles: " + (max_missiles-missiles_used) +
                                "\nRemaining Ships: " + ships_remaining +
                                "\nUser Accuracy: " + num_hits + "/" + missiles_used);
                        boxText.setText("H");
                        CPU_Board[x][y] = "h";


                    }

                }
            }

            if (num_hits == 17) {
                topLabel.setText("You Won the Game!!!");
                Won_the_Game = true;

            }
            return true;

            // if it was already hit

        } else if (CPU_Board[x][y] == "h"){
            // you BooZoo

            for (Ship ship : ShipList) {

                boolean hit = ship.checkForHit(x, y);
                // decreases missiles


                if (hit == true) {

                    // increasing missiles used
                    missiles_used++;

                    topLabel.setText("You BoZoo!!! \nYou already hit this spot on the " + ship.getType() + "!!!" +
                            "\nRemaining Missiles: " + (max_missiles-missiles_used) + "\nRemaining Ships: " + ships_remaining +
                            "\nUser Accuracy: " + num_hits + "/" + missiles_used);

                }

            }
            return true;
        }
        else {
            if(x < 0 || y < 0){

                // increases misses and decreases missiles

                num_misses++;
                missiles_used++;

                topLabel.setText("You Clicked Outside of the Board!!! You Clown!!!" +
                "\nRemaining Missiles: " + (max_missiles-missiles_used) + "\nRemaining Ships: " + ships_remaining +
                        "\nUser Accuracy: " + num_hits + "/" + missiles_used);
                // incrementing misses, substracting missiles, increasing missiles used



                return false;
            }
            if(CPU_Board[x][y] == "m"){
                num_misses++;
                missiles_used++;
                CPU_Board[x][y] = "m";

                System.out.println("Bozoo Miss");

                topLabel.setText("You BoZoo!!! You Already Missed Here!!!" +
                        "\nRemaining Missiles: " + (max_missiles-missiles_used) + "\nRemaining Ships: " + ships_remaining +
                        "\nUser Accuracy: " + num_hits + "/" + missiles_used);
                boxText.setText("M");

                return false;

            }
            else {

                // increases misses and decreases missiles

                num_misses++;
                missiles_used++;

                System.out.println("Miss!");

                CPU_Board[x][y] = "m";

                topLabel.setText("Miss!!! Learn to Aim Better!!!" +
                        "\nRemaining Missiles: " + (max_missiles-missiles_used) + "\nRemaining Ships: " + ships_remaining +
                        "\nUser Accuracy: " + num_hits + "/" + missiles_used);
                boxText.setText("M");

                return false;
            }
        }
    }



    // starts the GUI

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Creates a New Pane from CreateContent Method and launches it

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setTitle("BattleShip");
        primaryStage.show();


    }

    // launches the GUI from an external class

    public static void launchBattleShip(String... args){
        launch(args);
    }

    // prints the names of the ships for verification

    public void printShips(){
        System.out.println("printing ships...");
        for(Ship ship: this.ShipList){
            System.out.println(ship.getType());
        }
    }

    // prints the entire board for verification

    public void printBoard(int dim_x, int dim_y) {
        for (int i = 0; i < dim_x; i++) {
            for (int j = 0; j < dim_y; j++) {
                if(CPU_Board[i][j] != "e") {
                    System.out.println(i + " " + j + " " + CPU_Board[i][j]);

                }
            }
        }

    }

    // prints the ship positions for verification

   public void printShipPositions(){
        for(Ship ship: this.ShipList){
            ship.printPositions();
        }
   }

   // returns the ship at coordinates x, and y

    public Ship getShip(int x, int y) {
        Ship tmp_ship = null;
        for (Ship ship : this.ShipList) {
            tmp_ship = ship;
            boolean hit = ship.checkForHit(x, y);

            if (hit == true) {
                System.out.println(ship.Get_Ship_Code());
                return ship;


            }
        }
        return tmp_ship;
    }


    // checks the x, and y coordinates to see if there is a ship there
    // changes the topLabel to Bozoo if you hit the same spot twice







    public void printShipCodes(){
        for(Ship ship: this.ShipList){
            System.out.println(ship.Get_Ship_Code());

        }
    }




}
