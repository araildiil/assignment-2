package org.example;

import java.util.NoSuchElementException;

public class DynamicArray {

    private Object[] data;
    private int size;

    private static final int DEFAULT_CAPACITY = 10;

    public DynamicArray() {
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
    public void add(Object x) {
        ensureCapacity(size + 1);
        data[size] = x;
        size++;
    }

    public void add(int index, Object x) {
        checkIndexForAdd(index);
        ensureCapacity(size + 1);
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = x;
        size++;
    }

    public Object remove(int index) {
        checkIndex(index);
        Object removed = data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[size - 1] = null; // avoid memory leak
        size--;
        return removed;
    }

    public Object get(int index) {
        checkIndex(index);
        return data[index];
    }

    public boolean contains(Object x) {
        for (int i = 0; i < size; i++) {
            if (data[i] == null ? x == null : data[i].equals(x)) {
                return true;
            }
        }
        return false;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}