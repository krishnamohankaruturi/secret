package edu.ku.cete.report;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.ui.RectangleEdge;


public class CustomCategoryAxis extends CategoryAxis {

	private static final long serialVersionUID = 1L;

	
	   public void drawTickMarks(Graphics2D g2, double cursor, Rectangle2D dataArea, RectangleEdge edge, AxisState state)
	   /*      */   {
	   /* 1203 */     Plot p = getPlot();
	   /* 1204 */     if (p == null) {
	   /* 1205 */       return;
	   /*      */     }
	   /* 1207 */     CategoryPlot plot = (CategoryPlot)p;
	   /* 1208 */     double il = getTickMarkInsideLength();
	   /* 1209 */     double ol = getTickMarkOutsideLength();
	   /* 1210 */     Line2D line = new Line2D.Double();
	   /* 1211 */     List categories = plot.getCategoriesForAxis(this);	   				  
	   /* 1212 */     g2.setPaint(getTickMarkPaint());
	   /* 1213 */     g2.setStroke(getTickMarkStroke());
	   /* 1214 */     Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
	   /* 1215 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
	   /*      */     
	   /* 1217 */     if (edge.equals(RectangleEdge.TOP)) {
	   /* 1218 */       Iterator iterator = categories.iterator();
	   /* 1219 */       while (iterator.hasNext()) {
	   /* 1220 */         Comparable key = (Comparable)iterator.next();
	   /* 1221 */         double x = getCategoryMiddle(key, categories, dataArea, edge);
	   /* 1222 */         line.setLine(x, cursor, x, cursor + il);
	   /* 1223 */         g2.draw(line);
	   /* 1224 */         line.setLine(x, cursor, x, cursor - ol);
	   /* 1225 */         g2.draw(line);
	   /*      */       }
	   /* 1227 */       state.cursorUp(ol);
	   /* 1228 */     } else if (edge.equals(RectangleEdge.BOTTOM)) {
	   /* 1229 */       Iterator iterator = categories.iterator();
	   /* 1230 */       while (iterator.hasNext()) {
	   /* 1231 */         Comparable key = (Comparable)iterator.next();
	   /* 1232 */         double x = getCategoryMiddle(key, categories, dataArea, edge);
	   /* 1233 */        
	   					  line.setLine(x, cursor, x, cursor - il);
	   /* 1234 */         g2.draw(line);
	   /* 1235 */         line.setLine(x, cursor, x, cursor + ol);
	   /* 1236 */         g2.draw(line);
	   /*      */       }
	   /* 1238 */       state.cursorDown(ol);
	   /* 1239 */     } else if (edge.equals(RectangleEdge.LEFT)) {
	   /* 1240 */       Iterator iterator = categories.iterator();
	   /* 1241 */       while (iterator.hasNext()) {
	   /* 1242 */         Comparable key = (Comparable)iterator.next();

	   /* 1243 */         double y = getCategoryMiddle(key, categories, dataArea, edge);
	   					  y=y+24.14;
	   /* 1244 */         line.setLine(cursor, y, cursor + il, y);
	   /* 1245 */         g2.draw(line);
	   /* 1246 */         line.setLine(cursor, y, cursor - ol, y);
	   /* 1247 */         g2.draw(line);
	   /*      */       }
	   /* 1249 */       state.cursorLeft(ol);
	   /* 1250 */     } else if (edge.equals(RectangleEdge.RIGHT)) {
	   /* 1251 */       Iterator iterator = categories.iterator();
	   /* 1252 */       while (iterator.hasNext()) {
	   /* 1253 */         Comparable key = (Comparable)iterator.next();
	   /* 1254 */         double y = getCategoryMiddle(key, categories, dataArea, edge);
	   /* 1255 */         line.setLine(cursor, y, cursor - il, y);
	   /* 1256 */         g2.draw(line);
	   /* 1257 */         line.setLine(cursor, y, cursor + ol, y);
	   /* 1258 */         g2.draw(line);
	   /*      */       }
	   /* 1260 */       state.cursorRight(ol);
	   /*      */     }
	   /* 1262 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
	   /*      */   }
	   /*      */   
   
   
}