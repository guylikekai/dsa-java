package edu.emory.cs.sort.distribution;

import java.util.Arrays;
import java.util.Deque;
import java.util.function.Function;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class RadixSortQuiz extends RadixSort {
//    @Override
//    public void sort(Integer[] array, int beginIndex, int endIndex) {
//        int maxBit = getMaxBit(array, beginIndex, endIndex);
//        int[] aux = new int[array.length];
//        for (int bit = maxBit - 1; bit >= 0; bit--) {
//            int div = (int)Math.pow(10, bit);
//            int[] count = new int[10];
//            for (int i = 0; i < array.length; i++) {
//                count[(array[i] / div) % 10]++;
//
//            }
//            for (int i = 0; i < 9; i++) {
//                count[i + 1] += count[i];
//            }
//            for (int i = start; i < count[x]; i++) {
//                aux[i] =
//            }
//        }
//    }



        @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        int maxBit = getMaxBit(array, beginIndex, endIndex);
        sort(array, beginIndex, endIndex, maxBit-1);
    }

    protected void sort(Integer[] array, int beginIndex, int endIndex, int bit) {
//        System.out.println("Begin indexxxx" + beginIndex);
//        System.out.println("End Indexxxx" + endIndex);
            if (beginIndex >= endIndex | bit < 0) return;
        int[] indexes = new int[11];
        int div = (int)Math.pow(10, bit);
        int counter = 1;

        distribute(array, beginIndex, endIndex, key -> (key/div)%10);
        for (Deque<Integer> bucket : buckets) {
//            indexes[counter++] = bucket.size() + indexes[counter - 2];
            int x = beginIndex;
            while (!bucket.isEmpty()) {
                array[x] = bucket.remove();
//                System.out.println("in method" + array[x]);
                x++;
            }
            indexes[counter++] = x;
        }
//        System.out.println("out");
//        System.out.println("Begin index" + beginIndex);
//        System.out.println("End Index" + endIndex);
//        System.out.println("bit" + bit);
        for (int i = 0; i < 10; i++) {
            sort(array, beginIndex + indexes[i] + 1, beginIndex + indexes[i+1] , bit-1);
        }



    }


    protected void distribute(Integer[] array, int beginIndex, int endIndex, Function<Integer, Integer> bucketIndex) {
        for (int i = beginIndex; i < endIndex; i++)
            buckets.get(bucketIndex.apply(array[i])).add(array[i]);
    }

}






