package edu.ku.cete.report.charts;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.LogTick;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickType;
import org.jfree.chart.axis.ValueTick;
import org.jfree.chart.util.AttrStringUtils;
import org.jfree.data.Range;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.ui.RectangleEdge;

public class SubScoreValueAxis extends NumberAxis {
	private static final long serialVersionUID = -847249607745204585L;
	
	private int displayTicksFrom = 1;
	private int displayTickLabelsFrom = 1;
	
    /**
     * Calculates the value of the lowest visible tick on the axis.
     *
     * @return The value of the lowest visible tick on the axis.
     *
     * @see #calculateHighestVisibleTickValue()
     */
    protected double calculateLowestVisibleTickValue() {
       // double unit = getTickUnit().getSize();
       // double index = Math.ceil(getRange().getLowerBound() / unit);
        return getRange().getLowerBound();
    }

    /**
     * Calculates the value of the highest visible tick on the axis.
     *
     * @return The value of the highest visible tick on the axis.
     *
     * @see #calculateLowestVisibleTickValue()
     */
    protected double calculateHighestVisibleTickValue() {
       // double unit = getTickUnit().getSize();
      //  double index = Math.floor(getRange().getUpperBound() / unit);
        return getRange().getUpperBound(); //index * unit;
    }

    /**
     * Calculates the number of visible ticks.
     *
     * @return The number of visible ticks on the axis.
     */
    protected int calculateVisibleTickCount() {
        double unit = getTickUnit().getSize();
        Range range = getRange();
        return (int) ((range.getUpperBound() - range.getLowerBound())/unit)+1;
    }
    
    @Override
    @SuppressWarnings("rawtypes")
    protected AxisState drawTickMarksAndLabels(Graphics2D g2,
            double cursor, Rectangle2D plotArea, Rectangle2D dataArea,
            RectangleEdge edge) {

        AxisState state = new AxisState(cursor);
        if (isAxisLineVisible()) {
            drawAxisLine(g2, cursor, dataArea, edge);
        }
		List ticks = refreshTicks(g2, state, dataArea, edge);
        state.setTicks(ticks);
        g2.setFont(getTickLabelFont());
        Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, 
                RenderingHints.VALUE_STROKE_NORMALIZE);
        Iterator iterator = ticks.iterator();
        int tickNumber = 0;
        TextUtils.setDrawStringsWithFontAttributes(true);
        while (iterator.hasNext()) {
            ValueTick tick = (ValueTick) iterator.next();
            if (isTickLabelsVisible() && tickNumber >= getDisplayTickLabelsFrom()) {
                g2.setPaint(getTickLabelPaint());
                float[] anchorPoint = calculateAnchorPoint(tick, cursor,
                        dataArea, edge);
                if (tick instanceof LogTick) {
                    LogTick lt = (LogTick) tick;
                    if (lt.getAttributedLabel() == null) {
                        continue;
                    }
                    AttrStringUtils.drawRotatedString(lt.getAttributedLabel(), 
                            g2, anchorPoint[0], anchorPoint[1], 
                            tick.getTextAnchor(), tick.getAngle(), 
                            tick.getRotationAnchor());
                } else {
                    if (tick.getText() == null) {
                        continue;
                    }
                    TextUtils.drawRotatedString(tick.getText(), g2,
                            anchorPoint[0], anchorPoint[1], 
                            tick.getTextAnchor(), tick.getAngle(), 
                            tick.getRotationAnchor());
                }
            }

            if ((isTickMarksVisible() && tick.getTickType().equals(TickType.MAJOR)) 
            		|| (isMinorTickMarksVisible() && tick.getTickType().equals(TickType.MINOR))) {
            	if(tickNumber >= getDisplayTicksFrom()) {
	                double ol = (tick.getTickType().equals(TickType.MINOR)) 
	                        ? getMinorTickMarkOutsideLength() : getTickMarkOutsideLength();

	                double il = (tick.getTickType().equals(TickType.MINOR)) 
	                        ? getMinorTickMarkInsideLength() : getTickMarkInsideLength();

	                float xx = (float) valueToJava2D(tick.getValue(), dataArea, edge);
	                Line2D mark = null;
	                g2.setStroke(getTickMarkStroke());
	                g2.setPaint(getTickMarkPaint());
	                if (edge == RectangleEdge.LEFT) {
	                    mark = new Line2D.Double(cursor - ol, xx, cursor + il, xx);
	                }
	                else if (edge == RectangleEdge.RIGHT) {
	                    mark = new Line2D.Double(cursor + ol, xx, cursor - il, xx);
	                }
	                else if (edge == RectangleEdge.TOP) {
	                    mark = new Line2D.Double(xx, cursor - ol, xx, cursor + il);
	                }
	                else if (edge == RectangleEdge.BOTTOM) {
	                    mark = new Line2D.Double(xx, cursor + ol, xx, cursor - il);
	                }
	                g2.draw(mark);
            	}
            }
            tickNumber++;
        }
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
        
        // need to work out the space used by the tick labels...
        // so we can update the cursor...
        double used = 0.0;
        if (isTickLabelsVisible()) {
            if (edge == RectangleEdge.LEFT) {
                used += findMaximumTickLabelWidth(ticks, g2, plotArea,
                        isVerticalTickLabels());
                state.cursorLeft(used);
            } else if (edge == RectangleEdge.RIGHT) {
                used = findMaximumTickLabelWidth(ticks, g2, plotArea,
                        isVerticalTickLabels());
                state.cursorRight(used);
            } else if (edge == RectangleEdge.TOP) {
                used = findMaximumTickLabelHeight(ticks, g2, plotArea,
                        isVerticalTickLabels());
                state.cursorUp(used);
            } else if (edge == RectangleEdge.BOTTOM) {
                used = findMaximumTickLabelHeight(ticks, g2, plotArea,
                        isVerticalTickLabels());
                state.cursorDown(used);
            }
        }

        return state;
    }

	public int getDisplayTicksFrom() {
		return displayTicksFrom;
	}

	public void setDisplayTicksFrom(int displayTicksFrom) {
		this.displayTicksFrom = displayTicksFrom;
	}

	public int getDisplayTickLabelsFrom() {
		return displayTickLabelsFrom;
	}

	public void setDisplayTickLabelsFrom(int displayTickLabelsFrom) {
		this.displayTickLabelsFrom = displayTickLabelsFrom;
	}
}
