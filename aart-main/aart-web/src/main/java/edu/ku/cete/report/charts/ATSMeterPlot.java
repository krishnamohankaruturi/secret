package edu.ku.cete.report.charts;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.ValueDataset;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.TextAnchor;

public class ATSMeterPlot extends MeterPlot {
	private static final long serialVersionUID = -7272295079775658057L;

	public static final String ARROW_NEEDLE = "ARROW";

	private String needleType;

	public static final double THETA = Math.toRadians(28); // arrowhead
															// sharpness
	public static final int ARROW_SIZE = 10; // arrowhead length

	private boolean rangeLabelsVisible;

	public ATSMeterPlot() {
		super();
		this.initMeterDefaults();
	}

	public ATSMeterPlot(DefaultValueDataset defaultValueDataset) {
		super(defaultValueDataset);
		this.initMeterDefaults();
	}

	private void initMeterDefaults() {
		this.setMeterAngle(180);
		this.setDialShape(DialShape.PIE);
		this.setTickLabelsVisible(false);
		this.setTickLabelFont(new Font("Verdana", Font.PLAIN, 12));
		this.setTickLabelPaint(Color.blue);
		this.setTickLabelFormat(DecimalFormat.getInstance());
		this.setTickPaint(new Color(0, 0, 0, 0)); // to remove tick
		// this.setOutlineVisible(true);
		this.setDialBackgroundPaint(new Color(0, 0, 0, 0)); // to remove border
		// this.setDrawBorder(true);
		this.setNeedlePaint(new Color(33, 63, 153)); // this.setNeedlePaint(new
														// Color(55, 132, 198));
		this.setValuePaint(new Color(0, 0, 0, 0));
		rangeLabelsVisible = false;
		needleType = "ARROW";
	}

