package GameCore;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameWindow extends JFrame
{
    private JPanel gameScreen = new JPanel();               //Panel gameScreen to panel główny, wykorzystywany do CardLayout
    private JPanel innerGameScreen = new JPanel();          //Panel innerGameScreen służy jako panel zbiorczy dla nagłówka i okienka gry
    private Header upperPanel = new Header();               //Panel nagłówka, mieści informacje o grze, oraz kontrolki podreczne
    private PlayWindow lowerPanel = new PlayWindow();       //Panel gry, to na nim pojawiaja sie wartości przeznaczone do wybrania w HV
    private ScoreBoard scorePanel = new ScoreBoard();
    private GameMenu menuPanel = new GameMenu();            //Panel menu głównego
    private CardLayout cLayout = new CardLayout();          //Card Layout pozwala na szybkie przemieszczanie się pomiędzy panelami Menu i gry
    private HVLevel level = new HVLevel();                  //Reprezentacja poziomów, obiekt pozwala na uzyskiwanie różnicy w parametrach

    private static int wrongAnswerCounter = 0;       //Zmienna zliczająca błędne odpowiedzi
    private Thread timeMeasure;                             //Wątek odpowiedzialny za zliczanie czasu
    private String userName;

    GameWindow()
    {

        //zmienne uzywane przy centrowaniu ramki
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;        //Pobranie szerokosci ekranu
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;      //pobranie wysokosci ekrany

        this.setSize(1024, 768);        //Nadanie rozmiarow ramki
        this.setTitle("BrainBooster");                //ustawienie tytułu ramki

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);   //Ustawienie funkcji krzyżyka ramki
        this.setResizable(false);                       //Zablokowanie mozliwości zmiany rozmiaru

        int frameWidth = this.getSize().width;
        int frameHeight = this.getSize().height;
        this.setLocation((screenWidth - frameWidth)/2, (screenHeight - frameHeight)/2);         //Centrowanie ramki

        initializeHVComponents();
        OptionsFromInnerPanel();
        OptionsFromMenuPanel();
        OptionsFromScorePanel();

    }

    public void loadGame()
    {
        if(HVLevel.endOfTheGame)
        {
            finishTheGame();
            HVLevel.levelNumber =1;
            wrongAnswerCounter = 0;
        }

        lowerPanel.valueList.clear();
        lowerPanel.circleList.clear();

        if(!HVLevel.endOfTheGame)
        {
            level.whichLevel();
            upperPanel.lvlText="Poziom: "+HVLevel.levelNumber;
            upperPanel.missText ="Liczba błędów: "+wrongAnswerCounter;
            upperPanel.repaint();

            while(lowerPanel.circleList.size() != level.howManyObjects )
            {
                lowerPanel.addValue();
            }
            HVLevel.levelNumber ++;
        }
    }
    public void finishTheGame()
    {
        timeMeasure.interrupt();        //Przerwanie liczenia czasu
        userName = JOptionPane.showInputDialog("Podaj mi proszę swoje imię...");
        if(userName.isEmpty() || userName.equals(""))
            userName = "Brak";
        try
        {
            PrintWriter scoreSave = new PrintWriter(new FileWriter("score.txt", true));
            LocalDateTime currentDate = LocalDateTime.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = currentDate.format(dateFormat);
            scoreSave.println(formattedDate + "/" + userName + "/" + upperPanel.timeText
                    + "/" + wrongAnswerCounter);
            scoreSave.close();
            scorePanel.readScore();
        }
        catch (Exception e) { System.err.println(e.getMessage()); }
        //System.out.println(userName + ", twoj czas to: " + upperPanel.timeText);
        lowerPanel.repaint();           //Odswieżenie Panelu gry
        upperPanel.click = false;
    }

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

    public void OptionsFromScorePanel()
    {
        scorePanel.addMouseListener(new MouseAdapter()
        {
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

    public void OptionsFromMenuPanel()
    {
        menuPanel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                super.mousePressed(e);
                if(e.getX() > (menuPanel.distanceToRectX)/2 && e.getX() < (menuPanel.distanceToRectX)/2 + menuPanel.rectWidth)
                {
                    if(e.getY() > 2*menuPanel.rectHeight && e.getY() < 3*menuPanel.rectHeight)
                    {
                        System.out.println("click");
                        cLayout.show(gameScreen,"2");
                        JOptionPane.showMessageDialog(null,
                                "Witaj w grze 'The High Value'! Celem gry jest w jak najkrótszym czasie bla bla bla bla...");

                    }

                    if(e.getY() > (int)(3.5*menuPanel.rectHeight) && e.getY() < (int)(4.5*menuPanel.rectHeight))
                    {
                        cLayout.show(gameScreen,"3");
                    }
                    if(e.getY() > 5*menuPanel.rectHeight && e.getY() < 6*menuPanel.rectHeight)
                    {
                        System.exit(1);
                    }
                }
            }
        });
    }

    public void OptionsFromInnerPanel()
    {
        lowerPanel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(lowerPanel.isInValueArea(e) == true)
                {
                    if(lowerPanel.checkValue()==true)
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

        upperPanel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                super.mousePressed(e);
                if(e.getX() > 690 && e.getX() < 865 && !upperPanel.click)
                {
                    upperPanel.startText = "START";
                    upperPanel.repaint();
                    timeMeasure = new Thread(new TimeMeasuring());
                    HVLevel.endOfTheGame = false;
                    loadGame();
                    timeMeasure.start();
                    upperPanel.click = true;
                }

                if(e.getX() > 865)
                {
                    cLayout.show(gameScreen,"1");
                    HVLevel.levelNumber =1;
                    wrongAnswerCounter = 0;
                    upperPanel.click = false;

                    if(timeMeasure != null)
                        timeMeasure.interrupt();

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

    class TimeMeasuring implements Runnable {
        int oneTenthMilliseconds, seconds, minutes;
        String millisecondsText, secondsText, minutesText;
        public void run()
        {
            minutes = 0; seconds = 0; oneTenthMilliseconds = 0;
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

