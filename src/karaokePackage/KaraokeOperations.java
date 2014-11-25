package karaokePackage;


import java.io.File;
import javax.swing.JFileChooser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sush
 */
public class KaraokeOperations
{
    File myfile;
    public boolean OpenFile()
    {
        boolean flag=true;
       JFileChooser chooser = new JFileChooser();
       int returnval=chooser.showOpenDialog(null);
       if (returnval==JFileChooser.APPROVE_OPTION)
        {
                myfile = chooser.getSelectedFile();
                
        }
       
       String str=myfile+"";
       flag = str.matches("(.*).kar") || str.matches("(.*).mid");
       
       
       
       if (flag==true)
       {
         //System.out.println("successfule");
       }
       
       return(flag);
    }
    
    public File sendFile()
    {
       return(myfile);
    
    }
    
    
}
