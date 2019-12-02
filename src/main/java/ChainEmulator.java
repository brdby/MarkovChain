import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.Random;

public class ChainEmulator {

    private final double[] firstPosition;
    private final double[][] transitionMatrix;
    private final int[] chainValues;
    private final double length;

    public ChainEmulator(double[] firstPosition, double[][] transitionMatrix, int[] chainValues){
        this.firstPosition = firstPosition;
        this.transitionMatrix = transitionMatrix;
        this.chainValues = chainValues;
        this.length = firstPosition.length;
    }

    public String emulateChain(int steps, boolean showChart){
        StringBuilder output = new StringBuilder();
        Random random = new Random();
        XYSeries series = new XYSeries("Trajectory");

        //calculating first position
        int nextStep = 0;
        double rNum = random.nextDouble();
        for (int i = 0; i < length; i++){
            if (rNum > firstPosition[i]){
                rNum-=firstPosition[i];
            } else {
                nextStep = i;
                output.append(chainValues[i]);
                if (showChart) series.add(0, i);
                break;
            }
        }

        //emulating chain
        for (int i = 1; i <= steps; i++){
            rNum = random.nextDouble();
            for (int j = 0; j < length; j++){
                if (rNum > transitionMatrix[nextStep][j]){
                    rNum-=transitionMatrix[nextStep][j];
                } else {
                    nextStep = j;
                    output.append(chainValues[j]);
                    if (showChart) series.add(i, j);
                    break;
                }
            }
        }

        //Creating chart
        if (showChart){
            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(series);
            LineChart chart = new LineChart(dataset);
            chart.setVisible(true);
        }


        String text =  "P0 = 0 * (P0 + P1 + P2 + P3 + P4) = 0\n" +
                "P1 = O,50 * P0 + 1,00*P4\n" +
                "P2 = 0,75*P2 + 0,40*P3\n" +
                "P3 = 1,00*P1\n" +
                "P4 = 0,50*P0 + 0,25*P2 + 0,60*P3\n" +
                "P0 + P1 + P2 + P3 + P4 = 1\n" +
                "--------------------------------------------------\n" +
                "P1 = 1 * P4\n" +
                "P2 = 0,75 * P2 + 0,40 * P3\n" +
                "P3 = 1 * P1\n" +
                "P4 = 0,25 * P2 + 0,60 * P3\n" +
                "P1 + P2 + P3 + P4 = 1";
        System.out.println(text);

        double p0 = 0, p1 = 0.2174, p2 = 0.3478, p3 = 0.2174, p4 = 0.2174;

        System.out.println("P0 - " + p0 + "; P1 - " + p1 + "; P2 - " + p2 + "; P3 - " + p3 + "; P4 - " + p4);
        System.out.println("Проверка - P1 + P2 + P3 + P4 = " + (p1+p2+p3+p4) + "\n");

        return output.toString();
    }
}
