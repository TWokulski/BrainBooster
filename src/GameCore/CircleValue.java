package GameCore;

import javax.swing.*;
import java.awt.*;


/**
 *  Obiekt <code>CircleValue</code> reprezentuje okrag pojawiajacy sie na panelu Play window
 *  Sluzy on zwiekszeniu grywalnosci, poprzez wskazanie obszaru przeznaczonego do klikniecia
 *  Obiekty zapisywane sa w ArrayList
 *  Indeksy obiektow <code>CircleValue</code> odpowiadaja obiektom <code>Number</code>
 * @see PlayWindow#circleList
 * @see PlayWindow#addValue()
 *
 * @author Tomasz Gruzdzis
 */

public class CircleValue
{

    /** Zmienna przechowujaca obrazek tla */
    private static Image circle = new ImageIcon(CircleValue.class.getResource("/Resources/circle2.gif")).getImage();
    /** Zmienna przechowujaca rozmiar okregu */
    public int size;
    /** Zmienna przechowujaca wspolrzedna X okregu */
    public int x;
    /** Zmienna przechowujaca wspolrzedna Y okregu */
    public int y;

    /** Zmienna przechowujaca zmiane wspolrzednej X okregu */
    private int dx = 1;
    /** Zmienna przechowujaca zmiane wspolrzednej Y okregu */
    private int dy = 1;

    /**
     * Konstruktor domyslny.
     * @param w ustawia wspolrzedna x
     * @param h ustawia wspolrzedna y
     * @param s ustawia rozmiar size
     */

    CircleValue(int w, int h, int s)
    {
        x = w;
        y = h;
        size = s;
    }

    /**
     * Metoda opisujaca ruch obiektu i ograniczajaca go do granic panelu
     */
    public void changeCirclePosition()
    {
        Rectangle PanelBorders = new Rectangle(1024,696);
        x += dx;
        y += dy;

        if (y + size >= PanelBorders.getMaxY())
        {
            y = (int)(PanelBorders.getMaxY()-size);
            dy = -dy;
        }
        if (x + size >= PanelBorders.getMaxX())
        {
            x = (int)(PanelBorders.getMaxX()-size);
            dx = -dx;
        }
        if (y < PanelBorders.getMinY())
        {
            y = (int)PanelBorders.getMinY();
            dy = -dy;
        }

        if (x < PanelBorders.getMinX())
        {
            x = (int)PanelBorders.getMinX();
            dx = -dx;
        }
    }

    /** Metoda zwracajaca obraz okregu
     * @see PlayWindow#paintComponent(Graphics)
     */
    public static Image getImg()
    {
        return CircleValue.circle;
    }

}

