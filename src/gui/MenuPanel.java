/**
 * File: MenuPanel.java
 *
 * Project : Personality_Survey
 * Package : gui
 * 
 * Created : Jan 17, 2015 10:09:12 AM
 * Created by: Jack Li
 */
package gui;

import io.Parser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import common.Survey;
import common.Util;

/**
 * A panel used to display the menu
 * 
 * @author Jack Li
 *
 */
public class MenuPanel extends JPanel {
	private JLabel lblTitle;
	private JPanel buttonPanel;
	private JButton btnStart;
	private JButton btnLoad;
	private JButton btnHelp;
	private JButton btnExit;
	private MainFrame parent;

	public MenuPanel(MainFrame parent) {
		this.parent = parent;
		initialize();
		addListeners();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));

		lblTitle = new JLabel("Survey");
		lblTitle.setBorder(new EmptyBorder(100, 10, 10, 10));
		lblTitle.setFont(new Font("Tekton Pro Ext", Font.PLAIN, 41));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitle, BorderLayout.NORTH);

		buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.CENTER);
		SpringLayout sl_buttonPanel = new SpringLayout();
		buttonPanel.setLayout(sl_buttonPanel);

		btnStart = new JButton("Start");
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnStart, -349, SpringLayout.EAST, buttonPanel);
		btnStart.setToolTipText("Start a survey");
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnStart, 61, SpringLayout.NORTH, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnStart, 355, SpringLayout.WEST, buttonPanel);
		btnStart.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		buttonPanel.add(btnStart);

		btnLoad = new JButton("Load");
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnLoad, 32, SpringLayout.SOUTH, btnStart);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnLoad, 0, SpringLayout.EAST, btnStart);
		btnLoad.setToolTipText("Load a survey from a file");
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnLoad, 355, SpringLayout.WEST, buttonPanel);
		btnLoad.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		buttonPanel.add(btnLoad);

		btnHelp = new JButton("Help");
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnHelp, 40, SpringLayout.SOUTH, btnLoad);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnHelp, 355, SpringLayout.WEST, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnHelp, 0, SpringLayout.EAST, btnStart);
		btnHelp.setToolTipText("Show help");
		btnHelp.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		buttonPanel.add(btnHelp);

		btnExit = new JButton("Exit");
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnExit, 41, SpringLayout.SOUTH, btnHelp);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnExit, 355, SpringLayout.WEST, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnExit, 0, SpringLayout.EAST, btnStart);
		btnExit.setToolTipText("Exit the program");
		btnExit.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		buttonPanel.add(btnExit);
		this.setMinimumSize(new Dimension(800, 600));
		this.setPreferredSize(getMinimumSize());
		this.setSize(getPreferredSize());
	}

	private void addListeners() {
		final MenuPanel menu = this; // a menu used to reference this object
										// inside the listeners
		btnStart.addActionListener(new ActionListener() {
			// a wrapper class for surveys in order to be compatible with
			// JOptionePane's InputDialog
			final class SurveyWrapper {
				public String name;
				public Survey survey;

				public SurveyWrapper(Survey survey) {
					this.name = survey.getTitle();
					this.survey = survey;
				}

				public String toString() {
					return name;
				}
			}

			public void actionPerformed(ActionEvent e) {
				try {
					Survey[] surveys = io.Parser.readAll(new File("./assets"));
					SurveyWrapper[] wrapped = new SurveyWrapper[surveys.length];
					for (int i = 0; i < surveys.length; ++i) {
						wrapped[i] = new SurveyWrapper(surveys[i]);
					}
					SurveyWrapper selected = (SurveyWrapper) JOptionPane.showInputDialog(parent, "Select a survey:", "Choose a Survey",
							JOptionPane.PLAIN_MESSAGE, null, wrapped, wrapped[0]);
					if (selected == null)
						return;
					parent.addContent(new SurveyPanel(parent, selected.survey));
				} catch (IOException ex) {
					Util.showError("Could not read the surveys. Make sure there are surveys in the assets folder of this program.");
					ex.printStackTrace();
				}
			}
		});

		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = Util.chooseFile("Load", Util.TXT_FILTER);
				if (f != null)
					try {
						parent.addContent(new SurveyPanel(parent, Parser.readSurvey(f)));
					} catch (IOException e1) {
						Util.showError("Could not read file " + f.getAbsolutePath());
						e1.printStackTrace();
					} catch (IllegalArgumentException ex) {
						return;
					}

			}
		});
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Util.showConfirm("Are you sure you want to exit?"))
					System.exit(0);
			}
		});
	}
}
