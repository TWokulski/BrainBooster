package GameCore;

import javax.swing.*;
import java.awt.*;

/**
 *  Obiekt <code>Header</code> reprezentuje panel naglowka, laczony z panelem <code>PlayWindow</code>.
 *  W panelu informacje dotyczace rozgrywki: Czas gry, aktualny poziom, liczba popelnionych bledow
 *  W panelu umieszczone zostaly przyciski "START" i "BACK" pozwalajace na rozpoczecie i przerwanie gry
 *
 * @author Tomasz Gruzdzis
 */

public class Header extends JPanel
{
    /** Zmienna przechowujaca obrazek tla */
    private static Image bg = new ImageIcon("bg.gif").getImage();
    /** Zmienna prezentujaca ilosc popelnioych bledow */
    String missText = "Liczba błędów: ";
    /** Zmienna prezentujaca poziom aktualnej planszy */
    String lvlText = "Poziom: ";
    /** Zmienna prezentujaca czas jaki minal od momentu rozpoczecia rozgrywki*/
    String timeText = "00:00:00 ";
    /** Zmienna prezentujaca nazwe przycisku rozpoczecia rozgrywki */
    String startText = "START";
    /** Zmienna przechowujaca kolor <code>startText</code> */
    private Color textColor;
    /** Zmienna przechowujaca wspolrzedną X <code>startText</code> */
    private int startX;
    /** Zmienna przechowujaca wspolrzedna X <code>startText</code> */
    private int startY;
    /** Zmienna przechowujaca informacje o wybraniu opcji rozpoczęcia gry */
    public boolean click;

    /** Ustawienie preferowanego rozmiaru panelu*/
    Header(){ setPreferredSize(new Dimension(1024,72)); }

    /**
     * Metoda rysujaca panelu
     */

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        /** Ustawienie obrazu tla i parametrow tekstu */
        g.drawImage(bg,0,0,1024,72,null);
        g.setFont(new Font("Arial",Font.PLAIN, 30));
        textColor = Color.BLACK;
        g.setColor(textColor);
        /** Rozrysowanie informacji dotyczących gry*/
        g.drawString(timeText,10,45);
        g.drawString(lvlText,210,45);
        g.drawString(missText,410,45);
        /** Rozrysowanie przyciskow obslugi gry*/
        g.setFont(new Font("Arial",Font.BOLD, 40));
        g.drawLine(690,10,690,60);
        g.drawLine(865,10,865,60);
        g.drawString("BACK",885,50);
        if(!startText.equals("START"))
        {
            /** Zmiana parametrow w przypadku zmiany nazwy przycisku na "Restart"
             * @see GameWindow#OptionsFromInnerPanel()
             */
            textColor = Color.DARK_GRAY;
            g.setFont(new Font("Arial",Font.BOLD, 32));
            startX = 705;
            startY = 45;
        }
        else
         {
            startX = 710;
            startY = 50;
            textColor = Color.BLACK;
         }
        g.setColor(textColor);
        g.drawString(startText,startX,startY);
    }
}
