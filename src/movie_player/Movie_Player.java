/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package player;

import static com.sun.javafx.fxml.expression.Expression.add;
import static com.sun.javafx.fxml.expression.Expression.add;
import static com.sun.javafx.fxml.expression.Expression.add;
import static com.sun.javafx.fxml.expression.Expression.add;
import static com.sun.javafx.fxml.expression.Expression.add;
import static com.sun.javafx.fxml.expression.Expression.add;
import java.awt.BorderLayout;
import java.awt.Component;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 *
 * @author Sonia Kulkarni
 */
public class Movie_Player extends Application {

    static URL Url;
    
    /**
     *
     * @param args
     */
    
    public static void method1(URL mediaUrl,String[] args){
        
        try
        {
        Url=mediaUrl;
        launch(args);
        }catch(java.lang.IllegalStateException e)
        {
            JOptionPane.showMessageDialog(null,"Video can be played only once");
        }
       
    }
    
    public static void main(String[] args)
    {   
        
        launch(args);
        
    }
    
    @Override
    public void start(final Stage stage) throws Exception {
        
        /*URL mediaUrl=null;
        FileFilter filter = new FileNameExtensionFilter ("mp4","MP4");
        
        JFileChooser chooser = new JFileChooser("C:\\2BV12CS106\\Movie_Player1\\trailers");
        chooser.addChoosableFileFilter(filter);
        
        int returnval = chooser.showOpenDialog(null);
        try
        {
        if (returnval== JFileChooser.APPROVE_OPTION)
        {
            String str=chooser.getSelectedFile()+"";
                boolean res = str.matches("(.*).mp4") || str.matches("(.*).MP4");
                System.out.println(res+"");
                if (res==false)
                {
                javax.swing.JOptionPane.showMessageDialog(null,"Choose .mp4 file");
                }
                else{
                System.out.println("");
                 mediaUrl = chooser.getSelectedFile().toURI().toURL();
                }
                
                        
        }
        } catch (MalformedURLException ex) {
 
        System.out.println(ex);
 
        }catch(Exception e)
        {
            System.out.println(e);
        }*/
        stage.setTitle("Video Player");
        Group root = new Group();
        
        
        
        /*JFileChooser fileChooser = new JFileChooser();
 
        int returnval= fileChooser.showOpenDialog(null);
 
        URL mediaUrl=null;
 
        try {
 
            if (returnval== JFileChooser.APPROVE_OPTION)
        {
                
                
                String str=fileChooser.getSelectedFile()+"";
                boolean res = str.matches("(.*).mp4") || str.matches("(.*).MP4");
                System.out.println(res+"");
                if (res==false)
                {
                javax.swing.JOptionPane.showMessageDialog(null,"Choose .mp4 file");
                }
                else{
                System.out.println("");
                 mediaUrl = fileChooser.getSelectedFile().toURI().toURL();
                }
                
                        
        }
            
 
        } catch (MalformedURLException ex) {
 
        System.out.println(ex);
 
        }*/
        
       // File file = new File("C:/2BV12CS106/MoviePlayer/trailers/titli.MP4");
        Media media;
        media=new Media(Url.toString());
       // media = new Media(file.toURL().toString());
        final MediaPlayer player=new MediaPlayer(media);
        MediaView view=new MediaView(player);
        
        root.getChildren().add(view);        
       // System.out.print(media.getWidth());
        
        final Timeline slideIn =new Timeline();
        final Timeline slideOut=new Timeline();
        
        root.setOnMouseEntered(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                slideIn.play();
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        
        });
        root.setOnMouseExited(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                slideOut.play();
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        
        });
        
        final VBox vbox=new VBox();
        final Slider slider=new Slider();
        vbox.getChildren().add(slider);
        
        final HBox hbox=new HBox();
        final int bands=player.getAudioSpectrumNumBands();
        final Rectangle[] rects=new Rectangle[bands];
        
        for(int i=0;i<rects.length;i++)
        {
            rects[i]=new Rectangle();
            rects[i].setFill(Color.GREENYELLOW);           
            hbox.getChildren().add(rects[i]);
            
        }
        vbox.getChildren().add(hbox);
        
        root.getChildren().add(vbox);
        

        
        Scene scene=new Scene(root,400,400,Color.BLACK);
        stage.setScene(scene);
        stage.show();
        
        try {
 
Player mediaPlayer=Manager.createRealizedPlayer(new MediaLocator(Url));
 
Component video=mediaPlayer.getVisualComponent();
 
Component control=mediaPlayer.getControlPanelComponent();
 
if (video!=null) {
 
add(video, BorderLayout.CENTER);          // place the video component in the panel
 
}
 
//add(control, BorderLayout.SOUTH);            // place the control in  panel
 
mediaPlayer.start();
 
} catch (Exception e) {
 
}
        
        player.play();
        
        
        
        player.setOnReady(new Runnable(){
            @Override
            public void run()
            {
                int w =player.getMedia().getWidth();
                int h =player.getMedia().getHeight();
                
                hbox.setMinWidth(w);
                int bandWidth=w/rects.length;
                
                for(Rectangle r:rects){
                    r.setWidth(bandWidth);
                    r.setHeight(3);
                    
                }
                
                stage.setMinWidth(w);
                stage.setMinHeight(h);
                
                vbox.setMinSize(w,100);
                vbox.setTranslateY(100);
                
                slider.setMin(0.0);
                slider.setValue(0.0);
                slider.setMax(player.getTotalDuration().toSeconds());
                
                /*slideIn.getKeyFrames().addAll(
                        new KeyFrame(new Duration(0),
                                new KeyValue(vbox.translateYProperty(),h),
                                new KeyValue(vbox.opacityProperty(),0.0)
                                ),
                        new KeyFrame(new Duration(300),
                                new KeyValue(vbox.translateYProperty(),h-100),
                                new KeyValue(vbox.opacityProperty(),0.9)                        
                        )
                );
                
                slideOut.getKeyFrames().addAll(
                        new KeyFrame(new Duration(0),
                                new KeyValue(vbox.translateYProperty(),h-100),
                                new KeyValue(vbox.opacityProperty(),0.9)
                                ),
                        new KeyFrame(new Duration(300),
                                new KeyValue(vbox.translateYProperty(),h),
                                new KeyValue(vbox.opacityProperty(),0.0)                        
                        )
                );*/
                
            }
        });
        
        
        player.currentTimeProperty().addListener(new ChangeListener<Duration>(){

            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration duration, Duration current) {
               
                    slider.setValue(current.toSeconds());
                    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        slider.setOnMouseClicked(new EventHandler<MouseEvent> (){

           // @Override
            public void handle(MouseEvent event) {
                player.seek(Duration.seconds(slider.getValue()));
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        
        });
        
        player.setAudioSpectrumListener(new AudioSpectrumListener(){

           // @Override
            public void spectrumDataUpdate(double d, double d1, float[] mags, float[] floats1) {
                
                for(int i=0;i<rects.length;i++)
                {
                    double h=mags[i]+60;
                    if(h>2)
                    {
                        rects[i].setHeight(h);
                    }
                }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        
        });
        
        
        
        
        
     
        
        
            
       
        
        //controller
        
        
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*class MediaControl extends BorderPane {
    private MediaPlayer mp;
    private MediaView mediaView;
    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private Slider timeSlider;
    private Label playTime;
    private Slider volumeSlider;
    private HBox mediaBar;

    public MediaControl(final MediaPlayer mp) {
        this.mp = mp;
        setStyle("-fx-background-color: #bfc2c7;");
        mediaView = new MediaView(mp);
        Pane mvPane = new Pane() {                };
        mvPane.getChildren().add(mediaView);
        mvPane.setStyle("-fx-background-color: black;"); 
        setCenter(mvPane);
         mediaBar = new HBox();
        mediaBar.setAlignment(Pos.CENTER);
        mediaBar.setPadding(new Insets(5, 10, 5, 10));
        BorderPane.setAlignment(mediaBar, Pos.CENTER);
 
        final Button playButton  = new Button(">");
        
        playButton.setOnAction(new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e) {
        Status status = mp.getStatus();
 
        if (status == Status.UNKNOWN  || status == Status.HALTED)
        {
           // don't do anything in these states
           return;
        }
 
          if ( status == Status.PAUSED
             || status == Status.READY
             || status == Status.STOPPED)
          {
             // rewind the movie if we're sitting at the end
             if (atEndOfMedia) {
                mp.seek(mp.getStartTime());
                atEndOfMedia = false;
             }
             mp.play();
             } else {
               mp.pause();
             }
         }
   });
        
        mp.currentTimeProperty().addListener(new InvalidationListener() 
        {
            @Override
            public void invalidated(Observable ov) {
                updateValues();
            }

        });
 
        mp.setOnPlaying(new Runnable() {
            public void run() {
                if (stopRequested) {
                    mp.pause();
                    stopRequested = false;
                } else {
                    playButton.setText("||");
                }
            }
        });
 
        mp.setOnPaused(new Runnable() {
            public void run() {
                System.out.println("onPaused");
                playButton.setText(">");
            }
        });
 
        mp.setOnReady(new Runnable() {
            public void run() {
                duration = mp.getMedia().getDuration();
                updateValues();
            }
        });
 
        mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        mp.setOnEndOfMedia(new Runnable() {
            public void run() {
                if (!repeat) {
                    playButton.setText(">");
                    stopRequested = true;
                    atEndOfMedia = true;
                }
            }
       });
        
        mediaBar.getChildren().add(playButton);
        
        // Add spacer
Label spacer = new Label("   ");
mediaBar.getChildren().add(spacer);
 
// Add Time label
Label timeLabel = new Label("Time: ");
mediaBar.getChildren().add(timeLabel);
 
// Add time slider
timeSlider = new Slider();
HBox.setHgrow(timeSlider,Priority.ALWAYS);
timeSlider.setMinWidth(50);
timeSlider.setMaxWidth(Double.MAX_VALUE);

timeSlider.valueProperty().addListener(new InvalidationListener() {
    public void invalidated(Observable ov) {
       if (timeSlider.isValueChanging()) {
       // multiply duration by percentage calculated by slider position
          mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
       }
    }
});

mediaBar.getChildren().add(timeSlider);

// Add Play label
playTime = new Label();
playTime.setPrefWidth(130);
playTime.setMinWidth(50);
mediaBar.getChildren().add(playTime);
 
// Add the volume label
Label volumeLabel = new Label("Vol: ");
mediaBar.getChildren().add(volumeLabel);
 
// Add Volume slider
volumeSlider = new Slider();        
volumeSlider.setPrefWidth(70);
volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
volumeSlider.setMinWidth(30);

volumeSlider.valueProperty().addListener(new InvalidationListener() {
    public void invalidated(Observable ov) {
       if (volumeSlider.isValueChanging()) {
           mp.setVolume(volumeSlider.getValue() / 100.0);
       }
    }
});
 
mediaBar.getChildren().add(volumeSlider);
        
        setBottom(mediaBar); 

     }
    
    protected void updateValues() {
  if (playTime != null && timeSlider != null && volumeSlider != null) {
     Platform.runLater(new Runnable() {
        public void run() {
          Duration currentTime = mp.getCurrentTime();
          playTime.setText(formatTime(currentTime, duration));
          timeSlider.setDisable(duration.isUnknown());
          if (!timeSlider.isDisabled() 
            && duration.greaterThan(Duration.ZERO) 
            && !timeSlider.isValueChanging()) {
              timeSlider.setValue(currentTime.divide(duration).toMillis()
                  * 100.0);
          }
          if (!volumeSlider.isValueChanging()) {
            volumeSlider.setValue((int)Math.round(mp.getVolume() 
                  * 100));
          }
        }
     });
  }
}
    private  String formatTime(Duration elapsed, Duration duration) {
   int intElapsed = (int)Math.floor(elapsed.toSeconds());
   int elapsedHours = intElapsed / (60 * 60);
   if (elapsedHours > 0) {
       intElapsed -= elapsedHours * 60 * 60;
   }
   int elapsedMinutes = intElapsed / 60;
   int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 
                           - elapsedMinutes * 60;
 
   if (duration.greaterThan(Duration.ZERO)) {
      int intDuration = (int)Math.floor(duration.toSeconds());
      int durationHours = intDuration / (60 * 60);
      if (durationHours > 0) {
         intDuration -= durationHours * 60 * 60;
      }
      int durationMinutes = intDuration / 60;
      int durationSeconds = intDuration - durationHours * 60 * 60 - 
          durationMinutes * 60;
      if (durationHours > 0) {
         return String.format("%d:%02d:%02d/%d:%02d:%02d", 
            elapsedHours, elapsedMinutes, elapsedSeconds,
            durationHours, durationMinutes, durationSeconds);
      } else {
          return String.format("%02d:%02d/%02d:%02d",
            elapsedMinutes, elapsedSeconds,durationMinutes, 
                durationSeconds);
      }
      } else {
          if (elapsedHours > 0) {
             return String.format("%d:%02d:%02d", elapsedHours, 
                    elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d",elapsedMinutes, 
                    elapsedSeconds);
            }
        }
    }
}*/
    
}
