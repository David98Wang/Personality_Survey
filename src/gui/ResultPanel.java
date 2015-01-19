/**
 * File: ResultPanel.java
 *
 * Project : Personality_Survey
 * Package : gui
 * 
 * Created : Jan 17, 2015 10:10:18 AM
 * Created by: Jack Li
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import common.Result;
import common.Survey;
import common.Type;
import common.Util;

/**
 * A class used to display the results
 * 
 * @author Jack Li
 *
 */
public class ResultPanel extends JPanel {	
	/**
	 * Maximum entries displayed in the pie chart
	 */
	private static final int  MAX_CHART_ELEMENTS = 7;

/**
	 * A logger object to log any messages this classes has
	 */
	private static Logger logger = Logger.getLogger(ResultPanel.class.getName());

	/**
	 * The results to be displayed
	 */
	private List<Result> results;
	/**
	 * Charts generated from the results
	 */
	private JPanel barChart, pieChart;

	/**
	 * The survey producing these results
	 */
	private Survey survey;

	/**
	 * A reference to the parent frame containing this panel
	 */
	private MainFrame parent;
	private JTable table;

	public ResultPanel(MainFrame parent, Survey s) {
		this.survey = s;
		this.results = s.getResults();
		this.parent = parent;
		initialize();
		this.setVisible(true);
	}

