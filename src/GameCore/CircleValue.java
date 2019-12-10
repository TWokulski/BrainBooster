package GameCore;

import javax.swing.*;
import java.awt.*;


public class CircleValue
{
    private static Image circle = new ImageIcon("circle2.gif").getImage();
    public int size;
    public int x;
    public int y;
    CircleValue(int w, int h, int s)
    {
        x = w;
        y = h;
        size = s;

    }
    public static Image getImg()
    {
        return CircleValue.circle;
    }

}

