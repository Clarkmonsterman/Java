 

import java.util.ArrayList;

public class Ship{

    // type of ship
    protected final String type;
    // code used to display on board
    protected final String ship_code;
    // positions
    protected ArrayList<int[][]> Ship_Positions = new ArrayList<>();
    // total hits on the ship
    protected int total_hits = 0;
    // lifepoints/size
    protected final int life_points;

    // constructor
    public Ship(String type, int life_points, String ship_code){
        this.type = type;
        this.life_points = life_points;
        this.ship_code = ship_code;
    }

    // constructor for copying a ship object to another

    public Ship(Ship ship) {
        this.type = ship.type;
        this.ship_code = ship.ship_code;
        this.Ship_Positions = ship.Ship_Positions;
        this.total_hits = ship.total_hits;
        this.life_points = ship.life_points;
    }



    // Adds a hit and returns true if ship is dead

    public boolean Hit(){
        total_hits++;
        if(total_hits == life_points)
            return true;
        else
            return false;
    }

    // adds a position to where the ship is located

    public void placeShip(int x, int y){

        int[][] tmp = new int[1][2];
        tmp[0][0] = x;
        tmp[0][1] = y;

        Ship_Positions.add(tmp);

    }

    // Loops through ArrayList of Ship Position to Check for Hit

    public boolean checkForHit(int x, int y){
//        System.out.println("Checking for Hit!!!");
        for(int i = 0; i < this.life_points; i++){
            int[][] tmp = Ship_Positions.get(i);
            int ship_x = tmp[0][0];
            int ship_y = tmp[0][1];


            if(ship_x == x && ship_y == y)
                return true;
        }
        return false;
    }

    // prints the positions of the ship

    public void printPositions(){
        for(int[][] position: Ship_Positions){
            System.out.println(position[0][0] + " " + position[0][1]);
        }
    }

    // returns ship type
    public String getType(){return this.type;}
    // returns number of hits
    public int getHits(){
        return this.total_hits;
    }
    // returns ship display code
    public String Get_Ship_Code(){ return this.ship_code;}
    // returns ship size/total_lifepoints
    public int getSize(){return this.life_points;}

}
