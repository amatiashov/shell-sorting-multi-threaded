package com.github.turoj55;


import java.util.ArrayList;
import java.util.Comparator;

class ThreadsShell implements Runnable{
    private static final ArrayList<Integer> steps = new ArrayList<>();
    private static ArrayList<Thread> threads = new ArrayList<>();
    private static int[] array;
    private int  currentStep = -1;
    private int startItem = -1;


    ThreadsShell(int[] array) {
        ThreadsShell.array = array;
    }

    private ThreadsShell(int currentStep, int startItem){
        this.currentStep = currentStep;
        this.startItem = startItem;
    }


    @Override
    public void run() {
        if (currentStep < 0 || startItem < 0)
            throw new IllegalArgumentException("currentStep and startItem must be not null");

        for (int j = startItem; (j > 0) && (array[j-currentStep] > array[j]); j -= currentStep) {
            swap(j-currentStep, j);
        }
    }

    void sort(){
        // генерируем шаги сортироки
        genSteps(array.length);

        Thread currentThread;
        for (int step: steps) {
            for (int i = step; i < array.length; i += step) {
                currentThread = new Thread(new ThreadsShell(step, i));
                threads.add(currentThread);
                currentThread.start();
            }
            for (Thread thread: threads)
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            threads.clear();
        }
    }

    private void swap(int i, int j){
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private void genSteps(long sizeOfArray){
        // класс для сортровки шагов по убыванию. Передаем методу steps.sort().
        class ReversCompare implements Comparator<Integer> {
            @Override
            public int compare(Integer int1, Integer int2) {
                if (int1 < int2)
                    return 1;
                else if (int1.equals(int2))
                    return 0;
                else return -1;
            }
        }
        /**
         * Если длина массива меньше 4000 элементов, целесообразно использовать
         * эмпирическую последовательность Марцина Циура
         * http://bit.ly/2lHxaDv
         */
        if (array.length <= 4_000){
            int[] steps = new int[]{1, 4, 10, 23, 57, 145, 356, 911, 1968, 4711, 11969, 27901,
                    84801, 213331, 543749, 1355339, 3501671, 8810089, 21521774,
                    58548857, 157840433, 410151271, 1131376761, 2147483647};
            int i = 0;
            while (steps[i] < array.length)
                this.steps.add(steps[i++]);
            this.steps.trimToSize();
            this.steps.sort(new ReversCompare());
            return;
        }
        /**
         * Последовательность Р.Седжвика
         * http://codelab.ru/t/shell_sort:optimization/
         *
         *            [ 9 * 2^i - 9 * 2^(i/2) + 1,      i % 2 == 0
         * steps[i] = |
         *            [ 8 * 2^i - 6 * 2^{(i+1)/2} + 1,  i % 2 == 1
         * Обозначние переменных:
         *  2^i         = p1
         *  2^{(i+1)/2} = p2
         *  2^(i/2)     = p3
         */
        int p1, p2, p3;
        int i = -1;
        p1 = p2 = p3 = 1;
        do {
            if (++i % 2 == 0){
                steps.add(9 * p1 - 9 * p3 +1);
                p2 *= 2;
            }
            else {
                steps.add(8 * p1 - 6 * p2 + 1);
                p3 *= 2;
            }
            p1 *= 2;
        } while (3 * steps.get(i) < sizeOfArray);

        steps.trimToSize();
        steps.sort(new ReversCompare());
        if (i > 0)
            steps.remove(0);
    }

}
