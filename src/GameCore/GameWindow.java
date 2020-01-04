package GameCore;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Klasa glowna, reprezentujaca ramke programu.
 * W klasie znajdziemy zainicjowane komponenty i obsluge myszki poszczegolnych paneli.
 * W klasie wystepuje klasa wewnetrzna odpowiedzialna za odmierzanie czasu gry.
 *
 * @author Tomasz
 */

public class GameWindow extends JFrame
{
    /** Panel gameScreen to panel glowny, wykorzystywany do CardLayout. */
    private JPanel gameScreen = new JPanel();
    /** Panel innerGameScreen sluzy jako panel zbiorczy dla naglowka i okna gry. */
    private JPanel innerGameScreen = new JPanel();
    /** Instancja naglowka - <code>Header</code>. */
    private Header upperPanel = new Header();
    /** Instancja panelu gry - <code>PlayWindow</code>. */
    private PlayWindow lowerPanel = new PlayWindow();
    /** Instancja panelu winikow - <code>ScoreBoard</code>. */
    private ScoreBoard scorePanel = new ScoreBoard();
    /** Instancja panelu menu glownego - <code>GameMenu</code>. */
    private GameMenu menuPanel = new GameMenu();
    /** Card Layout pozwala na szybkie przemieszczanie sie pomiedzy panelami Menu i gry*/
    private CardLayout cLayout = new CardLayout();
    /** Instancja klasy odpowiedzialnej za system poziomow - <code>HVLevel</code>. */
    private HVLevel level = new HVLevel();

    /** Zmienna zliczajaca bledne odpowiedzi. */
    private static int wrongAnswerCounter = 0;
    /** Watek odpowiedzialny za zliczanie czasu gry. */
    private Thread timeMeasure;
    /** Zmienna przechowujaca imie gracza - reprezentowane przy wynikach. */
    private String userName;

    /**
     * Konstruktor domyslny.
     * Ustawia ramke.
     * Dodaje komponenty.
     */

    GameWindow()
    {
        /** Zmienne uzywane przy centrowaniu ramki. */
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;        //Pobranie szerokosci ekranu
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;      //pobranie wysokosci ekrany

        this.setSize(1024, 768);        //Nadanie rozmiarow ramki
        this.setTitle("BrainBooster");                //ustawienie tytułu ramki

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);   //Ustawienie funkcji krzyżyka ramki
        this.setResizable(false);                       //Zablokowanie mozliwości zmiany rozmiaru

        int frameWidth = this.getSize().width;
        int frameHeight = this.getSize().height;
        this.setLocation((screenWidth - frameWidth)/2, (screenHeight - frameHeight)/2);         //Centrowanie ramki

