import gui.MainFrame;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import common.Result;

/**
 * File: Launcher.java
 *
 * Project : Personality_Survey
 * Package : 
 * 
 * Created : Jan 17, 2015 10:06:21 AM
 * Created by: Jack Li
 */

/**
 * The launcher for this program
 * @author Jack Li
 *
 */
public class Launcher {
	/**
	 * A logger object to log any messages this classes has
	 */
	private static Logger logger = Logger.getLogger(Launcher.class.getName());

	public static void main(String[] args) {
		if (System.getProperty("os.name").toLowerCase().contains("windows"))
			try {

				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
				logger.log(Level.INFO,"You are not using windows, reverting to default look and feel");
			}
		
		ToolTipManager.sharedInstance().setInitialDelay(500);
		new MainFrame().setVisible(true);
	}

}
