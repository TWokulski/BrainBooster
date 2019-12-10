package GameCore;

import javax.swing.*;

public class Number
{
    public int Value;
    public int fontSize;
    public int x;
    public int y;

    Number(int w, int h, int s, int randomValue)
    {
        Value = randomValue;
        if(Value<10)
        {
            x = (int)(w+0.33*s);
        }
        else
        {
            x = (int)(w+0.22*s);
        }
        fontSize = (int)(0.5*s);

        y = (int)(h+0.7*s);

    }

}
