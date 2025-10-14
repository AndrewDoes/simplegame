package test;

import model.Enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A dedicated performance tester for the "fully fluent" Builder Design Pattern.
 * This class isolates the test to compare direct constructor instantiation (baseline)
 * against the refined builder where only the 'name' is in the constructor.
 */
public class BuilderTester {

    private static final int ITERATIONS = 10000;

    /**
     * Runs the complete suite of tests for the Builder pattern and prints the results.
     */
    public static void runTest() {
        System.out.println("====== FULLY FLUENT BUILDER PATTERN PERFORMANCE TEST ======");
        System.out.println("Comparing Baseline (new) vs. Fully Fluent Builder Method");
        System.out.println("Iterations per test: " + ITERATIONS + "\n");

        System.out.println("--- Testing Enemy Creation ---");
        testCreation(
            // Baseline: The original direct constructor call
            () -> new Enemy("Slime", "Cave", 5, 10, 100),
            
            // Refined Builder: The new version with chained methods for all attributes
            () -> new Enemy.EnemyBuilder("Slime")
                            .Level(5)
                            .Damage(10)
                            .Health(100)
                            .Dungeon("Cave")
                            .build()
        );

        System.out.println("====== FULLY FLUENT BUILDER TEST COMPLETE ======");
    }

    /**
     * A generic test method that takes two creation methods (baseline and builder)
     * and runs both time and memory performance tests on them.
     */
    private static void testCreation(Supplier<Enemy> baselineCreator, Supplier<Enemy> builderCreator) {
        // --- TIME TEST ---
        long baselineTime = measureTime("Baseline", baselineCreator::get);
        long builderTime = measureTime("Builder", builderCreator::get);
        System.out.printf("Time Result:   Baseline took %d ms | Builder took %d ms%n", baselineTime, builderTime);

        // --- MEMORY TEST ---
        long baselineMemory = measureMemory("Baseline", baselineCreator);
        long builderMemory = measureMemory("Builder", builderCreator);
        System.out.printf("Memory Result: Baseline used %d KB | Builder used %d KB%n%n", baselineMemory, builderMemory);
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
        return (endTime - startTime) / 1_000_000;
    }

    private static long measureMemory(String label, Supplier<Enemy> creator) {
        List<Enemy> list = new ArrayList<>();
        System.gc();
        long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        for (int i = 0; i < ITERATIONS; i++) {
            list.add(creator.get());
        }
        long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        return (memoryAfter - memoryBefore) / 1024;
    }
}