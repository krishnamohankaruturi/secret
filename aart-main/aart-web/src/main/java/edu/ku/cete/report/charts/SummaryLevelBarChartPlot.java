package edu.ku.cete.report.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.AxisLabelLocation;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPosition;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryLabelWidthType;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;
import org.jfree.data.category.CategoryDataset;
import org.jfree.chart.text.TextBlockAnchor;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.TextAnchor;

public class SummaryLevelBarChartPlot extends StudentLevelBarChartPlot {
	
	private static final long serialVersionUID = -706559608533669623L;
	private boolean isAxisLineOnly;
	private boolean isFirst;
	
	public SummaryLevelBarChartPlot(final CategoryDataset dataSet, String assessmentProgramCode, String gradeName, boolean isAxisLineOnly) {
		super(dataSet, assessmentProgramCode);
		this.isAxisLineOnly = isAxisLineOnly;
		CategoryAxis categoryAxis = getDomainAxis();
		categoryAxis.setTickLabelFont(new Font("Verdana", Font.PLAIN, 8));
		categoryAxis.setLabel(gradeName);
		categoryAxis.setLabelAngle(Math.PI/2);
		CategoryLabelPositions p = categoryAxis.getCategoryLabelPositions();
        CategoryLabelPosition left = new CategoryLabelPosition(RectangleAnchor.LEFT, TextBlockAnchor.CENTER_LEFT, 
            TextAnchor.CENTER_LEFT, 0.0, CategoryLabelWidthType.CATEGORY, 60f);
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.replaceLeftPosition(p, left));
        categoryAxis.setLabelInsets(new RectangleInsets(0, 2, 0, 15));

    	ValueAxis valueAxis = getRangeAxis();
		valueAxis.setUpperMargin(1);
		valueAxis.setLowerMargin(0);
		valueAxis.setAxisLineVisible(false);
		valueAxis.setTickLabelsVisible(false);
		
		AxisSpace space = new AxisSpace();
		space.setLeft(109.0);
		setFixedDomainAxisSpace(space);
		
