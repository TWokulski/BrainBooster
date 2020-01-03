package GameCore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class ScoreBoard extends JPanel
{
    private static Image bgMenu = new ImageIcon("bgMenu.gif").getImage();
    JTable scoreTable;
    String[] columnNames = {"Date", "Player Name", "Time", "Wrong answers"};

    ScoreBoard()
    {

        this.setLayout( new FlowLayout() );

        scoreTable = new JTable(0,0);
        readScore();
        scoreTable.setPreferredScrollableViewportSize(new Dimension(800,300));
        scoreTable.setFillsViewportHeight(true);

        scoreTable.setBackground(Color.BLACK);
        scoreTable.setFont(new Font("Arial",Font.PLAIN, 20));
        scoreTable.setForeground(Color.WHITE);


        JScrollPane scrollPane = new JScrollPane(scoreTable);

        add(scrollPane);


    }
    private  void readScore()
    {
        try
        {
            BufferedReader scoreRead = new BufferedReader (new FileReader("score.txt"));

            DefaultTableModel model = (DefaultTableModel)scoreTable.getModel();
            Object [] tableLines = scoreRead.lines().toArray();
            model.setColumnIdentifiers(columnNames);

            for(int i = 0; i < tableLines.length; i++)
            {
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split("/");
                model.addRow(dataRow);

            }
            scoreRead.close();
        }
        catch (Exception e)
        {

        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgMenu, 0, 0, 1024, 768, null);
        g.setFont(new Font("Arial",Font.BOLD, 60));
        g.setColor(Color.WHITE);
        g.drawLine(0,658,1024,658);
        g.drawString("BACK", 420,718);
    }
}
