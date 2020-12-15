package machine;

import java.util.*;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Machine machine = new Machine(new int[][] {{400, 540, 120, 9}, {550}});
        machine.startMachine();
         do {
             machine.processInput(scanner.next());
        } while (machine.getStatus() != machineStatus.OFF);
    }
}

enum machineStatus {
    STANDBY(0),
    BUY(0),
    FILL(0),
    OFF(0);

    private int n;

    machineStatus(int n) {
        this.n = n;
    }

    public void nextN() {
        this.n += 1;
    }

    public int getN() {
        return this.n;
    }
}

class Machine {
    private final String[] items = {"water", "milk", "coffee beans", "disposable cups"};
    private final String[][] fillItems = {
            {"water", "milk", "coffee beans", "coffee"},
            {"ml", "ml", "grams", "disposable cups"}
    };
    private machineStatus status = machineStatus.OFF;
    private int[][] stock;// = {{400, 540, 120, 9}, {550}};

    public Machine(int[][] stock) {
        this.stock = stock; // stock : {{items}, {balance}}
    }

    public void startMachine() {
        status = machineStatus.STANDBY;
        displayMainMenu();
    }

    public void displayMainMenu() {
        System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
    }

    public void processInput(String input) {
        switch (status) {
            case STANDBY:
                switch (input) {
                    case "remaining":
                        displayStock();
                        displayMainMenu();
                        break;
                    case "buy":
                        status = machineStatus.BUY;
                        buyPrompt();
                        break;
                    case "fill":
                        status = machineStatus.FILL;
                        fillPrompt(status.getN());
                        break;
                    case "take":
                        takeMoney();
                        displayMainMenu();
                        break;
                    case "exit":
                        status = machineStatus.OFF;
                        break;
                    default:
                        System.out.println("Unsupported / unrecognized operation.");
                        displayMainMenu();
                }
                break;
            case BUY:
                buyCoffee(input);
                status = machineStatus.STANDBY;
                displayMainMenu();
                break;
            case FILL:
                fillItem(status.getN(), input);
                if (status.getN() < items.length - 1) {
                    status.nextN();
                    fillPrompt(status.getN());
                } else {
                    status = machineStatus.STANDBY;
                    displayMainMenu();
                }
                break;
        }
    }

    public machineStatus getStatus() {
        return status;
    }

    private void displayStock() {
        System.out.println("\nThe coffee machine has:");
        for (int i = 0; i < this.items.length; i++) {
            System.out.printf("%d of %s\n", this.stock[0][i], this.items[i]);
        }
        System.out.printf("$%d of money\n", this.stock[1][0]);
    }

    private void buyPrompt() {
        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
    }

    private void buyCoffee(String option) {
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
                return;
        }

        if (coffeeCalc(quantity)) {
            System.out.println("I have enough resources, making you a coffee!");
            for (int i = 0; i < stock[0].length; i++) {
                stock[0][i] = stock[0][i] - quantity[i];
            }
            stock[1][0] = stock[1][0] + price;
        }
    }

    private void fillPrompt(int index) {
        System.out.print("\n");
        System.out.printf(
                "Write how many %s of %s do you want to add:\n",
                fillItems[1][index],
                fillItems[0][index]
        );
    }

    private void fillItem(int index, String quantity) {
        stock[0][index] += Integer.parseInt(quantity);
    }

    private void takeMoney() {
        System.out.printf("I gave you $%d\n", stock[1][0]);
        stock[1][0] = 0;
    }

    private boolean coffeeCalc(int[] quantity) {
        for (int i = 0; i < stock[0].length; i++) {
            if (quantity[i] != 0 && stock[0][i]/quantity[i] < 1) {
                System.out.printf("Sorry, not enough %s!\n", items[i]);
                return false;
            }
        }
        return true;
    }
}