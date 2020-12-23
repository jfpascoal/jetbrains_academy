package battleship;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Battle battle = newBattle();
        do  {
            battle.processInput(scanner.nextLine());
        } while (battle.getStatus() != Battle.Status.OVER);
    }

    private static Battle newBattle() {
        Battle battle = new Battle();
        battle.initPlayer(1);
        return battle;
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
                map.put(String.valueOf(y) + (col + 1), new int[] {row, col});
            }
        }
    }

    enum Status {
        START_P1(0),
        START_PASS(0),
        START_P2(0),
        TURN_P1(0),
        TURN_P2(0),
        TURN_PASS(0),
        OVER(0);

        private int index;

        Status(int index) { this.index = index; }

        private void nextIndex() { this.index += 1; }

        private int getIndex() { return this.index; }

        private void setIndex(int index) { this.index = index; }
    }

    Status getStatus() { return status; }

    void initPlayer(int playerId) {
        status = Status.valueOf("START_P" + playerId);
        Player p = new Player();
        System.out.printf("\nPlayer %d, place your ships to the field game\n", playerId);
        p.enterShip(0);
        if (playerId == 1) {
            p1 = p;
        } else if (playerId == 2) {
            p2 = p;
        }
    }

    void processInput(String input) {
        switch (status) {
            case START_P1:
                if (!setShipFromInput(p1, input)) {
                    return;
                } else if (!p1.enterShip(status.getIndex())) { // there are no more ships to enter
                    status = Status.START_PASS;
                    passMove();
                }
                break;
            case START_PASS:
                initPlayer(2);
                break;
            case START_P2:
                if (!setShipFromInput(p2, input)) {
                    return;
                } else if (!p2.enterShip(status.getIndex())) { // there are no more ships to enter
                    startBattle();
                }
                break;
            case TURN_P1:
                takeShotFromInput(2, input);
                break;
            case TURN_PASS:
                int playerId = status.getIndex();
                hitPrompt(playerId);
                status = Status.valueOf("TURN_P" + playerId);
                break;
            case TURN_P2:
                takeShotFromInput(1, input);
                break;
            default:
                break;
        }
    }

    private boolean setShipFromInput(Player p, String input) {
        char[][] field = p.getField(); // get player's field
        if (
                positionIsValid(input) // input coordinates are valid
                        && !isTooClose(input, field) // not too close to another ship
                        && p.setShipPosition(status.getIndex(), convertPosition(input)) // ship length is correct
        ) {
            status.nextIndex();
            return true;
        } else {
            return false;
        }
    }

    private void takeShotFromInput(int opponentId, String input) {
        Player p;
        if (opponentId == 1) {
            p = p1;
        } else if (opponentId == 2) {
            p = p2;
        } else {
            return;
        }

        if (map.containsKey(input)) { // check if position is valid
            p.takeShot(map.get(input));
        } else {
            System.out.println("\nError! You entered the wrong coordinates! Try again:");
            return;
        }
        if (p.noMoreShips()) {
            status = Status.OVER;
        } else {
            status = Status.TURN_PASS;
            status.setIndex(opponentId);
            passMove();
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
                    // ignore ArrayIndexOutOfBoundsException
                }
            }
        }
        return false;
    }

    private void passMove() {
        System.out.println("\nPress Enter and pass the move to another player\n...");
    }

    private void startBattle() {
        //System.out.println("\nThe game starts!");
        status = Status.TURN_PASS;
        status.setIndex(1);
        passMove();
    }

    private void hitPrompt(int playerId) {
        if (playerId == 1) {
            p1.printField(p2.getField());
        } else if (playerId ==2) {
            p2.printField(p1.getField());
        }
        System.out.printf("\nPlayer %d, it's your turn:\n", playerId);
    }
}


class Player {
    private final char[][] field = new char[10][10];
    private final ArrayList<Ship> ships = new ArrayList<>();
    private final HashMap<String, Ship> shipMap = new HashMap<>();

    Player() {
        for (char[] row : field) {
            Arrays.fill(row, '~'); // fill field row with '~'
        }
        // Create ships
        ships.add(new Ship("Aircraft Carrier", 5));
        ships.add(new Ship("Battleship", 4));
        ships.add(new Ship("Submarine", 3));
        ships.add(new Ship("Cruiser", 3));
        ships.add(new Ship("Destroyer", 2));
    }

    char[][] getField() { return field; }

    boolean noMoreShips() { return ships.size() == 0; }

    void printField(boolean fog, char[][] field) {
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

    void printField(char[][] opponent) {
        printField(true, opponent);
        System.out.print("---------------------");
        printField(false, this.field);
    }

    boolean enterShip(int index) {
        if (index < ships.size()) {
            printField(false, this.field);
            System.out.printf(
                    "\nEnter the coordinates of the %s (%d cells): \n",
                    ships.get(index).getType(),
                    ships.get(index).getSize()
            );
            return true;
        } else {
            printField(false, this.field);
            return false;
        }
    }

    boolean setShipPosition(int index, int[][] coordinates) {
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

    void takeShot(int[] pos) {
        if (field[pos[0]][pos[1]] == 'O') {
            field[pos[0]][pos[1]] = 'X';
            // check if ship sank
            Ship ship = shipMap.get(Arrays.toString(pos));
            ship.takeHit();
            if (ship.isSunk()) {
                ships.remove(ship);
            } else {
                System.out.println("\nYou hit a ship!");
                return;
            }
            if (ships.size() > 0) {
                System.out.println("\nYou sank a ship!");
            } else {
                System.out.println("\nYou sank the last ship. You won. Congratulations!");
            }
        } else {
            if (field[pos[0]][pos[1]] == '~') {
                field[pos[0]][pos[1]] = 'M';
            }
            System.out.println("\nYou missed!");
        }
    }
}

class Ship {
    private final String type;
    private final int size;
    private int hits = 0;

    public Ship(String type, int size) {
        this.type = type;
        this.size = size;
    }

    String getType() {
        return type;
    }

    int getSize() {
        return size;
    }

    void takeHit(){ hits++; }

    boolean isSunk() { return hits == size; }
}
