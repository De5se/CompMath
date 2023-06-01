import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.util.function.Function;

public class DrawChart extends ApplicationFrame {
    public static final double DEFAULT_STEP = 0.05;

    public DrawChart(String title) {
        super(title);
    }

    public void graph(double a, double b, Function<Double, Double>... functions) {
        XYDataset dataset = generateDataset(a, b, DEFAULT_STEP, functions);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Fi(x)",
                "x", "y", dataset, PlotOrientation.VERTICAL,
                true,true, false);

        JFrame frame = new JFrame("MinimalStaticChart");
        // Помещаем график на фрейм
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(1000, 500);
        frame.setVisible(true);
    }

    private XYDataset generateDataset(double from, double to, double step, Function<Double, Double>... functions) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (Function<Double, Double> f: functions) {
            XYSeries series = new XYSeries(f.hashCode());
            for (double x = from; x < to + step; x += step) {
                series.add(x, f.apply(x));
            }
            dataset.addSeries(series);
        }
        return dataset;
    }
}