package GameCore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWindow extends JFrame
{
    Header upperPanel = new Header();
    PlayWindow lowerPanel = new PlayWindow();
   // JButton p = new JButton("zmien");
    public static int wrongAnswerCounter = 0;
    HVLevel level = new HVLevel();

    GameWindow()
    {
        initializeHVComponents();

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        this.setSize(1024, 768);
        this.setTitle("BrainBooster");

        int frameWidth = this.getSize().width;
        int frameHeight = this.getSize().height;

        this.setLocation((screenWidth - frameWidth)/2, (screenHeight - frameHeight)/2);
        this.getContentPane().add(upperPanel, BorderLayout.NORTH);
        this.getContentPane().add(lowerPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

    }

    public void loadGame()
    {
        lowerPanel.valueList.clear();
        lowerPanel.circleList.clear();

        level.whichLevel();
        upperPanel.lvlText="Poziom: "+HVLevel.levelNumber;
        upperPanel.missText ="Liczba błędów: "+wrongAnswerCounter;
        upperPanel.repaint();

        while(lowerPanel.circleList.size() != level.howManyObjects )
        {
            lowerPanel.AddValue();
        }
        HVLevel.levelNumber ++;
        if(HVLevel.levelNumber>10)
        {
            HVLevel.levelNumber =1;
            wrongAnswerCounter = 0;
        }

    }

    public void initializeHVComponents()
    {
        upperPanel.setBackground(Color.BLUE);
        upperPanel.setPreferredSize(new Dimension(1024,72));
        lowerPanel.setBackground(Color.yellow);

        lowerPanel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(lowerPanel.isInValueArea(e) == true)
                {
                    if(lowerPanel.checkValue()==true)
                    {
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
                    loadGame();
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

}

