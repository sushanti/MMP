package mp3Package;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
//import org.jaudiotagger.audio.AudioFile;
//import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class MainClass 
{
    
    FileInputStream FIS,fis;
    BufferedInputStream BIS,bis;
    public Player player;
    public long pauseLocation;
    public String filePath;
    public long songTotalLength;
    public long songLengthFinal;
    public int flag;
    public long tempPause;
    
    FileOutputStream fos;
    BufferedOutputStream bos;
    static int position;
    MP3PlayerGUI obj;
    
    
    
    public void Stop()
    {
       if (player!=null)
       {
         player.close();
         pauseLocation=0;
         songTotalLength=0;
         flag=0;
         
       }
    
    }
    
    public void Pause()
    {
        flag=0;
       if (player!=null)
       {
               
           try {
               
               songTotalLength=songLengthFinal;
               
               pauseLocation = FIS.available();
               tempPause = pauseLocation;
               
               
               
                   
               
               player.close();
           } catch (IOException ex) {
               if(pauseLocation==0)
               pauseLocation=songTotalLength;
               
               else
               pauseLocation=tempPause;
           }
       }
    
    }
    
    public void Play(String path) throws CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, InterruptedException
    {
        try {
                FIS = new FileInputStream(path);
                BIS = new BufferedInputStream(FIS);
                player = new Player (BIS);
                songTotalLength = FIS.available();
                songLengthFinal=songTotalLength;
                filePath = path;
                
                
                
                
                Thread th = new Thread()
                {
                    public void run()
                    {
                        while(!player.isComplete())
                        {
                            position = player.getPosition();
                            position/=1000;
                            
                            



                            try
                            {

                             Thread.sleep(1000);


                            }
                            catch( Exception ee )
                            {
                             ee.printStackTrace();
                            }
                        }
                    }
              };
                        th.start();
                
                
                
                

                
            } catch (FileNotFoundException | JavaLayerException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       new Thread()
       {
         @Override
         public void run()
         {
             try {
                 player.play();
                 
                 
                 if (player.isComplete() && MP3PlayerGUI.count==1)
                 {
                   
                   Play(filePath);
                   

                 }
             } 
             catch (JavaLayerException ex) {
                 Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                 JOptionPane.showMessageDialog(null,"Invalid file format");
             } catch (CannotReadException ex) {
                 Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
             } catch (TagException ex) {
                 Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
             } catch (ReadOnlyFileException ex) {
                 Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
             } catch (InvalidAudioFrameException ex) {
                 Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
             } catch (InterruptedException ex) {
                 Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
       }.start();
    
    
    }
    
   public void Resume()
    {
        try {
            FIS = new FileInputStream(filePath);
            BIS = new BufferedInputStream(FIS);
            player = new Player(BIS);
            
            
            
            FIS.skip((songTotalLength - pauseLocation));
            
             
            
            
            
        } catch (FileNotFoundException | JavaLayerException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       new Thread()
       {
         @Override
         public void run()
         {
             try {
                 player.play();
             } catch (JavaLayerException ex) {
                 Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
       }.start();
    
    }
}
