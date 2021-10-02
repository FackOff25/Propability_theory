package com.company;

public class Main {


    public static void main(String[] args) {
        final int Mode=1;   //Условия вытаскивания. 0 для случая, когда шарики кладутся обратно, 1, когда нет
        Probability urns=new Probability();
        System.out.println(urns.probabilityToGet(1,1, 2, Mode));
        System.out.println(urns.probabilityToGetOthers(1,1, 3, Mode));
        urns.reset();
        System.out.println(urns.probabilityToGetExactly(1,1, 2, Mode));
        //System.out.println(Probability.calculateC());

    }


}
