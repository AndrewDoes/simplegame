package test;

import java.util.ArrayList;
import java.util.List;

import model.Archer;
import model.BattleRecord;
import model.Enemy;
import model.Hero;

public class HolisticPerformanceTester {

    private static final int CHARACTER_COUNT = 10000;

    public static void runTest() {
        System.out.println("\n====== HOLISTIC STRESS TEST (BASELINE SYSTEM) ======");
        System.out.println("Simulating " + CHARACTER_COUNT + " creations and battles without design patterns...");
        System.out.println("NOTE: This test is silent and does not print battle logs.\n");

        List<Hero> heroes = new ArrayList<>();
        List<Enemy> enemies = new ArrayList<>();
        List<BattleRecord> battleRecords = new ArrayList<>();

        // --- Measurement Start ---
        System.gc();
        long startTime = System.nanoTime();
        long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        // 1. Creation Phase (Baseline: using 'new')
        for (int i = 0; i < CHARACTER_COUNT; i++) {
            heroes.add(new Archer("Archer_" + i));
            enemies.add(new Enemy("Goblin_" + i, "Forest", 5, 15, 80));
        }

        // 2. Battle Phase (Baseline: silent battle, no prototype)
        for (int i = 0; i < 200; i++) {
            executeSilentBaselineBattle(heroes.get(i), enemies.get(i), battleRecords);
        }

        // --- Measurement End ---
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long memoryUsed = (memoryAfter - memoryBefore) / 1024;

        System.out.printf("Final state: %d heroes, %d enemies, %d battle records.%n", heroes.size(),
                enemies.size(), battleRecords.size());

        System.out.println("\n====== BASELINE TEST COMPLETE ======");
        System.out.printf("Time Result:   The baseline simulation took %d ms.%n", duration);
        System.out.printf("Memory Result: The net memory change was %d KB.%n", memoryUsed);
    }

    private static void executeSilentBaselineBattle(Hero hero, Enemy enemy, List<BattleRecord> records) {
        records.add(new BattleRecord(enemy, hero)); // No prototype pattern
        while (hero.isAlive() && enemy.isAlive()) {
            hero.attack(enemy);
            if (enemy.isAlive()) {
                enemy.attack(hero);
            }
        }
    }

}
