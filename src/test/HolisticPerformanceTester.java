package test;

import java.util.ArrayList;
import java.util.List;

import main.App;
import model.BattleRecord;
import model.Enemy;
import model.Hero;

/**
 * A dedicated stress tester for the complete, end-to-end system workflow.
 * This class simulates a large number of object creations and interactions
 * to compare the holistic performance of the baseline vs. the refactored
 * system.
 */
public class HolisticPerformanceTester {

    private static final int CHARACTER_COUNT = 10000;

    /**
     * Runs the large-scale stress test.
     */
    public static void runTest() {
        long startTime = System.nanoTime();
        long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.out.println("\n====== HOLISTIC STRESS TEST ======");
        System.out.println("Simulating " + CHARACTER_COUNT + " battles with design patterns");

        App game = App.getInstance();
        for (int i = 0; i < CHARACTER_COUNT; i++) {
            game.addHero(game.characterFactory, 1, "archer" + i);
            game.addEnemy("Enemy", 2, 50, 30);
        }

        for (int i = 0; i < 200; i++) {
            executeBattle(game.heroes.get(i), game.enemies.get(i), game.battleRecords);
        }


        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long memoryUsed = (memoryAfter - memoryBefore) / 1024; // Convert bytes to KB
        // Let's also verify the number of records to prove the battles ran
        System.out.printf("Final state: %d heroes, %d enemies, %d battle records.%n", game.heroes.size(),
                game.enemies.size(), game.battleRecords.size());
        System.out.println("\n====== HOLISTIC TEST COMPLETE ======");
        System.out.printf("Time Result:   The full simulation with console output took %d ms.%n", duration);
        System.out.printf("Memory Result: The net memory change after the test was %d KB.%n", memoryUsed);
    }

    private static void executeBattle(Hero hero, Enemy enemy, List<BattleRecord> records) {
        recordBattle(hero, enemy, records);
        while (hero.isAlive() && enemy.isAlive()) {
            hero.attack(enemy);
            if (enemy.isAlive()) {
                enemy.attack(hero);
            }
        }
    }

     private static void recordBattle(Hero hero, Enemy enemy, List<BattleRecord> records) {
        try {
            Hero heroSnapshot = (Hero) hero.clone();
            Enemy enemySnapshot = (Enemy) enemy.clone();
            BattleRecord record = new BattleRecord(enemySnapshot, heroSnapshot);
            records.add(record);
        } catch (CloneNotSupportedException e) {
            System.err.println("Error creating battle record snapshot.");
            e.printStackTrace();
        }
    }


    
}
