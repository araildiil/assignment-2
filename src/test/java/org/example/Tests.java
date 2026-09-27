package org.example;

import java.util.NoSuchElementException;

public class Tests {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testDynamicArrayEmpty();
        testDynamicArraySingleElement();
        testDynamicArrayMultipleElements();
        testDynamicArrayDuplicates();
        testDynamicArrayBoundaryIndices();
        testDynamicArrayInvalidIndices();
        testDynamicArrayLargeInput();

        testLinkedListEmpty();
        testLinkedListSingleElement();
        testLinkedListMultipleElements();
        testLinkedListDuplicates();
        testLinkedListBoundaryIndices();
        testLinkedListInvalidIndices();
        testLinkedListLargeInput();

        testMinHeapEmpty();
        testMinHeapSingleElement();
        testMinHeapMultipleElements();
        testMinHeapDuplicates();
        testMinHeapHeapPropertyAfterInsert();
        testMinHeapHeapPropertyAfterExtract();
        testMinHeapNonDecreasingOrder();
        testMinHeapLargeInput();

        System.out.println();
        System.out.println("Passed: " + passed + ", Failed: " + failed);
    }

    private static void check(boolean condition, String testName) {
        if (condition) {
            passed++;
            System.out.println("PASS: " + testName);
        } else {
            failed++;
            System.out.println("FAIL: " + testName);
        }
    }

    private static void testDynamicArrayEmpty() {
        DynamicArray array = new DynamicArray();
        check(array.isEmpty(), "DynamicArray empty on creation");
        check(array.size() == 0, "DynamicArray size is 0 when empty");
        check(!array.contains(1), "DynamicArray contains returns false on empty");
    }

    private static void testDynamicArraySingleElement() {
        DynamicArray array = new DynamicArray();
        array.add(42);
        check(array.size() == 1, "DynamicArray size 1 after single add");
        check((int) array.get(0) == 42, "DynamicArray get(0) returns correct value");
        check(array.contains(42), "DynamicArray contains finds single element");
    }

    private static void testDynamicArrayMultipleElements() {
        DynamicArray array = new DynamicArray();
        for (int i = 0; i < 5; i++) array.add(i);
        check(array.size() == 5, "DynamicArray size correct after multiple adds");
        array.add(2, 100);
        check((int) array.get(2) == 100, "DynamicArray add(index,x) inserts at correct position");
        check(array.size() == 6, "DynamicArray size increases after add(index,x)");
        array.remove(2);
        check((int) array.get(2) == 2, "DynamicArray remove(index) shifts correctly");
        check(array.size() == 5, "DynamicArray size decreases after remove");
    }

    private static void testDynamicArrayDuplicates() {
        DynamicArray array = new DynamicArray();
        array.add(7);
        array.add(7);
        array.add(7);
        check(array.size() == 3, "DynamicArray allows duplicates");
        check(array.contains(7), "DynamicArray contains finds duplicate value");
    }

    private static void testDynamicArrayBoundaryIndices() {
        DynamicArray array = new DynamicArray();
        for (int i = 0; i < 5; i++) array.add(i);
        check((int) array.get(0) == 0, "DynamicArray get first index");
        check((int) array.get(4) == 4, "DynamicArray get last index");
        array.add(0, -1);
        check((int) array.get(0) == -1, "DynamicArray add at index 0");
        array.add(array.size(), 999);
        check((int) array.get(array.size() - 1) == 999, "DynamicArray add at end index");
    }

    private static void testDynamicArrayInvalidIndices() {
        DynamicArray array = new DynamicArray();
        array.add(1);
        boolean thrown = false;
        try {
            array.get(5);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "DynamicArray get invalid index throws exception");

        thrown = false;
        try {
            array.get(-1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "DynamicArray get negative index throws exception");
    }

    private static void testDynamicArrayLargeInput() {
        DynamicArray array = new DynamicArray();
        int n = 100000;
        for (int i = 0; i < n; i++) array.add(i);
        check(array.size() == n, "DynamicArray handles large input size");
        check((int) array.get(n - 1) == n - 1, "DynamicArray large input correct last value");
        check(array.contains(n / 2), "DynamicArray large input contains works");
    }

    private static void testLinkedListEmpty() {
        LinkedList list = new LinkedList();
        check(list.isEmpty(), "LinkedList empty on creation");
        check(list.size() == 0, "LinkedList size is 0 when empty");
        check(!list.contains(1), "LinkedList contains returns false on empty");
    }

    private static void testLinkedListSingleElement() {
        LinkedList list = new LinkedList();
        list.add(42);
        check(list.size() == 1, "LinkedList size 1 after single add");
        check((int) list.get(0) == 42, "LinkedList get(0) returns correct value");
        check(list.contains(42), "LinkedList contains finds single element");
    }

    private static void testLinkedListMultipleElements() {
        LinkedList list = new LinkedList();
        for (int i = 0; i < 5; i++) list.add(i);
        check(list.size() == 5, "LinkedList size correct after multiple adds");
        list.add(2, 100);
        check((int) list.get(2) == 100, "LinkedList add(index,x) inserts at correct position");
        check(list.size() == 6, "LinkedList size increases after add(index,x)");
        list.remove(2);
        check((int) list.get(2) == 2, "LinkedList remove(index) shifts correctly");
        check(list.size() == 5, "LinkedList size decreases after remove");
    }

    private static void testLinkedListDuplicates() {
        LinkedList list = new LinkedList();
        list.add(7);
        list.add(7);
        list.add(7);
        check(list.size() == 3, "LinkedList allows duplicates");
        check(list.contains(7), "LinkedList contains finds duplicate value");
    }

    private static void testLinkedListBoundaryIndices() {
        LinkedList list = new LinkedList();
        for (int i = 0; i < 5; i++) list.add(i);
        check((int) list.get(0) == 0, "LinkedList get first index");
        check((int) list.get(4) == 4, "LinkedList get last index");
        list.add(0, -1);
        check((int) list.get(0) == -1, "LinkedList add at index 0");
        list.add(list.size(), 999);
        check((int) list.get(list.size() - 1) == 999, "LinkedList add at end index");
    }

    private static void testLinkedListInvalidIndices() {
        LinkedList list = new LinkedList();
        list.add(1);
        boolean thrown = false;
        try {
            list.get(5);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "LinkedList get invalid index throws exception");

        thrown = false;
        try {
            list.get(-1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "LinkedList get negative index throws exception");
    }

    private static void testLinkedListLargeInput() {
        LinkedList list = new LinkedList();
        int n = 20000;
        for (int i = 0; i < n; i++) list.add(i);
        check(list.size() == n, "LinkedList handles large input size");
        check((int) list.get(n - 1) == n - 1, "LinkedList large input correct last value");
        check(list.contains(n / 2), "LinkedList large input contains works");
    }

    private static void testMinHeapEmpty() {
        MinHeap heap = new MinHeap();
        check(heap.isEmpty(), "MinHeap empty on creation");
        check(heap.size() == 0, "MinHeap size is 0 when empty");
        boolean thrown = false;
        try {
            heap.peekMin();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "MinHeap peekMin on empty throws exception");
    }

    private static void testMinHeapSingleElement() {
        MinHeap heap = new MinHeap();
        heap.insert(5);
        check(heap.size() == 1, "MinHeap size 1 after single insert");
        check((int) heap.peekMin() == 5, "MinHeap peekMin returns single element");
        check((int) heap.extractMin() == 5, "MinHeap extractMin returns single element");
        check(heap.isEmpty(), "MinHeap empty after extracting only element");
    }

    private static void testMinHeapMultipleElements() {
        MinHeap heap = new MinHeap();
        int[] values = {5, 3, 8, 1, 9, 2};
        for (int v : values) heap.insert(v);
        check(heap.size() == values.length, "MinHeap size correct after multiple inserts");
        check((int) heap.peekMin() == 1, "MinHeap peekMin returns minimum value");
    }

    private static void testMinHeapDuplicates() {
        MinHeap heap = new MinHeap();
        heap.insert(4);
        heap.insert(4);
        heap.insert(4);
        check(heap.size() == 3, "MinHeap allows duplicate values");
        check((int) heap.extractMin() == 4, "MinHeap extractMin works with duplicates");
    }

    private static void testMinHeapHeapPropertyAfterInsert() {
        MinHeap heap = new MinHeap();
        int[] values = {10, 4, 15, 2, 8, 1, 20};
        for (int v : values) {
            heap.insert(v);
            check(isHeapValid(heap), "MinHeap property holds after inserting " + v);
        }
    }

    private static void testMinHeapHeapPropertyAfterExtract() {
        MinHeap heap = new MinHeap();
        int[] values = {10, 4, 15, 2, 8, 1, 20};
        for (int v : values) heap.insert(v);
        while (!heap.isEmpty()) {
            heap.extractMin();
            check(isHeapValid(heap), "MinHeap property holds after extraction");
        }
    }

    private static void testMinHeapNonDecreasingOrder() {
        MinHeap heap = new MinHeap();
        int[] values = {9, 3, 7, 1, 5, 2, 8, 4, 6};
        for (int v : values) heap.insert(v);
        int previous = Integer.MIN_VALUE;
        boolean nonDecreasing = true;
        while (!heap.isEmpty()) {
            int current = (int) heap.extractMin();
            if (current < previous) nonDecreasing = false;
            previous = current;
        }
        check(nonDecreasing, "MinHeap extractMin produces non-decreasing sequence");
    }

    private static void testMinHeapLargeInput() {
        MinHeap heap = new MinHeap();
        int n = 50000;
        java.util.Random random = new java.util.Random(42);
        for (int i = 0; i < n; i++) heap.insert(random.nextInt());
        check(heap.size() == n, "MinHeap handles large input size");

        int previous = Integer.MIN_VALUE;
        boolean nonDecreasing = true;
        while (!heap.isEmpty()) {
            int current = (int) heap.extractMin();
            if (current < previous) nonDecreasing = false;
            previous = current;
        }
        check(nonDecreasing, "MinHeap large input extractMin non-decreasing");
    }

    private static boolean isHeapValid(MinHeap heap) {
        try {
            java.lang.reflect.Field dataField = MinHeap.class.getDeclaredField("data");
            java.lang.reflect.Field sizeField = MinHeap.class.getDeclaredField("size");
            dataField.setAccessible(true);
            sizeField.setAccessible(true);
            Object[] data = (Object[]) dataField.get(heap);
            int size = (int) sizeField.get(heap);
            for (int i = 0; i < size; i++) {
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                if (left < size && ((Comparable) data[i]).compareTo(data[left]) > 0) return false;
                if (right < size && ((Comparable) data[i]).compareTo(data[right]) > 0) return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
