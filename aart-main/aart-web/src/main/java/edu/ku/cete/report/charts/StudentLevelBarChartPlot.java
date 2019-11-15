package edu.ku.cete.report.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.axis.AxisLabelLocation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.LogTick;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickType;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.axis.ValueTick;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.util.AttrStringUtils;
import org.jfree.data.category.CategoryDataset;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;


public class StudentLevelBarChartPlot extends CategoryPlot {
	private static final long serialVersionUID = -706559608533669623L;
	protected Font levelLabelFont = new Font("Verdana", Font.BOLD, 8);
	protected Font categoryLabelFont = new Font("Verdana", Font.BOLD, 9);
	private String apCode;
	public StudentLevelBarChartPlot(final CategoryDataset dataSet, final String assessmentProgramCode) {
		super();
		apCode = assessmentProgramCode;
		CategoryAxis categoryAxis = initLeftAxis(levelLabelFont);
		ValueAxis valueAxis = initValueAxis(levelLabelFont);
		initBarRenderer(dataSet, assessmentProgramCode);
		initTopAxis();
		initRightAxis();
		
		this.setDataset(dataSet);
		this.setDomainAxis(categoryAxis);
		this.setRangeAxis(valueAxis);
		this.setOrientation(PlotOrientation.HORIZONTAL);
		this.setAxisOffset(new RectangleInsets(0, 0, 0, 0));
		this.setBackgroundPaint(Color.WHITE);
		this.setOutlineVisible(false);
		this.setDomainGridlinesVisible(false);
		this.setRangeGridlinesVisible(false);
		this.setRangeZeroBaselineVisible(false);
		this.setRangeZeroBaselineStroke(new BasicStroke(1.5f));
		this.setRangeZeroBaselinePaint(Color.decode("#7C2C3D"));
		this.setRangeAxisLocation(0, AxisLocation.BOTTOM_OR_RIGHT);
		this.setInsets(new RectangleInsets(0, 0, 0, 0)); // to fix display of 100%
	}

	protected Paint[] getBarColors(final CategoryDataset dataSet,
			final String assessmentProgramCode) {
		Paint levelColors[] = null;

		if (assessmentProgramCode.equalsIgnoreCase("AMP")) {
			levelColors = new Paint[] { Color.decode("#F6B065"), // level1
					Color.decode("#F3EC64"),
					Color.decode("#A7CF52"), Color.decode("#CDBFDE") };
		} else if(dataSet.getRowKeys().size() > 4) {
			levelColors = new Paint[] { Color.decode("#EC6B38"), // level1
					Color.decode("#EA8F06"),
					Color.decode("#FBC668"), Color.decode("#F4D951"), 
					Color.decode("#F8EEA4") };
		} else {
			levelColors = new Paint[] { Color.decode("#EC6B38"), // level1
					Color.decode("#EA8F06"),
					Color.decode("#FBC668"), Color.decode("#F8EEA4") };
		}

		return levelColors;
	}

	protected void initBarRenderer(final CategoryDataset dataSet, final String assessmentProgramCode) {
		Paint[] levelColors = getBarColors(dataSet, assessmentProgramCode);
		StudentLevelChartBarRenderer extBarRenderer = new StudentLevelChartBarRenderer(
				levelColors);
		extBarRenderer.setDefaultItemLabelFont(levelLabelFont);

		setRenderer(extBarRenderer);
	}

	protected CategoryAxis initLeftAxis(Font levelLabelFont) {
		CategoryAxis categoryAxis = new CategoryAxis();
		categoryAxis.setLabelFont(levelLabelFont);
		categoryAxis.setLabelLocation(AxisLabelLocation.MIDDLE);
		categoryAxis.setTickMarksVisible(false);
		categoryAxis.setAxisLinePaint(Color.decode("#B8B8B8"));
		categoryAxis.setAxisLineVisible(true);
		categoryAxis.setAxisLineStroke(new BasicStroke(0.3f));
		return categoryAxis;
	}

	protected void initTopAxis() {
		final NumberAxis topAxis = new NumberAxis();
		topAxis.setRange(-100, 100);
		topAxis.setTickUnit(new NumberTickUnit(20));
		topAxis.setTickLabelsVisible(false);
		topAxis.setTickMarksVisible(false);
		topAxis.setAxisLinePaint(Color.decode("#B8B8B8"));
		topAxis.setAxisLineVisible(true);
		topAxis.setAxisLineStroke(new BasicStroke(0.3f));
		topAxis.setLowerMargin(0);
		topAxis.setUpperMargin(0);
		this.setRangeAxis(1, topAxis);
		this.setRangeAxisLocation(1, AxisLocation.TOP_OR_RIGHT);
	}

	protected void initRightAxis() {
		CategoryAxis rightAxis = new CategoryAxis();
		rightAxis.setLabelLocation(AxisLabelLocation.MIDDLE);
		rightAxis.setTickMarksVisible(false);
		rightAxis.setAxisLinePaint(Color.decode("#B8B8B8"));
		rightAxis.setAxisLineVisible(true);
		rightAxis.setAxisLineStroke(new BasicStroke(0.3f));
		this.setDomainAxis(1, rightAxis);
		this.setDomainAxisLocation(1, AxisLocation.TOP_OR_RIGHT);
	}

	protected ValueAxis initValueAxis(Font labelFont) {
		NumberAxis valueAxis = new NumberAxis() {
			private static final long serialVersionUID = -5702671258100270329L;
			
			@SuppressWarnings("rawtypes")
			@Override
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
		        while (iterator.hasNext()) {
		            ValueTick tick = (ValueTick) iterator.next();
		            if (isTickLabelsVisible()) {
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
		                    if (tick.getText() == null || (apCode.equalsIgnoreCase("AMP") && (int)tick.getValue() != 0)) {
		                        continue;
		                    }
		                    TextUtils.drawRotatedString(tick.getText(), g2,
		                            anchorPoint[0], anchorPoint[1], 
		                            tick.getTextAnchor(), tick.getAngle(), 
		                            tick.getRotationAnchor());
		                }
		            }

		            if ((isTickMarksVisible() && tick.getTickType().equals(
		                    TickType.MAJOR)) || (isMinorTickMarksVisible()
		                    && tick.getTickType().equals(TickType.MINOR))) {

		                double ol = (tick.getTickType().equals(TickType.MINOR)) 
		                        ? getMinorTickMarkOutsideLength()
		                        : getTickMarkOutsideLength();

		                double il = (tick.getTickType().equals(TickType.MINOR)) 
		                        ? getMinorTickMarkInsideLength()
		                        : getTickMarkInsideLength();

		                float xx = (float) valueToJava2D(tick.getValue(), dataArea,
		                        edge);
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
		};
		//Java-CoE -start
		//valueAxis.setRange(-100, 100);
		valueAxis.setRange(0, 100);
		//Java-CoE - end
		valueAxis.setTickMarksVisible(false);
		valueAxis.setLabelFont(labelFont);
		valueAxis.setLabelLocation(AxisLabelLocation.MIDDLE);
		valueAxis.setTickUnit(new NumberTickUnit(20));
		valueAxis.setNumberFormatOverride(new PercentNumberFormat());
		valueAxis.setAxisLinePaint(Color.decode("#B8B8B8"));
		valueAxis.setAxisLineVisible(true);
		valueAxis.setAxisLineStroke(new BasicStroke(0.3f));
		return valueAxis;
	}
}
