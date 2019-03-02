package com.androiddesdecero.funcionesaltoorden;

import java.util.function.Function;

/**
 * Created by albertopalomarrobledo on 1/3/19.
 */

public class Utils {
    public static int sumar(int a, int b){
        return a+b;
    }

    public static Function<Integer, Function<Integer,Integer>> makeAdder = x -> y -> x + y;

}
