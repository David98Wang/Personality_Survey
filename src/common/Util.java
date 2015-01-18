/**
 * File: Util.java
 *
 * Project : Personality_Survey
 * Package : common
 * 
 * Created : Jan 16, 2015 4:31:11 PM
 * Created by: Jack Li
 */
package common;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 * A class offering utilities for different parts of this project
 * @author Jack Li
 *
 */
public class Util {
	public static final Font QUESTION_FONT = new Font("Times New Roman", Font.PLAIN, 23);
	public static final Font CHOICE_FONT = new Font("Times New Roman", Font.PLAIN, 20);
	/**
	 * Saves the contents of an input {@link JComponent} as a {@link BufferedImage}
	 * @param src the source JComponent
	 * @return a screenshot of the source component, as its {@link JComponent#paint(Graphics)} method would draw
	 */
	public static Image getImageFromJComponent(JComponent src) {
		//allocate memory for the resulting image
		BufferedImage res = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
		//tell the Component to paint over the image
		src.paint(res.getGraphics());
		return res;
	}
	public static void showError(String msg) {
		JOptionPane.showMessageDialog(null, msg,"Error",JOptionPane.ERROR_MESSAGE);
	}
	public static boolean showConfirm(String msg) {
		return JOptionPane.showConfirmDialog(null, msg, "Confirm", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION;
	}
}
