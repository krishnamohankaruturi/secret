package edu.ku.cete.report.charts;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.LogTick;
import org.jfree.chart.axis.TickType;
import org.jfree.chart.axis.ValueTick;
import org.jfree.chart.util.AttrStringUtils;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.ui.RectangleEdge;

public class SummaryValueAxis extends SubScoreValueAxis {
	private static final long serialVersionUID = -8268994746382873261L;
	
    @Override
    @SuppressWarnings("rawtypes")
    protected AxisState drawTickMarksAndLabels(Graphics2D g2,
            double cursor, Rectangle2D plotArea, Rectangle2D dataArea,
            RectangleEdge edge) {
        TextUtils.setDrawStringsWithFontAttributes(true);
        AxisState state = new AxisState(cursor);
        if (isAxisLineVisible()) {
            drawAxisLine(g2, cursor, dataArea, edge);
        }
		List ticks = refreshTicks(g2, state, dataArea, edge);
        state.setTicks(ticks);
        g2.setFont(getTickLabelFont());
        Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        Iterator iterator = ticks.iterator();
        int tickNumber = 0, middleTickNumber = Math.round(ticks.size()/2), lastTickNumber = ticks.size()-1;

        while (iterator.hasNext()) {
            ValueTick tick = (ValueTick) iterator.next();
            if (isTickLabelsVisible() && (tickNumber == 1 || tickNumber == lastTickNumber || tickNumber == middleTickNumber)) {
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
            	if(tickNumber == 1 || tickNumber == lastTickNumber || tickNumber == middleTickNumber) {
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
}
