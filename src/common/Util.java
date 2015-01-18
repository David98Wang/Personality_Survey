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

import io.Parser;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 * A class offering utilities for different parts of this project
 * 
 * @author Jack Li
 *
 */
public class Util {
	/**
	 * Font for questions
	 */
	public static final Font QUESTION_FONT = new Font("Times New Roman", Font.PLAIN, 23);
	/**
	 * Font for choices
	 */
	public static final Font CHOICE_FONT = new Font("Times New Roman", Font.PLAIN, 20);
	/**
	 * A file filter for text files
	 */
	public static final FileFilter TXT_FILTER = new FileFilter() {
		@Override
		public String getDescription() {
			return "Survey files (.txt)";
		}

		@Override
		public boolean accept(File f) {
			return f.isDirectory() || Parser.fileFilter.accept(f);
		}
	};

	/**
	 * Saves the contents of an input {@link JComponent} as a
	 * {@link BufferedImage}
	 * 
	 * @param src
	 *            the source JComponent
	 * @return a screenshot of the source component, as its
	 *         {@link JComponent#paint(Graphics)} method would draw
	 */
	public static Image getImageFromJComponent(JComponent src) {
		// allocate memory for the resulting image
		BufferedImage res = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
		// tell the Component to paint over the image
		src.paint(res.getGraphics());
		return res;
	}

	public static void showError(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static boolean showConfirm(String msg) {
		return JOptionPane.showConfirmDialog(null, msg, "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	public static File chooseFile(String btnText, FileFilter f) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(f);
		chooser.setVisible(true);
		if (chooser.showDialog(null, btnText) == JFileChooser.APPROVE_OPTION) {
			System.out.println("Got	" + chooser.getSelectedFile().getAbsolutePath());
			return chooser.getSelectedFile();
		}
		System.out.println("Error");
		return null;
	}
	
	/**
	 * Creates a file filter that accepts any file whose name matches the pattern, as well as all directories
	 * @param description the description for this file filter
	 * @param pattern a regex pattern
	 * @return the file filter
	 */
	public static FileFilter createFileFilter(final String description,final String pattern) {
		return new FileFilter() {
			
			@Override
			public String getDescription() {
				return description;
			}
			
			@Override
			public boolean accept(File f) {
				if (f.isDirectory())
					return true;
				return f.getName().toLowerCase().matches(pattern);
			}
		};
	}

	/**
	 * Opens the specified file with the default associated application.
	 * @param file the file to open
	 */
	public static void open(File file) {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			showError("Error opening " + file.getAbsolutePath() + ":\n" + e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * Opens a webpage specified by the uri
	 * @param uri
	 */
	public static void openWebpage(URI uri) {
	    Desktop desktop = Desktop.getDesktop();
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	public static void openWebpage(URL url) {
	    try {
	        openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	}
}
