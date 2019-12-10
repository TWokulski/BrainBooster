package GameCore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class PlayWindow extends JPanel
{
    ArrayList circleList = new ArrayList();
    ArrayList valueList = new ArrayList();
    private int whichObjectWasClicked;
    private static Image bg2 = new ImageIcon("gb2.gif").getImage();

   HVLevel level = new HVLevel();

    PlayWindow()
    {
        setPreferredSize(new Dimension(1024, 696));
    }


    public void AddValue()
    {
        int scale = level.GetRandomSize();
        int startX = level.GetRandomX(scale);
        int startY = level.GetRandomY(scale);
        int middleX = (int)(startX + 0.5*scale);
        int middleY = (int)(startY + 0.5*scale);
        level.whichLevel();
        int randomValue = level.GetRandomValue();

        Point middlePoint = new Point(middleX,middleY);

            if(circleList.isEmpty())
            {
                circleList.add(new CircleValue(startX, startY, scale));
                valueList.add(new Number(startX, startY, scale, randomValue));
            }
            else
            {
                boolean overlapping = false;
                for (int i = 0; i < circleList.size(); i++)
                {
                    Point previousPoint = new Point(
                            (int)(((CircleValue) circleList.get(i)).x + 0.5*((CircleValue) circleList.get(i)).size),
                            (int)(((CircleValue) circleList.get(i)).y + 0.5*((CircleValue) circleList.get(i)).size));

                    if(middlePoint.distance(previousPoint)<(1.44*0.5*scale+1.44*0.5*((CircleValue) circleList.get(i)).size))
                    {
                        overlapping = true;
                        break;
                    }
                }
                if(overlapping!=true)
                {
                    circleList.add(new CircleValue(startX, startY, scale));
                    valueList.add(new Number(startX, startY, scale, randomValue));
                }
            }

        this.repaint();
    }






    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(bg2,0,0,1024,696,null);

        for (int i = 0; i < circleList.size(); i++)
        {
            g.drawImage(
                    CircleValue.getImg(),
                    ((CircleValue) circleList.get(i)).x, ((CircleValue) circleList.get(i)).y,
                    ((CircleValue) circleList.get(i)).size, ((CircleValue) circleList.get(i)).size, null);
        }
        for(int j = 0; j < valueList.size(); j++)
        {
            g.setFont(new Font("Arial",Font.BOLD, ((Number)valueList.get(j)).fontSize));
            g.drawString(
                    (((Number)valueList.get(j)).Value + ""),
                    ((Number)valueList.get(j)).x,
                    ((Number)valueList.get(j)).y);
        }
    }


    public boolean isInValueArea(MouseEvent cursor)
    {
        Point cursorPoint = new Point(cursor.getX(),cursor.getY());

        for(int i = 0; i < circleList.size(); i++)
        {
            Point checkingPoint = new Point(
                    (int)(((CircleValue)circleList.get(i)).x + 0.5*((CircleValue)circleList.get(i)).size),
                    (int)(((CircleValue)circleList.get(i)).y + 0.5*((CircleValue)circleList.get(i)).size)
            );
            if(cursorPoint.distance(checkingPoint)<(0.3*((CircleValue) circleList.get(i)).size))
            {
                whichObjectWasClicked = i;
                return true;
            }
        }
        return false;
    }

    public boolean checkValue()
    {
        for(int i = 0; i < valueList.size(); i++)
        {
            if(((Number)valueList.get(whichObjectWasClicked)).Value < ((Number)valueList.get(i)).Value)
            {
                return false;
            }
        }
        return true;
    }
}
