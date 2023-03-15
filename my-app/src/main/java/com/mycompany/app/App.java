package com.mycompany.app;

import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;
import java.awt.Color;

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.graphics.Label;
import de.erichseifert.gral.graphics.Location;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

public final class App extends JFrame {

    DataTable ansTable = new DataTable(Double.class,  Double.class);

    public App() {
        DataTable data = new DataTable(Double.class, Double.class, Double.class, Double.class, Double.class);
        setUpInputFunc(data);
        DataSeries series1 = new DataSeries("Series 1", data, 0, 1);
        DataSeries series2 = new DataSeries("Series 2", data, 0, 2);
        DataSeries series3 = new DataSeries("Series 3", data, 0, 3);
        DataSeries series = new DataSeries("Series", data, 0, 4);

        getMinSpentSale(series1, series2, series3);

        DataSeries ansSer = new DataSeries("Answers", ansTable, 0, 1);

        XYPlot plot = new XYPlot(series1, series2, series3, series, ansSer);

        setUpVisuals(plot, series, series1, series2, series3, ansSer);

        addDottedLines(plot);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        getContentPane().add(new InteractivePanel(plot));
    }

    public static void main(String[] args) {
        App frame = new App();
        frame.setVisible(true);
    }

    void setUpInputFunc(DataTable data){
        for (double x = 10.0; x <= 1000.0; x+=1.0) {
            double y1 = Math.ceil(20.0 * 340.0 / x + x + 340.0 * 5.0);
            double y2 = Math.ceil(20.0 * 340.0 / x + x + 340.0 * 4.0);
            double y3 = Math.ceil(20.0 * 340.0 / x + x + 340.0 * 3.0);
            double y = 0.0;
            if (x < 500) {
                y = y1;
            } else if (x >= 500 && x < 700) {
                y = y2;
            } else {
                y = y3;
            }
            data.add(x, y1, y2, y3, y);
        }
    }

    void setUpVisuals(XYPlot plot, DataSeries series, DataSeries series1, DataSeries series2, DataSeries series3, DataSeries ansSer){
        LineRenderer points1 = new DefaultLineRenderer2D();
        points1.setStroke(new BasicStroke(3f));
        points1.setColor(new Color(1.0f, 1.0f, 0.0f, 1.0f));
        plot.setLineRenderers(series1, points1);

        LineRenderer points2 = new DefaultLineRenderer2D();
        points2.setStroke(new BasicStroke(3f));
        points2.setColor(new Color(0.0f, 1.0f, 0.0f, 1.0f));
        plot.setLineRenderers(series2, points2);
        
        LineRenderer points3 = new DefaultLineRenderer2D();
        points3.setStroke(new BasicStroke(3f));
        points3.setColor(new Color(0.0f, 1.0f, 1.0f, 1.0f));
        plot.setLineRenderers(series3, points3);
        
        LineRenderer lines = new DefaultLineRenderer2D();
        lines.setStroke(new BasicStroke(5f));
        lines.setColor(new Color(0.8f, 0.3f, 1.0f, 1.0f));
        plot.setLineRenderers(series, lines);

        PointRenderer points = new DefaultPointRenderer2D();
        points.setColor(new Color(0.0f, 0.0f, 0.0f, 0.1f));
        points.setShape(new Ellipse2D.Double(-2.0, -2.0, 4.0, 4.0));
        
        PointRenderer pr = new DefaultPointRenderer2D();
        pr.setValueColor(new Color(0.0f, 0.0f, 1.0f, 1.0f));
        pr.setValueLocation(Location.SOUTH_EAST);
        pr.setShape(new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));
        pr.setColor(new Color(0.0f, 1.0f, 0.0f, 1.0f));
        pr.setValueVisible(true);
        pr.setValueRotation(-45.0);
        pr.setValueColumn(1);

        plot.setPointRenderers(ansSer, pr);
        plot.setPointRenderers(series1, points);
        plot.setPointRenderers(series2, points);
        plot.setPointRenderers(series3, points);
        plot.setPointRenderers(series, points);

