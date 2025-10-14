package test;

import main.App;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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
            // Baseline: Creates a new App instance every time, using the public constructor.
            () -> new App(),
            
            // Singleton: Retrieves the single, existing instance every time.
            () -> App.getInstance()
        );

        System.out.println("====== SINGLETON TEST COMPLETE ======");
    }

    private static void testCreation(Supplier<Object> baselineCreator, Supplier<Object> singletonRetriever) {
        // --- TIME TEST ---
        long baselineTime = measureTime("Baseline", baselineCreator::get);
        long singletonTime = measureTime("Singleton", singletonRetriever::get);
        System.out.printf("Time Result:   Baseline took %d ms | Singleton took %d ms%n", baselineTime, singletonTime);

        // --- MEMORY TEST ---
        long baselineMemory = measureMemory("Baseline", baselineCreator);
        long singletonMemory = measureMemory("Singleton", singletonRetriever);
        System.out.printf("Memory Result: Baseline used %d KB | Singleton used %d KB%n%n", baselineMemory, singletonMemory);
    }

    // --- Generic Measurement Utilities (Consistent with other testers) ---
    private static long measureTime(String label, Runnable operation) {
        long startTime = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            operation.run();
        }
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000;
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