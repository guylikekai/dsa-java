package edu.emory.cs.sort.hybrid;

import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.divide_conquer.IntroSort;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class HybridSortHW4<T extends Comparable<T>> implements HybridSort<T> {
    @Override
    public T[] sort(T[][] input) {
        // TODO: to be updated
        AbstractSort<T> introSort = new IntroSort<T>(new ShellSortKnuth<T>());
        int size = Arrays.stream(input).mapToInt(t -> t.length).sum();
        T[] output = (T[]) Array.newInstance(input[0][0].getClass(), size);


        //Iterate through each row and sort the elements in that row
        for(int i = 0; i < input.length; i++) {
            introSort.sort(input[i]);
        }

        int[] indexes = new int[input.length];

        for (int i = 0; i < output.length; i++) {
            output[i] = findMin(input, indexes);
        }

        return output;
    }

    private T findMin(T[][] input, int[] indexes) {
        int min = 0;

        while (indexes[min] == -1) {
            min++;
        }

        for (int i = min + 1; i < input.length; i++) {
            if (indexes[i] != -1 && input[min][indexes[min]].compareTo(input[i][indexes[i]]) > 0) {
                min = i;
            }
        }

        if(indexes[min] + 1 < input[min].length) {
            indexes[min]++;
            return input[min][indexes[min] -1];
        } else {
            int temp = indexes[min];
            int temp1 = min;
            indexes[min] = -1;
            return input[min][temp];
        }
    }
}