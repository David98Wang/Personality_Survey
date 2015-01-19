/**
 * File: ChoicePanel.java
 *
 * Project : Personality_Survey
 * Package : gui
 * 
 * Created : Jan 18, 2015 11:48:34 AM
 * Created by: Jack Li
 */
package gui;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import common.Choice;
import common.Question;
import common.Result;
import common.Survey;
import common.Util;

/**
 * @author Jack Li
 *
 */
public class ChoicePanel extends JPanel {
	/**
	 * A logger object to log any messages this classes has
	 */
	private static Logger logger = Logger.getLogger(ChoicePanel.class.getName());

	/**
	 * A reference to the question to be displayed
	 */
	private final Question question;
	/**
	 * A reference to the survey this question belongs to
	 */
	private final Survey survey;
	private ButtonGroup btnGp;
	private ArrayList<RadioWrapper> buttons;
	public ChoicePanel(Survey s, Question q) {
		question = q;
		this.survey = s;
		buttons = new ArrayList<>();
		initialize();
	}

	/**
	 * Initializes the gui
	 */
	private void initialize() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		btnGp = new ButtonGroup();
		//populate this panel
		for (Choice c : question.getChoices()) {
			RadioWrapper cur = new RadioWrapper(c);
			cur.setFont(Util.CHOICE_FONT);
			cur.setFocusable(false);
			cur.setToolTipText("Click the to select this option");
			btnGp.add(cur);
			buttons.add(cur);
			this.add(cur);
			logger.log(Level.FINEST,"Added choice: " + cur);
		}
	}

	/**
	 * @return the selected choice for this panel
	 */
	public Choice getSelectedChoce() {
		for (RadioWrapper r : buttons)
			if (r.isSelected())
				return r.getChoice();
		return null;
	}

	/**
	 * A wrapper class for JRadioButton to be able to associate a choice wth a
	 * button
	 * 
	 * @author Jack Li
	 *
	 */
	private static class RadioWrapper extends JRadioButton {
		private final Choice c;

		public RadioWrapper(Choice c) {
			this.c = c;
			this.setText(c.getText());
		}

		public Choice getChoice() {
			return c;
		}
	}

	/**
	 * @param choice the choice to select for this panel
	 */
	public void select(int choice) {
		buttons.get(choice).setSelected(true);
	}
}
