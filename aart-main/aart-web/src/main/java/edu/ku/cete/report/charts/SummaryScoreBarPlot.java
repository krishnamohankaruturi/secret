package edu.ku.cete.report.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;

public class SummaryScoreBarPlot extends SubScoreBarPlot {

	private static final long serialVersionUID = 7835065805005647361L;

	public SummaryScoreBarPlot(CategoryDataset dataset, Range range, boolean drawXAxisLineOnly, boolean yAxisTickLabelsVisible, boolean showNotRespondedLabel) {
		super(dataset, range, drawXAxisLineOnly, yAxisTickLabelsVisible, 10, showNotRespondedLabel);
		
		getRenderer().setDefaultOutlinePaint(Color.decode("#231f20"));
		getRenderer().setDefaultOutlineStroke(new BasicStroke(.75f));
		getRenderer().setDefaultItemLabelsVisible(true);
		getRenderer().setDefaultItemLabelFont(new Font("Verdana", Font.PLAIN, 8));
		getRenderer().setDefaultItemLabelPaint(Color.black);
		((BarRenderer) getRenderer()).setMaximumBarWidth(.3);
		Marker marker = new ValueMarker(range.getLowerBound()+(range.getUpperBound()-range.getLowerBound())/2);
        marker.setStroke(new BasicStroke(0.1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                0.1f, new float[] {3.0f, 3.0f}, 0.0f));
        marker.setPaint(Color.decode("#929597"));
        addRangeMarker(marker);
        
        Marker lowermarker = new ValueMarker(range.getLowerBound());
        lowermarker.setStroke(new BasicStroke(0.1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
               0.1f, new float[] {3.0f, 2.0f}, 0.0f));
        lowermarker.setPaint(Color.decode("#6d6e70"));        
        addRangeMarker(lowermarker);
        
        Marker uppermarker = new ValueMarker(range.getUpperBound());
        uppermarker.setStroke(new BasicStroke(0.1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
               0.1f, new float[] {3.0f, 2.0f}, 0.0f));
        uppermarker.setPaint(Color.decode("#6d6e70"));        
        addRangeMarker(uppermarker);
	}

	@Override
	protected CustomBarRenderer getBarRenderer() {
		final CustomBarRenderer renderer = new CustomBarRenderer(new Paint[] { Color.decode("#40ad48"), 
				Color.decode("#284496"), 
				Color.decode("#009ade")});

		renderer.setDefaultItemLabelFont(new Font("Verdana", Font.PLAIN, 9));
		renderer.setDefaultItemLabelsVisible(true);
		
		return renderer;
	}

	@Override
	protected NumberAxis getValueAxis() {
		return new SummaryValueAxis();
	}
}
