package edu.emory.cs.sort.hybrid;

import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.divide_conquer.IntroSort;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class HybridSortHW<T extends Comparable<T>> implements HybridSort<T> {

    @Override
    public T[] sort(T[][] input) {
        AbstractSort<T> introSort = new IntroSort<T>(new ShellSortKnuth<T>());
        int size = Arrays.stream(input).mapToInt(t -> t.length).sum();
        T[] output = (T[]) Array.newInstance(input[0][0].getClass(), size);


        //Iterate through each row and sort the elements in that row
        for(int i = 0; i < input.length; i++) {
            introSort.sort(input[i]);
        }

        //T[] array = (T[])new Object[input.length];

        // Creates an array of ArrayDeque containing the sorted elements of the input
        ArrayDeque<T>[] deque = new ArrayDeque[input.length];

        for(int i = 0; i < input.length; i++) {
            deque[i] = new ArrayDeque<T>();
            for(int j = 0; j < input[i].length; j++) {
                deque[i].addLast(input[i][j]);
            }
        }

        // stores values in order into output and removes them from deque
        for (int i = 0; i < size; i++) {
            output[i] = deque[getMin(deque)].remove();

        }

        return output;
    }

    // Finds minimum value in array, returns index of that value
    protected int getMin(ArrayDeque<T>[] deque) {
        int smallest = 0;

        // reinitializes smallest to non-null value
        for(int i = 0; i < deque.length; i++) {
            if (!deque[i].isEmpty()) {
                smallest = i;
                break;
            }
        }

        // finds the index of the smallest value out of the first positions in the ArrayDeques and stores it in smallest
        for (int i = 0; i < deque.length; i++) {
            if(!deque[i].isEmpty())
                smallest = deque[smallest].peek().compareTo(deque[i].peek()) < 0 ? smallest : i;
        }
        return smallest;
    }


//    public static void main(String args[]) {
//        Integer[][] input = {{0,  1,  2,  3},
//                {7,  6,  5,  4},
//                {0,  3,  1,  2},
//                {4,  7,  6,  5},
//                {9,  8, 11, 10}};
//
//        HybridSortHW hw = new HybridSortHW();
//
//        System.out.println(hw.sort(input));
//    }
}
