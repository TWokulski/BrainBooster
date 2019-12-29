package GameCore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWindow extends JFrame
{
    Header upperPanel = new Header();
    PlayWindow lowerPanel = new PlayWindow();
   // JButton p = new JButton("zmien");
    public static int wrongAnswerCounter = 0;
    HVLevel level = new HVLevel();
    Runnable timeMeasure = new TimeMeasuring();
    Thread time;

    GameWindow()
    {
        initializeHVComponents();

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        this.setSize(1024, 768);
        this.setTitle("BrainBooster");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        int frameWidth = this.getSize().width;
        int frameHeight = this.getSize().height;
        this.setLocation((screenWidth - frameWidth)/2, (screenHeight - frameHeight)/2);

        this.getContentPane().add(upperPanel, BorderLayout.NORTH);
        this.getContentPane().add(lowerPanel);

    }

    public void loadGame()
    {
        if(HVLevel.endOfTheGame==true)
        {
            finishTheGame();
        }
        if(HVLevel.levelNumber>10)
        {
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
                lowerPanel.AddValue();
            }
            HVLevel.levelNumber ++;
        }
    }
    public void finishTheGame()
    {
        System.out.println(upperPanel.timeText);
        time.interrupt();
        lowerPanel.repaint();
        upperPanel.click = false;
    }

    public void initializeHVComponents()
    {
        //upperPanel.setBackground(Color.BLUE);
        //upperPanel.setPreferredSize(new Dimension(1024,72));
        //lowerPanel.setBackground(Color.yellow);

        lowerPanel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(lowerPanel.isInValueArea(e) == true)
                {
                    if(lowerPanel.checkValue()==true)
                    {
                        if(HVLevel.levelNumber > 10)
                        {
                            HVLevel.endOfTheGame = true;
                        }

                        loadGame();
                    }
                    else
                    {
                        wrongAnswerCounter++;
                        upperPanel.missText ="Liczba błędów: "+wrongAnswerCounter;
                        upperPanel.repaint();
                        System.out.println("no nie");
                    }
                }
            }
        });

        upperPanel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
                if(e.getX() > 768 && !upperPanel.click)
                {
                    time = new Thread(timeMeasure);
                    HVLevel.endOfTheGame = false;
                    loadGame();
                    time.start();
                    upperPanel.click = true;

                }

            }
        });

        /*p.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        });*/
    }
    class TimeMeasuring implements Runnable {
        int milliseconds, seconds, minutes;
        String secondsText, minutesText;
        public void run()
        {
            minutes = 0; seconds = 0; milliseconds = 0;
            while (minutes < 61 || !Thread.currentThread().isInterrupted()){
                try {
                    upperPanel.repaint();
                    Thread.sleep(10);
                    milliseconds += 10;
                    if (milliseconds == 1000) {
                        milliseconds = 0;
                        seconds++;
                    }
                    if (seconds == 60) {
                        milliseconds = 0;
                        seconds = 0;
                        minutes++;
                    }
                    if (minutes == 60) {
                        System.out.println("You probably forgot about the game!");
                    }

                    if(seconds < 10)
                        secondsText = "0" + seconds;
                    else
                        secondsText = seconds + "";

                    if(minutes < 10)
                        minutesText = "0" + minutes;
                    else
                        minutesText = minutes + "";

                    upperPanel.timeText = minutesText + ":" + secondsText + ":" + milliseconds;

                }
                catch (InterruptedException e) {
                    break;
                }
            }

        }
    }


}

