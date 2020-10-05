package edu.emory.cs.sort.comparison;
import edu.emory.cs.sort.comparison.InsertionSort;

import java.util.*;
public abstract class ShellSort<T extends Comparable<T>> extends InsertionSort<T> {
    protected List<Integer> sequence;

    public ShellSort(Comparator<T> comparator) {
        super(comparator);
        sequence = new ArrayList<>();
        populateSequence(10000);
    }

    protected abstract void populateSequence(int n);

    protected abstract int getSequenceStartIndex(int n);

    @Override
    public void sort(T[] array, int beginIndex, int endIndex) {
        int n = endIndex - beginIndex;
        populateSequence(n);

        for (int i = getSequenceStartIndex(n); i >= 0; i--)
            sort(array, beginIndex, endIndex, sequence.get(i));
    }
}
