package baseline.main;

import java.util.Scanner;
import baseline.model.*;

public class App {

    private static Hero hero;
    private static Enemy enemy;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("=== Welcome to SIMPLEGAME ===");
        System.out.println("A mini Java console RPG demo.\n");

        while (running) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Create Hero");
            System.out.println("2. Create Enemy");
            System.out.println("3. Show Status");
            System.out.println("4. Battle!");
            System.out.println("5. Exit");
            System.out.print("Choose: ");

            int choice = getIntInput();

            switch (choice) {
                case 1: {
                    createHero();
                    break;
                }
                case 2: {
                    createEnemy();
                    break;
                }
                case 3: {
                    showStatus();
                    break;
                }
                case 4: {
                    startBattle();
                    break;
                }
                case 5: {
                    System.out.println("Exiting game...");
                    running = false;
                }
                default: {
                    System.out.println("Invalid choice! Try again.");
                }
            }
        }

        scanner.close();
    }

    private static void createHero() {
        System.out.println("\n=== Create Your Hero ===");
        System.out.println("1. Archer");
        System.out.println("2. Fighter");
        System.out.print("Choose your hero type: ");
        int type = getIntInput();

        System.out.print("Enter hero name: ");
        String name = scanner.next();

        switch (type) {
            case 1: {
                hero = new Archer(name);
                break;
            }
            case 2: {
                hero = new Fighter(name);
                break;
            }
            default: {
                System.out.println("Invalid hero type!");
                return;
            }
        }

        System.out.println("Hero created successfully: " + hero.getName());
    }

    private static void createEnemy() {
        System.out.println("\n=== Create Enemy ===");
        System.out.print("Enter enemy level: ");
        int level = scanner.nextInt();
        enemy = new Enemy(level);
        System.out.println("Enemy created successfully: " + enemy.toString());
    }

    private static void showStatus() {
        System.out.println("\n=== Current Status ===");
        if (hero != null)
            System.out.println("Hero " + hero.getName() + " | HP: " + hero.getHealth());
        else
            System.out.println("No hero created.");

        if (enemy != null)
            System.out.println("Enemy HP: " + enemy.getHealth());
        else
            System.out.println("No enemy created.");
    }

    private static void startBattle() {
        if (hero == null || enemy == null) {
            System.out.println("⚠️ Please create both hero and enemy first!");
            return;
        }

        System.out.println("\n=== Battle Start! ===");
        while (hero.isAlive() && enemy.isAlive()) {
            hero.attack(enemy);
            if (!enemy.isAlive()) {
                System.out.println("Enemy has been defeated!");
                break;
            }

            enemy.attack(hero);
            if (!hero.isAlive()) {
                System.out.println(hero.getName() + " died!");
                break;
            }

            System.out.println("→ " + hero.getName() + "'s HP: " + hero.getHealth()
                    + " | " +"Enemy's HP: " + enemy.getHealth());
        }

        System.out.println("\n=== Battle Over ===");
        if (hero.isAlive())
            System.out.println("Victory!");
        else
            System.out.println("Defeat...");
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