	private void initialize() {
		//set this component's size
		this.setPreferredSize(new Dimension(800, 600));
		this.setMinimumSize(getPreferredSize());
		this.setSize(getPreferredSize());

		//grab data from survey
		ArrayList<Type> types = new ArrayList<>(survey.getTypes());
		Collections.sort(types, new Comparator<Type>() {

			@Override
			public int compare(Type a, Type b) {
				return (int) (b.getPoints() - a.getPoints());
			}
			
		});;
		int chartSize = Math.min(types.size(), MAX_CHART_ELEMENTS+1);
		String[] names = new String[chartSize];
		double[] nums = new double[chartSize];
		if (types.size() > chartSize) {
			for (int i = 0; i < MAX_CHART_ELEMENTS; ++i) {
				names[i] = types.get(i).getText();
				nums[i] = types.get(i).getPoints();
			}
			//create a seperate category for the other things
			names[chartSize - 1] = "Other";
			nums[chartSize - 1] = 0;
			for (int i = chartSize - 1; i < types.size(); ++i)
				nums[chartSize - 1] += types.get(i).getPoints();
		} else {
			for (int i = 0; i < names.length; ++i) {
				names[i] = types.get(i).getText();
				nums[i] = types.get(i).getPoints();
			}
		}
		
		//create charts
		pieChart = PieChart.createPieChart(survey.getTitle(), "Results", names, nums);
		pieChart.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED ));
		JScrollPane pieScrollPane = new JScrollPane(pieChart);
		pieScrollPane.setToolTipText("Right click for more options");
		barChart = BarChart.createBarChart(nums, names, survey.getTitle(), "Categories", "Points", "Chart");
		barChart.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED ));
		JScrollPane barScrollPane = new JScrollPane(barChart);
		barScrollPane.setToolTipText("Right click for more options");
		setBorder(new EmptyBorder(20, 20, 20, 20));
		StringBuilder resultBuilder = new StringBuilder();
		resultBuilder.append("Results: \n");
		for (int i = 0; i < results.size(); ++i) {
			resultBuilder.append(String.format("%d: ", i+1) + results.get(i).getText() + "\n");
		}
		setLayout(new BorderLayout(0, 0));
		
		//add panels for layout control
		JPanel contentPanel = new JPanel();
		add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));

		JPanel textPanel = new JPanel();
		contentPanel.add(textPanel);
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		
		//create text area to display result text
		JTextArea textArea = new JTextArea();
		textArea.setToolTipText("This is the result of the survey based on your decisions");
		textArea.setFont(new Font("Baskerville Old Face", Font.PLAIN, 20));
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBackground(this.getBackground().brighter());
		textArea.setText(resultBuilder.toString());
		textArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED ));
		JScrollPane textScroll = new JScrollPane(textArea);
		textScroll.setToolTipText("This is the result of the survey based on your decisions");
		//scroll to top left corner
		textScroll.scrollRectToVisible(new Rectangle(0,0,1,1));
		textPanel.add(textScroll);
		
		//create spacing component
		Component verticalStrut = Box.createVerticalStrut(20);
		textPanel.add(verticalStrut);
		
		//create table
		String[] columnNames = { "Category", "Points", "Percentage" };
		Object[][] tableData = new Object[types.size()][3];
		double totalPoints = 0;// the total points for this survey
		for (Type t : types) {
			totalPoints += t.getPoints();
		}
		for (int i = 0; i < types.size(); ++i) {
			Type curType = types.get(i);
			tableData[i ][0] = curType.getText();
			tableData[i ][1] = String.format("%.2f",curType.getPoints());
			tableData[i ][2] = String.format("%05.2f%%", curType.getPoints() / totalPoints * 100);
		}
		table = new JTable(tableData, columnNames);
		table.setFont(new Font("Baskerville Old Face", Font.PLAIN, 17));
		table.setToolTipText("This is the detailed breakdown of the points you scored in the different categories of this survey");
		table.setRowHeight((int)(table.getFontMetrics(table.getFont()).getHeight()*1.602));
		table.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED ));
		table.setEnabled(false);
		JScrollPane tabelScrollPane = new JScrollPane(table);
		tabelScrollPane.setToolTipText("This is the detailed breakdown of the points you scored in the different categories of this survey");
		textPanel.add(tabelScrollPane);
		
		//create spacing component
		Component horizontalStrut = Box.createHorizontalStrut(20);
		contentPanel.add(horizontalStrut);
		
		//create panel for flow control
		JPanel graphPanel = new JPanel();
		contentPanel.add(graphPanel);
		graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));
		graphPanel.add(barScrollPane);
		graphPanel.add(Box.createVerticalStrut(20));
		graphPanel.add(pieScrollPane);
		
		//create button panel and buttons
		JPanel buttonPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) buttonPanel.getLayout();
		flowLayout.setHgap(10);
		buttonPanel.setSize(800, 50);
		add(buttonPanel, BorderLayout.SOUTH);

		JButton btnSave = new JButton("Save");
		btnSave.setToolTipText("Click to save the results into a webpage");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//print html page
				File file = Util.chooseFile("Save", Util.createFileFilter("HTML file (.html)", "^.*\\.html$"));
				if (!file.getAbsolutePath().endsWith(".html"))
					file = new File(file.getAbsolutePath() + ".html");
				if (!file.exists())
					try {
						file.createNewFile();
					} catch (IOException e2) {
						Util.showError("Error creating file " + file.getAbsolutePath());
						e2.printStackTrace();
						return;
					}
				PrintWriter fout = null;
				try {
					fout = new PrintWriter(file);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					return;	//impossible exception
				} 
				
				fout.println("<!DOCTYPE html>");
				fout.println("<head>");
				fout.println("<strong>" + survey.getTitle() + "</strong>");
				fout.println("</head>");
				fout.println("<body>");
				fout.println("<p>");
				fout.println("Results:<br>");
				fout.println("<ol type=\"1\">");
				for (int i = 0; i < results.size(); ++i) {
					fout.println("<li>");
					fout.printf("%s", results.get(i).getText());
					fout.println("</li>");
				}
				fout.println("</ol>");
				fout.println("</p>");
				fout.println("<p>");
				fout.println("Points breakdown:<br>");
				fout.println("<ul>");
				for (int i = 0; i < survey.getTypes().size(); ++i) {
					fout.println("<li>");
					fout.printf("%s: %4.2f", survey.getTypes().get(i).getText(),survey.getTypes().get(i).getPoints());
					fout.println("</li>");
				}
				fout.println("</ul>");
				fout.println("</p>");
				fout.println("<br><br><br>");
				String website = survey.getWebsite();
				if (!(website.startsWith("http://") || website.startsWith("https://")) )
					website = "http://" + website;
				fout.printf("For more information, click <a href=\"%s\">here</a>\n",website);
				fout.println("</body>");
				if (fout != null)
					fout.close();
				logger.log(Level.INFO,"Written to " + file.getAbsolutePath());
				Util.open(file);
			}
		});
		buttonPanel.add(btnSave);

		JButton btnBackToMenu = new JButton("Back to Menu");
		btnBackToMenu.setToolTipText("Click to go back to the menu");
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.removeContent(ResultPanel.this);
				parent.invalidate();
				parent.validate();
				parent.setResizable(true);
				logger.log(Level.INFO,"Removed");
				ResultPanel.this.dispose();
			}
		});
		buttonPanel.add(btnBackToMenu);

		JButton btnMoreInfo = new JButton("More Info");
		btnMoreInfo.setToolTipText("Click to open a link in the browser for more information");
		btnMoreInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String website = survey.getWebsite();
				if (!(website.startsWith("http://") || website.startsWith("https://")) )
					website = "http://" + website;
				try {
					Util.openWebpage(new URL(website));
				} catch (MalformedURLException e1) {
					// a dirty hack that works on windows
					File f = new File(website);
					Util.open(f);
					e1.printStackTrace();
				}
			}
		});
		buttonPanel.add(btnMoreInfo);
	}

	/**
	 * Releases all the resources of this object
	 */
	public void dispose() {
		this.setVisible(false);
		this.setEnabled(false);
		results = null;
		survey = null;
		parent = null;
	}
}
