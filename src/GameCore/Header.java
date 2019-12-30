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


        g.drawString(timeText,10,45);
        g.drawString(lvlText,210,45);
        g.drawString(missText,410,45);
        g.setFont(new Font("Arial",Font.BOLD, 40));
        g.drawLine(690,10,690,60);
        g.drawLine(865,10,865,60);
        //g.drawRect(718,9,170,52);
        g.drawString("START",710,50);
        g.drawString("BACK",885,50);

    }
}
