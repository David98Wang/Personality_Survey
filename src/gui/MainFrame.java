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

import java.awt.Dimension;
import java.util.Stack;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * The main container for the menu, survey, and result
 * 
 * @author Jack Li
 *
 */
public class MainFrame extends JFrame {

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
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Changes the active content of this window to the specified content
	 * 
	 * @param content
	 */
	public void addContent(JComponent content) {
		if (!components.empty()) {
			components.peek().setVisible(false);
			this.revalidate();
		}
		this.add(content);
		components.add(content);
		Dimension temp = components.peek().getSize();
		temp.width += 20;
		temp.height += 16;
		this.setMinimumSize(temp);
		this.setSize(temp);
		this.validate();
		System.out.println("Added " + content);
	}

	/**
	 * Removes the specified content
	 * 
	 * @param content
	 */
	public void removeContent(JComponent content) {
		this.remove(content);
		components.remove(content);
		if (!components.empty()) {
			components.peek().setVisible(true);
			this.setMinimumSize(components.peek().getSize());
			this.setSize(getMinimumSize());
			System.out.println("Under: "+components.peek());
		}
		this.validate();
		System.out.println("Removed " + content);
	}
}
