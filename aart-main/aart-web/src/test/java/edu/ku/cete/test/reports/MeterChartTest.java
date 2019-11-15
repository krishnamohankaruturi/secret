package edu.ku.cete.test.reports;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;
import org.junit.Assert;
import org.junit.Test;

import edu.ku.cete.report.charts.ATSMeterPlot;
import edu.ku.cete.test.BaseTest;

public class MeterChartTest extends BaseTest {

	@Test
	public void testKAP() throws IOException {
		ATSMeterPlot plot = new ATSMeterPlot(new DefaultValueDataset(221));
		plot.setRange(new Range(101,119));
		plot.setNeedleType(ATSMeterPlot.ARROW_NEEDLE);
		//GradientPaint gradientPaint = new GradientPaint(0.0F, 0.0F, Color.ORANGE, 270, 300, Color.WHITE, false);

		plot.addInterval(new MeterInterval("1", new Range(101, 105), Color.BLACK, new BasicStroke(2.0f), new Color(236,107,56)));
		plot.addInterval(new MeterInterval("2", new Range(106, 109), Color.BLACK, new BasicStroke(2.0f), new Color(234,143,6)));
		plot.addInterval(new MeterInterval("3", new Range(110, 115), Color.BLACK, new BasicStroke(2.0f), new Color(251,198,104)));
		//plot.addInterval(new MeterInterval("4", new Range(230, 280), Color.BLACK, new BasicStroke(2.0f), new Color(244,217,81))); //sci
		plot.addInterval(new MeterInterval("4", new Range(116, 119), Color.BLACK, new BasicStroke(2.0f), new Color(248,238,164)));
		
		plot.setRangeLabelsVisible(true);

		JFreeChart chart = new JFreeChart(plot);
		chart.setBackgroundPaint(new Color(0, 0, 0, 0));
		chart.removeLegend();
		
//		BufferedImage bufferedImage = chart.createBufferedImage(500, 350, null);
//		Assert.assertNotNull(bufferedImage);
//		Assert.assertEquals(500, bufferedImage.getWidth());
		
		SVGGraphics2D g2 = new SVGGraphics2D(320, 130);
        Rectangle r = new Rectangle(0, 0, 320, 130);
        chart.draw(g2, r);
        File f = new File("meterchart.svg");
        SVGUtils.writeToSVG(f, g2.getSVGElement());
	}

	@Test
	public void testAMP() {
		ATSMeterPlot plot = new ATSMeterPlot(new DefaultValueDataset(195));
		plot.setRange(new Range(101,300));
		plot.setNeedleType(ATSMeterPlot.ARROW_NEEDLE);

		plot.addInterval(new MeterInterval("1", new Range(101, 149), Color.BLACK, new BasicStroke(2.0f), new Color(236,107,56)));
		plot.addInterval(new MeterInterval("2", new Range(150, 189), Color.BLACK, new BasicStroke(2.0f), new Color(234,143,6)));
		plot.addInterval(new MeterInterval("3", new Range(190, 229), Color.BLACK, new BasicStroke(2.0f), new Color(251,198,104)));
		plot.addInterval(new MeterInterval("4", new Range(230, 279), Color.BLACK, new BasicStroke(2.0f), new Color(244,217,81))); //sci
		plot.addInterval(new MeterInterval("5", new Range(280, 300), Color.BLACK, new BasicStroke(2.0f), new Color(248,238,164)));
		
		plot.setRangeLabelsVisible(true);

		JFreeChart chart = new JFreeChart(plot);
		chart.setBackgroundPaint(new Color(0, 0, 0, 0));
		chart.removeLegend();
		
		BufferedImage bufferedImage = chart.createBufferedImage(500, 350, null);
		Assert.assertNotNull(bufferedImage);
		Assert.assertEquals(500, bufferedImage.getWidth());
	}
}
