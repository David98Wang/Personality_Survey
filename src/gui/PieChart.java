package gui;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import javax.swing.JButton;

public class PieChart {

	/**
	 * @param applicationTitle
	 * @param chartTitle
	 * @param name
	 * @param number
	 * @return ChartPanel
	 */
	public static ChartPanel PieChart(String applicationTitle,
			String chartTitle, String name[], double number[]) {
		PieDataset dataset = createDataset(name, number);
		JFreeChart chart = createChart(dataset, chartTitle);
		ChartPanel panel = new ChartPanel(chart);
		panel.setMouseWheelEnabled(true);
		return panel;
	}

	/**
	 */
	private static PieDataset createDataset(String name[], double number[]) {
		DefaultPieDataset result = new DefaultPieDataset();
		for (int i = 0; i < name.length; i++)
			result.setValue(name[i], number[i]);

		return result;

	}

	/**
	 * @param dataset
	 * @param title
	 * @return JFreeChart
	 */
	private static JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true,
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);

		return chart;

	}

}
