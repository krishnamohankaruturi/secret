package edu.ku.cete.report.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.AbstractCategoryItemRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.chart.ui.RectangleEdge;

public class StudentLevelChartBarRenderer extends StackedBarRenderer {

	private static final long serialVersionUID = -453907489758939465L;
	private static final int MIN_VALUE = 1;
	private Paint[] colors;
	
	public StudentLevelChartBarRenderer(final Paint[] colors) {
		super();
		this.colors = colors;
		Font labelFont = new Font("Verdana", Font.PLAIN, 8);
		setDefaultItemLabelGenerator(new LevelItemLabelGenerator());
		setDefaultItemLabelsVisible(true);
		setDrawBarOutline(false);
		setDefaultOutlineStroke(new BasicStroke(1.0f));
		setDefaultOutlinePaint(Color.BLACK);
		setMaximumBarWidth(0.3);
		setDefaultItemLabelFont(labelFont);
	}

	@Override
	public Paint getItemPaint(final int row, final int column) {
		if (row < this.colors.length) {
			return this.colors[row];
		} else {
			return this.colors[row % this.colors.length];
		}
	}

	
	
	
	public void drawItem(Graphics2D g2, CategoryItemRendererState state,
			Rectangle2D dataArea, CategoryPlot plot,
			CategoryAxis domainAxis, ValueAxis rangeAxis,
			CategoryDataset data, int row, int column, int pass) {
		LevelCategoryDataset dataset = (LevelCategoryDataset) data;
		// nothing is drawn for null values...
		Number dataValue = dataset.getValue(row, column);
		double overridedPercentage = 0;
		
		if(dataset.getPercentMap(row, column) != null && dataset.getPercentMap(row, column) > 0) {
			overridedPercentage = dataset.getPercentMap(row, column);
		}		
		
		if (dataValue == null) {
			return;
		}

		double value = dataValue.doubleValue();
		if(overridedPercentage!=0 && overridedPercentage>1) value = overridedPercentage;
		
		PlotOrientation orientation = plot.getOrientation();
		double barW0 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot.getDomainAxisEdge())
				- state.getBarWidth() / 2.0;

		double positiveBase = 0.0;

		for (int i = 0; i < row; i++) {
			Number v = dataset.getValue(i, column);
			if (v != null) {
				double d = v.doubleValue();
				
				double overridedPercentageLevel = 0;				
				
				if(dataset.getPercentMap(i, column) != null && dataset.getPercentMap(i, column) > 0) {
					overridedPercentageLevel = dataset.getPercentMap(i, column);
				}
				
				if(overridedPercentageLevel!=0 && overridedPercentageLevel>1) d=overridedPercentageLevel;
				
				if (d > 0) {
					if (Math.abs(d) <= MIN_VALUE) {
						positiveBase = positiveBase + getAdjustedValueForWidth(d);
					} else {
						positiveBase = positiveBase + d;
					}
				} 
			}
		}

		double translatedBase = 0;
		double translatedValue = 0;
		RectangleEdge location = plot.getRangeAxisEdge();
		//Java-CoE - start
		//if (Math.signum(value) >= 0.0 && row > 1) {
		if (Math.signum(value) >= 0.0) {
		//Java-CoE - end
			translatedBase = rangeAxis.valueToJava2D(positiveBase, dataArea, location);

			if (Math.abs(value) <= MIN_VALUE) {
				double adjValue = positiveBase + getAdjustedValueForWidth(value);
				translatedValue = rangeAxis.valueToJava2D(adjValue, dataArea, location);
			} else {
				translatedValue = rangeAxis.valueToJava2D(positiveBase
						+ value, dataArea, location);
			}
		} /*else {
			translatedBase = rangeAxis.valueToJava2D(negativeBase, dataArea, location);

			if (Math.abs(value) < MIN_VALUE) {
				double adjValue = negativeBase + getAdjustedValueForWidth(value);
				if(adjValue <= -100) {
					adjValue = -100.0;
				}
				translatedValue = rangeAxis.valueToJava2D(adjValue, dataArea, location);
			} else {
				translatedValue = rangeAxis.valueToJava2D(negativeBase
						+ value, dataArea, location);
			}
		}*/
		
		double barL0 = 0.0;
		double barLength = 0.0;
		if (Math.abs(value) <= MIN_VALUE) {
			barL0 = Math.min(translatedBase, translatedValue);
			barLength = Math.max(Math.abs(translatedValue - translatedBase),
					getMinimumBarLength());
			barL0 = barL0 - 0.5;
			barLength += 0.5;
		} else {
			barL0 = Math.min(translatedBase, translatedValue);
			barLength = Math.max(Math.abs(translatedValue - translatedBase),
					getMinimumBarLength());
		}
		
		

		
		
