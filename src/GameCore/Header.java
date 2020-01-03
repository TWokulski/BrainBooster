package GameCore;

import javax.swing.*;
import java.awt.*;

/**
 *  Obiekt <code>Header</code> reprezentuje panel nagłówka, łączony z panelem <code>PlayWindow</code>.
 *  W panelu informacje dotyczące rozgrywki: Czas gry, aktualny poziom, liczba popełnionych błędów
 *  W panelu umieszczone zostały przyciski "START" i "BACK" pozwalające na rozpoczacie i przerwanie gry
 *
 * @author Tomasz Gruzdzis
 */

public class Header extends JPanel
{
    private static Image bg = new ImageIcon("bg.gif").getImage();
    String missText = "Liczba błędów: ";
    String lvlText = "Poziom: ";
    String timeText = "00:00:00 ";
    String startText = "START";
    private Color textColor;
    private int startX;
    private int startY;
    public boolean click;

    Header(){ setPreferredSize(new Dimension(1024,72)); }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(bg,0,0,1024,72,null);
        g.setFont(new Font("Arial",Font.PLAIN, 30));
        textColor = Color.BLACK;
        g.setColor(textColor);
        g.drawString(timeText,10,45);
        g.drawString(lvlText,210,45);
        g.drawString(missText,410,45);
        g.setFont(new Font("Arial",Font.BOLD, 40));
        g.drawLine(690,10,690,60);
        g.drawLine(865,10,865,60);
        g.drawString("BACK",885,50);
        if(!startText.equals("START"))
        {
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
