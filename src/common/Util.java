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

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * A class offering utilities for different parts of this project
 * @author Jack Li
 *
 */
public class Util {
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
}