		Rectangle2D bar = null;
		if (orientation == PlotOrientation.HORIZONTAL) {
			bar = new Rectangle2D.Double(barL0, barW0, barLength, state.getBarWidth());
		} else {
			bar = new Rectangle2D.Double(barW0, barL0, state.getBarWidth(), barLength);
		}
		Paint seriesPaint = getItemPaint(row, column);
		g2.setPaint(seriesPaint);
		if(value>0) g2.fill(bar);

		
		/*
		 * Used to fill border outlines
		 */
		
	/*	if (isDrawBarOutline()
				&& state.getBarWidth() > BAR_OUTLINE_WIDTH_THRESHOLD
				&& orientation == PlotOrientation.HORIZONTAL) {
			g2.setStroke(new BasicStroke(1.0f));
			g2.setPaint(getItemOutlinePaint(row, column));
			Line2D line = null;
			if(dataset.getRowKey(row)!=null) {
				if (dataset.getLastLevel() != null && dataset.getLastLevel().equals(dataset.getRowKey(row))) {
					line = new Line2D.Double((int) bar.getMaxX() + 1,
							(int) bar.getMinY(), (int) bar.getMaxX() + 1,
							(int) (bar.getMinY() + state.getBarWidth() + 1));
					g2.draw(line);
				}
				if (dataset.getFirstLevel() != null && dataset.getFirstLevel().equals(dataset.getRowKey(row))) {
					line = new Line2D.Double((int) bar.getMinX(),
							(int) bar.getMinY(), (int) bar.getMinX(),
							(int) (bar.getMinY() + state.getBarWidth() + 1));
					g2.draw(line);
				}
			}
			line = new Line2D.Double((int) bar.getMinX(),
					(int) (bar.getMinY()),
					(int) (bar.getMinX() + barLength + 1),
					(int) (bar.getMinY()));
			g2.draw(line);
			line = new Line2D.Double((int) bar.getMinX(),
					(int) (bar.getMinY() + state.getBarWidth() + 1),
					(int) (bar.getMinX() + barLength + 1),
					(int) (bar.getMinY() + state.getBarWidth() + 1));
			g2.draw(line);
		} else {
		//	g2.setStroke(getItemStroke(row, column));
		//	g2.setPaint(getItemOutlinePaint(row, column));
		//	g2.draw(bar);
		}*/
	//	drawZeroRangeBaseline(g2, plot, dataArea);
		CategoryItemLabelGenerator generator = getItemLabelGenerator(row,
				column);
			
		
		
		
		drawItemLabel(g2, dataset, row, column, plot, generator, bar,
				(value < 0.0));
	}
	
	protected void drawZeroRangeBaseline(Graphics2D g2, CategoryPlot plot,
			Rectangle2D area) {
		if (!plot.isRangeZeroBaselineVisible()) {
			return;
		}
		CategoryItemRenderer r = plot.getRenderer();
		if (r instanceof AbstractCategoryItemRenderer) {
			drawZeroRangeBaseline(g2, plot, plot.getRangeAxis(), area, 0.0,
					plot.getRangeZeroBaselinePaint(),
					plot.getRangeZeroBaselineStroke());
		} else {
			r.drawRangeLine(g2, plot, plot.getRangeAxis(), area, 0.0,
					plot.getRangeZeroBaselinePaint(),
					plot.getRangeZeroBaselineStroke());
		}
	}
	
	protected void drawZeroRangeBaseline(Graphics2D g2, CategoryPlot plot, ValueAxis axis,
            Rectangle2D dataArea, double value, Paint paint, Stroke stroke) {

        // TODO: In JFreeChart 1.2.0, put this method in the
        // CategoryItemRenderer interface
        Range range = axis.getRange();
        if (!range.contains(value)) {
            return;
        }

        PlotOrientation orientation = plot.getOrientation();
        Line2D line = null;
        double v = axis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());
        if (orientation == PlotOrientation.HORIZONTAL) {
            line = new Line2D.Double(v, dataArea.getMinY(), v,
                    dataArea.getMaxY());
        } else if (orientation == PlotOrientation.VERTICAL) {
            line = new Line2D.Double(dataArea.getMinX(), v,
                    dataArea.getMaxX(), v);
        }

        g2.setPaint(paint);
        g2.setStroke(stroke);
        Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, 
                RenderingHints.VALUE_STROKE_NORMALIZE);
        g2.draw(line);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
    }
	
	private double getAdjustedValueForWidth(double val) {
		double result = val;
		if (val < 0 && Math.abs(val) <= MIN_VALUE) {
			while (Math.abs(result) <= MIN_VALUE) {
				result = result - 1;
			}
		} else if (val > 0 && Math.abs(val) <= MIN_VALUE) {
			while (Math.abs(result) <= MIN_VALUE) {
				result = result + 0.3;
			}
		}
		return result;
	}
}

