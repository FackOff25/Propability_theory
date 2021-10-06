package com.company;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        /*Блок со значениями для моего (3-6) варианта, просто чтобы не тратить время на ввод
        int[][] balls=new int[][]{{7, 10, 6},
                {9, 5, 9},
                {6, 5, 9}};
        int n=5;
        final int[] need={1,2,2};
         */

        Scanner sc = new Scanner(System.in);

        /*Блок ввода значений с консоли. Чтобы быстро проверять закоментировать блок и снять комментарий вокруг предыдущего*/
        int[][] balls=new int[3][3];
        int n;
        final int[] need=new int[3];

        //Ввод
        System.out.println("Введите R1 G1 B1 R2 G2 B2 R3 G3 B3 через пробел");
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                balls[i][j]=sc.nextInt();
            }
        }
        System.out.println("Введите R G B через пробел");
        for (int i=0; i<3; i++){
                need[i]=sc.nextInt();
        }
        System.out.println("Введите n");
        n=sc.nextInt();
        /*Конец блока ввода значений с консоли*/

        //Получение выбираемого задания
        char[] problem;
        String problemString="";

        System.out.println("Введите номер задания в формате задание пункт [параметр], например: \"3.1 а\", \"3.2 2 б\" или \"3.3 3 а\"");
        while (problemString.length()<5) {
            problemString = sc.nextLine();
        }
        problem=problemString.toCharArray();
        sc.close();

        switch(problem[2]){
            case '1': System.out.println(problem31(problem, balls, need, n)); break;
            case '2': System.out.println(problem32(problem, balls, need, n)); break;
            case '3': System.out.println(problem33(problem, balls, need, n)); break;
        }
    }

    //Вычисление ответа для 3.1
    public static double problem31(char[] problem, int[][] balls, int[] need, int n){
        Probability urns=new Probability(balls, n);
        double probability=0;
        int mode = (problem[4]=='а'||problem[4]=='в') ? 0 : 1;

        for (int count=1; count<4; count++) {
            double preProbability=1;
            for(int count2=0; count2<3; count2++){
                preProbability*= urns.probabilityToGet(count,count2+1, need[count2], mode);
            }
            probability+=preProbability;
        }

        probability/=3;
        if (!(problem[4]=='в'||problem[4]=='г')) probability*=Probability.permutationWithRepetition(need);
        else probability*=Probability.factorial(3);

        return probability;
    }
    //Вычисление ответа для 3.2
    public static double problem32(char[] problem, int[][] balls, int[] need, int n){
        Probability urns=new Probability(balls, n);
        double probability=0;
        int mode= (problem[6]=='а'||problem[6]=='в') ? 0 : 1;

        //Вычисление полной вероятности для пунктов 1-3 и одновременно приведение системы в начальное состояние для пункта 4
        for (int count=1; count<4; count++) {
            double preProbability=1;
            for(int count2=0; count2<3; count2++){
                preProbability*=urns.probabilityToGet(count,count2+1, need[count2], mode);
            }
            probability+=preProbability;
        }

        /*Т.к. в итоге сократится, то считать "чисто" не требуется. Оставлю в комментариях чтобы было понятно, что это предусмотрено
        probability/=3;
        if (!(problem[4]=='в'||problem[4]=='г')) probability*=Probability.permutationWithRepetition(need);
        else probability*=Probability.factorial(3);*/

        if (problem[4]<'4') {
            urns.reset();

            double preProbability=1;                                                                                    //Задание 3.2 пункты 1-3 (Формула Байеса)
            int urn=Integer.parseInt(problem[4]+"");

            for(int count=0; count<3; count++){

                preProbability*=urns.probabilityToGet(urn,count+1, need[count], mode);

            }

            /*Сократится. См. закоментированный блок выше
            preProbability/=3;
            if (!(problem[6]=='в'||problem[6]=='г')) preProbability*=Probability.permutationWithRepetition(need);
            else preProbability*=Probability.factorial(3);*/

            return preProbability/probability;
        }else {                                                                                                         //Задание 3.2 пункт 4
            double nextRedProbability=0;
            for(int count=1; count<4; count++){
                nextRedProbability+=urns.probabilityToGet(count,1, 1, mode)/3;
            }
            return nextRedProbability;
        }
    }
    //Вычисление ответа для 3.2
    public static double problem33(char[] problem, int[][] balls, int[] need, int n){
        Probability urns=new Probability(balls, n);
        int mode= (problem[6]=='а') ? 0 : 1;
        switch (problem[4]){
            case '1': return urns.probabilityToGetExactlyFromAll(1, 2*need[0], mode);
            case '2': return urns.probabilityToGetLessFromAll(2, 2*need[1], mode);
            case '3': return urns.probabilityToGetMoreFromAll(3, need[2], mode);
            default: return 0;
        }
    }

}
