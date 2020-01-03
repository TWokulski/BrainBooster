package GameCore;

import javax.swing.*;
import java.awt.*;

/**
 *  Obiekt <code>CircleValue</code> reprezentuje okręg pojawiający sie na panelu Play window
 *  Służy on zwiekszeniu grywalności, poprzez wskazanie obszaru przeznaczonego do kliknięcia
 *  Obiekty zapisywane są w ArrayList
 * @see PlayWindow#circleList
 * @see PlayWindow#addValue()
 * Indeksy obiektów <code>CircleValue</code> odpowiadają obiektom <code>Number</code>
 *
 * @author Tomasz Gruzdzis
 */

public class CircleValue
{
    /** Zmienna przechowująca obrazek tła */
    private static Image circle = new ImageIcon("circle2.gif").getImage();
    /** Zmienna przechowująca rozmiar okręgu */
    public int size;
    /** Zmienna przechowująca współrzędną X okręgu */
    public int x;
    /** Zmienna przechowująca współrzędną Y okręgu */
    public int y;

    CircleValue(int w, int h, int s)
    {
        /**
         * @param w ustawia współrzędną x
         * @param h ustawia współrzędną y
         * @param s ustawia rozmiar size
         */
        x = w;
        y = h;
        size = s;
    }
    /** Metoda zwracająca obraz okręgu
     * @see PlayWindow#paintComponent(Graphics)
     */
    public static Image getImg()
    {
        return CircleValue.circle;
    }

}

