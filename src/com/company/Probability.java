package com.company;

public class Probability {
    //Шарики в урнах
    int[][] Balls= {{7,10,6},
            {9,5 ,9},
            {6,5 ,9}};
    //Сколько нужно вытащить
    final int[] Need={1,2,2};
    final int N=5;

    public double probabilityToGet(int urn, int colour, int mode){
        return probabilityToGet(urn, colour, 1, mode);
    }

    public double probabilityToGet(int urn, int colour, int number, int mode){
        urn--;
        colour--;

        if (number>Balls[urn][colour]) return 0;
        int balls=Balls[urn][0]+Balls[urn][1]+Balls[urn][2];

        double probability=1;

        for (int count=0; count<number; count++){
            probability*=((double) Balls[urn][colour]/balls);
            balls-=mode;
            Balls[urn][colour]-=mode;
        }

        return probability;
    }

    double probabilityToGetOthers(int urn, int colour, int number, int mode){
        urn--;
        colour--;

        int balls=Balls[urn][0]+Balls[urn][1]+Balls[urn][2];
        int nBalls=balls-Balls[urn][colour];
        if (number>nBalls) return 0;

        double probability=1;

        for (int count=0; count<number; count++){
            probability*=((double) nBalls)/balls;
            balls-=mode;
            nBalls-=mode;
        }

        if(mode==1){
            if (Balls[urn][(colour+1)%3]<number) {
                number=number-Balls[urn][(colour+1)%3];
                Balls[urn][(colour+1)%3]=0;
                Balls[urn][(colour+2)%3]-=number;
            }else Balls[urn][(colour+1)%3]-=number;
        }

        return probability;
    }
    double probabilityToGetExactly(int urn, int colour, int number, int mode){
        double probability=1;
        probability*=probabilityToGet(urn,colour, number, mode);
        probability*=probabilityToGetOthers(urn,colour, N-number, mode);
        probability*=calculateC(N-number+1, number);
        return probability;
    }
    void reset(){
        Balls= new int[][]{{7, 10, 6},
                {9, 5, 9},
                {6, 5, 9}};
    }
    static long factorial(int number){
        long fact=1;
        for (int count=2; count<=number; count++) fact*=count;
        return fact;
    }
    static long calculateC(int n, int m){
        long c=factorial(n)/(factorial(m)*factorial(n-m));
        return c;
    }
}
