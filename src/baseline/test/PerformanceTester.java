package baseline.test;

import java.util.ArrayList;
import java.util.List;

import baseline.model.*; // Make sure to import necessary model classes

public class PerformanceTester {

    public static void testHeroCreation() {
        System.out.println("--- Running Performance Test: Hero Creation ---");

        // Test Case 1: Baseline (using 'new')
        long startTimeBaseline = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            new Archer("Archer " + i);
        }
        long endTimeBaseline = System.nanoTime();
        long durationBaseline = (endTimeBaseline - startTimeBaseline) / 1000000;
        System.out.println("Baseline (new Archer()): " + durationBaseline + " ms");
        System.out.println("--- Test Complete ---\n");
    }
    
    public static void testMemoryUsage() {
        System.out.println("--- Running Performance Test: Memory Usage ---");
        
        // Test Case 1: Baseline (using 'new')
        runMemoryTest_Baseline();

        // Test Case 2: Refactored (using Factory)
        // UNCOMMENT this line after you create the HeroFactory
        // runMemoryTest_Factory();
        
        System.out.println("--- Memory Test Complete ---\n");
    }

    private static void runMemoryTest_Baseline() {
        List<Hero> heroList = new ArrayList<>();
        
        // Force garbage collection to get a cleaner measurement
        System.gc(); 
        
        long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        for (int i = 0; i < 10000; i++) {
            heroList.add(new Archer("Archer " + i));
        }

        long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long memoryUsed = (memoryAfter - memoryBefore) / 1024; // Convert bytes to KB

        System.out.println("Baseline (new Archer()) Memory Used: " + memoryUsed + " KB");
    }
}