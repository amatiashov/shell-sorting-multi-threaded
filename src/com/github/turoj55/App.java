package com.github.turoj55;

import com.github.turoj55.generator.*;

public class App {

    private static final int ARRAY_LENGTH = 1_000_000;
    private static int[] array;
    private static long startTime;
    private static long stopTime;
    private static boolean checkResult;
    private static String result;

    public static void main(String[] args) throws InterruptedException {
//        генерация массива для сортировки
        ArrayGenThread arrayGenThread = new ArrayGenThread(ARRAY_LENGTH);
        array = arrayGenThread.getNewRandomArray();

//        System.out.println("Сортровка Шелла в один поток");
//        PlainShell plainShell = new PlainShell(array);
//        long startTime = System.currentTimeMillis();
//        plainShell.sort();
//        long stopTime = System.currentTimeMillis();
//        System.out.println(String.format("Elapsed time: %d", (stopTime-startTime)));
//
//        boolean checkResult = checkSort();
//        String result = checkResult ? "Test success!" : "Test Failed!";
//        System.out.println("Check result:  " + result);


        System.out.println("Многопоточная сортировка Шелла:");
        array = arrayGenThread.getNewRandomArray();
        ThreadsShell threadsShell = new ThreadsShell(array);
        startTime = System.currentTimeMillis();
        threadsShell.sort();
        stopTime = System.currentTimeMillis();
        System.out.println(String.format("Elapsed time: %d", (stopTime-startTime)));
        checkResult = checkSort();
        result = checkResult ? "Test success!" : "Test Failed!";
        System.out.println("Check result:  " + result);
        for (int item: array)
            System.out.printf(item + " ");
        System.out.println();

    }

    private static boolean checkSort(){
        if (array != null) {
            for (int i = 0; i < (array.length - 1); i++) {
                if (array[i] > array[i + 1]){
                    System.out.println(String.format("a[%d] = %d a[%d] = %d", i, array[i], i+1, array[i+1]));
                    return false;
                }
            }
        }
        return true;
    }
}
