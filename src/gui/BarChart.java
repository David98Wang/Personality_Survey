package gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * @author David
 * @date January 8, 2014
 */
public class BarChart {
	/**
	 * Create and return chart panel
	 * @param number
	 * @param name
	 * @param title
	 * @param domainLabel
	 * @param rangeLabel
	 * @param label
	 * @return ChartPanel
	 */
	public static ChartPanel BarChart(double number[], String name[],
			String title, String domainLabel, String rangeLabel, String label) 
	{
		CategoryDataset dataset = createDataset(name, number, label);//create object data set for bar graph
		JFreeChart chart = createChart(dataset, title, domainLabel, rangeLabel);//draw chart
		ChartPanel panel = new ChartPanel(chart);//create chart panel
		panel.setMouseZoomable(true);// able to zoom with mouse
		return panel;
	}

	/**
	 * Create and return Default Category data set
	 * @param name
	 * @param number
	 * @param label
	 * @return DefaultCategoryDataset
	 */
	public static CategoryDataset createDataset(String name[], double number[],
			String label) // create bar chart data set
	{
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();//create Default Category data set(the type of data set used for bar chart)
		for (int i = 0; i < name.length; i++) {
			dataset.addValue(number[i], name[i], "");//create bar with name and number inputed
		}
		return dataset;
	}

	/**
	 * Create and return bar chart
	 * @param dataset
	 * @param title
	 * @param dl
	 * @param rl
	 * @return Chart
	 */
	public static JFreeChart createChart(CategoryDataset dataset, String title,
			String dl, String rl) {
		JFreeChart chart = ChartFactory.createBarChart3D(title, // title
				dl, // domain axis label
				rl, // range axis label
				dataset // data
				);

		return chart;
	}
}
