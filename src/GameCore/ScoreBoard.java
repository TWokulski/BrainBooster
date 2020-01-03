package GameCore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *  Obiekt <code>ScoreBoard</code> reprezentuje panel z tablicą wyników.
 *  W panelu znajdziemy tablice z wynikami odczytanymi z pliku tekstowego oraz przycisk powrotu do Menu
 *
 * @author Tomasz Gruzdzis
 */

public class ScoreBoard extends JPanel
{
    /** Zmienna przechowująca obrazek tła */
    private static Image bgMenu = new ImageIcon("bgMenu.gif").getImage();
    /** Deklaracja Tabeli, przeznaczonej do prezentowania wyników */
    private JTable scoreTable;
    /** Zadeklarowanie kolum jakie będą używane w tabeli */
    private String[] columnNames = {"Date", "Player Name", "Time", "Wrong answers"};

    ScoreBoard()
    {
        this.setLayout( new FlowLayout() );
        /**
         * Stworzenie obiektu tabeli, nie posiadającej kolumn i wiersz.
         * Kolumny i wiersze dodane zostają w readScore()
         * @see GameCore.ScoreBoard#readScore()
         */
        scoreTable = new JTable(0,0);
        readScore();
        /** Ustawienie maksymalnych wymiarów tabeli, a także koloru tła i czcionki */
        scoreTable.setPreferredScrollableViewportSize(new Dimension(800,300));
        scoreTable.setFillsViewportHeight(true);
        scoreTable.setBackground(Color.BLACK);
        scoreTable.setFont(new Font("Arial",Font.PLAIN, 20));
        scoreTable.setForeground(Color.WHITE);
        /** Wprowadzenie domyslnego sortowania
         * pozwala miedzy innymi na zmiane trybu prezentowania wyników względem daty
         */
        scoreTable.setAutoCreateRowSorter(true);
        /** Zablokowanie możliwości edytowania z punktu programu */
        scoreTable.setEnabled(false);

        /** Deklaracja scrollPane, wprowadza suwak, który umozliwia gromadzenie wiekszej ilości danych w tabeli  */
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        add(scrollPane);
    }

    public void readScore()
    {
        /** Metoda odczytująca dane z pliku i zapisująca je do tabeli */
        try
        {
            /** Otwarcie strumienia odczytującego i wybranie pliku z wynikami
             * @see GameWindow#finishTheGame()
             * */
            BufferedReader scoreRead = new BufferedReader (new FileReader("score.txt"));

            DefaultTableModel model = (DefaultTableModel)scoreTable.getModel();
            /** Zapisanie lini do tablicy */
            Object [] tableLines = scoreRead.lines().toArray();
            /** Ustawienie kolumn tabeli */
            model.setColumnIdentifiers(columnNames);

            if(scoreTable.getRowCount() != 0)
            {
                /** Jeżeli tablica nie jest pusta, dodany do tablicy zostanie tylko ostatni wiersz w pliku tekstowym
                 *  @see GameWindow#finishTheGame()
                 */
                String line = tableLines[tableLines.length - 1].toString().trim();
                /** Podział pobranej lini względem znaku "/" */
                String[] dataRow = line.split("/");
                /** Dodanie wiersza do tabeli */
                model.addRow(dataRow);
            }
            else
            {
                /** Jeżeli tablica jest pusta, dodany do tablicy zostaną wszystkie wiersze w pliku tekstowym
                 *  @see ScoreBoard#ScoreBoard()
                 */
                for(int i = 0; i < tableLines.length; i++)
                {
                    /** Do <code>line</code> zostaną przypisane kolejne wiersze w pliku tekstowym
                     * Po każdym wieszu nastepuje dodanie do tabeli
                     */
                    String line = tableLines[i].toString().trim();
                    String[] dataRow = line.split("/");
                    model.addRow(dataRow);
                }
            }
            /** Zamknięcie strumienia*/
            scoreRead.close();
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        /** Metoda rysująca obraz tła, oraz przycisk powrotu u dołu panelu */
        super.paintComponent(g);
        g.drawImage(bgMenu, 0, 0, 1024, 768, null);
        g.setFont(new Font("Arial",Font.BOLD, 60));
        g.setColor(Color.WHITE);
        /** Narysowanie lini obszaru przycisku powrotu */
        g.drawLine(0,658,1024,658);
        g.drawString("BACK", 420,718);
    }
}
