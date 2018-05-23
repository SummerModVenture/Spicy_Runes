package com.spicymemes.runes.coremod;

/**
 * Created by Spencer on 5/22/18.
 */
public class Test {
    public static int test(int a, int b){
        test1(a, b);
        if(a > b){
            return test1(a, b);
        }
        return test2(a, b);
    }

    public static int test1(int a, int b){
        return a + b;
    }

    public static int test2(int a, int b){
        return a - b;
    }
}
