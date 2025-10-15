package test;

import main.App;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A dedicated performance tester for the Singleton Design Pattern,
 * designed to be consistent with the methodology used for other patterns.
 */
public class SingletonTester {

    private static final int ITERATIONS = 10000;

    public static void runTest() {
        System.out.println("====== SINGLETON PATTERN PERFORMANCE TEST ======");
        System.out.println("Comparing Baseline (new App()) vs. Singleton (App.getInstance())");
        System.out.println("Iterations per test: " + ITERATIONS + "\n");

        // --- Qualitative Check: Instance Uniqueness ---
        App instance1 = App.getInstance();
        App instance2 = App.getInstance();
        boolean isSameInstance = (instance1 == instance2);
        System.out.println("--- Qualitative Check: Instance Uniqueness ---");
        System.out.println("Are both instances the same object? " + isSameInstance);
        System.out.println("Uniqueness Test Passed: " + isSameInstance + "\n");

        // --- Quantitative Performance Measurement ---
        System.out.println("--- Quantitative Performance Measurement ---");
        testCreation(
            // Baseline: Creates a new App instance every time, using a public constructor.
            () -> new App(),
            
            // Singleton: Retrieves the single, existing instance every time.
            () -> App.getInstance()
        );

        System.out.println("====== SINGLETON TEST COMPLETE ======");
    }

    /**
     * A generic test method that runs both time and memory performance tests.
     */
    private static void testCreation(Supplier<Object> baselineCreator, Supplier<Object> singletonRetriever) {
        // --- TIME TEST ---
        // Warm-up phase to allow JIT compilation to stabilize
        measureTime("Warm-up", baselineCreator::get, 1000); // Warm-up with fewer iterations
        measureTime("Warm-up", singletonRetriever::get, 1000);

        // Actual timed runs with the full iteration count
        long baselineTime = measureTime("Baseline", baselineCreator::get, ITERATIONS);
        long singletonTime = measureTime("Singleton", singletonRetriever::get, ITERATIONS);
        System.out.printf("Time Result:   Baseline took %d ms | Singleton took %d ms%n", baselineTime, singletonTime);

        // --- MEMORY TEST ---
        long baselineMemory = measureMemory("Baseline", baselineCreator);
        long singletonMemory = measureMemory("Singleton", singletonRetriever);
        System.out.printf("Memory Result: Baseline used %d KB | Singleton used %d KB%n%n", baselineMemory, singletonMemory);
    }

    // ===================================================================
    //  Generic Measurement Utilities (Consistent with other testers)
    // ===================================================================

    private static long measureTime(String label, Runnable operation, int iterations) {
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            operation.run();
        }
        long endTime = System.nanoTime();
        // Don't print warm-up runs to keep the output clean
        if (!label.equals("Warm-up")) {
            return (endTime - startTime) / 1_000_000;
        }
        return 0;
    }

    private static long measureMemory(String label, Supplier<Object> creator) {
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
