package GameCore;

/**
 *  Obiekt <code>Number</code> reprezentuje liczbę pojawiającą się na panelu Play window
 *  współrzędnę obiektu odpowiadają wspołrzędnym obiektu typu <code>CircleValue</code>
 *  Rozmiar obiektu jest uzależniony od rozmiary obiektu typu <code>CircleValue</code>
 *  Obiekty zapisywane są w ArrayList
 * @see PlayWindow#valueList
 * @see PlayWindow#addValue()
 * Indeksy obiektów <code>CircleValue</code> odpowiadają obiektom <code>Number</code>
 *
 * @author Tomasz Gruzdzis
 */

public class Number
{
    /** Zmienna przechowująca wartość liczby */
    public int value;
    /** Zmienna przechowująca rozmiar liczby */
    public int fontSize;
    /** Zmienna przechowująca współrzędną X liczby */
    public int x;
    /** Zmienna przechowująca współrzędną Y liczby */
    public int y;

    Number(int w, int h, int s, int randomValue)
    {
        /**
         * @param w ustawia współrzędną x
         * @param h ustawia współrzędną y
         * @param s ustawia rozmiar fontSize
         * @param randomValue wartośc liczby
         */
        value = randomValue;

        /** Wprowadzone wspołcztnniki służą umieszczeniu wartości wewnątrz okregu
         *  Różny parametr dla wartości wiekszych/mniejszych od "10" służy wyśrodkowaniu liczby w okregu
         */
        if(value <10)
            x = (int)(w+0.33*s);

        else
            x = (int)(w+0.22*s);

        fontSize = (int)(0.5*s);

        y = (int)(h+0.7*s);
    }

}
