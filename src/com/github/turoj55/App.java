package com.github.turoj55;

import com.github.turoj55.generator.*;

public class App {

    private static final int ARRAY_LENGTH = 1_00_000;
    private static int[] array;

    public static void main(String[] args) throws InterruptedException {
        // генерация массива для сортировки
        ArrayGenThread arrayGenThread = new ArrayGenThread(ARRAY_LENGTH);
        array = arrayGenThread.getNewRandomArray();

        // сортровка Шелла в один поток
        PlainShell plainShell = new PlainShell(array);
        final long startTime = System.currentTimeMillis();
        plainShell.sort();
        final long stopTime = System.currentTimeMillis();
        System.out.println(String.format("Elapsed time: %d", (stopTime-startTime)));

        boolean checkResult = checkSort();
        String result = checkResult ? "Test success!" : "Test Failed!";
        System.out.println("Check result:  " + result);

    }

    private static boolean checkSort(){
        if (array != null) {
            for (int i = 0; i < (array.length - 1); i++) {
                if (array[i] > array[i + 1])
                    return false;
            }
        }
        return true;
    }
}
