package machine;

import java.util.*;
public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] items = {"water", "milk", "coffee beans", "disposable cups"};
        // stock : {{items}, {balance}}
        int[][] stock = new int[][] {{400, 540, 120, 9}, {550}};
        displayStock(items, stock);
        System.out.println("\nWrite action (buy, fill, take):");
        String action = scanner.next();
        int[][] newStock;
        switch (action) {
            case "buy":
                newStock = buyCoffee(scanner, stock);
                break;
            case "fill":
                newStock = fillMachine(scanner, stock);
                break;
            case "take":
                newStock = takeMoney(scanner, stock);
                break;
            default:
                newStock = stock;
                break;
        }
        displayStock(items, newStock);
    }

    private static void displayStock(String[] items, int[][] stock) {
        // stock : {{water, milk, coffee beans, cups}, {balance}}
        // items : {"water", "milk", "coffee beans", "disposable cups"};
        System.out.println("\nThe coffee machine has:");
        for (int i = 0; i < items.length; i++) {
            System.out.printf("%d of %s\n", stock[0][i], items[i]);
        }
        System.out.printf("%d of money\n", stock[1][0]);
    }

    private static int[][] buyCoffee(Scanner scanner, int[][] stock) {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
        int option = scanner.nextInt();
        int[] quantity = new int[stock[0].length];
        int price = 0;
        switch (option) {
            case 1: // espresso
                quantity = new int[] {250, 0, 16, 1};
                price = 4;
                break;
            case 2: // latte
                quantity = new int[] {350, 75, 20, 1};
                price = 7;
                break;
            case 3: // cappuccino
                quantity = new int[] {200, 100, 12, 1};
                price = 6;
        }

        int n = 1; // number of coffees

        if (coffeeCalc(n, stock[0], quantity)) {
            for (int i = 0; i < stock[0].length; i++) {
                stock[0][i] = stock[0][i] - n * quantity[i];
            }
            stock[1][0] = stock[1][0] + n * price;
        }
        return stock;
    }

    private static int[][] fillMachine(Scanner scanner, int[][] stock) {
        String[][] stockComp = {
                {"water", "ml"},
                {"milk", "ml"},
                {"coffee beans", "grams"},
                {"coffee", "disposable cups"}
        };
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

    private static int[][] takeMoney(Scanner scanner, int[][] stock) {
        System.out.printf("I gave you $%d\n", stock[1][0]);
        stock[1][0] = 0;
        return stock;
    }

    private static boolean coffeeCalc(int n, int[] stock, int[] quantity) {
        /*
        quantity / stock:
        {water, milk, coffee beans, cups}
         */

        int minCups = 0;
        boolean primed = false;
        for (int i = 0; i < stock.length; i++) {
            if (quantity[i] == 0) {
                continue;
            } else if (!primed || stock[i]/quantity[i] < minCups) {
                minCups = stock[i]/quantity[i];
                if (!primed) {
                    primed = true;
                }
            }
        }
        return minCups >= n;
    }

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
}
