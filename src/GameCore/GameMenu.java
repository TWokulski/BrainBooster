package GameCore;

import javax.swing.*;
import java.awt.*;

/**
 *  Obiekt <code>GameMenu</code> reprezentuje panel menu, pierwszy widoczny element gry.
 *  Na panel skladaja sie 3 przyciski: uruchomieniowy gry, przycisk wynikow, oraz przycisk opuszczenia gry.
 *
 * @author Tomasz Gruzdzis
 */

public class GameMenu extends JPanel
{
    /** Zmienna przechowujaca obrazek tla. */
    private static Image bgMenu = new ImageIcon("bgMenu.gif").getImage();
    /** Zmienna przechowujaca dlugosc przycisku. */
    public final int rectWidth = 384;
    /** Zmienna przechowujaca szerokosc przycisku. */
    public final int rectHeight = 96;
    /** Zmienna pomocnicza do ustawienia przyciskow na panelu. */
    public final int distanceToRectX = (1024-rectWidth);

    /**
     * Konstruktor domyslny.
     * Ustawia rozmiar panelu.
     */
    GameMenu()
    {
        setPreferredSize(new Dimension(1024, 768));
    }

    /**
     * Metoda rysujaca panel.
     * Ustawia obraz tla i przyciski.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(bgMenu,0,0,1024,768,null);

        g.setFont(new Font("Arial",Font.BOLD, 60));
        g.setColor(Color.WHITE);

        g.drawRect((distanceToRectX)/2,2*rectHeight, rectWidth, rectHeight);
        g.drawRect((distanceToRectX)/2,(int)(3.5*rectHeight) ,rectWidth, rectHeight);
        g.drawRect((distanceToRectX)/2,5*rectHeight, rectWidth, rectHeight);

        g.drawString("High Value",(int)((distanceToRectX)/1.8),(int)(2.7*rectHeight));
        g.drawString("Wyniki",(int)((distanceToRectX)/1.55),(int)(4.2*rectHeight));
        g.drawString("Wyjście",(int)((distanceToRectX)/1.6),(int)(5.7*rectHeight));
    }

}
