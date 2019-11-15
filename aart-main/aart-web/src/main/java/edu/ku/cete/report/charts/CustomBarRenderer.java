package edu.ku.cete.report.charts;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.data.statistics.StatisticalCategoryDataset;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.ui.GradientPaintTransformer;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.TextAnchor;

public class CustomBarRenderer extends StatisticalBarRenderer {
	private static final long serialVersionUID = 3050194710635888342L;

	/** The colors. */
	private Paint[] colors;
	private boolean showNotRespondedLabel;

	public CustomBarRenderer(final Paint[] colors) {
		this.colors = colors;
	}

	public Paint getItemPaint(final int row, final int column) {
		return this.colors[column % this.colors.length];
	}

	@Override
	protected void drawHorizontalItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis,
			StatisticalCategoryDataset dataset, int visibleRow, int row, int column) {

		RectangleEdge xAxisLocation = plot.getDomainAxisEdge();

		// BAR Y
		double rectY = domainAxis.getCategoryStart(column, getColumnCount(), dataArea, xAxisLocation);

		int seriesCount = state.getVisibleSeriesCount() >= 0 ? state.getVisibleSeriesCount() : getRowCount();
		int categoryCount = getColumnCount();
		if (seriesCount > 1) {
			double seriesGap = dataArea.getHeight() * getItemMargin() / (categoryCount * (seriesCount - 1));
			rectY = rectY + visibleRow * (state.getBarWidth() + seriesGap);
		} else {
			rectY = rectY + visibleRow * state.getBarWidth();
		}

		// BAR X
		Number meanValue = dataset.getMeanValue(row, column);
		if (meanValue == null) {
			return;
		}
		double value = meanValue.doubleValue();
		double base = 0.0;
		double lclip = getLowerClip();
		double uclip = getUpperClip();

		if (uclip <= 0.0) { // cases 1, 2, 3 and 4
			if (value >= uclip) {
				return; // bar is not visible
			}
			base = uclip;
			if (value <= lclip) {
				value = lclip;
			}
		} else if (lclip <= 0.0) { // cases 5, 6, 7 and 8
			if (value >= uclip) {
				value = uclip;
			} else {
				if (value <= lclip) {
					value = lclip;
				}
			}
		} else { // cases 9, 10, 11 and 12
			//if (value <= lclip) {
			//	return; // bar is not visible
			//}
			base = getLowerClip();
			if (value >= uclip) {
				value = uclip;
			}
		}

		RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
		double transY1 = rangeAxis.valueToJava2D(base, dataArea, yAxisLocation);
		double transY2 = (value == 0.0) ? transY1 : rangeAxis.valueToJava2D(value, dataArea, yAxisLocation);

		double rectX = Math.min(transY2, transY1);
		double rectHeight = state.getBarWidth();
		if (value > lclip) {
			double rectWidth = Math.abs(transY2 - transY1);
	
			Rectangle2D bar = new Rectangle2D.Double(rectX, rectY-1, rectWidth, rectHeight-3);
			Paint itemPaint = getItemPaint(row, column);
			GradientPaintTransformer t = getGradientPaintTransformer();
			if (t != null && itemPaint instanceof GradientPaint) {
				itemPaint = t.transform((GradientPaint) itemPaint, bar);
			}
			g2.setPaint(itemPaint);
			g2.fill(bar);
	
			// draw the outline...
			if (state.getBarWidth() > BAR_OUTLINE_WIDTH_THRESHOLD) {
				Stroke stroke = getItemOutlineStroke(row, column);
				Paint paint = getItemOutlinePaint(row, column);
				if (stroke != null && paint != null) {
					g2.setStroke(stroke);
					g2.setPaint(paint);
	
					/*Line2D line = new Line2D.Double(rectX, rectY, rectX+rectWidth, rectY);
					g2.draw(line);
					line = new Line2D.Double(rectX, (rectY+rectHeight), (rectX+rectWidth), (rectY+rectHeight));
					g2.draw(line);
					line = new Line2D.Double((rectX+rectWidth), rectY, (rectX+rectWidth), (rectY+rectHeight));
					g2.draw(line);*/
				}
			}
	
			// standard deviation lines
			Number n = dataset.getStdDevValue(row, column);
			if (n != null) {
				double w = 6d;
				double valueDelta = n.doubleValue();//getAdjustedValueForWidth(n.doubleValue());
				double highVal = 0;
				double lowVal = 0;
	
				NumberAxis numberAxis = (NumberAxis)rangeAxis;
				
				if(meanValue.doubleValue() >= (numberAxis.getRange().getUpperBound()-valueDelta)) {
					highVal = rangeAxis.valueToJava2D(meanValue.doubleValue(), dataArea, yAxisLocation);
				} else {
					highVal = rangeAxis.valueToJava2D(meanValue.doubleValue() + valueDelta, dataArea, yAxisLocation);
				}
				
				if(meanValue.doubleValue() <= (numberAxis.getRange().getLowerBound()+numberAxis.getTickUnit().getSize()+valueDelta)) {
					lowVal = rangeAxis.valueToJava2D(meanValue.doubleValue(), dataArea, yAxisLocation);
				} else {
					lowVal = rangeAxis.valueToJava2D(meanValue.doubleValue(), dataArea, yAxisLocation);
				}
	
				if (getErrorIndicatorPaint() != null) {
					g2.setPaint(getErrorIndicatorPaint());
				} else {
					g2.setPaint(getItemOutlinePaint(row, column));
				}
				if (getErrorIndicatorStroke() != null) {
					g2.setStroke(getErrorIndicatorStroke());
				} else {
					g2.setStroke(getItemOutlineStroke(row, column));
				}
				
			/*	Line2D line = new Line2D.Double(lowVal, rectY + rectHeight / 2.0d, highVal, rectY + rectHeight / 2.0d);
				g2.draw(line);
				line = new Line2D.Double(highVal, rectY + rectHeight * 0.25, highVal, rectY + rectHeight * 0.75);
				g2.draw(line);
				line = new Line2D.Double(lowVal, rectY + rectHeight * 0.25, lowVal, rectY + rectHeight * 0.75);
				g2.draw(line);*/
				
				double h = rectHeight * 0.5;
				//recalculate high/low val for rectangle.
				highVal = rangeAxis.valueToJava2D(meanValue.doubleValue() + valueDelta, dataArea, yAxisLocation)-w/2.0d;
				lowVal = rangeAxis.valueToJava2D(meanValue.doubleValue() , dataArea, yAxisLocation)-w/2.0d;
			/*	Rectangle2D box = new Rectangle2D.Double(lowVal+(highVal-lowVal)/2, (rectY + rectHeight / 2.0d)-h/2, w, h);
				g2.draw(box);
				g2.fill(box);
				*/
				if(meanValue.doubleValue() <= (numberAxis.getRange().getLowerBound()+numberAxis.getTickUnit().getSize()+valueDelta)) {
					drawItemLabel(g2, row, column, rectY, value, rectHeight, lowVal);
				} else {
					drawItemLabel(g2, row, column, rectY, value, rectHeight, lowVal);
				}
			} else {
				drawItemLabel(g2, row, column, rectY, value, rectHeight, rectX);
			}
			
	
			CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
			if (generator != null && isItemLabelVisible(row, column)) {
				drawItemLabel(g2, dataset, row, column, plot, generator, bar, (value < 0.0));
			}
	
			// add an item entity, if this information is being collected
			EntityCollection entities = state.getEntityCollection();
			if (entities != null) {
				addItemEntity(entities, dataset, row, column, bar);
			}
		} else if(showNotRespondedLabel) {
			Font labelFont = getItemLabelFont(row, column);
		    g2.setFont(labelFont);
		    Paint paint = getItemLabelPaint(row, column);
		    g2.setPaint(paint);
		    
		    FontMetrics fm = g2.getFontMetrics();
			Rectangle2D labelBounds = TextUtils.getTextBounds(""+Math.round(value), g2, fm);
			
			TextUtils.drawAlignedString("Student did not answer any questions for this Claim", g2,
		            (float) (rectX+2), 
		            (float) (rectY+labelBounds.getHeight()/2.0d), 
		            TextAnchor.CENTER_LEFT);
		}
	}

	protected void drawItemLabel(Graphics2D g2, int row, int column,
			double rectY, double value, double rectHeight, double lowVal) {
		if(isItemLabelVisible(row, column)) {
			Font labelFont = getItemLabelFont(row, column);
		    g2.setFont(labelFont);
		    Paint paint = getItemLabelPaint(row, column);
		    g2.setPaint(paint);
		    
		    FontMetrics fm = g2.getFontMetrics();
			Rectangle2D labelBounds = TextUtils.getTextBounds(""+Math.round(value), g2, fm);
			
			TextUtils.drawAlignedString(""+Math.round(value), g2,
		              (float) ((lowVal - 2 - labelBounds.getWidth()/2.0d)+23.0d), 
		              (float) ((rectY + rectHeight / 2.0d) - (labelBounds.getHeight() / 2.0d)-3.0d), 
		              TextAnchor.TOP_CENTER);
		}
	}

	public boolean isShowNotRespondedLabel() {
		return showNotRespondedLabel;
	}

	public void setShowNotRespondedLabel(boolean showNotRespondedLabel) {
		this.showNotRespondedLabel = showNotRespondedLabel;
	}
}
