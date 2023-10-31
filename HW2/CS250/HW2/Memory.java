package HW2;

import java.util.Random;

public class Memory {

// ~~~~~~~~~~ TASK 1 ~~~~~~~~~~
    static volatile int loopIterV;
    public static void T1(String size, String experiments) {
        int exp = Integer.parseInt(experiments); // 'For' variable setup
        long totals[] = new long[exp];
        long totalsV[] = totals; 
        double times[] = new double[exp]; 
        double timesV[] = times;
        final double nanoToSec = 1000000000;

        for (int i = 0; i < exp; i++) { // For loop start ~~~~~~~~~~
            long runningTotal = 0; // 'While' variable setup
            long runningTotalV = 0;
            int loopIter = Integer.parseInt(size); 
            loopIterV = loopIter;

            long startTime = System.nanoTime();
            while (loopIter > 0) { // Non-volatile loop start ----------
                if (loopIter % 2 == 0) {
                    runningTotal += loopIter;
                } else {
                    runningTotal -= loopIter;
                }
                loopIter--;
            } // Non-volatile loop end ----------
            long estimatedTime = System.nanoTime() - startTime;
            totals[i] = runningTotal; // Collect totals
            times[i] = estimatedTime/nanoToSec; // & times

            long startTimeV = System.nanoTime();
            while (loopIterV > 0) { // Volatile loop start ----------
                if (loopIterV % 2 == 0) {
                    runningTotalV += loopIterV;
                } else {
                    runningTotalV -= loopIterV;
                }
                loopIterV--;
            } // Volatile loop end ----------
            long estimatedTimeV = System.nanoTime() - startTimeV;
            totalsV[i] = runningTotalV; // Collect totals (volatile)
            timesV[i] = estimatedTimeV/nanoToSec; // & times (volatile)

        } // For loop end ~~~~~~~~~~

        System.out.println("Regular: " + String.format("%.10f", getAverage(times)) + " seconds");
        System.out.println("Volatile: " + String.format("%.10f", getAverage(timesV)) + " seconds");
        System.out.println("Avg regular sum: " + getAverage(totals));
        System.out.println("Avg volatile sum: " + getAverage(totalsV)); 
    }

// ~~~~~~~~~~ TASK 2 ~~~~~~~~~~
    public static void T2(String size, String experiments, String seed) {
        int sizeInt = Integer.parseInt(size);
        Random rand = new Random(Integer.parseInt(seed));
        Integer[] arr = new Integer[sizeInt];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt();
        }
        double avg;
        long startTime1;
        long estimatedTime1;
        long[] times1 = new long[sizeInt/10 * Integer.parseInt(experiments)];
        Integer[] accessArr = new Integer[sizeInt/10 + 1 * Integer.parseInt(experiments)];
        long startTime2;
        long estimatedTime2;
        long[] times2 = new long[sizeInt/10 * Integer.parseInt(experiments)];
        int randomAccess = rand.nextInt(sizeInt/10);
        for (int i = 0; i < Integer.parseInt(experiments); i++) { // For "experiments" number of times
            for (int j = 0; j < sizeInt/10; j++) { // Access first 10%
                startTime1 = System.nanoTime();
                accessArr[j] = arr[j];
                estimatedTime1 = System.nanoTime() - startTime1;
                times1[j] = estimatedTime1;
            }
            startTime2 = System.nanoTime();
            accessArr[-1] = arr[sizeInt - randomAccess]; // Access a random last 10%
            estimatedTime2 = startTime2 - System.nanoTime();
            times2[i] = estimatedTime2;
        }
        
        System.out.println("Avg time to access known element: " + String.format("%.2f", getAverage(times1)) + " nanoseconds");
        System.out.println("Avg time to access random element: " + String.format("%.2f", getAverage(times2)) + " nanoseconds");
        System.out.println("Sum: " + String.format("%.2f", getAverage(accessArr)));
    }

// ~~~~~~~~~~ TASK 3 ~~~~~~~~~~
    public static void T3(String size, String experiments) {

    }

// Helper Methods
    public static double getAverage(long[] arr) {
        double sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        double average = sum / (double)arr.length;
        return average;
    }
    
    public static double getAverage(double[] arr) {
        double sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        double average = sum / (double)arr.length;
        return average;
    }

    public static double getAverage(Integer[] arr) {
        double sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        double average = sum / (double)arr.length;
        return average;
    }

    public static void main(String[] args) { // Main loop
        int SIZE = 0, EXPERIMENTS = 1, SEED = 2; // Enum variable setup
        String[] args2 = {"25000000", "1", "42"}; // TODO REMOVE AND FIX

        // TASK 1
        System.out.println("Task 1");
        T1(args2[SIZE], args2[EXPERIMENTS]);

        // TASK 2
        System.out.println("\nTask 2");
        T2(args2[SIZE], args2[EXPERIMENTS], args2[SEED]);

        // TASK 3
        System.out.println("\nTask 3");
        T3(args2[SIZE], args2[EXPERIMENTS]);
    }
}