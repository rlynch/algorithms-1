package com.ryan.algorithms1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.QuickFindUF;

public class TestWeighted {
    public static void main(String[] args) {
        TestWeighted.testQU();
    }

    protected static void testWQU() {
        WeightedQuickUnionUF wqu = new WeightedQuickUnionUF(10);
        wqu.union(2, 3);
        wqu.union(2, 6);
        wqu.union(5, 7);
        wqu.union(4, 2);
        wqu.union(0, 8);
        wqu.union(3, 9);
        wqu.union(5, 0);
        wqu.union(1, 2);
        wqu.union(4, 0);
        System.out.println(wqu.toString());
    }

    protected static void testQU() {
        QuickFindUF qf = new QuickFindUF(10);
        qf.union(4, 3);
        qf.union(9, 3);
        qf.union(5, 2);
        qf.union(6, 8);
        qf.union(3, 2);
        qf.union(0, 7);
        System.out.println(qf.toString());
    }

}
