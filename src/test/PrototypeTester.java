package test;

import model.Enemy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A dedicated performance tester for the Prototype Design Pattern.
 * This class isolates the test to compare direct constructor instantiation (baseline)
 * against cloning a pre-existing prototype instance.
 */
public class PrototypeTester {

    private static final int ITERATIONS = 1_000_000;

    /**
     * Runs the complete suite of tests for the Prototype pattern.
     */
    public static void runTest() {
        System.out.println("====== PROTOTYPE PATTERN PERFORMANCE TEST ======");
        System.out.println("Comparing Baseline (new) vs. Prototype (clone)");
        System.out.println("Iterations per test: " + ITERATIONS + "\n");

        // --- Create a master prototype object that will be cloned ---
        Enemy goblinPrototype = new Enemy("Goblin", "Forest", 5, 15, 80);

        System.out.println("--- Testing Enemy Creation ---");
        testCreation(
            // Baseline: Creating a new Enemy object from scratch every time.
            () -> new Enemy("Goblin", "Forest", 5, 15, 80),
            
            // Prototype: Cloning the existing prototype object every time.
            () -> {
                try {
                    return (Enemy) goblinPrototype.clone();
                } catch (CloneNotSupportedException e) {
                    // This should not happen if Enemy implements Cloneable correctly
                    e.printStackTrace();
                    return null;
                }
            }
        );

        System.out.println("====== PROTOTYPE TEST COMPLETE ======");
    }

    /**
     * A generic test method that runs both time and memory performance tests.
     */
    private static void testCreation(Supplier<Enemy> baselineCreator, Supplier<Enemy> prototypeCloner) {
        // --- TIME TEST ---
        // Warm-up phase to allow JIT compilation to stabilize
        measureTime("Warm-up", baselineCreator::get, 10000); // Warm-up with fewer iterations
        measureTime("Warm-up", prototypeCloner::get, 10000);

        // Actual timed runs with the full iteration count
        long baselineTime = measureTime("Baseline", baselineCreator::get, ITERATIONS);
        long prototypeTime = measureTime("Prototype", prototypeCloner::get, ITERATIONS);
        System.out.printf("Time Result:   Baseline took %d ms | Prototype took %d ms%n", baselineTime, prototypeTime);

        // --- MEMORY TEST ---
        long baselineMemory = measureMemory("Baseline", baselineCreator);
        long prototypeMemory = measureMemory("Prototype", prototypeCloner);
        System.out.printf("Memory Result: Baseline used %d KB | Prototype used %d KB%n%n", baselineMemory, prototypeMemory);
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
