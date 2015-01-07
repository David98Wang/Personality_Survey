package tests;
import gui.BarChart;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class BarTest extends JFrame{
	public static String[] x={"a","b","c","d","e","f","g","h","i","j"};
	public static double[] y=new double[10];
	public BarTest()
	{
        ChartPanel p =  BarChart.BarChart( y, x,"a","b","c","d");
        
        p.setPreferredSize(new java.awt.Dimension(500, 270));
        p.setVisible(true);
        setContentPane(p);
        
	}
	public static void main(String[] args)
	{
		for(int i=0;i<10;i++)
		{
			y[i]=Math.random()*10.*Math.pow(-1,(int)Math.random()*2);
		}
		BarTest a = new BarTest();
		a.setVisible(true);
		a.pack();
		a.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
