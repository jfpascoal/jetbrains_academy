package battleship;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Battle battle = new Battle();
        do  {
            battle.processInput(scanner.nextLine());
        } while (battle.getStatus() != Battle.Status.OVER);
    }
}


class Battle {
    private Player p1;
    private Player p2;
    private Status status;
    private final HashMap<String, int[]> map = new HashMap<>(); // coordinate : {row, col} indexes

    Battle() {
        char y = 'A';
        for (int row = 0; row < 10; row++, y++) {
            for (int col = 0; col < 10; col++) { // fill HashMap for coordinates
                StringBuilder coordinate = new StringBuilder().append(y).append(col + 1);
                map.put(coordinate.toString(), new int[] {row, col});
            }
        }
        status = Status.START_P1;
        p1 = new Player(); // start player 1
    }

    enum Status {
        START_P1(0),
        //START_P2(0),
        TURN_P1(0),
        //TURN_P2(0),
        OVER(0);

        private int count;

        Status(int count) { this.count = count; }

        void add() { this.count += 1; }

        int getCount() { return this.count; }
    }

    public Status getStatus() { return status; }

    public void processInput(String input) {
        switch (status) {
            case START_P1:
                char[][] field = p1.getField(); // get player's field
                if (
                        positionIsValid(input) // input coordinates are valid
                        && !isTooClose(input, field) // not too close to another ship
                        && p1.setShipPosition(status.getCount(), convertPosition(input)) // ship length is correct
                ) {
                    status.add();
                } else {
                    return;
                }
                if (!p1.enterShip(status.getCount())) { // there are no more ships to enter
                    startBattle();
                }
                break;
            case TURN_P1:
                if (map.containsKey(input)) { // check if position is valid
                    p1.takeHit(map.get(input));
                } else {
                    System.out.println("\nError! You entered the wrong coordinates! Try again!");
                    return;
                }
                if (p1.noMoreShips()) {
                    status = Status.OVER;
                }
                break;
            default:
                return;
        }
    }

    private int[][] convertPosition(String input) {
        // convert coordinates to array with position, i.e., the {row, col} indexes of each occupied cell
        // this method should only be run with validated input coordinates!
        String[] coordinates = input.trim().split("\\s+");
        int[] start = map.get(coordinates[0]);
        int[] stop = map.get(coordinates[1]);
        int[][] position; // declare output variable
        if (start[0] == stop[0]) { // placed horizontally
            int[] range = generateRange(start[1], stop[1]);
            position = new int[range.length][2];
            for (int i = 0; i < range.length; i++) {
                position[i][0] = start[0];
                position[i][1] = range[i];
            }
        } else { // placed vertically
            int[] range = generateRange(start[0], stop[0]);
            position = new int[range.length][2];
            for (int i = 0; i < range.length; i++) {
                position[i][0] = range[i];
                position[i][1] = start[1];
            }
        }
        return position;
    }

    private int[] generateRange(int start, int stop) {
        // generates a sorted sequence of ints between start and stop (inclusive);
        int[] limits = {start, stop};
        Arrays.sort(limits);
        int size = limits[1] - limits[0] + 1;
        int[] range = new int[size];
        for (int i = 0; i < size; i++) {
            range[i] = limits[0] + i;
        }
        return range;
    }

    private boolean positionIsValid(String input){
        try {
            // check that input is in correct format
            String[] coordinates = input.trim().split("\\s+");
            if (
                    coordinates.length != 2
                    || !coordinates[0].matches("^[A-J][1-9]$|^[A-J]10$")
                    || !coordinates[1].matches("^[A-J][1-9]$|^[A-J]10$")
            ) {
                throw new IllegalArgumentException();
            }

            // check that coordinates are a valid position, i.e. horizontal or vertical
            if (
                    Objects.equals(coordinates[0], coordinates[1]) // only one cell or
                    || ( coordinates[0].charAt(0) != coordinates[1].charAt(0) // different row and
                    && !Objects.equals(coordinates[0].substring(1), coordinates[1].substring(1))) // different col
            ) {
                System.out.println("Error! Wrong ship location! Try again:");
                return false;
            }
        } catch (RuntimeException e) {
            System.out.println("Error! Invalid coordinates! Try again:");
            return false;
        }
        return true;
    }

