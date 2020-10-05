package edu.emory.cs.sort.divide_conquer;

import edu.emory.cs.sort.distribution.RadixSortQuiz;

public class Test {
    public static void main(String args[]){
        Integer[] array = new Integer[5];
        array[0] = 11;
        array[1] = 220;
        array[2] = 33;
        array[3] = 32;
        array[4] = 38;

        RadixSortQuiz x = new RadixSortQuiz();
        x.sort(array, 0, 5);

        for (int i = 0; i < 5; i++) {
            System.out.println(array[i]);
        }
    }
}
