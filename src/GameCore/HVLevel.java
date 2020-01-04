package GameCore;

import java.util.Random;

/**
 * Obiekt <code>HVLevel</code> sluzy przedstawieniu systemu poziomow rozgrywki.
 * Obiekt decyduje o wymiarach parametrow gry, takich jak ilosc obiektow na planszy czy roznice pojawiajacych sie wartosci.
 * @author Tomasz Gruzdzis
 */


public class HVLevel
{
    /** Zmienna informujaca o aktualnym poziomie gry (1-10). */
    public static int levelNumber = 1;
    /** Ilosc obiektow jaka ma sie pojawic na planszy. */
    public int howManyObjects;
    /** Zmienna okreslajaca limit wartosci pojawiajacych sie liczb. */
    private int valueLimit;
    /** Flaga informujaca o zakonczeniu rozgrywki. */
    public static boolean endOfTheGame;

    Random random = new Random();

    /**
     * Metoda ustwiajaca parametry poziomu rozgrywki
     */

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

    /**
     * Metoda okreslajaca losowy rozmiar obiektu.
     * Minimalna wartosc zwiekszona o 50 px
     */
    public int getRandomSize()
    {
        return random.nextInt(210) +50;
    }

    /**
     * Metoda okreslajaca losowy wspolrzedna X obiektu.
     * Ograniczona rozmiarem obiektu.
     */
    public int getRandomX(int objectSize)
    {
        return random.nextInt(1000 - objectSize);
    }
    /**
     * Metoda okreslajaca losowy wspolrzedna Y obiektu.
     * Ograniczona rozmiarem obiektu.
     */
    public int getRandomY(int objectSize)
    {
        return random.nextInt(672 -objectSize);
    }

    /**
     * Metoda okreslajaca losowa wartosc obiektu.
     * (Od 1 do ustawionego przez poziom limitu.)
     */
    public int getRandomValue()
    {
        return (random.nextInt(valueLimit) + 1) ;
    }
}
