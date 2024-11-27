package me.ItxItay.RTPPlugin.Utils;

public class RTPMath {

    public static int MathLoader(int max, int min){
        int PlusOrMinus = (Math.random() < .5) ? -1 : 1;

        int range = max - min + 1;
        int rand = (int)((Math.random() * range) + min)* PlusOrMinus;

        return rand;
    }
}
