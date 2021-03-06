package voicePackage;

//import com.sun.media.sound.WaveFileWriter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import static javax.sound.sampled.AudioSystem.getAudioFileTypes;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Shwetha
 */
public class Soundcapture extends JPanel implements ActionListener {

    static JFrame f;
    final int bufSize = 16384;

    public Capture capture = new Capture();

    public Playback playback = new Playback();

    AudioInputStream audioInputStream;

    public JButton playB, captB;

    JTextField textField;

    String errStr;

    double duration, seconds;

    File file;

    public Soundcapture() {
        setLayout(new BorderLayout());
        EmptyBorder eb = new EmptyBorder(5, 5, 5, 5);
        SoftBevelBorder sbb = new SoftBevelBorder(SoftBevelBorder.LOWERED);
        setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));

        JPanel p2 = new JPanel();
        p2.setBorder(sbb);
        p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new EmptyBorder(10, 0, 5, 0));

        /*playB = new JButton("Play");
         playB.setBackground(new java.awt.Color(255, 255, 255));
         playB.setForeground(new java.awt.Color(0,0,0));
         playB.setIcon(new javax.swing.ImageIcon("C:\\2BV12CS106\\TabbedPaneProject\\src\\Pics\\playbutton.jpg"));
         playB.setEnabled(false);
         captB = new JButton("Record");
         captB.setBackground(new java.awt.Color(255, 255, 255));
         captB.setForeground(new java.awt.Color(0,0,0));
         captB.setIcon(new javax.swing.ImageIcon("C:\\2BV12CS106\\TabbedPaneProject\\src\\Pics\\recordbutton.jpg"));
    
         buttonsPanel.add(playB);
         buttonsPanel.add(captB);*/
        playB = addButton("Play", buttonsPanel, false);
        captB = addButton("Record", buttonsPanel, true);
        playB.setIcon(new ImageIcon("/home/sush/Documents/TabbedPaneProject/src/Pics/playbutton.jpg"));
        captB.setIcon(new ImageIcon("/home/sush/Documents/TabbedPaneProject/src/Pics/recordbutton.jpg"));

        p2.add(buttonsPanel);
        buttonsPanel.setBackground(java.awt.Color.white);

        p1.add(p2);
        add(p1);
    }

    public void open() {
    }

    public void close() {
        if (playback.thread != null) {
            playB.doClick(0);
        }
        if (capture.thread != null) {
            captB.doClick(0);
        }
    }

    private JButton addButton(String name, JPanel p, boolean state) {
        JButton b = new JButton(name);
        b.addActionListener(this);
        b.setEnabled(state);
        p.add(b);
        return b;
    }

    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(playB)) {
            if (playB.getText().startsWith("Play")) {
                playback.start();
                captB.setEnabled(false);
                playB.setText("Stop");
                playB.setIcon(new ImageIcon("/home/sush/Documents/TabbedPaneProject/src/Pics/stopbutton.jpg"));
            } else {
                playback.stop();
                captB.setEnabled(true);
                playB.setText("Play");
                playB.setIcon(new ImageIcon("/home/sush/Documents/TabbedPaneProject/src/Pics/playbutton.jpg"));
            }
        } else if (obj.equals(captB)) {
            if (captB.getText().startsWith("Record")) {
                capture.start();
                playB.setEnabled(false);
                captB.setText("Stop");
                captB.setIcon(new ImageIcon("/home/sush/Documents/TabbedPaneProject/src/Pics/stopbutton.jpg"));
            } else {
                capture.stop();

                playB.setEnabled(true);
                playB.setIcon(new ImageIcon("/home/sush/Documents/TabbedPaneProject/src/Pics/playbutton.jpg"));
            }

        }

    }

    /**
     * Write data to the OutputChannel.
     */
    public class Playback implements Runnable {

        SourceDataLine line;

        Thread thread;

        public void start() {
            errStr = null;
            thread = new Thread(this);
            thread.setName("Playback");
            thread.start();
        }

        public void stop() {
            thread = null;
        }

        private void shutDown(String message) {
            if ((errStr = message) != null) {
                System.err.println(errStr);
            }
            if (thread != null) {
                thread = null;
                captB.setEnabled(true);
                playB.setText("Play");
            }
        }

        public void run() {

      // make sure we have something to play
            if (audioInputStream == null) {
                shutDown("No loaded audio to play back");
                return;
            }
            // reset to the beginnning of the stream
            try {
                audioInputStream.reset();
            } catch (Exception e) {
                shutDown("Unable to reset the stream\n" + e);
                return;
            }

      // get an AudioInputStream of the desired format for playback
            AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
            float rate = 44100.0f;
            int channels = 2;
            int frameSize = 4;
            int sampleSize = 16;
            boolean bigEndian = true;

            AudioFormat format = new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8)
                    * channels, rate, bigEndian);

            AudioInputStream playbackInputStream = AudioSystem.getAudioInputStream(format,
                    audioInputStream);

            if (playbackInputStream == null) {
                shutDown("Unable to convert stream of format " + audioInputStream + " to format " + format);
                return;
            }

      // define the required attributes for our line,
            // and make sure a compatible line is supported.
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                shutDown("Line matching " + info + " not supported.");
                return;
            }

      // get and open the source data line for playback.
            try {
                line = (SourceDataLine) AudioSystem.getLine(info);
                line.open(format, bufSize);
            } catch (LineUnavailableException ex) {
                shutDown("Unable to open the line: " + ex);
                return;
            }

      // play back the captured audio data
            int frameSizeInBytes = format.getFrameSize();
            int bufferLengthInFrames = line.getBufferSize() / 8;
            int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
            byte[] data = new byte[bufferLengthInBytes];
            int numBytesRead = 0;

            // start the source data line
            line.start();

            while (thread != null) {
                try {
                    if ((numBytesRead = playbackInputStream.read(data)) == -1) {
                        break;
                    }
                    int numBytesRemaining = numBytesRead;
                    while (numBytesRemaining > 0) {
                        numBytesRemaining -= line.write(data, 0, numBytesRemaining);
                    }
                } catch (Exception e) {
                    shutDown("Error during playback: " + e);
                    break;
                }
            }
      // we reached the end of the stream.
            // let the data play out, then
            // stop and close the line.
            if (thread != null) {
                line.drain();
            }
            line.stop();
            line.close();
            line = null;
            shutDown(null);
        }
    } // End class Playback

    /**
     * Reads data from the input channel and writes to the output stream
     */
    class Capture implements Runnable {

        TargetDataLine line;

        Thread thread;
        private OutputStream outputStream;

        public void start() {

            errStr = null;
            thread = new Thread(this);
            thread.setName("Capture");
            thread.start();
        }

        public void stop() {
            thread = null;

        }

        private void shutDown(String message) {
            if ((errStr = message) != null && thread != null) {
                thread = null;
                playB.setEnabled(true);
                captB.setText("Record");
                System.err.println(errStr);
            }
        }

        @Override
        public void run() {

            duration = 0;
            audioInputStream = null;
            int z = 1;
            String s = System.getProperty("user.home");

      // define the required attributes for our line,
            // and make sure a compatible line is supported.
            AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
            float rate = 44100.0f;
            int channels = 2;
            int frameSize = 4;
            int sampleSize = 16;
            boolean bigEndian = true;

            AudioFormat format = new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8)
                    * channels, rate, bigEndian);

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                shutDown("Line matching " + info + " not supported.");
                return;
            }

      // get and open the target data line for capture.
            try {

                line = (TargetDataLine) AudioSystem.getLine(info);
                line.open(format, line.getBufferSize());
            } catch (LineUnavailableException ex) {
                shutDown("Unable to open the line: " + ex);
                return;
            } catch (SecurityException ex) {
                shutDown(ex.toString());
                //JavaSound.showInfoDialog();
                return;
            } catch (Exception ex) {
                shutDown(ex.toString());
                return;
            }

      // play back the captured audio data
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int frameSizeInBytes = format.getFrameSize();
            int bufferLengthInFrames = line.getBufferSize() / 8;
            int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
            byte[] data = new byte[bufferLengthInBytes];
            int numBytesRead;

            line.start();

            while (thread != null) {
                if ((numBytesRead = line.read(data, 0, bufferLengthInBytes)) == -1) {
                    break;
                }

                out.write(data, 0, numBytesRead);
            }

      // we reached the end of the stream.
            // stop and close the line.
            line.stop();
            line.close();
            line = null;

            // stop and close the output stream
            try {
                out.flush();
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

      // load bytes into the audio input stream for playback
            byte audioBytes[] = out.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
            audioInputStream = new AudioInputStream(bais, format, audioBytes.length / frameSizeInBytes);

            long milliseconds = (long) ((audioInputStream.getFrameLength() * 1000) / format
                    .getFrameRate());
            duration = milliseconds / 1000.0;

            try {
                audioInputStream.reset();
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
            try {

                OutputStream fos;

                //System.out.println(s);
                fos = new FileOutputStream(new File(s + "//Music//Voice_Record" + z + ".WAV"));
                out.writeTo(fos);
                while (thread != null) {
                    if ((numBytesRead = line.read(data, 0, bufferLengthInBytes)) == -1) {
                        break;
                    }
                    out.write(data, 0, numBytesRead);
                }
            } catch (IOException ioe) {
    // Handle exception here

            }
            try {
                AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(s + "//Music//Voice_Record" + z + ".mp3"));
                z++;
            } catch (IOException e) {
                //System.out.println("ufffff");
            }
        }
    } // End class Capture

    public static void main(String s[]) {
        Soundcapture ssc = new Soundcapture();
        ssc.open();
        f = new JFrame("Voice Recorder");
        // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add("Center", ssc);
        f.pack();
        f.setSize(450, 450);
        f.setBackground(Color.white);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = 360;
        int h = 170;
   // f.setLocation(screenSize.width / 2 - w / 2, screenSize.height / 2 - h / 2);
        // f.setSize(w, h);
        f.show();
    }

}
