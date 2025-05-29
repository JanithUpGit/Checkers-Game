package com.janith.checkersgame;

import java.util.List;

public class DevlopmentTools {

    public void print3DimList(List<List<int[]>> autoCaptures){
        //print autoCaptures
            for (List<int[]> list : autoCaptures) {
                for (int[] array : list) {
                    System.out.print("[");
                    for (int i = 0; i < array.length; i++) {
                        System.out.print(array[i]);
                        if (i < array.length - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.print("] ");
                }
                System.out.println();
            }
    }

    public void print2DimList(List<int[]> list){
        //print autoCaptures
        for (int[] array : list) {
            System.out.print("[");
            for (int i = 0; i < array.length; i++) {
                System.out.print(array[i]);
                if (i < array.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.print("] ");
        }
        System.out.println();
    }

}