    private boolean isTooClose(String input, char[][] field) {
        // check that the new position is not next to any ship already set
        int[][] position = convertPosition(input);
        for (int[] cell : position) {
            int row = cell[0];
            int col = cell[1];
            int[][] neighbours = new int[8][2];
            neighbours[0] = new int[] {row - 1, col - 1};
            neighbours[1] = new int[] {row - 1, col};
            neighbours[2] = new int[] {row - 1, col + 1};
            neighbours[3] = new int[] {row, col + 1};
            neighbours[4] = new int[] {row + 1, col + 1};
            neighbours[5] = new int[] {row + 1, col };
            neighbours[6] = new int[] {row + 1, col - 1};
            neighbours[7] = new int[] {row, col - 1};
            for (int[] n : neighbours) {
                try {
                    if (field[n[0]][n[1]] == 'O') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue; // ignore exception
                }
            }
        }
        return false;
    }

    private void startBattle() {
        System.out.println("\nThe game starts!");
        status = Status.TURN_P1;
        hitPrompt(p1);
    }

    private void hitPrompt(Player p) {
        p.printField(true);
        System.out.println("\nTake a shot!");
    }
}


class Player {
    private char[][] field = new char[10][10];
    private ArrayList<Ship> ships = new ArrayList<>();
    private HashMap<String, Ship> shipMap = new HashMap<>();

    public Player() {
        for (char[] row : field) {
            Arrays.fill(row, '~'); // fill field row with '~'
        }
        // Create ships
        ships.add(new Ship("Aircraft Carrier", 5));
        ships.add(new Ship("Battleship", 4));
        ships.add(new Ship("Submarine", 3));
        ships.add(new Ship("Cruiser", 3));
        ships.add(new Ship("Destroyer", 2));

        enterShip(0);
    }

    public char[][] getField() {
        return field;
    }

    public boolean noMoreShips() { return ships.size() == 0; }

    public void printField(boolean fog) {
        System.out.println("\n  1 2 3 4 5 6 7 8 9 10");
        char col = 'A';
        for (char[] row : field) {
            System.out.print(col + " ");
            col++;
            for (char cell : row) {
                if (fog && cell == 'O') {
                    System.out.print("~ ");
                } else {
                    System.out.print(cell + " ");
                }
            }
            System.out.print("\n");
        }
    }

    public boolean enterShip(int index) {
        if (index < ships.size()) {
            printField(false);
            System.out.printf(
                    "\nEnter the coordinates of the %s (%d cells): \n",
                    ships.get(index).getType(),
                    ships.get(index).getSize()
            );
            return true;
        } else {
            printField(false);
            return false;
        }
    }

    public boolean setShipPosition(int index, int[][] coordinates) {
        if (coordinates.length == ships.get(index).getSize()) { // check if size is correct
            //ships[index].setPosition(coordinates);
            for (int[] cell : coordinates) { // change field
                field[cell[0]][cell[1]] = 'O';
                shipMap.put(Arrays.toString(cell), ships.get(index));
            }
            return true;
        } else {
            System.out.printf("Error! Wrong length of the %s! Try again:\n", ships.get(index).getType());
            return false;
        }
    }

    public void takeHit(int[] pos) {
        if (field[pos[0]][pos[1]] == 'O') {
            field[pos[0]][pos[1]] = 'X';
            // check if ship sank
            Ship ship = shipMap.get(Arrays.toString(pos));
            ship.takeHit();
            if (ship.isSunk()) {
                ships.remove(ship);
            } else {
                printField(true);
                System.out.println("\nYou hit a ship! Try again:");
                return;
            }
            if (ships.size() > 0) {
                printField(true);
                System.out.println("\nYou sank a ship! Specify a new target:");
            } else {
                printField(true);
                System.out.println("\nYou sank the last ship. You won. Congratulations!");
            }
        } else if (field[pos[0]][pos[1]] == 'M' || field[pos[0]][pos[1]] == 'X') {
            printField(true);
            System.out.println("\nYou shot there before! Try again:");
        } else {
            field[pos[0]][pos[1]] = 'M';
            printField(true);
            System.out.println("\nYou missed! Try again:");
        }
    }
}

class Ship {
    private String type;
    private int size;
    private int hits = 0;

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

    public void takeHit(){ hits++; }

    public boolean isSunk() { return hits == size; }
}
