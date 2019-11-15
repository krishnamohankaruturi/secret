package edu.ku.cete.report.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;

import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPosition;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryLabelWidthType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.chart.text.TextBlockAnchor;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.TextAnchor;

public class SubScoreBarPlot extends CategoryPlot {
	private static final long serialVersionUID = -8359200941590385528L;
	
	public SubScoreBarPlot(final CategoryDataset dataset, final Range range, final boolean drawXAxisLineOnly, final boolean yAxisTickLabelsVisible, final double tickUnit, boolean showNotRespondedLabel) {
		super();
		final CustomBarRenderer renderer = getBarRenderer();
		renderer.setShowNotRespondedLabel(showNotRespondedLabel);
		renderer.setErrorIndicatorPaint(Color.black);
		renderer.setDefaultOutlinePaint(Color.black);
		renderer.setDefaultOutlineStroke(new BasicStroke(.3f));
		renderer.setBarPainter(new StandardBarPainter());
		renderer.setShadowVisible(false);
		renderer.setMaximumBarWidth(.4);
		renderer.setDefaultItemLabelsVisible(false);
		// change the auto tick unit selection to integer units only...
		final NumberAxis valueAxis = getValueAxis();
		valueAxis.setTickLabelsVisible(false);
		valueAxis.setTickMarksVisible(true);
		valueAxis.setAxisLineVisible(false);
		valueAxis.setAutoTickUnitSelection(false);
		valueAxis.setTickUnit(new NumberTickUnit(tickUnit));
		valueAxis.setTickLabelInsets(RectangleInsets.ZERO_INSETS);
		valueAxis.setTickLabelFont(new Font("Verdana", Font.PLAIN, 8));
		valueAxis.setRange(range.getLowerBound()-2, range.getUpperBound()-2);
		valueAxis.setRangeWithMargins(valueAxis.getRange(),false,false);
		
		CategoryAxis categoryAxis = new SubScoreCategoryAxis();
		categoryAxis.setTickMarksVisible(false);
		categoryAxis.setTickLabelsVisible(false);
		categoryAxis.setAxisLineVisible(false);
		categoryAxis.setUpperMargin(0.10);
		categoryAxis.setLowerMargin(0.10);
		categoryAxis.setTickLabelFont(new Font("Verdana", Font.PLAIN, 9));
		AxisSpace space = new AxisSpace();
		space.setLeft(5.0);
		setFixedDomainAxisSpace(space);
		CategoryLabelPositions p = categoryAxis.getCategoryLabelPositions();
        CategoryLabelPosition left = new CategoryLabelPosition(
            RectangleAnchor.LEFT, TextBlockAnchor.CENTER_LEFT, 
            TextAnchor.CENTER_LEFT, 0.0,
            CategoryLabelWidthType.RANGE, .7f
        );
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.replaceLeftPosition(p, left));
        if(drawXAxisLineOnly) {
			valueAxis.setTickLabelsVisible(true);
			valueAxis.setUpperMargin(0.01d);
			valueAxis.setLowerMargin(0.01d);
		} else {
			categoryAxis.setTickLabelsVisible(yAxisTickLabelsVisible);
		}
		
		this.setOrientation(PlotOrientation.HORIZONTAL);
		this.setRangeGridlinesVisible(false);
		this.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
		this.setOutlineVisible(false);
		this.setDataset(dataset);

		this.setDomainAxis(categoryAxis);
		this.setRangeAxis(valueAxis);
		this.setRenderer(renderer);
		this.setAxisOffset(RectangleInsets.ZERO_INSETS);
		this.setInsets(RectangleInsets.ZERO_INSETS);
	}

	protected CustomBarRenderer getBarRenderer() {
		final CustomBarRenderer renderer = new CustomBarRenderer(new Paint[] { Color.decode("#89c8DF"), 
				Color.decode("#F1E59A"), 
				Color.decode("#ACEAAD"), 
				Color.decode("#F2BA88")});
		renderer.setDefaultItemLabelFont(new Font("Verdana", Font.PLAIN, 9));
		renderer.setDefaultItemLabelsVisible(false);
		return renderer;
	}

	protected NumberAxis getValueAxis() {
		final NumberAxis valueAxis = new SubScoreValueAxis();
		return valueAxis;
	}
}
