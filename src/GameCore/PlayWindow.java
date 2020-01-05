package GameCore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *  Obiekt <code>PlayWindow</code> reprezentuje panel gry, laczony z panelem <code>Header</code>.
 *  Panel reprezentuje plansze, na ktorej pojawiaja sie wartosci przeznaczone do wybrania.
 *  Wartosci reprezentowane sa przez obiekty <code>CircleValue</code> i <code>Number</code>.
 *  Wartosci pojawiajz sie na w losowo wybranej lokalizacji.
 *  Ilosc obecnych obiektow jak i ich parametry okreslone sa przez obiekt klasy <code>HVLevel</code>.
 *
 * @author Tomasz Gruzdzis
 */

public class PlayWindow extends JPanel
{
    /** ArrayList przechowuja obiekty odpowiednio klas <code>CircleValue</code> i <code>Number</code>
     * Indeks obiektu jednej klasy odpowiada indeksowi obiektu klasy drugiej.
     */
    ArrayList circleList = new ArrayList();
    ArrayList valueList = new ArrayList();
    /** Zmienna przechowujaca informacje o indeksie wybranego obiektu */
    private int whichObjectWasClicked;
    /** Zmienna przechowujaca obrazek tla */
    private static Image bg2 = new ImageIcon("gb2.gif").getImage();
    private static Image instr = new ImageIcon("Instr.png").getImage();
    /** Reprezentacja klasy <code>HVLevel</code> informujaca o zmieniajacych sie parametrach wraz z poziomami */
    HVLevel level = new HVLevel();
    /** Deklarcja watku odpowiedzialnego za poruszanie sie wartosci */
    private Thread movingAnimation;
    private ThreadGroup animationGroup = new ThreadGroup("Moving Circles");
    /** Flaga informujaca o wlaczeniu instrukcji. */
    public boolean instructionShow;

    /**
     * Konstruktor domyslny
     * Ustawienie preferowanego rozmiaru panelu
     */
    PlayWindow(){ setPreferredSize(new Dimension(1024, 696)); }

    /**
     * Metoda odpowiedzialna za dodawanie wartosci do panelu w losowo obranej lokalizacji.
     */

    public void addValue()
    {

        /** Pobranie losowych zmiennych, z ograniczeniami ustanowionymi w <code>HVLevel</code>. */
        int scale = level.getRandomSize();
        int startX = level.getRandomX(scale);
        int startY = level.getRandomY(scale);

        /** Wprowadzenie wspołrzędnych srodka obiektu.
         * Wykorzystywane w zabezpieczeniu przed nachodzeniem na siebie wartosci*/
        int middleX = (int)(startX + 0.5*scale);
        int middleY = (int)(startY + 0.5*scale);
        Point middlePoint = new Point(middleX,middleY);
        /** Wywolanie metody informującej o aktualnym poziomie*/
        level.whichLevel();
        int randomValue = level.getRandomValue();
        /** Jezeli na planszy nie ma jeszcze zadnej wartosci, to pierwsza dodana wartosc moze przyjąc dowolne parametry*/
        if(circleList.isEmpty())
        {
            /** Dodanie do list pierwszych obiektow*/
            circleList.add(new CircleValue(startX, startY, scale));
            valueList.add(new Number(startX, startY, scale, randomValue));
        }
        else
        {
            /** Flaga informująca o nachodzeniu na siebie tych samych obiektow*/
            boolean overlapping = false;

            /** Petla sprawdzajaca kazdy istniejacy obiekt w <code>circleList</code>*/
            for (int i = 0; i < circleList.size(); i++)
            {
                /** Zmienna reprezentujaca srodek wspolrzednych istniejacego juz obiektu*/
                Point previousPoint = new Point(
                        (int)(((CircleValue) circleList.get(i)).x + 0.5*((CircleValue) circleList.get(i)).size),
                        (int)(((CircleValue) circleList.get(i)).y + 0.5*((CircleValue) circleList.get(i)).size));

                /** Jezeli dystans miedzy srodkiem przyszlego obiektu jest mniejszy od przekatnej poprzedniego obiektu,
                 *  to obiekty nachodza na siebie.*/
                if(middlePoint.distance(previousPoint)<(1.44*0.5*scale+1.44*0.5*((CircleValue) circleList.get(i)).size))
                {
                    overlapping = true;
                    /** Przerwanie petli w przypadku pierwszej zmiany flagi. */
                    break;
                }
            }
            /** Jezeli obiekt o obranych parametrach nie bedzie nachodzil na zaden  inny, moze powstac. */
            if(!overlapping)
            {
                /** Dodanie do list nowych obiektow, o wybranych wyzej parametrach.*/
                circleList.add(new CircleValue(startX, startY, scale));
                valueList.add(new Number(startX, startY, scale, randomValue));
            }

        }
        /** Odswiezenie panelu*/
        this.repaint();
    }

    /**
     * Metoda odpowiedzialna za wywolanie animacji ruchu
     * @param index ustawia ktory obiekt ma sie poruszac.
     */

