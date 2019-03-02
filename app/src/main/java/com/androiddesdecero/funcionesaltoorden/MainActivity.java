package com.androiddesdecero.funcionesaltoorden;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements SumarInterfaz {

    /*
    Una función de orden superior es una Función que:
    1.- Puede recibir una función como parametro de Entrada
    2.- Puede devolver una función como return
    3.- Con que se cumpla una de las dos afirmaciones anterioes es una funcion de orden superior.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int resultado;
        //1.-
        Log.d("TAG1", "---------Función Normal----------");
        resultado = sumar(2,4);
        Log.d("TAG1", "Resultado Función Sumar: " + resultado);
        //2.-
        Log.d("TAG1", "---------Uso de Interfaz----------");
        resultado = apply(2,5);

        Log.d("TAG1", "Resultado Interfaz Sumar Interfaz: " + resultado);
        //3.-
        Log.d("TAG1", "---------High Order Functions----------");
        resultado = sumarHighOrderFun(this,2,6);
        Log.d("TAG1", "Resultado High Order Function: " + resultado);
        Log.d("TAG1", "---------High Order Functions 2----------");
        SumarInterfaz sumarInterfaz = new SumarInterfaz() {
            @Override
            public int apply(int a, int b) {
                return a+b;
            }
        };
        resultado = sumarHighOrderFun(sumarInterfaz,2,7);
        Log.d("TAG1", "Resultado High Order Function: " + resultado);
        SumarInterfaz sumarInterfazJava8 = (a,b)->a+b;
        /*
        Hasta la fecha hemos visto como hacer una función de HighOrder antes de Java 8
        lo que nos obligaba a crearnos una interfaz aparte.
        Pero en Java se dieron cuenta y han creado ellos una serie de interfaces que nos
        hacen el trabajo más facil
         */
        /*
        Funtion
        interface Function<T, R>
        {
            R apply(T t)
        }
        Acepta un argumento y produce un resultado
        */
        Function<String, String> convertirAMayusculas = new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s.toUpperCase();
            }
        };
        imprimirMayusculas(convertirAMayusculas, "alberto");

        /*
        Java 8 tambien nos ofrece BiFuntions
        public interface BiFunction<T,U,R>
        {
            R apply (T t, U u)
        }
        T - el tipo de primer argumento de la función
        U - el tipo del segundo argumento de la función
        R - el tipo del resultado de la función
        Es decir que BiFunction aceta 2 argumentos y produce un resultado
         */

        /*
        Vamos a hacer un ejemplo con el parametro de entrada una lista y una función y nos
        devuelve una lista
         */

        /*
        Predicade -> Un predicado es uan interfaz funcional que define una condición
        que un objeto determiando debe cumplir.
        Es decir que es una función que nos va a deovler un valor de verdadero o falso
        Ejemplo que los numeros sean mayor que cero.
        interface Predicate<T>
		 {
		 	Boolean text (T t)
		 }
         */

        List<Integer> numeros = Arrays.asList(6,23,-5,4,68,-9,-67,45);

        BiFunction<
                List<Integer>,
                Predicate<Integer>,
                List<Integer>
                > filtrar;
        filtrar = (lista, predicado)->
            lista.stream().filter(e->predicado.test(e)).collect(Collectors.toList());
        Log.d("TAG1", "---------Bi Funcions y Predicate----------");

        Log.d("TAG1", filtrar.apply(numeros, e->e>0)+"");

        /*
        Un consumer es una interface funcional que acpeta un único parametro de entrada
        y que no devuelve resutlado
        En este ejemplo el cosumer muestra en un logo el valor que le llega
        interface Consumer<T>
        {
            void accept(T t)
        }
        consumer = e->Log.d("TAG1", e)
         */

        Log.d("TAG1", "---------Filtrar y Consumer----------");
        List<String> nombres = new ArrayList<>();
        nombres.add("Alberto");
        nombres.add("Maria");
        nombres.add("Paco");
        filtrar(nombres, e->Log.d("TAG1", e), 7);




        Function<Integer, Integer> add1 = hacerSuma.apply(7);
        resultado = add1.apply(1);
        Log.d("TAG1", resultado + "");

        Log.d("TAG1", sumar3.apply(6) + "");

    }

    /*
    1.- Forma hasta la fecha de hacer una función
     */
    private int sumar(int a, int b){
        return a+b;
    }

    /*
    2.- Interfaz
     */
    @Override
    public int apply(int a, int b) {
        return a+b;
    }

    /*
    3.- High order Funcions
     */
    public int sumarHighOrderFun(SumarInterfaz sumar, int a, int b){
        return sumar.apply(a,b);
    }

    /*
    4.- Imprimir en Mayusculas
     */
    public void imprimirMayusculas(Function<String, String> function, String nombre){
        Log.d("TAG1", function.apply(nombre));
    }

    public void filtrar(List<String> lista, Consumer<String> consumer, int maximoCaracteres){
        lista.stream().filter(logicaPredicado(maximoCaracteres)).forEach(consumer);
    }

    public Predicate<String> logicaPredicado(int maximoCaracteres){
        return e->e.length() < maximoCaracteres;
    }











    /*
    La funcion hacerSuma toma un Integer x y crea una nueva función que
    toma un Integer X y cuando es invocado suma x+y.
    La llamamos función de alto orden porque produce una nueva función.
     */
    public Function<Integer, Function<Integer,Integer>> hacerSuma = x->y -> x+y;
    public Function<Integer, Integer> sumar3 = hacerSuma.apply(5);

}
