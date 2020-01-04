package GameCore;

/**
 *  Obiekt <code>Number</code> reprezentuje liczbe pojawiajacą sie na panelu Play window
 *  wspolrzedne obiektu odpowiadaja wspolrzednym obiektu typu <code>CircleValue</code>
 *  Rozmiar obiektu jest uzalezniony od rozmiaru obiektu typu <code>CircleValue</code>
 *  Obiekty zapisywane sa w ArrayList
 *  Indeksy obiektow <code>CircleValue</code> odpowiadaja obiektom <code>Number</code>
 * @see PlayWindow#valueList
 * @see PlayWindow#addValue()
 *
 * @author Tomasz Gruzdzis
 */

public class Number
{
    /** Zmienna przechowujaca wartosc liczby */
    public int value;
    /** Zmienna przechowujaca rozmiar liczby */
    public int fontSize;
    /** Zmienna przechowujaca wspolrzedna X liczby */
    public int x;
    /** Zmienna przechowujaca wspolrzedna Y liczby */
    public int y;


    /**
     * Konstruktor domyslny.
     * @param w ustawia wspolrzedna x
     * @param h ustawia wspolrzedna y
     * @param s ustawia rozmiar fontSize
     * @param randomValue wartosc liczby
     */

    Number(int w, int h, int s, int randomValue)
    {
        value = randomValue;

        /** Wprowadzone wspolczynniki sluza umieszczeniu wartosci wewnatrz okregu
         *  Rozny parametr dla wartosci wiekszych/mniejszych od "10" sluzy wysrodkowaniu liczby w okregu
         */
        if(value <10)
            x = (int)(w+0.33*s);

        else
            x = (int)(w+0.22*s);

        fontSize = (int)(0.5*s);

        y = (int)(h+0.7*s);
    }

    public void changeNumberPosition(int w, int h, int s)
    {
        if(value <10)
            x = (int)(w+0.33*s);

        else
            x = (int)(w+0.22*s);

        y = (int)(h+0.7*s);
    }

}
