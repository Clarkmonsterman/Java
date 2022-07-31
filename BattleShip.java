 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// main method to call the game



public class BattleShip {



    public static void main(String [] args) throws Exception {

        // creates a gameboard instance
        // gameboard calls an instance of ActionGame from within the Pane
        // I tried copying the data from ActionGame to GameBoard and controlling it via ActionGame
        // but this resulted in continuous null pointer exceptions

        Gameboard board1 = new Gameboard();
        board1.launchBattleShip();



    }


// End of Class
}
