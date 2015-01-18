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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import common.Question;
import common.Survey;
import common.Util;

/**
 * A classed used to display the survey
 * 
 * @author Jack Li
 *
 */
public class SurveyPanel extends JPanel implements KeyListener {

	/**
	 * The parent frame
	 */
	private MainFrame parent;
	/**
	 * The active survey of this panel
	 */
	private Survey survey;
	private JTextArea textArea;
	private HashMap<Question, ChoicePanel> questions;
	/**
	 * A reference to the question that is currently being displayed
	 */
	private Question displayedQuestion;
	private JButton btnNext;
	private JButton btnPrevious;
	private JButton btnSubmitAnswers;
	private JButton btnBackToMenu;
	private Component horizontalStrut;
	private JSeparator separator;

	/**
	 * Creates a panel for a given survey
	 * 
	 * @param parent
	 *            the parent frame for this panel
	 * @param survey
	 *            the input survey
	 */
	public SurveyPanel(final MainFrame parent, Survey survey) {
		if (survey == null)
			throw new IllegalArgumentException("Invalid survey!");
		this.parent = parent;
		this.survey = survey;
		questions = new HashMap<>();
		displayedQuestion = null;
		initialize();
		this.setVisible(true);
	}

	private void initialize() {
		this.setSize(800, 600);
		this.setMinimumSize(new Dimension(600, 400));
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		setLayout(new MigLayout("", "[][grow][][::500px,fill][][][][][grow][][][][]",
				"[][][26.00][][][73.00][][-19.00][][181.00,grow,baseline][center][center][grow,center][grow,baseline]"));
		textArea = new JTextArea();
		textArea.setFont(Util.QUESTION_FONT);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setToolTipText("This is the question you are supposed to answer.\r\n");
		textArea.setEditable(false);
		textArea.setBackground(getBackground());
		textArea.addKeyListener(this);
		;
		add(textArea, "cell 0 1 7 2,width 60%");

		horizontalStrut = Box.createHorizontalStrut(200);
		add(horizontalStrut, "flowx,cell 0 7 13 3,grow");

		btnNext = new JButton("Next");
		btnNext.addKeyListener(this);
		add(btnNext, "flowy,cell 11 12,growx");

		btnPrevious = new JButton("Previous");
		btnPrevious.addKeyListener(this);
		add(btnPrevious, "cell 11 12,growx");

		btnSubmitAnswers = new JButton("Submit Answers");
		btnSubmitAnswers.addKeyListener(this);
		add(btnSubmitAnswers, "cell 11 12");

		btnBackToMenu = new JButton("Back to Menu");
		btnBackToMenu.addKeyListener(this);
		add(btnBackToMenu, "cell 11 12,growx");

		separator = new JSeparator();
		add(separator, "cell 11 9");
		setContent(survey.getQuestion());

		addActions();
		parent.addKeyListener(this);
		this.addKeyListener(this);
	}

	/**
	 * Adds actions to the buttons
	 */
	private void addActions() {
		// a reference to the current object for use inside listeners
		final SurveyPanel cur = this;
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.removeContent(cur);
				dispose();
			}
		});
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cur.setContent(survey.getNextQuestion());
			}
		});
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cur.setContent(survey.getPrevQuestion());
			}
		});
		btnSubmitAnswers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Question> questions = survey.getQuestionArray(); // get
																			// the
																			// questions
																			// of
																			// this
																			// survey
				LinkedList<Integer> missedQuestions = new LinkedList<>(); // a
																			// list
																			// to
																			// store
																			// the
																			// missed
																			// questions
				for (int i = 0; i < questions.size(); ++i) {
					ChoicePanel c = SurveyPanel.this.questions.get(questions.get(i));
					if (c == null) {
						missedQuestions.add(i + 1);
						continue;
					}
					if (c.getSelectedChoce() == null) {
						missedQuestions.add(i + 1);
					}
				}
				if (!missedQuestions.isEmpty()) {
					Util.showError("You have missed questions:\n " + missedQuestions);
					return;
				}
				for (int i = 0; i < questions.size(); ++i) {
					ChoicePanel c = SurveyPanel.this.questions.get(questions.get(i));
					survey.choose(c.getSelectedChoce());
				}
				ResultPanel panel = new ResultPanel(parent, survey);
				parent.addContent(panel);
				parent.removeContent(cur);
				cur.dispose();
			}
		});
	}

	private void setContent(Question q) {
		// ignore invalid requests
		if (q == null)
			return;
		textArea.setText(String.format("%d/%d: ", survey.getIndex() + 1, survey.getQuestionSize()) + q.getText());
		if (displayedQuestion != null) {
			// remove previously displayed choice
			this.remove(questions.get(displayedQuestion));
			questions.get(displayedQuestion).setVisible(false);
			System.out.println("Removed panel " + questions.get(displayedQuestion));
		}
		ChoicePanel curPanel = questions.get(q); // retrieve the choice panel
													// from the map
		// check if the panel exists in the map
		if (curPanel == null) {
			curPanel = new ChoicePanel(survey, q);
			questions.putIfAbsent(q, curPanel);
		}
		add(curPanel, "cell 0 4 7 5");
		curPanel.setVisible(true);
		curPanel.repaint();
		curPanel.addKeyListener(this);
		this.revalidate();
		parent.setMinimumSize(this.getPreferredSize());
		this.displayedQuestion = q;
	}

	/**
	 * Releases all the resources taken up by this class
	 */
	public void dispose() {
		this.removeAll();
		this.questions.clear();
		displayedQuestion = null;
		survey = null;
		parent = null;
		this.setVisible(false);
		this.setEnabled(false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() > KeyEvent.VK_0 && e.getKeyCode() < KeyEvent.VK_9) {
			int choice = e.getKeyCode() - KeyEvent.VK_0;
			if (choice == 0)
				choice += 10;
			if (choice <= displayedQuestion.getChoices().size()) {
				questions.get(displayedQuestion).select(choice - 1);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			btnNext.doClick();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			btnPrevious.doClick();
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			btnSubmitAnswers.doClick();
		}
	}
}
