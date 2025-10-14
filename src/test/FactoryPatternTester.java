package test;

import factories.ArcherFactory;
import factories.FighterFactory;
import model.Archer;
import model.Fighter;
import model.Hero;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A dedicated performance tester for the Factory Design Pattern.
 * This class isolates the test to compare direct instantiation (baseline)
 * against the factory method for creating different Hero types.
 */
public class FactoryPatternTester {

    private static final int ITERATIONS = 10000;

    /**
     * Runs the complete suite of tests for the Factory pattern and prints the results.
     */
    public static void runTest() {
        System.out.println("====== FACTORY PATTERN PERFORMANCE TEST ======");
        System.out.println("Comparing Baseline (new) vs. Factory Method");
        System.out.println("Iterations per test: " + ITERATIONS + "\n");

        // --- Run tests for Archer ---
        System.out.println("--- Testing Archer Creation ---");
        testCreation(
            () -> new Archer("BaselineArcher"), 
            new ArcherFactory()::createHero
        );

        // --- Run tests for Fighter ---
        System.out.println("--- Testing Fighter Creation ---");
        testCreation(
            () -> new Fighter("BaselineFighter"), 
            new FighterFactory()::createHero
        );
        
        System.out.println("====== FACTORY TEST COMPLETE ======");
    }

    /**
     * A generic test method that takes two creation methods (baseline and factory)
     * and runs both time and memory performance tests on them.
     *
     * @param baselineCreator A lambda expression for the baseline object creation.
     * @param factoryCreator  A lambda expression for the factory object creation.
     */
    private static void testCreation(Supplier<Hero> baselineCreator, java.util.function.Function<String, Hero> factoryCreator) {
        // --- TIME TEST ---
        // Warm-up phase to allow JIT compilation to stabilize
        measureTime("Warm-up", () -> baselineCreator.get());
        measureTime("Warm-up", () -> factoryCreator.apply("Test"));

        // Actual timed runs
        long baselineTime = measureTime("Baseline", () -> baselineCreator.get());
        long factoryTime = measureTime("Factory", () -> factoryCreator.apply("Test"));
        System.out.printf("Time Result:   Baseline took %d ms | Factory took %d ms%n", baselineTime, factoryTime);
        
        // --- MEMORY TEST ---
        long baselineMemory = measureMemory("Baseline", () -> baselineCreator.get());
        long factoryMemory = measureMemory("Factory", () -> factoryCreator.apply("Test"));
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
        // Don't print warm-up runs to keep the output clean
        if (!label.equals("Warm-up")) {
            return (endTime - startTime) / 1_000_000;
        }
        return 0;
    }

    private static long measureMemory(String label, Supplier<Hero> creator) {
        List<Hero> list = new ArrayList<>();
        System.gc();
        long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        
        for (int i = 0; i < ITERATIONS; i++) {
            list.add(creator.get());
        }
        
        long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        return (memoryAfter - memoryBefore) / 1024; // KB
    }
}