       /* if(isAxisLineOnly) {
    		valueAxis.setAxisLineVisible(true);
    		valueAxis.setTickLabelsVisible(true);
    		valueAxis.setTickMarksVisible(true);
    		valueAxis.setTickMarkPaint(Color.decode("#B8B8B8"));
    		
    		CategoryAxis rightAxis = new CategoryAxis();
    		rightAxis.setLabelLocation(AxisLabelLocation.MIDDLE);
    		rightAxis.setTickMarksVisible(false);
    		rightAxis.setAxisLinePaint(Color.decode("#B8B8B8"));
    		rightAxis.setAxisLineVisible(true);
    		rightAxis.setAxisLineStroke(new BasicStroke(0.3f));
    		setDomainAxis(1, rightAxis);
    		setDomainAxisLocation(1, AxisLocation.TOP_OR_RIGHT);
        }*/
	}
	
	@Override
	public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo state) {
		super.draw(g2, area, anchor, parentState, state);
		
		if(!this.isAxisLineOnly) {
			g2.setPaint(Color.decode("#B8B8B8"));
	        g2.setStroke(new BasicStroke(0.3f));
	    //    Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
	    //    g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
	        
	        Line2D axisLine = new Line2D.Double(area.getX(), area.getHeight()+1.5, area.getMaxX(), area.getHeight()+1.5);
	        g2.draw(axisLine);
	        
	       /* axisLine = new Line2D.Double(area.getMinX(), area.getMinY(), area.getMinX(), area.getHeight()+2);
	        g2.draw(axisLine);
	        
	        axisLine = new Line2D.Double(area.getMaxX(), area.getMinY(), area.getMaxX(), area.getHeight()+3);
	        g2.draw(axisLine);*/
	        
	    /*    //top
	        if(!isFirst) {
		        axisLine = new Line2D.Double(area.getX(), area.getMinY()-0.5, area.getMaxX(), area.getMinY()-0.5);
		        g2.draw(axisLine);
	        }
	    /*    //draw grade line
	        axisLine = new Line2D.Double(54, area.getMinY()-0.5, 54, area.getHeight()+1.5);
	        g2.draw(axisLine);
	        */
	  //      g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
		}
	}

	
	@Override
	protected CategoryAxis initLeftAxis(Font levelLabelFont) {
		CategoryAxis categoryAxis = new CategoryAxis() {
			private static final long serialVersionUID = 6068180088826400492L;
			
			/*@Override
		    public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea,
		            Rectangle2D dataArea, RectangleEdge edge,
		            PlotRenderingInfo plotState) {

		        // if the axis is not visible, don't draw it...
		        if (!isVisible()) {
		            return new AxisState(cursor);
		        }

		        if (isAxisLineVisible()) {
		            drawAxisLine(g2, cursor, dataArea, edge);
		        }
		        AxisState state = new AxisState(cursor);
		        if (isTickMarksVisible()) {
		            drawTickMarks(g2, cursor, dataArea, edge, state);
		        }

		        createAndAddEntity(cursor, state, dataArea, edge, plotState);

		        // draw the category labels and axis label
		        state = drawCategoryLabels(g2, plotArea, dataArea, edge, state, plotState);
		        if (getAttributedLabel() != null) {
		            state = drawAttributedLabel(getAttributedLabel(), g2, plotArea, 
		                    dataArea, edge, state);
		            
		        } else {
		            state = drawGradeLabel(getLabel(), g2, plotArea, dataArea, edge, state);
		        }
		        return state;
		    }*/
			
		/*	@SuppressWarnings("rawtypes")
			@Override
		    public List refreshTicks(Graphics2D g2, AxisState state, 
		            Rectangle2D dataArea, RectangleEdge edge) {/*

		        List<Tick> ticks = new ArrayList<Tick>();

		        // sanity check for data area...
		        if (dataArea.getHeight() <= 0.0 || dataArea.getWidth() < 0.0) {
		            return ticks;
		        }

		        CategoryPlot plot = (CategoryPlot) getPlot();
		        List categories = plot.getCategoriesForAxis(this);
		        double max = 0.0;

		        if (categories != null) {
		            CategoryLabelPosition position = this.getCategoryLabelPositions().getLabelPosition(edge);
		            float r = this.getMaximumCategoryLabelWidthRatio();
		            if (r <= 0.0) {
		                r = position.getWidthRatio();
		            }

		            float l;
		            if (position.getWidthType() == CategoryLabelWidthType.CATEGORY) {
		                l = (float) calculateCategorySize(categories.size(), dataArea, edge);
		            }
		            else {
		                if (RectangleEdge.isLeftOrRight(edge)) {
		                    l = (float) dataArea.getWidth();
		                }
		                else {
		                    l = (float) dataArea.getHeight();
		                }
		            }
		            int categoryIndex = 0;
		            Iterator iterator = categories.iterator();
		            while (iterator.hasNext()) {
		                Comparable category = (Comparable) iterator.next();
		                g2.setFont(getTickLabelFont(category));
		                TextBlock label = createLabel(category, l * r, edge, g2);
		                if (edge == RectangleEdge.TOP || edge == RectangleEdge.BOTTOM) {
		                    max = Math.max(max, calculateTextBlockHeight(label, position, g2));
		                }
		                else if (edge == RectangleEdge.LEFT
		                        || edge == RectangleEdge.RIGHT) {
		                    max = Math.max(max, calculateTextBlockWidth(createFixedLabel("District **", l * r, edge, g2), position, g2));
		                }
		                Tick tick = new CategoryTick(category, label, position.getLabelAnchor(), 
		                		position.getRotationAnchor(), position.getAngle());
		                ticks.add(tick);
		                categoryIndex = categoryIndex + 1;
		            } 
		        }
		        state.setMax(max);
		        return ticks;
		    }*/
			
		  /*  protected AxisState drawGradeLabel(String label, Graphics2D g2,
		            Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge,
		            AxisState state) {

		        // it is unlikely that 'state' will be null, but check anyway...
		        ParamChecks.nullNotPermitted(state, "state");

		        if ((label == null) || (label.equals(""))) {
		            return state;
		        }

		        Font font = getLabelFont();
		        RectangleInsets insets = getLabelInsets();
		        g2.setFont(font);
		        g2.setPaint(getLabelPaint());
		        FontMetrics fm = g2.getFontMetrics();
		        Rectangle2D labelBounds = TextUtilities.getTextBounds("Grade 12", g2, fm);

		        if (edge == RectangleEdge.TOP) {
		            AffineTransform t = AffineTransform.getRotateInstance(
		                    getLabelAngle(), labelBounds.getCenterX(),
		                    labelBounds.getCenterY());
		            Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
		            labelBounds = rotatedLabelBounds.getBounds2D();
		            double labelx = labelLocationX(this.getLabelLocation(), dataArea);
		            double labely = state.getCursor() - insets.getBottom()
		                            - labelBounds.getHeight() / 2.0;
		            TextAnchor anchor = labelAnchorH(this.getLabelLocation());
		            TextUtilities.drawRotatedString(label, g2, (float) labelx,
		                    (float) labely, anchor, getLabelAngle(), TextAnchor.CENTER);
		            state.cursorUp(insets.getTop() + labelBounds.getHeight()
		                    + insets.getBottom());
		        }
		        else if (edge == RectangleEdge.BOTTOM) {
		            AffineTransform t = AffineTransform.getRotateInstance(
		                    getLabelAngle(), labelBounds.getCenterX(),
		                    labelBounds.getCenterY());
		            Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
		            labelBounds = rotatedLabelBounds.getBounds2D();
		            double labelx = labelLocationX(this.getLabelLocation(), dataArea);
		            double labely = state.getCursor()
		                            + insets.getTop() + labelBounds.getHeight() / 2.0;
		            TextAnchor anchor = labelAnchorH(this.getLabelLocation());
		            TextUtilities.drawRotatedString(label, g2, (float) labelx,
		                    (float) labely, anchor, getLabelAngle(), TextAnchor.CENTER);
		            state.cursorDown(insets.getTop() + labelBounds.getHeight()
		                    + insets.getBottom());
		        }
		        else if (edge == RectangleEdge.LEFT) {
		            AffineTransform t = AffineTransform.getRotateInstance(
		                    getLabelAngle() - Math.PI / 2.0, labelBounds.getCenterX(),
		                    labelBounds.getCenterY());
		            Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
		            labelBounds = rotatedLabelBounds.getBounds2D();
		            double labelx = state.getCursor()
		                            - insets.getRight() - labelBounds.getWidth() / 2.0;
		            double labely = labelLocationY(this.getLabelLocation(), dataArea);
		            TextAnchor anchor = labelAnchorV(this.getLabelLocation());
		            TextUtilities.drawRotatedString(label, g2, (float) labelx,
		                    (float) labely, anchor, getLabelAngle() - Math.PI / 2.0, 
		                    anchor);
		            state.cursorLeft(insets.getLeft() + labelBounds.getWidth()
		                    + insets.getRight());
		        }
		        else if (edge == RectangleEdge.RIGHT) {
		            AffineTransform t = AffineTransform.getRotateInstance(
		                    getLabelAngle() + Math.PI / 2.0,
		                    labelBounds.getCenterX(), labelBounds.getCenterY());
		            Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
		            labelBounds = rotatedLabelBounds.getBounds2D();
		            double labelx = state.getCursor()
		                            + insets.getLeft() + labelBounds.getWidth() / 2.0;
		            double labely = labelLocationY(this.getLabelLocation(), dataArea);
		            TextAnchor anchor = labelAnchorV(this.getLabelLocation());
		            TextUtilities.drawRotatedString(label, g2, (float) labelx,
		                    (float) labely, anchor, getLabelAngle() + Math.PI / 2.0, 
		                    anchor);
		            state.cursorRight(insets.getLeft() + labelBounds.getWidth()
		                    + insets.getRight());
		        }
		        return state;
		    }
			*/
		   /* protected TextBlock createFixedLabel(String category, float width,
		            RectangleEdge edge, Graphics2D g2) {
		        TextBlock label = TextUtilities.createTextBlock(category.toString(),
		                getTickLabelFont(category), getTickLabelPaint(category), width,
		                this.getMaximumCategoryLabelLines(), new G2TextMeasurer(g2));
		        return label;
		    }*/
		};
		
		/*
		 * For displaying Y-Axis grade label
		 */
		categoryAxis.setLabelFont(levelLabelFont);
		categoryAxis.setLabelLocation(AxisLabelLocation.MIDDLE);
		categoryAxis.setTickMarksVisible(false);
		categoryAxis.setAxisLinePaint(Color.decode("#B8B8B8"));
		categoryAxis.setAxisLineVisible(false);
		categoryAxis.setAxisLineStroke(new BasicStroke(0.3f));
		return categoryAxis;
	}

	@Override
	protected void initTopAxis() {
		//do nothing
	}

	@Override
	protected void initRightAxis() {
		//do nothing
	}
	
	/*@Override
	protected void initBarRenderer(final CategoryDataset dataSet, final String assessmentProgramCode) {
		Paint[] levelColors = getBarColors(dataSet, assessmentProgramCode);
		StudentLevelChartBarRenderer extBarRenderer = new StudentLevelChartBarRenderer(
				levelColors);
		extBarRenderer.setBaseItemLabelFont(new Font("Verdana", Font.PLAIN, 9));

		setRenderer(extBarRenderer);
	}*/

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
}
