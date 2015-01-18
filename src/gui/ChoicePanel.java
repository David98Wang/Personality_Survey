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

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import common.Choice;
import common.Question;
import common.Survey;
import common.Util;

import javax.swing.BoxLayout;

/**
 * @author Jack Li
 *
 */
public class ChoicePanel extends JPanel {
	/**
	 * A reference to the question to be displayed
	 */
	private final Question question;
	/**
	 * A reference to the survey this question belongs to
	 */
	private final Survey survey;
	private DefaultListener listener;

	public ChoicePanel(Survey s, Question q) {
		question = q;
		this.survey = s;
		listener = new DefaultListener();
		initialize();
	}

	private void initialize() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		ButtonGroup btnGp = new ButtonGroup();
		for (Choice c : question.getChoices()) {
			RadioWrapper cur = new RadioWrapper(c);
			cur.addActionListener(listener);
			cur.setFont(Util.CHOICE_FONT);
			btnGp.add(cur);
			this.add(cur);
		}
	}

	/**
	 * A listener that adds the poitns of the choices to a survey
	 * 
	 * @author Jack Li
	 *
	 */
	private class DefaultListener implements ActionListener {
		Choice selected = null;

		@Override
		public void actionPerformed(ActionEvent e) {
			RadioWrapper rw = (RadioWrapper) e.getSource();
			selected = rw.getChoice();
		}

	}

	/**
	 * @return the selected choice for this panel
	 */
	public Choice getSelectedChoce() {
		return listener.selected;
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
}
