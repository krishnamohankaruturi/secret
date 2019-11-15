package edu.ku.cete.test.reports;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.jfree.chart.JFreeChart;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.junit.Assert;
import org.junit.Test;

import edu.ku.cete.report.charts.SubScoreBarPlot;

public class SubScoreBarPlotTest {

	@Test
	public void testSubScorePlot() {
		Range range = new Range(100L, 250L);
		SubScoreBarPlot plot = new SubScoreBarPlot(createDataset(), range, false, false, 10, false);
		JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, false);

		chart.setBackgroundPaint(new Color(0, 0, 0, 0));
		chart.removeLegend();
		
		BufferedImage bufferedImage = chart.createBufferedImage(500, 350, null);
		Assert.assertNotNull(bufferedImage);
		Assert.assertEquals(500, bufferedImage.getWidth());
	}

	private CategoryDataset createDataset() {
		DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();
		dataset.add(10D, 1.2D, "Row 1", "Column 1");
		dataset.add(15D, 1.2D, "Row 1", "Column 2");
		dataset.add(13D, 1.2D, "Row 1", "Column 3");
		dataset.add(8D, 1.2D, "Row 1", "Column 4");
		
		return dataset;
	}
}