        plot.getAxisRenderer(XYPlot.AXIS_X).setLabel(new Label("Q"));
        plot.getAxisRenderer(XYPlot.AXIS_Y).setLabel(new Label("L"));
        plot.getAxisRenderer(XYPlot.AXIS_X).setTickSpacing(100.0);
        plot.getAxisRenderer(XYPlot.AXIS_Y).setTickSpacing(100.0);
        plot.getTitle().setText("Lab 9");
    }

    void addDottedLines(XYPlot plot){
        boolean num = true;
        BasicStroke lineStyle = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {3f, 3f}, 0.0f);
        double n = 0.0, a = 0.0;
        for (Comparable<?> d : ansTable) {
            if (num) {
                n = (double) d;
                num = !num;
            } else {
                a = (double) d;
                num = !num;
                DataTable dt = new DataTable(Double.class, Double.class);
                dt.add(0.0,a);
                dt.add(n,a);
                dt.add(n,0.0);
                DataSeries ds = new DataSeries(dt, 0, 1);
                plot.add(ds);
                LineRenderer lr = new DefaultLineRenderer2D();
                lr.setStroke(lineStyle);
                PointRenderer pr1 = new DefaultPointRenderer2D();
                pr1.setValueColor(new Color(0.0f, 0.5f, 1.0f, 1.0f));
                pr1.setValueLocation(Location.NORTH_WEST);
                pr1.setShape(new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));
                pr1.setColor(new Color(0.0f, 1.0f, 0.0f, 1.0f));
                pr1.setValueVisible(true);
                pr1.setValueRotation(-45.0);
                pr1.setValueColumn(0);
                plot.setLineRenderers(ds, lr);
                plot.setPointRenderers(ds, pr1);
            }
        }
    }

    double[][] getMinSpentSale(DataSeries data1, DataSeries data2, DataSeries data3){
        double[] qp1 = getQp(500, data2);
        System.out.println("Первая точка разрыва цен:");
        System.out.println(Arrays.toString(qp1));
        ansTable.add(qp1[0],qp1[1]);
        double[] firstSaleQ = getQ(0, 500, qp1, data1, data2);
        System.out.println("Наименьшее между основной ценой и ценой с первой скидкой:");
        System.out.println(Arrays.toString(firstSaleQ));
        ansTable.add(firstSaleQ[0],firstSaleQ[1]);
        double[] qp2 = getQp(700, data3);
        System.out.println("Вторая точка разрыва цен:");
        System.out.println(Arrays.toString(qp2));
        ansTable.add(qp2[0],qp2[1]);
        double[] secondSaleQ = getQ(firstSaleQ[0], Double.MAX_VALUE, qp2, data1, data3);
        System.out.println("Наименьшее между предыдущим значением и ценой со второй скидкой:");
        System.out.println(Arrays.toString(secondSaleQ));
        ansTable.add(secondSaleQ[0],secondSaleQ[1]);
        return new double[][]{secondSaleQ,qp2,firstSaleQ,qp1};
    }

    double[] getQ(double start, double stop, double[] qp, DataSeries data1, DataSeries data2){
        double[] qw = new double[2];
        if (start == 0.0){
            qw = getMinData(data1);
        } else {
            qw = getQp(start, data1);
        }
        if (qp[0] < qw[0]) {
            return qw;
        } else {
            double[] q1 = getQ1(qw, data2);
            ansTable.add(q1[0],q1[1]);
            if (qp[0] < q1[0]) {
                return qp;
            } else {
                return qw;
            }
        }
    }

    double[] getMinData(DataSeries data){
        double min = (double) data.get(1, 0);
        double num = (double) data.get(0, 0);
        for (int i = 0; i < data.getRowCount(); i ++) {
            if ((double) data.get(1, i) <= min){
                min = (double) data.get(1, i);
                num = (double) data.get(0, i);
            }
        }
        return new double[]{num, min};
    }

    double[] getQ1(double[] qw, DataSeries data){
        double q1 = (double) data.get(1, 0);
        double num = (double) data.get(0, 0);
        for (int i = 0; i < data.getRowCount(); i ++) {
            if ((double) data.get(1, i) == qw[1]){
                q1 = (double) data.get(1, i);
                num = (double) data.get(0, i);
                return new double[]{num, q1};
            }
        }
        return new double[]{num, q1};
    }

    double[] getQp(double qp1Ind, DataSeries data){
        double qp1 = (double) data.get(1, 0);
        double num = (double) data.get(0, 0);
        for (int i = 0; i < data.getRowCount(); i ++) {
            if ((double) data.get(0, i) == qp1Ind){
                qp1 = (double) data.get(1, i);
                num = (double) data.get(0, i);
                return new double[]{num, qp1};
            }
        }
        return new double[]{num, qp1};
    }
}