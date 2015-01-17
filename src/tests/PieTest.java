package tests;
import gui.PieChart;

import org.jfree.chart.ChartPanel;

import javax.swing.JFrame;

public class PieTest extends JFrame{
	public static String[] x={"a","b","c","d","e","f","g","h","i","j"};
	public static double[] y=new double[10];
	public PieTest()
	{
        ChartPanel p =  PieChart.createPieChart("a", "b", x, y);
        
        p.setPreferredSize(new java.awt.Dimension(500, 270));
        p.setVisible(true);
        setContentPane(p);
        
	}
	public static void main(String[] args)
	{
		for(int i=0;i<10;i++)
		{
			y[i]=(int)(Math.random()*10.0);
		}
		PieTest a = new PieTest();
		a.setVisible(true);
		a.pack();
		a.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