	@Override
	public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor,
			PlotState parentState, PlotRenderingInfo info) {
		if (info != null) {
			info.setPlotArea(area);
		}
		// adjust for insets...
		RectangleInsets insets = getInsets();
		insets.trim(area);

		area.setRect(area.getX(), area.getY(), area.getWidth(), area.getHeight());

		// draw the background
		if (this.getDrawBorder()) {
			drawBackground(g2, area);
		}

		// adjust the plot area by the interior spacing value
		double gapHorizontal = (2 * DEFAULT_BORDER_SIZE);
		double gapVertical = (2 * DEFAULT_BORDER_SIZE * 4);
		double meterX = area.getX() + gapHorizontal / 2;
		double meterY = area.getY() + gapVertical / 2;
		if (this.rangeLabelsVisible) {
			meterY = 15 + area.getY() + gapVertical / 2;
		}
		double meterW = area.getWidth() - gapHorizontal;
		double meterH = area.getHeight() - gapVertical
				+ ((this.getMeterAngle() <= 180) && (this.getDialShape() != DialShape.CIRCLE) ? 
						area.getHeight() / 1.25 : 0);

		double min = Math.min(meterW, meterH) / 2;
		meterX = (meterX + meterX + meterW) / 2 - min;
		meterY = (meterY + meterY + meterH) / 2 - min;
		meterW = 2 * min;
		meterH = 2 * min;

		Rectangle2D meterArea = new Rectangle2D.Double(meterX, meterY, meterW, meterH);

		Rectangle2D.Double originalArea = new Rectangle2D.Double(
				meterArea.getX() - 3, meterArea.getY() - 3,
				meterArea.getWidth() + 4, meterArea.getHeight() + 4);

		double meterMiddleX = meterArea.getCenterX();
		double meterMiddleY = meterArea.getCenterY();

		// plot the data (unless the dataset is null)...
		ValueDataset data = getDataset();
		if (data != null) {
			double dataMin = this.getRange().getLowerBound();
			double dataMax = this.getRange().getUpperBound();

			Shape savedClip = g2.getClip();
			g2.clip(originalArea);

			Composite originalComposite = g2.getComposite();
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					getForegroundAlpha()));

			if (this.getDialBackgroundPaint() != null) {
				fillArc(g2, originalArea, dataMin, dataMax,
						this.getDialBackgroundPaint(), true);
			}
			drawTicks(g2, meterArea, dataMin, dataMax);
			drawArcForInterval(g2, meterArea, new MeterInterval("", this.getRange(), this
							.getDialOutlinePaint(), new BasicStroke(1f), null));

			double radius = (meterArea.getWidth() / 2) + DEFAULT_BORDER_SIZE - 4.8f;

			// draw regions
			for (int i = 0; i < this.getIntervals().size(); i++) {
				MeterInterval interval = (MeterInterval) this.getIntervals().get(i);
				MeterInterval tmpInterval =interval;
				if (i != 0) {
					MeterInterval prevInterval = (MeterInterval) this.getIntervals().get(i-1);
					tmpInterval = new MeterInterval(interval.getLabel(), 
							new Range(interval.getRange().getLowerBound()-(interval.getRange().getLowerBound()-prevInterval.getRange().getUpperBound()), 
							interval.getRange().getUpperBound()), interval.getOutlinePaint(), 
							interval.getOutlineStroke(), interval.getBackgroundPaint());
				}
				drawArcForInterval(g2, meterArea, tmpInterval);
			}

			// draw inner borders for regions
			g2.setPaint(Color.BLACK);
			g2.setStroke(new BasicStroke(0.65f));

			for (int i = 0; i < this.getIntervals().size(); i++) {
				MeterInterval interval = (MeterInterval) this.getIntervals().get(i);
				if (i != (this.getIntervals().size() - 1)) {
					double valueAngle = -valueToAngle(interval.getRange().getUpperBound());
					double endX = meterMiddleX + ((radius+(2.0f)) * Math.cos(Math.PI * (valueAngle / 180)));
					double endY = meterMiddleY + ((radius+(1.0f)) * Math.sin(Math.PI * (valueAngle / 180)));
					if (i == 0 || i == 1 ) {
						if(i == 0){
							endX = endX + (0.6f);
							endY = endY - (0.4f);							
						}
						if(i == 1){
							endX = endX +0.35f;
							endY = endY - (0.4f);	
						}
						     Shape x1Line = new Line2D.Double(meterMiddleX, (meterMiddleY),
						        (endX), (endY));						    
						     g2.draw(x1Line);
					}
					if (i == 2) {
						endX = endX - 0.2f;
						endY = endY - 0.1f;
						Shape x1Line = new Line2D.Double(meterMiddleX, (meterMiddleY),
						        (endX), (endY));						     
						     g2.draw(x1Line);
					}
				}
			}

			// draw inner white circle
			double meterW1 = area.getWidth() - (area.getWidth() * .35) - gapHorizontal;
			double meterH1 = area.getHeight() - (area.getHeight() * .35)
					- gapVertical + ((this.getMeterAngle() <= 180)
							&& (this.getDialShape() != DialShape.CIRCLE) ? (area
							.getHeight() - (area.getHeight() * .45)) / 1.25 : 0);
			double min1 = Math.min(meterW1, meterH1) / 2;
			double meterX1 = (meterX + meterX + meterW) / 2 - min1;
			double meterY1 = (meterY + meterY + meterH) / 2 - min1;
			meterW1 = 2 * min1;
			meterH1 = 2 * min1;
			Rectangle2D meterArea1 = new Rectangle2D.Double(meterX1+0.20, meterY1, meterW1, meterH1);
			drawArcForInterval(g2, meterArea1, new MeterInterval("", this.getRange(), Color.BLACK,
							new BasicStroke(1f), Color.WHITE));

			// draw start and end borders
			g2.setPaint(Color.BLACK);
			g2.setStroke(new BasicStroke(1.0f));
			for (int i = 0; i < this.getIntervals().size(); i++) {
				MeterInterval interval = (MeterInterval) this.getIntervals().get(i);
				if (i == 0) {
					double valueAngle = -valueToAngle(interval.getRange()
							.getLowerBound());
					double endX = meterMiddleX + ((radius + 2) * Math.cos(Math.PI
									* (valueAngle / 180)));
					double endY = meterMiddleY + ((radius + 2) * Math.sin(Math.PI
									* (valueAngle / 180)));

					g2.drawLine((int) meterMiddleX, (int) (meterMiddleY + 1),
							(int) (endX), (int) (endY + 1));
				} else if (i == (this.getIntervals().size() - 1)) {
					double valueAngle = -valueToAngle(interval.getRange()
							.getUpperBound());
					double endX = meterMiddleX
							+ ((radius + 3) * Math.cos(Math.PI
									* (valueAngle / 180)));
					double endY = meterMiddleY
							+ ((radius + 3) * Math.sin(Math.PI
									* (valueAngle / 180)));

					g2.drawLine((int) meterMiddleX, (int) (meterMiddleY + 1),
							(int) (endX), (int) (endY + 1));
				}
			}

			Number n = data.getValue();

			g2.setClip(savedClip);
			g2.setComposite(originalComposite);

			// Draw labels
			drawRangeLabelAndValue(g2, meterMiddleX, meterMiddleY, radius, n);
			
			if (n != null) {
			//changes for increasing line height (removing arrow line header)
				renderArrowNeedle(g2, meterMiddleX, meterMiddleY, radius+2.5, n);
			}

			double radius1 = (meterArea1.getWidth() / 2) + DEFAULT_BORDER_SIZE - 5;

			// region labels
			g2.setPaint(Color.BLACK);
			if (this.rangeLabelsVisible) {
				g2.setFont(new Font("Verdana", Font.BOLD, 11));
			} else {
				g2.setFont(new Font("Verdana", Font.PLAIN, 9));
			}

			TextUtils.setDrawStringsWithFontAttributes(true);
			if (this.rangeLabelsVisible) {
				for (int i = 0; i < this.getIntervals().size(); i++) {
					MeterInterval interval = (MeterInterval) this.getIntervals()
							.get(i);

					double valueAngle = -valueToAngle(((interval.getRange()
							.getUpperBound() - interval.getRange().getLowerBound()) / 2)
							+ interval.getRange().getLowerBound());
					double endX = meterMiddleX
							+ ((radius - (radius - radius1) / 2) * Math.cos(Math.PI
									* (valueAngle / 180)));
					double endY = meterMiddleY
							+ ((radius - (radius - radius1) / 2) * Math.sin(Math.PI
									* (valueAngle / 180)));

					FontMetrics fm = g2.getFontMetrics();
					Rectangle2D tickLabelBounds = TextUtils.getTextBounds(
							interval.getLabel(), g2, fm);
					
					TextUtils.drawAlignedString(interval.getLabel(), g2,
							(int) (endX - tickLabelBounds.getWidth() + 7),
							(int) (endY - (tickLabelBounds.getHeight() / 2)),
							TextAnchor.TOP_CENTER);
				}
			}
		}
		if (this.getDrawBorder()) {
			drawOutline(g2, area);
		}
	}

	private void drawRangeLabelAndValue(Graphics2D g2, double meterMiddleX, double meterMiddleY, double radius, Number n) {
		g2.setPaint(Color.BLACK);
		g2.setStroke(new BasicStroke(1.0f));
		TextUtils.setDrawStringsWithFontAttributes(true);
		double arrowAngle=0,arrowLabelX=0,arrowLabelY = 0;
		Rectangle2D arrowLblRect=null;
		double scoreEndX =0.0;
		double scoreEndY =0.0;			
		//get arrow label text bounds
		if (n != null) {
			g2.setFont(new Font("Verdana", Font.BOLD, 11));
			FontMetrics fm = g2.getFontMetrics();
			Rectangle2D arrowLabelBounds = TextUtils.getTextBounds(""+ new Double(n.doubleValue()).intValue(), g2,fm);
			arrowAngle = -valueToAngle(n.doubleValue());
		 	arrowLabelX = meterMiddleX + ((radius + arrowLabelBounds.getWidth()) * Math.cos(Math.PI * (arrowAngle / 180)));
		 	arrowLabelY = meterMiddleY + ((radius + 12) * Math.sin(Math.PI * (arrowAngle / 180)));
			arrowLblRect = new Rectangle2D.Double(arrowLabelX, arrowLabelY,arrowLabelBounds.getWidth(),arrowLabelBounds.getHeight());
		}
		if (this.rangeLabelsVisible) {
			g2.setFont(new Font("Verdana", Font.PLAIN, 10));
			FontMetrics fm = g2.getFontMetrics();
			for (int i = 0; i < this.getIntervals().size(); i++) {
				MeterInterval interval = (MeterInterval) this.getIntervals().get(i);
	
				if (i == 0) {
					double valueAngle = -valueToAngle(interval.getRange().getLowerBound());
					Rectangle2D tickLabelBounds = TextUtils.getTextBounds("" + new Double(interval.getRange().getLowerBound()).intValue(),
									g2, fm);
	
					double endX = meterMiddleX + ((radius + tickLabelBounds.getWidth()) * Math.cos(Math.PI * (valueAngle / 180)));
					double endY = (meterMiddleY - tickLabelBounds.getHeight() / 2);
					TextUtils.drawAlignedString(""+ new Double(interval.getRange().getLowerBound()).intValue(), g2,
							(int) endX, (int) endY, TextAnchor.HALF_ASCENT_CENTER);
					
					if(n != null && arrowLblRect != null) {
						Rectangle2D lblRect = new Rectangle2D.Double(endX, endY,tickLabelBounds.getWidth(),tickLabelBounds.getHeight());
						while(arrowLblRect.intersects(lblRect)) {
							arrowLabelY = arrowLabelY - 2;
							arrowLblRect = new Rectangle2D.Double(arrowLabelX, arrowLabelY,arrowLblRect.getWidth(),arrowLblRect.getHeight());
						}
					}
				}
				double rvalue = interval.getRange().getUpperBound();
				if (i < this.getIntervals().size()-1) {
					MeterInterval nextinterval = (MeterInterval) this.getIntervals().get(i+1);
					rvalue = nextinterval.getRange().getLowerBound();
				}
				double valueAngle = -valueToAngle(interval.getRange().getUpperBound());
				Rectangle2D tickLabelBounds = TextUtils.getTextBounds(""+ new Double(interval.getRange()
										.getUpperBound()).intValue(), g2,fm);
	
				double endX = meterMiddleX + ((radius + tickLabelBounds.getWidth()) * Math.cos(Math.PI * (valueAngle / 180)));
				double endY = meterMiddleY + ((radius + 12) * Math.sin(Math.PI * (valueAngle / 180)));
				if(i == 1){
					scoreEndX =  endX;
					scoreEndY = endY - 15;		
				}				
				if (i == (this.getIntervals().size() - 1)) {
					endY = meterMiddleY - tickLabelBounds.getHeight() / 2;
					endX = endX - 2;
				}
	
				TextUtils.drawAlignedString("" + new Double(rvalue).intValue(), g2,
						(int) (endX), (int) (endY), TextAnchor.HALF_ASCENT_CENTER);
				if(n != null && arrowLblRect != null) {
					Rectangle2D lblRect = new Rectangle2D.Double(endX, endY,tickLabelBounds.getWidth(),tickLabelBounds.getHeight());
					while(arrowLblRect.intersects(lblRect)) {
						arrowLabelY = arrowLabelY - 2;
						arrowLblRect = new Rectangle2D.Double(arrowLabelX, arrowLabelY,arrowLblRect.getWidth(),arrowLblRect.getHeight());
					}
				}
			}
			if(n != null) {
				g2.setFont(new Font("Verdana", Font.BOLD, 11));
				TextUtils.drawAlignedString("" + new Double(n.doubleValue()).intValue(),
						g2, (int) (scoreEndX), (int) (scoreEndY), TextAnchor.HALF_ASCENT_CENTER);
			}
			
		} else if(n != null) {
			/*g2.setFont(new Font("Verdana", Font.BOLD, 8));
			FontMetrics fm = g2.getFontMetrics();
			Rectangle2D arrowLabelBounds = TextUtils.getTextBounds(""+ new Double(n.doubleValue()).intValue(), g2,fm);
		 	arrowLabelX = meterMiddleX + ((radius + arrowLabelBounds.getWidth()) * Math.cos(Math.PI * (arrowAngle / 180)));
		 	arrowLabelY = meterMiddleY + ((radius + 12) * Math.sin(Math.PI * (arrowAngle / 180)));
			TextUtils.drawAlignedString("" + new Double(n.doubleValue()).intValue(),
					g2, (int) (arrowLabelX), (int) (arrowLabelY), TextAnchor.HALF_ASCENT_CENTER);*/
		}		
				
	}

	private void renderArrowNeedle(Graphics2D g2, double meterMiddleX, double meterMiddleY, double radius, Number n) {
		meterMiddleY = meterMiddleY+0.2f;
		double value = n.doubleValue();
		int arrowSize = ARROW_SIZE;
		if (!this.rangeLabelsVisible) {
			arrowSize = arrowSize - 2;
		}else
			arrowSize = arrowSize - 1;
		
		if (this.getRange().contains(value)) {
			double valueAngle = -valueToAngle(value);

			g2.setPaint(this.getNeedlePaint());
			if (this.rangeLabelsVisible) {
				g2.setStroke(new BasicStroke(1.0f));
			} else {
				g2.setStroke(new BasicStroke(1.0f));
			}

			double endX = meterMiddleX + ((radius - 1) * Math.cos(Math.PI * (valueAngle / 180)));
			double endY = meterMiddleY + ((radius - 1) * Math.sin(Math.PI * (valueAngle / 180)));

			g2.drawLine((int) meterMiddleX, (int) (meterMiddleY), (int) (endX),
					(int) (endY));

			endX = meterMiddleX + ((radius + 2) * Math.cos(Math.PI * (valueAngle / 180)));
			endY = meterMiddleY + ((radius + 2) * Math.sin(Math.PI * (valueAngle / 180)));

			// calculate points for arrowhead
			double angle = Math.atan2(endY - meterMiddleY, endX - meterMiddleX) + Math.PI;

			int x3 = (int) (endX + Math.cos(angle - THETA) * arrowSize);
			int y3 = (int) (endY + .5 + Math.sin(angle - THETA) * arrowSize);

			int x4 = (int) (endX + Math.cos(angle + THETA) * arrowSize);
			int y4 = (int) (endY + .5 + Math.sin(angle + THETA) * arrowSize);

			GeneralPath arrow = new GeneralPath();
			arrow.moveTo((int) endX, (int) endY);
			//commented for removing arrow line header
			//arrow.lineTo(x3, y3);
			//arrow.lineTo(x4, y4);
			arrow.closePath();
			
			g2.setPaint(this.getNeedlePaint());
			g2.fill(arrow);
		}
	}

	public boolean isRangeLabelsVisible() {
		return rangeLabelsVisible;
	}

	public void setRangeLabelsVisible(boolean rangeLabelsVisible) {
		this.rangeLabelsVisible = rangeLabelsVisible;
	}

	public String getNeedleType() {
		if (needleType == null) {
			needleType = "ARROW";
		}
		return needleType;
	}

	public void setNeedleType(String needleType) {
		this.needleType = needleType;
	}
}
