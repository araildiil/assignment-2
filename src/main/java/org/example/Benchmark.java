package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final int REPEATS = 5;
    private static final long SEED = 42;
    private static final String RESULTS_DIR = "results/tables/";

    public static void main(String[] args) throws IOException {
        runWorkload1();
        runWorkload2();
        runWorkload3();
        runWorkload4();
    }

    private static void runWorkload1() throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(RESULTS_DIR + "workload1_random_access.csv"));
        writer.println("n,structure,avgTimeNs,accesses");

        for (int n : SIZES) {
            Random dataRandom = new Random(SEED);
            int[] values = new int[n];
            for (int i = 0; i < n; i++) values[i] = dataRandom.nextInt();

            Random indexRandom = new Random(SEED);
            int[] indices = new int[10000];
            for (int i = 0; i < indices.length; i++) indices[i] = indexRandom.nextInt(n);

            DynamicArray array = new DynamicArray();
            for (int v : values) array.add(v);

            LinkedList list = new LinkedList();
            for (int v : values) list.add(v);

            long arrayTime = 0;
            for (int r = 0; r < REPEATS; r++) {
                long start = System.nanoTime();
                for (int idx : indices) array.get(idx);
                arrayTime += System.nanoTime() - start;
            }
            arrayTime /= REPEATS;

            long listTime = 0;
            for (int r = 0; r < REPEATS; r++) {
                long start = System.nanoTime();
                for (int idx : indices) list.get(idx);
                listTime += System.nanoTime() - start;
            }
            listTime /= REPEATS;

            writer.println(n + ",DynamicArray," + arrayTime + "," + indices.length);
            writer.println(n + ",LinkedList," + listTime + "," + indices.length);

            System.out.println("Workload1 n=" + n + " Array=" + arrayTime + "ns List=" + listTime + "ns");
        }
        writer.close();
    }

    private static void runWorkload2() throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(RESULTS_DIR + "workload2_search.csv"));
        writer.println("n,structure,avgTimeNs,comparisons");

        for (int n : SIZES) {
            Random dataRandom = new Random(SEED);
            int[] values = new int[n];
            for (int i = 0; i < n; i++) values[i] = dataRandom.nextInt();

            Random searchRandom = new Random(SEED);
            int[] searchValues = new int[1000];
            for (int i = 0; i < searchValues.length; i++) searchValues[i] = searchRandom.nextInt();

            DynamicArray array = new DynamicArray();
            for (int v : values) array.add(v);

            LinkedList list = new LinkedList();
            for (int v : values) list.add(v);

            long arrayTime = 0;
            long arrayComparisons = 0;
            for (int r = 0; r < REPEATS; r++) {
                long[] comparisons = new long[1];
                long start = System.nanoTime();
                for (int v : searchValues) array.contains(v, comparisons);
                arrayTime += System.nanoTime() - start;
                if (r == 0) arrayComparisons = comparisons[0];
            }
            arrayTime /= REPEATS;

            long listTime = 0;
            long listComparisons = 0;
            for (int r = 0; r < REPEATS; r++) {
                long[] comparisons = new long[1];
                long start = System.nanoTime();
                for (int v : searchValues) list.contains(v, comparisons);
                listTime += System.nanoTime() - start;
                if (r == 0) listComparisons = comparisons[0];
            }
            listTime /= REPEATS;

            writer.println(n + ",DynamicArray," + arrayTime + "," + arrayComparisons);
            writer.println(n + ",LinkedList," + listTime + "," + listComparisons);

            System.out.println("Workload2 n=" + n + " Array=" + arrayTime + "ns/" + arrayComparisons
                    + " List=" + listTime + "ns/" + listComparisons);
        }
        writer.close();
    }

    private static void runWorkload3() throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(RESULTS_DIR + "workload3_insert_remove.csv"));
        writer.println("n,structure,operation,position,avgTimeNs,movements");

        for (int n : SIZES) {
            Random dataRandom = new Random(SEED);
            int[] values = new int[n];
            for (int i = 0; i < n; i++) values[i] = dataRandom.nextInt();

            benchmarkInsertRemove(writer, n, values, 0, "beginning");
            benchmarkInsertRemove(writer, n, values, n / 2, "middle");
        }
        writer.close();
    }

    private static void benchmarkInsertRemove(PrintWriter writer, int n, int[] values, int position, String label) {
        long arrayInsertTime = 0;
        long arrayRemoveTime = 0;
        long listInsertTime = 0;
        long listRemoveTime = 0;
        int actualRemovals = Math.min(1000, n);

        for (int r = 0; r < REPEATS; r++) {
            DynamicArray arrayForInsert = new DynamicArray();
            for (int v : values) arrayForInsert.add(v);
            long start = System.nanoTime();
            for (int i = 0; i < 1000; i++) arrayForInsert.add(position, i);
            arrayInsertTime += System.nanoTime() - start;

            DynamicArray arrayForRemove = new DynamicArray();
            for (int v : values) arrayForRemove.add(v);
            start = System.nanoTime();
            for (int i = 0; i < actualRemovals && !arrayForRemove.isEmpty(); i++) {
                int removeIndex = Math.min(position, arrayForRemove.size() - 1);
                arrayForRemove.remove(removeIndex);
            }
            arrayRemoveTime += System.nanoTime() - start;

            LinkedList listForInsert = new LinkedList();
            for (int v : values) listForInsert.add(v);
            start = System.nanoTime();
            for (int i = 0; i < 1000; i++) listForInsert.add(position, i);
            listInsertTime += System.nanoTime() - start;

            LinkedList listForRemove = new LinkedList();
            for (int v : values) listForRemove.add(v);
            start = System.nanoTime();
            for (int i = 0; i < actualRemovals && !listForRemove.isEmpty(); i++) {
                int removeIndex = Math.min(position, listForRemove.size() - 1);
                listForRemove.remove(removeIndex);
            }
            listRemoveTime += System.nanoTime() - start;
        }

        arrayInsertTime /= REPEATS;
        arrayRemoveTime /= REPEATS;
        listInsertTime /= REPEATS;
        listRemoveTime /= REPEATS;

        long movements = (long) (n - position) * 1000;

        writer.println(n + ",DynamicArray,insert," + label + "," + arrayInsertTime + "," + movements);
        writer.println(n + ",DynamicArray,remove," + label + "," + arrayRemoveTime + "," + ((long) position * actualRemovals));
        writer.println(n + ",LinkedList,insert," + label + "," + listInsertTime + "," + ((long) position * 1000));
        writer.println(n + ",LinkedList,remove," + label + "," + listRemoveTime + "," + ((long) position * actualRemovals));

        System.out.println("Workload3 n=" + n + " pos=" + label
                + " ArrayInsert=" + arrayInsertTime + " ArrayRemove=" + arrayRemoveTime
                + " ListInsert=" + listInsertTime + " ListRemove=" + listRemoveTime
                + " (actualRemovals=" + actualRemovals + ")");
    }

    private static void runWorkload4() throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(RESULTS_DIR + "workload4_heap.csv"));
        writer.println("n,insertTimeNs,extractTimeNs,comparisons,sorted");

        for (int n : SIZES) {
            Random dataRandom = new Random(SEED);
            int[] values = new int[n];
            for (int i = 0; i < n; i++) values[i] = dataRandom.nextInt();

            long insertTime = 0;
            long extractTime = 0;
            long comparisons = 0;
            boolean sorted = true;

            for (int r = 0; r < REPEATS; r++) {
                MinHeap heap = new MinHeap();
                long[] insertComparisons = new long[1];

                long start = System.nanoTime();
                for (int v : values) heap.insert(v, insertComparisons);
                insertTime += System.nanoTime() - start;

                long[] extractComparisons = new long[1];
                Integer previous = null;
                boolean localSorted = true;

                start = System.nanoTime();
                for (int i = 0; i < n; i++) {
                    Integer extracted = (Integer) heap.extractMin(extractComparisons);
                    if (previous != null && extracted < previous) localSorted = false;
                    previous = extracted;
                }
                extractTime += System.nanoTime() - start;

                if (r == 0) {
                    comparisons = insertComparisons[0] + extractComparisons[0];
                    sorted = localSorted;
                }
            }

            insertTime /= REPEATS;
            extractTime /= REPEATS;

            writer.println(n + "," + insertTime + "," + extractTime + "," + comparisons + "," + sorted);

            System.out.println("Workload4 n=" + n + " insert=" + insertTime + "ns extract=" + extractTime
                    + "ns comparisons=" + comparisons + " sorted=" + sorted);
        }
        writer.close();
    }
}