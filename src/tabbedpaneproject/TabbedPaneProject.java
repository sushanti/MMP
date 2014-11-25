/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tabbedpaneproject;

import javax.swing.UIManager;
import static tabbedpaneproject.TabbedPaneFrame.arguments;

/**
 *
 * @author Sonia Kulkarni
 */
public class TabbedPaneProject {

    /**
     * @param args the command line arguments
     */
     //static String[] arguments;
    public static void main(String[] args) {
        
        arguments = new String[args.length];
        
        for(int i=0;i<args.length;i++)
        {
            arguments[i]=args[i];
            
        }
        
        TabbedPaneFrame ob = new TabbedPaneFrame(null);
        ob.setVisible(true);
        try{
                   //UIManager.setLookAndFeel("com.easynth.lookandfeel.EaSynthLookAndFeel");
                    //  UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
                    UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
                   // UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
                }catch(Exception e)
                {
                    System.out.println("CustomGUIException");
                }
     
    }
    
}
