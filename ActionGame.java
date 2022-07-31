 

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ActionGame {

    String[][] CPU_Board;


    // final constants for the Size/Life of Each Ship

    final int Aircraft_Carrier_Life = 5;
    final int BattleShip_Life = 4;
    final int Destroyer_Life = 3;
    final int Submarine_Life = 3;
    final int Patrol_Life = 2;

    // an ArrayList for Each Ship and it's information

    protected ArrayList<Ship> ShipList = new ArrayList<>();


    // dimensions for the game

    int x_dim;
    int y_dim;

    //total number of hits



    // returns gamebaord
    public String[][] getBoard(){
        return this.CPU_Board;
    }
    // returns shipList
    public ArrayList<Ship> getShipList(){
        return this.ShipList;
    }

    // sets up the board with empty values

    public void setUpBoard(int x_dim, int y_dim){

        this.x_dim = x_dim;
        this.y_dim = y_dim;

        this.CPU_Board = new String[x_dim][y_dim];

        for(int i = 0; i < x_dim; i++){
            for(int j = 0; j < y_dim; j++){
                // e is the empty char for the board

                this.CPU_Board[i][j] = "e";

            }
        }
    }

    // places each ship randomly on the boards, creates a Ship object, adds info to ship object, and adds Ship object to
    // an arraylist of Ships

    public void PlaceShips() {

        // place AirCraft_Carrier, sets name, hit points/size, and the character used to represent it
        Ship AirCraft_Carrier = new Ship("AirCraft Carrier", Aircraft_Carrier_Life, "A");
        placeCPUShip(AirCraft_Carrier);
        ShipList.add(AirCraft_Carrier);
        // place BattleShip, sets name, hit points/size, and the character used to represent it
        Ship BattleShip = new Ship("BattleShip", BattleShip_Life, "B");
        placeCPUShip(BattleShip);
        ShipList.add(BattleShip);
        // place Destroyer, sets name, hit points/size, and the character used to represent it
        Ship Destroyer = new Ship("Destroyer", Destroyer_Life, "D");
        placeCPUShip(Destroyer);
        ShipList.add(Destroyer);
        // place Submarine, sets name, hit points/size, and the character used to represent it
        Ship Submarine = new Ship("Submarine", Submarine_Life, "S");
        placeCPUShip(Submarine);
        ShipList.add(Submarine);
        // place Patrol Boat, sets name, hit points/size, and the character used to represent it
        Ship Patrol = new Ship("Patrol Boat", Patrol_Life, "P");
        placeCPUShip(Patrol);
        ShipList.add(Patrol);
    }

    // randomly places the Ships on the board

    public void placeCPUShip(Ship ship) {

        boolean available = false;


        // if random position is not available continues loop until finding a position

        while(available == false) {

            // generations random x and y positions on the board
            int x = (int)(Math.random() * this.x_dim + 1);
            int y = (int)(Math.random() * this.y_dim + 1);

            // north, west, east, south direction for the ship placement


            int direction = (int)(Math.random()*4 + 0);

            System.out.println(direction);


            // Checks to make sure it will fit the board, and not collide with a preexisting ship.

            available = availableSpots(ship, x, y,  direction, false, 0);

        }


    }

    // checks to see if spots are available for placement
    // if it is available uses recursion to place the ship
    // adds iteration number to prevent infinite loop while using recursion to place ships

    public boolean availableSpots(Ship ship, int x, int y, int direction, boolean place, int iteration) {

        boolean available = false;
        int needed_positions = ship.life_points;

        // north
        if(direction == 0) {
            for(int z = 0; z < ship.getSize(); z++) {



                int k = y + z;

                // if place is true skips rest of steps, and places the ship

                if(place == true) {
                    this.CPU_Board[x][k] = ship.Get_Ship_Code();
                    ship.placeShip(x,k);

                }

                else {

                    // breaks loop if position of random ship would exceed boundaries, and forces placeCPUShip to try another random position

                    if (x >= this.x_dim || x < 0) {
                        return available;
                    }

                    if (k >= this.y_dim || k < 0) {
                        return available;
                    }

                    // if the array is not empty with 'e' sets the availability to false, and searches for another position

                    if (this.CPU_Board[x][k] != "e") {
                        return available;
                    }

                    if(this.CPU_Board[x][k] == "e")
                        needed_positions--;

                    // places ship on selected position if previous loop found that position was available
                }

            }


        }


// east

        if(direction == 1){

            for(int z = 0; z < ship.getSize(); z++) {

                int k = x + z;

                // if place is true skips rest of steps, and places the ship


                if (place == true) {
                    this.CPU_Board[k][y] = ship.Get_Ship_Code();
                    ship.placeShip(k, y);

                } else {

                    // breaks loop if position of random ship would exceed boundaries, and forces placeCPUShip to try another random position

                    if (k >= this.x_dim || k < 0) {
                        available = false;
                        return available;
                    }

                    if (y >= this.y_dim || y < 0) {
                        available = false;
                        return available;
                    }

                    // if the array is not empty with 'e' sets the availability to false, and searches for another position

                    if (this.CPU_Board[k][y] != "e") {
                        available = false;
                        return available;
                    }

                    if(this.CPU_Board[k][y] == "e")
                        needed_positions--;

                }
            }




        }


        // south

        if(direction == 2) {

            for (int z = 0; z < ship.getSize(); z++) {
                int k = y - z;

                // if place is true skips rest of steps, and places the ship

                if (place == true) {
                    this.CPU_Board[x][k] = ship.Get_Ship_Code();
                    ship.placeShip(x, k);

                } else {

                    // breaks loop if position of random ship would exceed boundaries, and forces placeCPUShip to try another random position


                    if (x >= this.x_dim || x < 0) {
                        available = false;
                        return available;
                    }

                    if (k >= this.y_dim || k < 0) {
                        available = false;
                        return available;
                    }

                    // if the array is not empty with 'e' sets the availability to false, and searches for another position


                    if (this.CPU_Board[x][k] != "e") {
                        available = false;
                        return available;

                    }
                    if(this.CPU_Board[x][k] == "e")
                        needed_positions--;


                }
            }
        }




        // west

        if(direction == 3){

            for(int z = 0; z < ship.getSize(); z++) {
                int k = x - z;

                // if place is true skips rest of steps, and places the ship


                if (place == true) {
                    this.CPU_Board[k][y] = ship.Get_Ship_Code();
                    ship.placeShip(k, y);

                } else {

                    // breaks loop if position of random ship would exceed boundaries, and forces placeCPUShip to try another random position


                    if (k >= this.x_dim || k < 0) {
                        available = false;
                        return available;
                    }

                    if (y >= this.y_dim || y < 0) {
                        available = false;
                        return available;
                    }

                    // if the array is not empty with 'e' sets the availability to false, and searches for another position


                    if (this.CPU_Board[k][y] != "e") {
                        available = false;
                        return available;
                    }

                    if(this.CPU_Board[k][y] == "e")
                        needed_positions--;

                }
            }

        }

        if(needed_positions == 0)
            available = true;

        if(available == true && iteration == 0) {
            System.out.println(ship.getType());
            availableSpots(ship, x, y, direction, true, 1);

        }

        return available;

    }

    public void printShipPositions(){
        for(Ship ship: ShipList){
            System.out.println(ship.getType());
            ship.printPositions();

        }
    }


}
