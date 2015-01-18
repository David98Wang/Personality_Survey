import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.MainFrame;

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

	public static void main(String[] args) {
		if (System.getProperty("os.name").toLowerCase().contains("windows"))
			try {

				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
				System.out.println("You are not using windows, reverting to default look and feel");
			}
		new MainFrame().setVisible(true);
	}

}
