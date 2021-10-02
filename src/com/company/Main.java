package com.company;

public class Main {


    public static void main(String[] args) {

        int Mode=0;   //Условия вытаскивания. 0 для случая, когда шарики кладутся обратно, 1, когда нет\
        int[][] balls=new int[][]{{7, 10, 6},
                {9, 5, 9},
                {6, 5, 9}};
        int N=5;
        final int[] Need={1,2,2};

        Probability urns=new Probability(balls, N);
        System.out.println(urns.probabilityToGetMoreFromAll(3,2, Mode));

    }


}
