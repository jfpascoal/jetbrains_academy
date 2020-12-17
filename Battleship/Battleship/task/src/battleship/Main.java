package battleship;

import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Player p1 = new Player();
        //Player p2 = new Player();
    }
}

/*
class Battle {
    Player p1;
    Player p2;
    Status status;

    public void startGame() {

    }

    private enum Status {
        START_P1,
        START_P2,
        TURN_P1,
        TURN_P2,
        OVER;
    }
}
*/

class Player {
    private char[][] field = new char[10][10];
    private HashMap<String, int[]> map = new HashMap<>(); // coordinate : {row, col} indexes
    private Ship[] ships = new Ship[5];

    public Player() {
        char y = 'A';
        for (int row = 0; row < 10; row++, y++) {
            Arrays.fill(field[row], '~'); // fill field row with '~'
            for (int col = 0; col < 10; col++) { // fill HashMap for coordinates
                StringBuilder coordinate = new StringBuilder().append(y).append(col + 1);
                map.put(coordinate.toString(), new int[] {row, col});
            }
        }

        ships[0] = new Ship("Aircraft Carrier", 5);
        ships[1] = new Ship("Battleship", 4);
        ships[2] = new Ship("Submarine", 3);
        ships[3] = new Ship("Cruiser", 3);
        ships[4] = new Ship("Destroyer", 2);

        for (Ship ship : ships) {
            enterShip(ship);
        }
    }

    public HashMap<String, int[]> getMap() { // move map to class Battle
        return map;
    }

    public void printField() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char col = 'A';
        for (char[] row : field) {
            System.out.print(col + " ");
            col++;
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.print("\n");
        }
    }

    private void enterShip(Ship ship) {
        printField();

        System.out.printf(
                "\nEnter the coordinates of the %s (%d cells): \n",
                ship.getType(),
                ship.getSize()
        );
    }

}

class Ship {
    private String type;
    private int size;
    private int[][] position = new int[size][2];
    private boolean sunk = false;

    public Ship(String type, int size) {
        this.type = type;
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public int[][] getPosition() {
        return position;
    }

    public boolean isSunk() {
        return sunk;
    }

    public void setPosition(int[][] position) {
        this.position = position;
    }

    public void sink() {
        sunk = true;
    }
}
