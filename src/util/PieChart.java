package util;

import javax.swing.*;
import java.awt.*;

public class PieChart extends JPanel {

    private int[] values;
    private float[] labelValues;
    private String[] labels;
    private Color[] colors;

    public PieChart(int[] values, float[] labelValues, String[] labels) {
        this.values = values;
        this.labelValues = labelValues;
        this.labels = labels;
        this.colors = generateColors(values.length);
    }

    private Color[] generateColors(int numColors) {
        Color[] colors = new Color[numColors];
        for (int i = 0; i < numColors; i++) {
            colors[i] = new Color((int) (Math.random() * 0x1000000));
        }
        return colors;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int total = 0;
        for (int value : values) {
            total += value;
        }

        int startAngle = 0;
        for (int i = 0; i < values.length; i++) {
            int arcAngle = (int) Math.round((double) values[i] / total * 360);
            g.setColor(colors[i]);
            g.fillArc(50, 50, 400, 400, startAngle, arcAngle);
            startAngle += arcAngle;
        }

        // Draw labels
        int legendX = 500;
        int legendY = 100;
        for (int i = 0; i < labels.length; i++) {
            g.setColor(colors[i]);
            g.fillRect(legendX, legendY + (i * 20), 10, 10);
            g.setColor(Color.BLACK);
            g.drawString(labels[i] + " (" + labelValues[i] * 100 + "%)", legendX + 15, legendY + (i * 20) + 10);
        }
    }
}