        initializeHVComponents();   //Wywolanie metody inicjujacej komponenty
        OptionsFromInnerPanel();   //Wywolanie metody wprowadzajacej obsluge myszki dla panelu gry
        OptionsFromMenuPanel();   //Wywolanie metody wprowadzajacej obsluge myszki dla panelu menu
        OptionsFromScorePanel();   //Wywolanie metody wprowadzajacej obsluge myszki dla panelu wynikow

    }

    /**
     * Metoda wczytujaca gre.
     * Ustawia plansze, obsluguje informacje dotyczace gry.
     */

    public void loadGame()
    {
        /** Jezeli gra zostala zakonczona */
        if(HVLevel.endOfTheGame)
        {
            /** Wywolanie metody zakonczenia i zapisu gry.*/
            finishTheGame();
            /** Zerowanie poziomu i bledow. */
            HVLevel.levelNumber =1;
            wrongAnswerCounter = 0;
        }

        /** Wyczyszcenie list przed wywolaniem obiektow.*/
        lowerPanel.valueList.clear();
        lowerPanel.circleList.clear();

        /** Jezeli gra nie zostala zakonczona. */
        if(!HVLevel.endOfTheGame)
        {
            /** Pobranie parametru poziomu */
            level.whichLevel();
            /** Aktualizacja naglowka */
            upperPanel.lvlText="Poziom: "+HVLevel.levelNumber;
            upperPanel.missText ="Liczba błędów: "+wrongAnswerCounter;
            upperPanel.repaint();

            /** Do momentu osiagniecia wymaganej liczby obeiktow na planszy wywoluje metode stworzenia obiektu. */
            while(lowerPanel.circleList.size() != level.howManyObjects )
            {
                lowerPanel.addValue();
            }
            /** Kolejny poziom */
            HVLevel.levelNumber ++;
        }
    }

    /**
     * Metoda konczaca gre.
     * Odpowiada za zapis wynikow do pliku.
     */

    public void finishTheGame()
    {
        timeMeasure.interrupt();        //Przerwanie liczenia czasu
        /** Pobranie imienia uzytkownika */
        userName = JOptionPane.showInputDialog("Podaj mi proszę swoje imię...");
        if(userName.isEmpty() || userName.equals(""))
            userName = "Brak";
        try
        {
            /** Otwarcie strumienia zapisujacego. */
            PrintWriter scoreSave = new PrintWriter(new FileWriter("score.txt", true));
            /** Pobranie aktualnej daty i czasu do pozniejszego zidentyfikowania wynikow. */
            LocalDateTime currentDate = LocalDateTime.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            /** Zapis do pliku z uzyciem separatora "/" */
            String formattedDate = currentDate.format(dateFormat);
            scoreSave.println(formattedDate + "/" + userName + "/" + upperPanel.timeText
                    + "/" + wrongAnswerCounter);
            scoreSave.close();
            /** Zaktualizowanie tablicy wynikow. */
            scorePanel.readScore();
        }
        catch (Exception e) { System.err.println(e.getMessage()); }
        lowerPanel.repaint();           //Odswieżenie Panelu gry
        upperPanel.click = false;
    }

    /**
     * Metoda inicjujaca komponenty.
     * Dodaje do ramki potrzebne panele i ustawia ktory ma byc widoczny po uruchomieniu.
     */

    public void initializeHVComponents()
    {

        innerGameScreen.setPreferredSize(new Dimension(1024, 768));
        innerGameScreen.setBackground(Color.DARK_GRAY);
        gameScreen.setLayout(cLayout);          //Ustawienie Card Layout'u na głównym panelu
        this.getContentPane().add(gameScreen);  //Dodanie Panelu do Ramki

        gameScreen.add(menuPanel,"1");              //Menu Panel przyjmuje podpis "1" w Card Layout
        gameScreen.add(innerGameScreen,"2");        //Panel nagłówka i gry przyjmuje podpis "2" w Card Layout
        gameScreen.add(scorePanel,"3");

        innerGameScreen.add(upperPanel, BorderLayout.NORTH);  //Dodanie Panelu nagłowka, ustawienie go powyżej panelu gry
        innerGameScreen.add(lowerPanel);                      //Dodanie Panelu gry

        cLayout.show(gameScreen,"1");                   //Wybranie wyświetlenia Panelu Menu
    }

    /**
     * Metoda wprowadzajaca obsluge myszki dla panelu wynikow
     */

    public void OptionsFromScorePanel()
    {
        scorePanel.addMouseListener(new MouseAdapter()
        {
            /** Obsluga przycisku "Back" */
            @Override
            public void mousePressed(MouseEvent e)
            {
                super.mousePressed(e);
                if(e.getY() > 658)
                {
                    cLayout.show(gameScreen,"1");
                }
            }
        });
    }

    /**
     * Metoda wprowadzajaca obsluge myszki dla panelu Menu
     */

    public void OptionsFromMenuPanel()
    {
        menuPanel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                /** Obsluga przycisku uruchomienia gry */
                super.mousePressed(e);
                if(e.getX() > (menuPanel.distanceToRectX)/2 && e.getX() < (menuPanel.distanceToRectX)/2 + menuPanel.rectWidth)
                {
                    if(e.getY() > 2*menuPanel.rectHeight && e.getY() < 3*menuPanel.rectHeight)
                    {
                        cLayout.show(gameScreen,"2");
                        JOptionPane.showMessageDialog(null,
                                "Witaj w grze 'The High Value'! Celem gry jest w jak najkrótszym czasie bla bla bla bla...");
                    }

                    /** Obsluga przycisku przejscia do panelu wynikow */
                    if(e.getY() > (int)(3.5*menuPanel.rectHeight) && e.getY() < (int)(4.5*menuPanel.rectHeight))
                    {
                        cLayout.show(gameScreen,"3");
                    }
                    /** Obsluga przycisku wysjcia */
                    if(e.getY() > 5*menuPanel.rectHeight && e.getY() < 6*menuPanel.rectHeight)
                    {
                        System.exit(1);
                    }
                }
            }
        });
    }

    /**
     * Metoda wprowadzajaca obsluge myszki dla gry
     */
    public void OptionsFromInnerPanel()
    {
        /**
         * Obsluga panelu <code>PlayWindow</code>
         */
        lowerPanel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e) {
                /** Obsluga klikniecia w wartosc na planszy */
                super.mousePressed(e);
                if(lowerPanel.isInValueArea(e))
                {
                    /** Jezeli nie ma wiekszej wartosci */
                    if(lowerPanel.checkValue())
                    {
                        if(HVLevel.levelNumber > 10)
                        {
                            HVLevel.endOfTheGame = true;
                            upperPanel.startText = "RESTART";
                            upperPanel.repaint();
                        }
                        playSound(new File("goodSound.wav"));
                        loadGame();
                    }
                    /** Jezeli jest wieksza wartosci */
                    else
                    {
                        wrongAnswerCounter++;
                        playSound(new File("badSound.wav"));
                        if(wrongAnswerCounter > 100)
                            upperPanel.missText ="Liczba błędów: 100+";
                        else
                            upperPanel.missText ="Liczba błędów: "+wrongAnswerCounter;

                        upperPanel.repaint();
                    }
                }
            }
        });

        /**
         * Obsluga panelu <code>Header</code>
         */
        upperPanel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                super.mousePressed(e);
                /** Obsluga przycisku "START" */
                if(e.getX() > 690 && e.getX() < 865 && !upperPanel.click)
                {
                    upperPanel.startText = "START";
                    upperPanel.repaint();
                    timeMeasure = new Thread(new TimeMeasuring());
                    HVLevel.endOfTheGame = false;
                    loadGame();
                    timeMeasure.start();    //Rozpoczecie mierzenia czasu
                    upperPanel.click = true;
                }

                /** Obsluga przycisku "BACk" */
                if(e.getX() > 865)
                {
                    cLayout.show(gameScreen,"1");
                    HVLevel.levelNumber =1;
                    wrongAnswerCounter = 0;
                    upperPanel.click = false;

                    if(timeMeasure != null)
                        timeMeasure.interrupt();

                    /** Wyczyszczenie planszy */
                    lowerPanel.valueList.clear();
                    lowerPanel.circleList.clear();
                    upperPanel.lvlText = "Poziom: ";
                    upperPanel.timeText = "00:00:00 ";
                    upperPanel.missText = "Liczba błędów: ";
                    upperPanel.startText = "START";
                    upperPanel.repaint();
                    lowerPanel.repaint();

                }
            }
        });
    }

    /**
     * Metoda obslugujaca dzwieki dobrego i zlego wyboru wartosci.
     * @param soundFile przekazuje jaki dzwiek ma zostac odtworzony
     */

    public static synchronized void playSound(final File soundFile) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip sound = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundFile);
                    sound.open(inputStream);
                    sound.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    /**
     * Klasa wewnetrzna obslugujaca odmierzanie czasu gry
     */

    class TimeMeasuring implements Runnable {
        /** Zmienne przechowujace jednostki czasu */
        int oneTenthMilliseconds, seconds, minutes;
        /** Zmienne pomocnicze uzupelniane zerami jezeli zmienna przechowujaca jednostke czasu jest jedno cyforwa */
        String millisecondsText, secondsText, minutesText;
        public void run()
        {
            minutes = 0; seconds = 0; oneTenthMilliseconds = 0;
            /** Do momentu kiedy czas nie zostanie przerwany lub kiedy maksymalny czas gry zostanie przekroczony. */
            while (minutes < 61 || !Thread.currentThread().isInterrupted())
            {
                try
                {
                    upperPanel.repaint();
                    Thread.sleep(10);
                    oneTenthMilliseconds += 1;
                    if (oneTenthMilliseconds == 100)
                    {
                        oneTenthMilliseconds = 0;
                        seconds++;
                    }
                    if (seconds == 60)
                    {
                        oneTenthMilliseconds = 0;
                        seconds = 0;
                        minutes++;
                    }
                    if (minutes == 60)
                    {
                        System.out.println("You probably forgot about the game!");
                    }

                    if(oneTenthMilliseconds < 10)
                        millisecondsText = "0" + oneTenthMilliseconds;
                    else
                        millisecondsText = oneTenthMilliseconds + "";
                    if(seconds < 10)
                        secondsText = "0" + seconds;
                    else
                        secondsText = seconds + "";

                    if(minutes < 10)
                        minutesText = "0" + minutes;
                    else
                        minutesText = minutes + "";

                    upperPanel.timeText = minutesText + ":" + secondsText + ":" + oneTenthMilliseconds;
                }
                catch (InterruptedException e) { break;}
            }
        }
    }

}

