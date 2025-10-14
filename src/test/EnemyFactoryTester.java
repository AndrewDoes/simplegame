package test;

import model.Enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import factories.EnemyFactory;

/**
 * A dedicated performance tester for applying the Factory Design Pattern to Enemy creation.
 * This class isolates the test to compare direct constructor instantiation (baseline)
 * against the factory method.
 */
public class EnemyFactoryTester {

    private static final int ITERATIONS = 10000;

    /**
     * Runs the complete test for the Enemy Factory pattern and prints the results.
     */
    public static void runTest() {
        System.out.println();
        System.out.println("====== ENEMY FACTORY PATTERN PERFORMANCE TEST ======");
        System.out.println("Comparing Baseline (new) vs. Factory Method");
        System.out.println("Iterations per test: " + ITERATIONS + "\n");

        // --- Run tests for a standard Enemy ---
        System.out.println("--- Testing Enemy Creation ---");
        // We create the factory once, outside the test, to follow standard practice
        EnemyFactory enemyFactory = new EnemyFactory();

        testCreation(
            // Baseline: Direct constructor call
            () -> new Enemy("Goblin", "Asphodel", 5, 15, 80),
            // Factory: Using the pre-made factory instance
            () -> enemyFactory.createEnemy("Goblin", "Asphodel", 5, 15, 80)
        );

        System.out.println("====== ENEMY FACTORY TEST COMPLETE ======");
    }

    /**
     * A generic test method that runs both time and memory performance tests.
     */
    private static void testCreation(Supplier<Enemy> baselineCreator, Supplier<Enemy> factoryCreator) {
        // --- TIME TEST ---
        // Warm-up phase
        measureTime("Warm-up", baselineCreator::get);
        measureTime("Warm-up", factoryCreator::get);

        // Actual timed runs
        long baselineTime = measureTime("Baseline", baselineCreator::get);
        long factoryTime = measureTime("Factory", factoryCreator::get);
        System.out.printf("Time Result:   Baseline took %d ms | Factory took %d ms%n", baselineTime, factoryTime);

        // --- MEMORY TEST ---
        long baselineMemory = measureMemory("Baseline", baselineCreator);
        long factoryMemory = measureMemory("Factory", factoryCreator);
        System.out.printf("Memory Result: Baseline used %d KB | Factory used %d KB%n%n", baselineMemory, factoryMemory);
    }

    // ===================================================================
    //  Generic Measurement Utilities
    // ===================================================================

    private static long measureTime(String label, Runnable operation) {
        long startTime = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            operation.run();
        }
        long endTime = System.nanoTime();
        if (!label.equals("Warm-up")) {
            return (endTime - startTime) / 1_000_000;
        }
        return 0;
    }

    private static long measureMemory(String label, Supplier<Enemy> creator) {
        List<Enemy> list = new ArrayList<>();
        System.gc();
        long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        for (int i = 0; i < ITERATIONS; i++) {
            list.add(creator.get());
        }

        long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        return (memoryAfter - memoryBefore) / 1024; // KB
    }
}
