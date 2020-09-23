package edu.emory.cs.queue;
import java.util.*;

public class TernaryHeapQuiz<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    private final List<T> keys;

    public TernaryHeapQuiz() {
        this(Comparator.naturalOrder());
    }

    public TernaryHeapQuiz(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
        keys.add(null); // creates null element at index 0 to reduce arithmetic in indexing
    }

    private int compare(int k1, int k2){
        return priority.compare(keys.get(k1), keys.get(k2));
    }

    @Override
    public void add(T key) {
        keys.add(key);
        swim(size());
    }

    private void swim(int k) {
        for (; k>1 && compare((k+1)/3, k) < 0; k = (k+1)/3) {
            Collections.swap(keys, (k+1)/3, k);
        }
    }

    @Override
    public T remove() {
        if (isEmpty()) return null;
        Collections.swap(keys, 1, size());
        T max = keys.remove(size());
        sink();
        return max;
    }

    private void sink() {
        for (int k = 1, i = 2; i <= size(); k = i, i = (i*3) - 1) {
            i = findMax(i, i + 1, i + 2);
            if (compare(k, i) >= 0) break;
            Collections.swap(keys, k, i);
        }
    }

    private int findMax(int i, int j, int k) { // returns the max int out of three given ints
       int temp = i;
       if (j <= size()) temp = compare(i,j) < 0 ? j : i;
       if (k <= size()) temp = compare(temp,k) < 0 ? k : temp;
        return temp;
    }

    @Override
    public int size() {
        return keys.size() - 1;
    }
}


