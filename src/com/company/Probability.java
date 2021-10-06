package com.company;

public class Probability {
    //Начальное значение шариков в урнах
    final int[][] OBalls=new int[3][3];
    //Фактическое значения шариков в урнах
    int[][] Balls=new int[3][3];
    //Значение n
    final int N;

    //
    //Технические методы
    //
    Probability(int[][] urns, int _n){
        for (int i=0; i<3; i++){
            System.arraycopy(urns[i], 0, OBalls[i], 0, 3);
        }
        for (int i=0; i<3; i++){
            System.arraycopy(OBalls[i], 0, Balls[i], 0, 3);
        }
        N=_n;
    }
    //Возвращает урны в изначальное состояние
    void reset(){
        for (int i=0; i<3; i++){
            System.arraycopy(OBalls[i], 0, Balls[i], 0, 3);
        }
    }
    //Подсчёт факториала
    static long factorial(int number){
        long fact=1;
        for (int count=2; count<=number; count++) fact*=count;
        return fact;
    }
    //Метод посдсчёта с повторениями. numbers количества шариков каждего цвета
    static long permutationWithRepetition(int[] numbers){
        int n=0;
        long repetition=1;

        for (int number : numbers) {
            n+=number;                                                                                                  //Подсчёт общего числа
            repetition*=factorial(number);
        }
        return factorial(n)/repetition;
    }
    //Метод подсчёта C из n по k
    static long calculateC(int n, int k){
        long c=1;
        for (int count=n; count>k; count--) c*=count;
        for (int count=n-k; count>1; count--) c/=count;
        return c;
    }

    //
    //Методы работы с одной урной
    //
    //Вероятность вытащить один шарик этого цвета из урны
    public double probabilityToGet(int urn, int colour, int mode){
        urn--;
        colour--;

        if (Balls[urn][colour]==0) return 0;

        double probability=(double) Balls[urn][colour]/(Balls[urn][0]+Balls[urn][1]+Balls[urn][2]);
        Balls[urn][colour]-=mode;

        return probability;
    }
    //Вероятность вытащить один шарик не этого цвета из урны
    public double probabilityToGetOther(int urn, int colour, int mode){
        urn--;
        colour--;

        if (Balls[urn][(colour+1)%3]+Balls[urn][(colour+2)%3]==0) return 0;

        double probability=((double) Balls[urn][(colour+1)%3]+Balls[urn][(colour+2)%3])/(Balls[urn][0]+Balls[urn][1]+Balls[urn][2]);
        if (Balls[urn][(colour+1)%3]!=0) Balls[urn][(colour+1)%3]-=mode;
        else Balls[urn][(colour+2)%3]-=mode;

        return probability;
    }
    //Вероятность вытащить number шариков этого цвета из урны
    public double probabilityToGet(int urn, int colour, int number, int mode){
        double probability=1;

        for (int count=0; count<number; count++)probability*=probabilityToGet(urn, colour, mode);

        return probability;
    }
    //Вероятность вытащить number шариков не этого цвета из урны
    double probabilityToGetOthers(int urn, int colour, int number, int mode){
        double probability=1;

        for (int count=0; count<number; count++) probability*=probabilityToGetOther(urn, colour, mode);

        return probability;
    }
    //Вероятность вытащить ровно number шариков этого цвета из урны
    double probabilityToGetExactly(int urn, int colour, int number, int mode){
        double probability=1;
        probability*=probabilityToGet(urn,colour, number, mode);
        probability*=probabilityToGetOthers(urn,colour, N-number, mode);
        probability*=calculateC(N, number);
        return probability;
    }

    //
    //Методы работы с несколькими урнами
    //
    //Вероятность получения конкретного числа шаров данного цвета из всех урн
    double probabilityToGetExactlyFromAll(int color, int number, int mode){
        double probability=0;
        //Цикл ровно по одному разу проверяет все возможные выборы количсетва шариков из каждой из урн, если number>шариков нужного цвета в любой из корзин
        for (int take1=number; take1>=0; take1--){                                                                   //Сколько шариков должно будет взято из первой урны
            for(int take2=number-take1; take2>=0; take2--) {                                                         //Сколько шариков должно будет взято из второй урны
                probability+=probabilityToGetExactly(1, color, take1, mode)
                            *probabilityToGetExactly(2, color, take2, mode)
                            *probabilityToGetExactly(3, color, number-take1-take2, mode);
                reset();
            }
        }
        return probability;
    }
    //Вероятность получения меньше данного количества шаров данного цвета из всех урн
    double probabilityToGetLessFromAll(int color, int number, int mode){
        double probability=0;
        for (int count=0; count<number; count++){
            probability+=probabilityToGetExactlyFromAll(color, count, mode);
        }
        return probability;

    }
    //Вероятность получения больше данного количества шаров данного цвета из всех урн
    double probabilityToGetMoreFromAll(int color, int number, int mode){
        double probability=0;
        number++;
        //Цикл ровно по одному разу проверяет все возможные выборы количсетва шариков из каждой из урн, если number>шариков нужного цвета в любой из корзин
        for (int take1=number; take1>=0; take1--){                                                                   //Сколько шариков должно будет взято из первой урны
            for(int take2=number-take1; take2>=0; take2--) {                                                         //Сколько шариков должно будет взято из второй урны
                probability+=probabilityToGet(1, color, take1, mode)
                            *probabilityToGet(2, color, take2, mode)
                            *probabilityToGet(3, color, number-take1-take2, mode);
                reset();
            }
        }
        return probability;
    }
}
