package com.github.turoj55.generator;

import java.util.Random;

public class ArrayGenThread implements Runnable {
    private int[] array;
    private int arrayLength;
    private StateObject stateObject;

    public ArrayGenThread(int arrayLength){
        this.stateObject = new StateObject();
        this.arrayLength = arrayLength;
    }

    @Override
    public void run() {
        array = new int [arrayLength];
        Random random = new Random();
        for (int i = 0; i < arrayLength; i++)
            array[i] = random.nextInt(100);
            //array[i] = arrayLength-i; // заглушка. Числа по убыванию от 1 до array.length
        stateObject.changeState();

    }

    public int[] getNewRandomArray() throws InterruptedException {
        genArray();
        return array;
    }
    private void genArray() throws InterruptedException {
        System.out.println("Array length: " + arrayLength);
        System.out.printf("Generate array");
        (new Thread(this)).start();

        while (stateObject.getState()){
            System.out.printf(".");
            Thread.sleep(100);
        }
        stateObject.reset();
        System.out.println("OK!");
    }
}
