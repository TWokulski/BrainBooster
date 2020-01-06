package GameCore;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 *  Klasa z metoda main.
 *  Rola uruchomieniowa.
 *
 * @author Tomasz Gruzdzis
 */

public class BB_Main
{
    public static void main(String[] args)
    {
        new GameWindow().setVisible(true);
        playMusic();
    }

    /**
     *  Metoda odpowiedzialna za muzyke w tle.
     */

    public static synchronized void playMusic()
    {
        URL musicFile = BB_Main.class.getResource("/Resources/music1.wav");
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip music = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(musicFile);
                    music.open(inputStream);
                    music.loop(100);
                    FloatControl gainControl =
                            (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(-30.0f);
                    music.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
}
