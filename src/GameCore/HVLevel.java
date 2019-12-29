package GameCore;

import java.util.Random;

public class HVLevel
{
    public static int levelNumber = 1;
    public int howManyObjects;
    public int valueLimit;
    public static boolean endOfTheGame;

    Random random = new Random();

   public void whichLevel()
   {
       switch (levelNumber)
       {
           case 1:
               endOfTheGame = false;
               howManyObjects = 6;
               valueLimit = 14;
               break;
           case 2:
               howManyObjects = 7;
               valueLimit = 19;
               break;
           case 3:
               howManyObjects = 7;
               valueLimit = 24;
               break;
           case 4:
               howManyObjects = 8;
               valueLimit = 24;
               break;
           case 5:
               howManyObjects = 8;
               valueLimit = 29;
               break;
           case 6:
               howManyObjects = 8;
               valueLimit = 34;
               break;
           case 7:
               howManyObjects = 9;
               valueLimit = 30;
               break;
           case 8:
               howManyObjects = 10;
               valueLimit = 34;
               break;
           case 9:
               howManyObjects = 10;
               valueLimit = 40;
               break;
           case 10:
               howManyObjects = 10;
               valueLimit = 50;
               break;

       }
   }


    public int getRandomSize()
    {
        return random.nextInt(210) +50;
    }

    public int getRandomX(int objectSize)
    {
        return random.nextInt(1000 - objectSize);
    }

    public int getRandomY(int objectSize)
    {
        return random.nextInt(672 -objectSize);
    }

    public int getRandomValue()
    {
        return random.nextInt(valueLimit)+1;
    }
}
