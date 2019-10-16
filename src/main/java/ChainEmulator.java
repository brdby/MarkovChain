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

        return output.toString();
    }
}
