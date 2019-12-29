package GameCore;

import javax.swing.*;
import java.awt.*;


public class Header extends JPanel
{
    private static Image bg = new ImageIcon("bg.gif").getImage();
    String missText = "Liczba błędów: ";
    String lvlText = "Poziom: ";
    String timeText = "00:00:00 ";
    public boolean click;
    Header()
    {
        setPreferredSize(new Dimension(1024,72));
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(bg,0,0,1024,72,null);
        g.setFont(new Font("Arial",Font.PLAIN, 30));


        g.drawString(timeText,11,45);
        g.drawString(lvlText,211,45);
        g.drawString(missText,411,45);
        g.setFont(new Font("Arial",Font.BOLD, 40));

        g.drawRect(768,9,245,52);
        g.drawString("START",798,50);
    }
}
