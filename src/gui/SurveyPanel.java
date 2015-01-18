/**
 * File: SurveyPanel.java
 *
 * Project : Personality_Survey
 * Package : gui
 * 
 * Created : Jan 17, 2015 10:10:02 AM
 * Created by: Jack Li
 */
package gui;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import common.Survey;
import common.Util;

/**
 * A classed used to display the survey
 * 
 * @author Jack Li
 *
 */
public class SurveyPanel extends JPanel {

	/**
	 * The parent frame
	 */
	private MainFrame parent;
	/**
	 * The active survey of this panel
	 */
	private Survey survey;

	/**
	 * Creates a panel for a given survey
	 * 
	 * @param parent
	 *            the parent frame for this panel
	 * @param survey
	 *            the input survey
	 */
	public SurveyPanel(final MainFrame parent, Survey survey) {
		if (survey==null)
			throw new IllegalArgumentException("Invalid survey!");
		this.parent = parent;
		this.survey = survey;
		initialize();
		this.setVisible(true);
	}

	private void initialize() {
		
	}

}
