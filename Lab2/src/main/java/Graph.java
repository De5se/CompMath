import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JFrame;

public class Graph extends JFrame {
    public Graph(Task task, InputOutput inputOutput) {
        super("График уравнения");
        double a;
        double b;
        try {
            a = inputOutput.getAGraph();
            b = inputOutput.getBGraph(a);
        } catch (Exception e) {
            a = -50;
            b = 50;
        }

        XYSeriesCollection dataset = new XYSeriesCollection();

        for (int i = 0; i < task.getEquations().length; i++) {
            XYSeries series = new XYSeries(task.getStr()[i]);
            for (double x = a; x <= b; x += Math.abs((a - b) / 1000)) {
                double y = task.getEquations()[i].f(x);
                series.add(x, y);
            }
            dataset.addSeries(series);
        }

        JFreeChart chart = ChartFactory.createXYLineChart(
                "График",
                "x",
                "y",
                dataset, PlotOrientation.VERTICAL,
                true, true, false
        );

        JFrame frame = new JFrame("MinimalStaticChart");
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(1000, 500);
        frame.setVisible(true);
    }
}