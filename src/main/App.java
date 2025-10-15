package main;

import factories.EnemyFactory;
import factories.StandardCharacterFactory;
import model.*;

import java.util.ArrayList;
import java.util.Scanner;
import test.HolisticPerformanceTester;

public class App {

    // All public fields and methods below are supposed to be private
    // they're public for test purpose
    private static App instance;

    public ArrayList<Hero> heroes = new ArrayList<>();
    public ArrayList<Enemy> enemies = new ArrayList<>();
    public ArrayList<BattleRecord> battleRecords = new ArrayList<>();
    public Scanner scanner = new Scanner(System.in);
    public StandardCharacterFactory characterFactory = new StandardCharacterFactory();

    private App() {
    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public static void main(String[] args) {
        App.getInstance().run();
    }

    public void run() {
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
            System.out.println("6. Launch Test");
            System.out.println("7. Exit");
            System.out.print("Choose: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    createHero();
                    break;
                case 2:
                    createEnemy();
                    break;
                case 3:
                    showStatus();
                    break;
                case 4:
                    startBattle();
                    break;
                case 5:
                    viewBattleRecords();
                    break;
                case 6:
                    HolisticPerformanceTester.runTest();
                    break;
                case 7:
                    System.out.println("Exiting game...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
                    break;
            }
        }
        scanner.close();
    }

    private void createHero() {
        System.out.println("\n=== Create Your Hero ===");
        System.out.println("1. Archer");
        System.out.println("2. Fighter");
        System.out.print("Choose your hero type: ");
        int type = getIntInput();

        if (characterFactory.getHeroFactory(type) == null) {
            System.out.println("Invalid hero type!");
            return;
        }

        System.out.print("Enter hero name: ");
        String name = scanner.next();

        Hero hero = addHero(characterFactory, type, name);
        System.out.println("Hero created successfully: " + hero.getName());
    }

    public Hero addHero(StandardCharacterFactory factory, int type, String name) {
        Hero hero = factory.getHeroFactory(type).createHero(name);
        heroes.add(hero);
        return hero;
    }

    private void createEnemy() {
        System.out.println("\n=== Create Enemy ===");
        System.out.print("Enter enemy's name: ");
        String name = scanner.next();
        System.out.print("Enter enemy level: ");
        int level = getIntInput();
        System.out.print("Enter enemy damage: ");
        int damage = getIntInput();
        System.out.print("Enter enemy health: ");
        int health = getIntInput();

        Enemy enemy = addEnemy(name, level, damage, health);
        System.out.println("Enemy created successfully: " + enemy.toString());
    }

    public Enemy addEnemy(String name, int level, int damage, int health) {
        Enemy enemy = new EnemyFactory().createEnemy(name, name, level, health, damage);
        enemies.add(enemy);
        return enemy;
    }

    private void startBattle() {
        if (heroes.isEmpty() || enemies.isEmpty()) {
            System.out.println("No hero or enemy created!");
            return;
        }

        System.out.println("\n====== Select Your Hero ======");
        displayHeroes();
        System.out.print("Choose hero: ");
        int heroChoice = getIntInput();
        Hero hero = heroes.get(heroChoice - 1);

        System.out.println("\n====== Select Enemy ======");
        displayEnemies();
        System.out.print("Choose enemy: ");
        int enemyChoice = getIntInput();
        Enemy enemy = enemies.get(enemyChoice - 1);

        System.out.println("\n====== Battle Start! ======");
        System.out.println(hero.getName() + " (HP: " + hero.getHealth() + ") vs " + enemy.getName() + " (HP: "
                + enemy.getHealth() + ")\n");

        executeBattle(hero, enemy);
    }

    public void executeBattle(Hero hero, Enemy enemy) {
        recordBattle(hero, enemy);
        while (hero.isAlive() && enemy.isAlive()) {
            hero.attack(enemy);
            if (!enemy.isAlive()) {
                System.out.println(enemy.getName() + " defeated by " + hero.getName() + "!");
                enemies.remove(enemy);
                break;
            }

            enemy.attack(hero);
            if (!hero.isAlive()) {
                System.out.println(hero.getName() + " has been defeated...");
                heroes.remove(hero);
                break;
            }

            System.out.println("→ " + hero.getName() + " HP: " + hero.getHealth() + " | " + enemy.getName() + " HP: "
                    + enemy.getHealth());
            System.out.println("------------------------------");
        }

        System.out.println("\n====== Battle Over ======");
    }

    private void recordBattle(Hero hero, Enemy enemy) {
        try {
            Hero heroSnapshot = (Hero) hero.clone();
            Enemy enemySnapshot = (Enemy) enemy.clone();
            BattleRecord record = new BattleRecord(enemySnapshot, heroSnapshot);
            battleRecords.add(record);
        } catch (CloneNotSupportedException e) {
            System.err.println("Error creating battle record snapshot.");
            e.printStackTrace();
        }
    }

    public void viewBattleRecords() {
        System.out.println("\n====== Battle Records (Snapshots at start of battle) ======");
        if (battleRecords.isEmpty()) {
            System.out.println("No battles have been recorded yet.");
            return;
        }
        for (int i = 0; i < battleRecords.size(); i++) {
            System.out.println("\n--- Record " + (i + 1) + " ---");
            battleRecords.get(i).printBattleRecord();
        }
    }

    private void showStatus() {
        System.out.println("\n====== Current Status ======");
        displayHeroes();
        displayEnemies();
    }

    private void displayEnemies() {
        System.out.println("\n========= Enemies =========");
        if (enemies.isEmpty()) {
            System.out.println("No enemies have been created.");
            return;
        }
        for (int i = 0; i < enemies.size(); i++) {
            System.out.println((i + 1) + ". " + enemies.get(i).toString());
        }
    }

    private void displayHeroes() {
        System.out.println("\n========= Heroes =========");
        if (heroes.isEmpty()) {
            System.out.println("No heroes have been created.");
            return;
        }
        for (int i = 0; i < heroes.size(); i++) {
            System.out.println((i + 1) + ". " + heroes.get(i).toString());
        }
    }

    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
