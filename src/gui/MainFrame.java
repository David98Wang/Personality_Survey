/**
 * File: MainFrame.java
 *
 * Project : Personality_Survey
 * Package : gui
 * 
 * Created : Jan 17, 2015 10:02:32 AM
 * Created by: Jack Li
 */
package gui;

import java.util.Stack;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * The main container for the menu, survey, and result
 * @author Jack Li
 *
 */
public class MainFrame extends JFrame{
	
	/**
	 * A list of components that has been added to this frame
	 */
	private Stack<JComponent> components;
	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -8026416994513756565L;
	public MainFrame() {
		this.components = new Stack<>();
		this.addContent(new MenuPanel(this));
		this.setTitle("Survey");
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * Changes the active content of this window to the specified content
	 * @param content
	 */
	public void addContent(JComponent content) {
		System.out.println("Added " + content);
		if (!components.empty()) {
			components.peek().setVisible(false);this.revalidate();System.out.println("Hid "+components.peek());
		}
		this.add(content);
		components.add(content);
	}
	
	/**
	 * Removes the specified content
	 * @param content
	 */
	public void removeContent(JComponent content) {
		this.remove(content);
		components.remove(content);
		if (!components.empty())
			components.peek().setVisible(true);
		System.out.println("Removed " + content);
	}
}
