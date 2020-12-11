package machine;

import java.util.*;
public class CoffeeMachine {
    public static String[] items = {"water", "milk", "coffee beans", "disposable cups"};
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // stock : {{items}, {balance}}
        int[][] stock = new int[][] {{400, 540, 120, 9}, {550}};
        boolean exit = false;

        do {
            System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
            String action = scanner.next();
            switch (action) {
                case "buy":
                    stock = buyCoffee(stock);
                    break;
                case "fill":
                    stock = fillMachine(stock);
                    break;
                case "take":
                    stock = takeMoney(stock);
                    break;
                case "remaining":
                    displayStock(items, stock);
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Unknown action");
                    break;
            }
        } while (!exit);
    }

    private static void displayStock(String[] items, int[][] stock) {
        // stock : {{water, milk, coffee beans, cups}, {balance}}
        // items : {"water", "milk", "coffee beans", "disposable cups"};
        System.out.println("\nThe coffee machine has:");
        for (int i = 0; i < items.length; i++) {
            System.out.printf("%d of %s\n", stock[0][i], items[i]);
        }
        System.out.printf("$%d of money\n", stock[1][0]);
    }

    private static int[][] buyCoffee(int[][] stock) {
        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
        String option = scanner.next();
        int[] quantity = new int[stock[0].length];
        int price = 0;
        switch (option) {
            case "1": // espresso
                quantity = new int[] {250, 0, 16, 1};
                price = 4;
                break;
            case "2": // latte
                quantity = new int[] {350, 75, 20, 1};
                price = 7;
                break;
            case "3": // cappuccino
                quantity = new int[] {200, 100, 12, 1};
                price = 6;
                break;
            case "back":
                return stock;
        }

        int n = 1; // number of coffees

        if (coffeeCalc(n, stock[0], quantity)) {
            System.out.println("I have enough resources, making you a coffee!");
            for (int i = 0; i < stock[0].length; i++) {
                stock[0][i] = stock[0][i] - n * quantity[i];
            }
            stock[1][0] = stock[1][0] + n * price;
        }
        return stock;
    }

    private static int[][] fillMachine(int[][] stock) {
        String[][] stockComp = {
                {"water", "ml"},
                {"milk", "ml"},
                {"coffee beans", "grams"},
                {"coffee", "disposable cups"}
        };
        System.out.print("\n");
        for (int i = 0; i < stockComp.length; i++) {
            System.out.printf(
                    "Write how many %s of %s do you want to add:\n",
                    stockComp[i][1],
                    stockComp[i][0]
            );
            stock[0][i] += scanner.nextInt();
        }
        return stock;
    }

    private static int[][] takeMoney(int[][] stock) {
        System.out.printf("I gave you $%d\n", stock[1][0]);
        stock[1][0] = 0;
        return stock;
    }

    private static boolean coffeeCalc(int n, int[] stock, int[] quantity) {
        /*
        quantity / stock:
        {water, milk, coffee beans, cups}
         */

        for (int i = 0; i < stock.length; i++) {
            if (quantity[i] != 0 && stock[i]/quantity[i] < n) {
                System.out.printf("Sorry, not enough %s!\n", items[i]);
                return false;
            }
        }
        return true;
    }
/*
    private static void printStatus(short status) {
        switch (status) {
            case 0:
                System.out.println("Starting to make a coffee");
                break;
            case 1:
                System.out.println("Grinding coffee beans");
                break;
            case 2:
                System.out.println("Boiling water");
                break;
            case 3:
                System.out.println("Mixing boiled water with crushed coffee beans");
                break;
            case 4:
                System.out.println("Pouring coffee into the cup");
                break;
            case 5:
                System.out.println("Pouring some milk into the cup");
                break;
            case 6:
                System.out.println("Coffee is ready!");
                break;
        }
    }
 */
}
