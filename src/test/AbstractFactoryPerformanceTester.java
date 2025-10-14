package test;

import factories.CharacterFactory;
import factories.EnemyFactory;
import factories.HeroFactory;
import factories.StandardCharacterFactory;
import model.Archer;
import model.Enemy;
import model.Fighter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A dedicated performance tester for the Abstract Factory Design Pattern.
 * This class isolates the test to compare direct constructor instantiation (baseline)
 * against the full, multi-step Abstract Factory creation process.
 */
public class AbstractFactoryPerformanceTester {

    private static final int ITERATIONS = 10000;

    public static void runTest() {
        System.out.println("====== ABSTRACT FACTORY PATTERN PERFORMANCE TEST ======");
        System.out.println("Comparing Baseline (new) vs. Abstract Factory Method");
        System.out.println("Iterations per test: " + ITERATIONS + "\n");

        // --- Setup the Abstract Factory ---
        // The main factory is created once, as it would be in a real application.
        CharacterFactory mainFactory = new StandardCharacterFactory();

        // --- Test Archer Creation ---
        System.out.println("--- Testing Archer Creation ---");
        testCreation(
            // Baseline: Direct instantiation
            () -> new Archer("Test"),
            // Abstract Factory: Full process of getting sub-factory then creating
            () -> {
                HeroFactory heroFactory = mainFactory.getHeroFactory(1); // 1 for Archer
                return heroFactory.createHero("Test");
            }
        );

        // --- Test Fighter Creation ---
        System.out.println("--- Testing Fighter Creation ---");
        testCreation(
            () -> new Fighter("Test"),
            () -> {
                HeroFactory heroFactory = mainFactory.getHeroFactory(2); // 2 for Fighter
                return heroFactory.createHero("Test");
            }
        );

        // --- Test Enemy Creation ---
        System.out.println("--- Testing Enemy Creation ---");
        testCreation(
            () -> new Enemy("Test", "Dungeon", 1, 10, 100),
            () -> {
                EnemyFactory enemyFactory = mainFactory.getEnemyFactory();
                return enemyFactory.createEnemy("Test", "Dungeon", 1, 10, 100);
            }
        );

        System.out.println("====== ABSTRACT FACTORY TEST COMPLETE ======");
    }

    /**
     * Generic test method for any object type that extends Object.
     */
    private static void testCreation(Supplier<Object> baselineCreator, Supplier<Object> factoryCreator) {
        // Time Test
        long baselineTime = measureTime(baselineCreator::get);
        long factoryTime = measureTime(factoryCreator::get);
        System.out.printf("Time Result:   Baseline took %d ms | Abstract Factory took %d ms%n", baselineTime, factoryTime);

        // Memory Test
        long baselineMemory = measureMemory(baselineCreator);
        long factoryMemory = measureMemory(factoryCreator);
        System.out.printf("Memory Result: Baseline used %d KB | Abstract Factory used %d KB%n%n", baselineMemory, factoryMemory);
    }

    // --- Generic Measurement Utilities ---

    private static long measureTime(Runnable operation) {
        long startTime = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            operation.run();
        }
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000;
    }

    private static long measureMemory(Supplier<Object> creator) {
        List<Object> list = new ArrayList<>();
        System.gc();
        long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        for (int i = 0; i < ITERATIONS; i++) {
            list.add(creator.get());
        }
        long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        return (memoryAfter - memoryBefore) / 1024; // KB
    }
}