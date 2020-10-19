package edu.emory.cs.sort.distribution;

import java.util.Deque;
import java.util.function.Function;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class RadixSortQuiz extends RadixSort {


    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        int maxBit = getMaxBit(array, beginIndex, endIndex);
        sort(array, beginIndex, endIndex, maxBit - 1);
    }

    public void sort(Integer[] array, int beginIndex, int endIndex, int bit) {
        if (bit < 0) return;
        int div = (int)Math.pow(10, bit);

        int begin;
        int end;
        int i;
        int j;
        int x;

        x = 0;
        int[] sizeArray = new int[10];
        for (Deque<Integer> bucket : buckets) {
            sizeArray[x] = bucket.size();
            x++;
        }

        distribute(array, beginIndex, endIndex, key -> (key/div)%10);

        bit--;
        begin = beginIndex;
        for (i = 0; i < 10; i++) {
            Deque<Integer> bucket = buckets.get(i);
            end = begin + bucket.size() - sizeArray[i];
            j = begin;
            while(bucket.size() > sizeArray[i]) array[j++] = bucket.removeLast();
            sort(array, begin, end, bit);
            begin = end;
        }
    }


    public void distribute(Integer[] array, int beginIndex, int endIndex, Function<Integer, Integer> bucketIndex) {
        for (int i = beginIndex; i < endIndex; i++)
            buckets.get(bucketIndex.apply(array[i])).add(array[i]);
    }
}