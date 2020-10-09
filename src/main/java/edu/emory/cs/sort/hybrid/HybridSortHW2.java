package edu.emory.cs.sort.hybrid;

import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.divide_conquer.IntroSort;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class HybridSortHW2<T extends Comparable<T>> implements HybridSort<T> {
    @Override
    public T[] sort(T[][] input) {
        int size = Arrays.stream(input).mapToInt(t -> t.length).sum();
        T[] output = (T[]) Array.newInstance(input[0][0].getClass(), size);

        // TODO: to be updated
        // TODO: Insertion sort on rows
        for (int i = 0; i < input.length; i++) {
            insertionSort(input[i]);
        }
        // TODO: Merge rows
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

    private void insertionSort(T[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int j = i + 1;
            while (j > 0 && array[j].compareTo(array[j-1]) < 0) {
                T holder = array[j];
                array[j] = array[j-1];
                array[j-1] = holder;
                j--;
            }
        }
    }
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
//        Integer[] x = {1, 4, 5,32, 5,24,64,3};
//        HybridSortHW2 y = new HybridSortHW2();
//        y.insertionSort(x);
//        for (Integer i : x) {
//            System.out.println(i);
//        }
//
//    }

}