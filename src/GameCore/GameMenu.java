package GameCore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameMenu extends JPanel
{
    private static Image bgMenu = new ImageIcon("bgMenu.gif").getImage();
    public final int rectWidth = 384;
    public final int rectHeight = 96;
    public final int distanceToRectX = (1024-rectWidth);


    GameMenu()
    {
        setPreferredSize(new Dimension(1024, 768));


    }

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
