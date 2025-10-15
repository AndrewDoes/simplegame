package main;

import java.util.ArrayList;
import java.util.Scanner;
import model.*;
import test.BuilderTester;

public class App {
    private static ArrayList<Hero> heroes = new ArrayList<>();
    private static ArrayList<Enemy> enemies = new ArrayList<>();
    private static ArrayList<BattleRecord> battleRecords = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("====== Welcome to SIMPLEGAME ======");
        System.out.println("A mini Java console RPG demo.\n");

        while (running) {
            System.out.println("\n======= MAIN MENU =======");
            System.out.println("1. Create Hero");
            System.out.println("2. Create Enemy");
            System.out.println("3. Show Status");
            System.out.println("4. Battle!");
            System.out.println("5. View Battle Records");
            System.out.println("6. Test Performance");
            System.out.println("7. Exit");
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
                    viewBattleRecords();
                    break;
                }
                case 6:{
                    BuilderTester.runTest();
                    break;
                }
                case 7: {
                    System.out.println("Exiting game...");
                    running = false;
                    break;
                }
                default: {
                    System.out.println("Invalid choice! Try again.");
                    break;
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

        Hero hero;

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

        heroes.add(hero);

        System.out.println("Hero created successfully: " + hero.getName());
    }

    private static void viewBattleRecords() {
        System.out.println("\n====== Battle Records ======");
        for (BattleRecord battleRecord : battleRecords) {
            battleRecord.printBattleRecord();
        }
    }

    private static void createEnemy() {
        System.out.println("\n=== Create Enemy ===");
        System.out.print("Enter enemy's name: ");
        String name = scanner.next();
        System.out.print("Enter enemy level: ");
        int level = scanner.nextInt();
        System.out.print("Enter enemy damage: ");
        int damage = scanner.nextInt();
        System.out.print("Enter enemy health: ");
        int health = scanner.nextInt();

        Enemy enemy;
        enemy = new Enemy.EnemyBuilder(name)
        .level(level)
        .dungeon("Asphodel")
        .damage(damage)
        .health(health)
        .build();

        enemies.add(enemy);
        System.out.println("Enemy created successfully: " + enemy.toString());
    }

    private static void showStatus() {
        System.out.println("\n====== Current Status ======");
        displayHeroes();
        displayEnemies();
    }

    private static void displayEnemies() {
        int i = 1;
        System.out.println("\n========= Enemies =========");
        if (enemies != null) {
            for (Enemy enemy : enemies) {
                System.out.println(i++ + ". " + enemy.toString());
            }
        } else
            System.out.println("No enemy created.");
    }

    private static void displayHeroes() {
        int i = 1;
        System.out.println("\n========= Heroes =========");
        if (heroes != null)
            for (Hero hero : heroes) {
                System.out.println(i++ + ". " + hero.toString());
            }
        else
            System.out.println("No hero created.");
    }

    private static void startBattle() {
        if (heroes.isEmpty() || enemies.isEmpty()) {
            System.out.println("No hero or enemy created!");
            return;
        }
        System.out.println("\n====== Select Your Hero ======");
        displayHeroes();
        System.out.print("Choose hero: ");
        int heroChoice = getIntInput() - 1;

        if (heroChoice < 0 || heroChoice >= heroes.size()) {
            System.out.println("Invalid hero choice!");
            return;
        }

        Hero hero = heroes.get(heroChoice);

        System.out.println("\n====== Select Enemy ======");
        displayEnemies();
        System.out.print("Choose enemy: ");
        int enemyChoice = getIntInput() - 1;

        if (enemyChoice < 0 || enemyChoice >= enemies.size()) {
            System.out.println("Invalid enemy choice!");
            return;
        }

        Enemy enemy = enemies.get(enemyChoice);

        BattleRecord battleRecord = new BattleRecord(enemy, hero);
        battleRecords.add(battleRecord);

        System.out.println("\n====== Battle Start! ======");
        System.out
                .println(hero.getName() + " (HP: " + hero.getHealth() + ") vs Enemy (HP: " + enemy.getHealth() + ")\n");

        while (hero.isAlive() && enemy.isAlive()) {
            hero.attack(enemy);

            if (!enemy.isAlive()) {
                System.out.println("Enemy defeated by " + hero.getName() + "!");
                enemies.remove(enemyChoice);
                break;
            }

            enemy.attack(hero);

            if (!hero.isAlive()) {
                System.out.println(hero.getName() + " has been defeated...");
                heroes.remove(heroChoice);
                break;
            }

            System.out.println("→ " + hero.getName() + " HP: " + hero.getHealth() +
                    " | Enemy HP: " + enemy.getHealth());
            System.out.println("------------------------------");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n====== Battle Over ======");
        if (hero.isAlive())
            System.out.println("Victory for " + hero.getName() + "!");
        else
            System.out.println("Enemy wins!");
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
