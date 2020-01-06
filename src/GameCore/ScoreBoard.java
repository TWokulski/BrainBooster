package GameCore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *  Obiekt <code>ScoreBoard</code> reprezentuje panel z tablica wynikow.
 *  W panelu znajdziemy tablice z wynikami odczytanymi z pliku tekstowego oraz przycisk powrotu do Menu
 *
 * @author Tomasz Gruzdzis
 */

public class ScoreBoard extends JPanel
{
    /** Zmienna przechowujaca obrazek tla */
    private Image bgMenu = new ImageIcon(getClass().getResource("/Resources/bgMenu.gif")).getImage();
    /** Deklaracja Tabeli, przeznaczonej do prezentowania wynikow */
    private JTable scoreTable;
    /** Zadeklarowanie kolum jakie beda uzywane w tabeli */
    private String[] columnNames = {"Date", "Player Name", "Time", "Wrong answers"};

    /**
     * Konstruktor domyslny.
     */

    ScoreBoard()
    {
        this.setLayout( new FlowLayout() );
        /**
         * Stworzenie obiektu tabeli, nie posiadajacej kolumn i wierszy.
         * Kolumny i wiersze dodane zostaja w readScore()
         * @see GameCore.ScoreBoard#readScore()
         */
        scoreTable = new JTable(0,0);
        readScore();
        /** Ustawienie maksymalnych wymiarow tabeli, a takze koloru tla i czcionki */
        scoreTable.setPreferredScrollableViewportSize(new Dimension(800,300));
        scoreTable.setFillsViewportHeight(true);
        scoreTable.setBackground(Color.BLACK);
        scoreTable.setFont(new Font("Arial",Font.PLAIN, 20));
        scoreTable.setForeground(Color.WHITE);
        /** Wprowadzenie domyslnego sortowania
         * pozwala miedzy innymi na zmiane trybu prezentowania wynikow wzgledem daty
         */
        scoreTable.setAutoCreateRowSorter(true);
        /** Zablokowanie mozliwosci edytowania z punktu programu */
        scoreTable.setEnabled(false);

        /** Deklaracja scrollPane, wprowadza suwak, ktory umozliwia gromadzenie wiekszej ilosci danych w tabeli  */
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        add(scrollPane);
    }

    /**
     * Metoda odczytujaca dane z pliku i zapisujaca je do tabeli
     */

    public void readScore()
    {
        try
        {
            /** Otwarcie strumienia odczytujacego i wybranie pliku z wynikami
             * @see GameWindow#finishTheGame()
             */
            BufferedReader scoreRead = new BufferedReader (new FileReader("score.txt"));

            DefaultTableModel model = (DefaultTableModel)scoreTable.getModel();
            /** Zapisanie lini do tablicy */
            Object [] tableLines = scoreRead.lines().toArray();
            /** Ustawienie kolumn tabeli */
            model.setColumnIdentifiers(columnNames);

            if(scoreTable.getRowCount() != 0)
            {
                /** Jezeli tablica nie jest pusta, dodany do tablicy zostanie tylko ostatni wiersz w pliku tekstowym
                 *  @see GameWindow#finishTheGame()
                 */
                String line = tableLines[tableLines.length - 1].toString().trim();
                /** Podzial pobranej lini wzgledem znaku "/" */
                String[] dataRow = line.split("/");
                /** Dodanie wiersza do tabeli */
                model.addRow(dataRow);
            }
            else
            {
                /** Jezeli tablica jest pusta, dodany do tablicy zostana wszystkie wiersze w pliku tekstowym
                 *  @see ScoreBoard#ScoreBoard()
                 */
                for(int i = 0; i < tableLines.length; i++)
                {
                    /** Do <code>line</code> zostana przypisane kolejne wiersze w pliku tekstowym
                     * Po kazdym wieszu nastepuje dodanie do tabeli
                     */
                    String line = tableLines[i].toString().trim();
                    String[] dataRow = line.split("/");
                    model.addRow(dataRow);
                }
            }
            /** Zamkniecie strumienia*/
            scoreRead.close();
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Metoda rysujaca obraz tla, oraz przycisk powrotu u dolu panelu
     */

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgMenu, 0, 0, 1024, 768, null);
        g.setFont(new Font("Arial",Font.BOLD, 60));
        g.setColor(Color.WHITE);
        /** Narysowanie lini obszaru przycisku powrotu */
        g.drawLine(0,658,1024,658);
        g.drawString("BACK", 420,718);
    }
}
