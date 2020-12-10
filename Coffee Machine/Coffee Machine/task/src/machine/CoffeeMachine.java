package machine;

import java.util.Scanner;
public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] stock = inputStock(scanner);
        System.out.println("Write how many cups of coffee you will need:");
        int n = scanner.nextInt();
        coffeeCalc(n, stock);
    }

    private static boolean coffeeCalc(int n, int[] stock) {
        /*
        quantity / ingredients / stock:
        {water, milk, coffeeBeans}
         */
        int[] quantity = {200, 50, 15};
        int[] ingredients = {n * quantity[0], n * quantity[1], n * quantity[2]};
        int minCups = 0;
        for (int i = 0; i < ingredients.length; i++) {
            if (i == 0) {
                minCups = stock[i]/quantity[i];
            } else if (stock[i]/quantity[i] < minCups) {
                minCups = stock[i]/quantity[i];
            }
        }
        if (minCups == n) {
            System.out.println("Yes, I can make that amount of coffee");
            return true;
        } else if (minCups > n) {
            System.out.printf(
                    "Yes, I can make that amount of coffee (and even %d more than that)\n",
                    minCups-n
            );
            return true;
        } else {
            System.out.printf(
                    "No, I can make only %d cup(s) of coffee)\n",
                    minCups
            );
            return false;
        }

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

    private static int[] inputStock(Scanner scanner) {
        String[][] stockComp = {
                {"water", "ml"},
                {"milk", "ml"},
                {"coffee beans", "grams"}
        };
        int[] stock = new int[stockComp.length];
        for (int i = 0; i < stockComp.length; i++) {
            System.out.printf(
                    "Write how many %s of %s the coffee machine has:\n",
                    stockComp[i][1],
                    stockComp[i][0]
            );
            stock[i] = scanner.nextInt();
        }
        return stock;
    }

}
