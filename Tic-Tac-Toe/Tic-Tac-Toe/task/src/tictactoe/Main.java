package tictactoe;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Get input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter cells: ");
        String field = scanner.next().toUpperCase();

        // Evaluate game
        //String state = evaluateState(field);

        // Print field
        printField(field);

        // Get play
        field = getPlay(field, 'X');

        // Print field with play
        printField(field);
        //System.out.println(state);
    }

    private static String getPlay(String field, char player) {
        System.out.print("Enter the coordinates: ");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            int c1 = scanner.nextInt();
            if (c1 < 1 || c1 > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                return getPlay(field, player);
            } else if (scanner.hasNextInt()) {
                int c2 = scanner.nextInt();
                if (c2 < 1 || c2 > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    return getPlay(field, player);
                } else {
                    return integratePlay(new int[]{c1, c2}, player, field); // happy path
                }
            } else {
                System.out.println("You should enter numbers!");
                return getPlay(field, player);
            }
        } else {
            System.out.println("You should enter numbers!");
            return getPlay(field, player);
        }
    }

    private static String integratePlay(int[] coordinates, char player, String field) {
        int index = ((6 + coordinates[1]) / (2 * coordinates[1]) - 1) * 3 + coordinates[0] - 1;
        if (field.charAt(index) == 'X' || field.charAt(index) == 'O') {
            System.out.println("This cell is occupied! Choose another one!");
            return getPlay(field, player);
        } else if (index == 0) {
            return player + field.substring(1);
        } else if (index == 8) {
            return field.substring(0,8) + player;
        } else {
            return field.substring(0,index) + player + field.substring(index + 1);
        }
    }

    private static void printField(String field) {
        // Add spaces between characters
        int i = 0;
        while (i < field.length()) {
            field = field.substring(0, i + 1) +
                    " " +
                    field.substring(i + 1);
            i += 2;
        }
        // Print output
        System.out.println("---------");
        System.out.println("| " + field.substring(0,6) + "|");
        System.out.println("| " + field.substring(6,12) + "|");
        System.out.println("| " + field.substring(12,18) + "|");
        System.out.println("---------");
    }

    private static String evaluateState(String field) {
        if (checkImpossible(field)) { // Check if state is impossible
            return "Impossible";
        } else if (hasRow(field, 'X')) { // Check if X has row
            return "X wins";
        } else if (hasRow(field, 'O')) { // Check if O has row
            return "O wins";
        } else if (field.contains("_")) { // Check if there are empty cells
            return "Game not finished";
        } else {
            return "Draw";
        }
    }

    private static boolean checkImpossible(String field) {
        int c = 0;
        int nO = 0;
        int nX = 0;
        while (c < field.length()) {
            char currChar = field.charAt(c);
            if (currChar == 'X') {
                nX += 1;
            } else if (currChar == 'O') {
                nO += 1;
            }
            c += 1;
        }
        if (java.lang.Math.abs(nO - nX) > 1) { // check number of plays
            return true;
        } else { //check if both have win
            return hasRow(field, 'X') && hasRow(field, 'O');
        }
    }

    private static boolean hasRow(String field, char p) {
        String[] wins = {
                "^["+p+"]{3}.{6}", //top row
                "^.{3}[" + p + "]{3}.{3}", //middle row
                "^.{6}[" + p + "]{3}", //bottom row
                "^" + p + ".{2}" + p + ".{2}" + p + ".{2}", //left col
                "^." + p + ".{2}" + p + ".{2}" + p + ".", //middle col
                "^.{2}" + p + ".{2}" + p + ".{2}" + p, //right col
                "^" + p + ".{3}" + p + ".{3}" + p, //down across
                "^.{2}" + p + "." + p + "." + p + ".{2}", //up across
        };
        for (String win : wins) {
            if (field.matches(win)) {
                return true;
            }
        }
        return false;
    }
}