    public synchronized void startAnimation(int index)
    {
        movingAnimation = new Thread(animationGroup,
                new MoveTheCircle(((CircleValue) circleList.get(index)), ((Number) valueList.get(index)) ));
        movingAnimation.start();
    }

    /**
     * Klasa wewnetrzna wykorzystywana do przedstawienia ruchu.
     */
    class MoveTheCircle implements Runnable {

        /** Przeslany obiekt kola poprzez numer indeksu */
        CircleValue circle;
        /** Przeslany obiekt wartosci poprzez numer indeksu */
        Number value;

        /** Konstruktor domyslny
         * @param circle ustawia wybrane kolo
         * @param value ustawia wybrana wartosc
         */
        MoveTheCircle(CircleValue circle, Number value)
        {
            this.circle = circle;
            this.value = value;
        }

        /** Metoda wywolujaca zmiane pozycji */
        public void run()
        {
            try
            {
                while(!Thread.currentThread().isInterrupted())
                {
                    if(HVLevel.endOfTheGame)
                        Thread.currentThread().interrupt();

                    circle.changeCirclePosition();

                    value.changeNumberPosition(circle.x,circle.y,circle.size);  //Wewnetrzna wartosc przyjmuje takie same parametry jak kolo

                    repaint();
                    //Zmiana predkosci w zaleznosci od rozmiaru w celu latwiejszego wybrania pola wartosci
                    if(circle.size < 150)
                        Thread.sleep(100);
                    else
                        Thread.sleep(50);
                }
            }
            catch (InterruptedException e) { }
        }
    }


    /**
     * Metoda sprawdzajaca lokalicacje, na ktorą wskazal uzytkownik.
     */

    public boolean isInValueArea(MouseEvent cursor)
    {
        /** Punkt o wspołrzednych miejsca w ktore wybral uzytkownik.*/
        Point cursorPoint = new Point(cursor.getX(),cursor.getY());

        /** Petla sprawdzajaca czy punkt zawiera się w otoczeniu wartosci z planszy.*/
        for(int i = 0; i < circleList.size(); i++)
        {
            Point checkingPoint = new Point(
                    (int)(((CircleValue)circleList.get(i)).x + 0.5*((CircleValue)circleList.get(i)).size),
                    (int)(((CircleValue)circleList.get(i)).y + 0.5*((CircleValue)circleList.get(i)).size)
            );
            /** Jezeli dystans miedzy punktem, ktory wybral gracz jest mniejszy niz promien wewnetrznego okregu,
             * to indeks tego okregu zostaje zapisany. */
            if(cursorPoint.distance(checkingPoint)<(0.3*((CircleValue) circleList.get(i)).size))
            {
                /** Zmienna przechowujaca indeks wybranego okregu.*/
                whichObjectWasClicked = i;
                /** Zwrocenie informacji, ze zostala wybrana pewna wartosc.*/
                return true;
            }
        }
        /** Zwrocenie informacji, ze nie zostala wybrana wartosc.*/
        return false;
    }


    /**
     * Metoda sprawdzająca, czy wybrana wartosc jest najwieksza z mozliwych.
     */

    public boolean checkValue()
    {
        for(int i = 0; i < valueList.size(); i++)
        {
            /** Jezeli wybrana przez gracza wartosc jest mniejsza od ktorejs z kolejnych wartosci, to znaczy ze nie jest najwieksza.*/
            if(((Number)valueList.get(whichObjectWasClicked)).value < ((Number)valueList.get(i)).value)
                return false;
        }
        /** Zwrocenie informacji o tym, ze wybrana wartosc jest rowna najwiekszej wartosci na planszy. */
        return true;
    }


    /**
     * Metoda rysujaca panelu.
     */

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        /** Ustawienie obrazu tla. */
        g.drawImage(bg2,0,0,1024,696,null);

        if(instructionShow)
        {
            g.drawImage(instr,0,0,1024,696,null);
        }

        /** Petla odpowiedzialna za narysowanie wszystkich obiektow z <code>circleList</code> */
        for (int i = 0; i < circleList.size(); i++)
        {
            g.drawImage(
                    CircleValue.getImg(),
                    ((CircleValue) circleList.get(i)).x, ((CircleValue) circleList.get(i)).y,
                    ((CircleValue) circleList.get(i)).size, ((CircleValue) circleList.get(i)).size, null);
        }
        /** Petla odpowiedzialna za narysowanie wszystkich obiektow z <code>valueList</code> */
        for(int j = 0; j < valueList.size(); j++)
        {
            g.setFont(new Font("Arial",Font.BOLD, ((Number)valueList.get(j)).fontSize));
            g.drawString(
                    (((Number)valueList.get(j)).value + ""),
                    ((Number)valueList.get(j)).x,
                    ((Number)valueList.get(j)).y);
        }
        /** Jezeli nastapil koniec gry, wyswietlony zostanie specjalny napis. */
        if(HVLevel.endOfTheGame)
        {
            g.setFont(new Font("Arial",Font.BOLD, 50));
            g.drawString("Gratulacje udało ci się ukończyć grę!",50,300);
        }

    }

}
