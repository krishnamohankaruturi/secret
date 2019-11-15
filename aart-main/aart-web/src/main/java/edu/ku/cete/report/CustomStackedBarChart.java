package edu.ku.cete.report;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.category.CategoryDataset;
import org.jfree.chart.ui.RectangleEdge;

public class CustomStackedBarChart extends GroupedStackedBarRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private KeyToGroupMap seriesToGroupMap;
	private boolean generateBar;
	
	public CustomStackedBarChart(boolean barGeneration) {
		seriesToGroupMap = new KeyToGroupMap();
		generateBar = barGeneration;
	}

	@Override
	public void drawItem(Graphics2D g2, CategoryItemRendererState state,
			Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis,
			ValueAxis rangeAxis, CategoryDataset dataset, int row, int column,
			int pass) {
		Number dataValue = dataset.getValue(row, column);

		if (dataValue == null) {
			return;
		}

		double value = dataValue.doubleValue();
		Comparable group = seriesToGroupMap.getGroup(dataset.getRowKey(row));
		PlotOrientation orientation = plot.getOrientation();
		double barW0 = calculateBarW0(plot, orientation, dataArea, domainAxis,
				state, row, column);

		double positiveBase = 0.0D;
		double negativeBase = 0.0D;
		boolean rangeLevel = false;
		for (int i = 0; i < row; i++) {
			if (group.equals(seriesToGroupMap.getGroup(dataset.getRowKey(i)))) {
				Number v = dataset.getValue(i, column);
				if (v != null) {
					double d = v.doubleValue();
					if (d > 0.0D) {
						positiveBase += d;
					} else {
						negativeBase += d;
					}
				}
			}

			Paint itemPaint = getSeriesPaint(i + 1);
			if ((itemPaint instanceof GradientPaint)) {
				/* 117 */GradientPaint gp = (GradientPaint) itemPaint;
				/* 118 */Color c0 = gp.getColor1();
				/* 119 */if (c0.equals(Color.BLACK)) {
					rangeLevel = true;
				} else {
					rangeLevel = false;
				}
				/*     */}

			// if(dataset.getRowKey(i).equals("Level 3")) rangeLevel = true;
			// else rangeLevel=false;
		}

		boolean positive = value > 0.0D;
		boolean inverted = rangeAxis.isInverted();
		RectangleEdge barBase;
		if (orientation == PlotOrientation.HORIZONTAL) {
			if (((positive) && (inverted)) || ((!positive) && (!inverted))) {
				barBase = RectangleEdge.RIGHT;
			} else {
				barBase = RectangleEdge.LEFT;
			}
		} else {

			if (((positive) && (!inverted)) || ((!positive) && (inverted))) {
				barBase = RectangleEdge.BOTTOM;
			} else {
				barBase = RectangleEdge.TOP;
			}
		}
		RectangleEdge location = plot.getRangeAxisEdge();
		double translatedValue;
		double translatedBase;
		if (value > 0.0D) {
			translatedBase = rangeAxis.valueToJava2D(positiveBase, dataArea,
					location);

			translatedValue = rangeAxis.valueToJava2D(positiveBase + value,
					dataArea, location);
		} else {
			translatedBase = rangeAxis.valueToJava2D(negativeBase, dataArea,
					location);

			translatedValue = rangeAxis.valueToJava2D(negativeBase + value,
					dataArea, location);
		}

		double barL0 = Math.min(translatedBase, translatedValue);
		double barLength = Math.max(Math.abs(translatedValue - translatedBase),
				getMinimumBarLength());
		Rectangle2D bar;

		if (orientation == PlotOrientation.HORIZONTAL) {
			if (rangeLevel)
				bar = new Rectangle2D.Double(barL0, barW0 - 2.7, barLength,
						state.getBarWidth() + 5.3);
			else
				bar = new Rectangle2D.Double(barL0, barW0, barLength,
						state.getBarWidth());
		} else {
			if (rangeLevel)
				bar = new Rectangle2D.Double(barW0, barL0, state.getBarWidth(),
						barLength);
			else
				bar = new Rectangle2D.Double(barW0, barL0, state.getBarWidth(),
						barLength);
		}

		if (generateBar)
			getBarPainter().paintBar(g2, this, row, column, bar, barBase);

		CategoryItemLabelGenerator generator = getItemLabelGenerator(row,
				column);

		if ((generator != null) && (isItemLabelVisible(row, column))) {
			drawItemLabel(g2, dataset, row, column, plot, generator, bar,
					value < 0.0D);
		}

		if (state.getInfo() != null) {
			EntityCollection entities = state.getEntityCollection();
			if (entities != null) {
				addItemEntity(entities, dataset, row, column, bar);
			}
		}
	}

}
