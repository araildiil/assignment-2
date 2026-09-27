package org.example;

public class MinHeap {

    private Object[] data;
    private int size;

    private static final int DEFAULT_CAPACITY = 10;

    public MinHeap() {
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(Comparable x) {
        insert(x, null);
    }

    public void insert(Comparable x, long[] comparisons) {
        ensureCapacity(size + 1);
        data[size] = x;
        siftUp(size, comparisons);
        size++;
    }

    public Object peekMin() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Heap is empty");
        }
        return data[0];
    }

    public Object extractMin() {
        return extractMin(null);
    }

    public Object extractMin(long[] comparisons) {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Heap is empty");
        }
        Object min = data[0];
        size--;
        data[0] = data[size];
        data[size] = null;
        if (size > 0) {
            siftDown(0, comparisons);
        }
        return min;
    }

    private void siftUp(int index, long[] comparisons) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (comparisons != null) comparisons[0]++;
            if (compare(data[index], data[parent]) < 0) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index, long[] comparisons) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size) {
                if (comparisons != null) comparisons[0]++;
                if (compare(data[left], data[smallest]) < 0) {
                    smallest = left;
                }
            }
            if (right < size) {
                if (comparisons != null) comparisons[0]++;
                if (compare(data[right], data[smallest]) < 0) {
                    smallest = right;
                }
            }
            if (smallest == index) {
                break;
            }
            swap(index, smallest);
            index = smallest;
        }
    }

    @SuppressWarnings("unchecked")
    private int compare(Object a, Object b) {
        return ((Comparable<Object>) a).compareTo(b);
    }

    private void swap(int i, int j) {
        Object temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = data.length * 2;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            Object[] newData = new Object[newCapacity];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }
}