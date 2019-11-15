package edu.ku.cete.util;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorNameUtil {
	
	private static Map<String, String> colors;
	
	private static Logger LOGGER = LoggerFactory.getLogger(ColorNameUtil.class);
	
	static {
		// use LinkedHashMap just to guarantee consistent results and iteration order
		colors = new LinkedHashMap<String, String>();
		colors.put("#F0F8FF", "Alice Blue");
		colors.put("#FAEBD7", "Antique White");
		colors.put("#00FFFF", "Aqua");
		colors.put("#7FFFD4", "Aquamarine");
		colors.put("#F0FFFF", "Azure");
		colors.put("#F5F5DC", "Beige");
		colors.put("#FFE4C4", "Bisque");
		colors.put("#000000", "Black");
		colors.put("#FFEBCD", "Blanched Almond");
		colors.put("#0000FF", "Blue");
		colors.put("#8A2BE2", "Blue Violet");
		colors.put("#A52A2A", "Brown");
		colors.put("#DEB887", "Burly Wood");
		colors.put("#5F9EA0", "Cadet Blue");
		colors.put("#7FFF00", "Chartreuse");
		colors.put("#D2691E", "Chocolate");
		colors.put("#FF7F50", "Coral");
		colors.put("#6495ED", "Cornflower Blue");
		colors.put("#FFF8DC", "Cornsilk");
		colors.put("#DC143C", "Crimson");
		colors.put("#00FFFF", "Cyan");
		colors.put("#00008B", "Dark Blue");
		colors.put("#008B8B", "Dark Cyan");
		colors.put("#B8860B", "Dark Goldenrod");
		colors.put("#A9A9A9", "Dark Gray");
		colors.put("#006400", "Dark Green");
		colors.put("#BDB76B", "Dark Khaki");
		colors.put("#8B008B", "Dark Magenta");
		colors.put("#556B2F", "Dark Olive Green");
		colors.put("#FF8C00", "Dark Orange");
		colors.put("#9932CC", "Dark Orchid");
		colors.put("#8B0000", "Dark Red");
		colors.put("#E9967A", "Dark Salmon");
		colors.put("#8FBC8F", "Dark Sea Green");
		colors.put("#483D8B", "Dark Slate Blue");
		colors.put("#2F4F4F", "Dark Slate Gray");
		colors.put("#00CED1", "Dark Turquoise");
		colors.put("#9400D3", "Dark Violet");
		colors.put("#FF1493", "Deep Pink");
		colors.put("#00BFFF", "Deep Sky Blue");
		colors.put("#696969", "Dim Gray");
		colors.put("#1E90FF", "Dodger Blue");
		colors.put("#B22222", "Fire Brick");
		colors.put("#FFFAF0", "Floral White");
		colors.put("#228B22", "Forest Green");
		colors.put("#FF00FF", "Fuchsia");
		colors.put("#DCDCDC", "Gainsboro");
		colors.put("#F8F8FF", "Ghost White");
		colors.put("#FFD700", "Gold");
		colors.put("#DAA520", "Goldenrod");
		colors.put("#808080", "Gray");
		colors.put("#008000", "Green");
		colors.put("#ADFF2F", "Green Yellow");
		colors.put("#F0FFF0", "Honeydew");
		colors.put("#FF69B4", "Hot Pink");
		colors.put("#4B0082", "Indigo");
		colors.put("#FFFFF0", "Ivory");
		colors.put("#F0E68C", "Khaki");
		colors.put("#E6E6FA", "Lavender");
		colors.put("#FFF0F5", "Lavender Blush");
		colors.put("#7CFC00", "Lawn Green");
		colors.put("#FFFACD", "Lemon Chiffon");
		colors.put("#ADD8E6", "Light Blue");
		colors.put("#F08080", "Light Coral");
		colors.put("#E0FFFF", "Light Cyan");
		colors.put("#FAFAD2", "Light Goldenrod Yellow");
		colors.put("#D3D3D3", "Light Gray");
		colors.put("#90EE90", "Light Green");
		colors.put("#FFB6C1", "Light Pink");
		colors.put("#FFA07A", "Light Salmon");
		colors.put("#20B2AA", "Light Sea Green");
		colors.put("#87CEFA", "Light Sky Blue");
		colors.put("#778899", "Light Slate Gray");
		colors.put("#B0C4DE", "Light Steel Blue");
		colors.put("#FFFFE0", "Light Yellow");
		colors.put("#00FF00", "Lime");
		colors.put("#32CD32", "Lime Green");
		colors.put("#FAF0E6", "Linen");
		colors.put("#FF00FF", "Magenta");
		colors.put("#800000", "Maroon");
		colors.put("#66CDAA", "Medium Aqua Marine");
		colors.put("#0000CD", "Medium Blue");
		colors.put("#BA55D3", "Medium Orchid");
		colors.put("#9370DB", "Medium Purple");
		colors.put("#3CB371", "Medium Sea Green");
		colors.put("#7B68EE", "Medium Slate Blue");
		colors.put("#00FA9A", "Medium Spring Green");
		colors.put("#48D1CC", "Medium Turquoise");
		colors.put("#C71585", "Medium Violet Red");
		colors.put("#191970", "Midnight Blue");
		colors.put("#F5FFFA", "Mint Cream");
		colors.put("#86FDAA", "Mint Green");
		colors.put("#FFE4E1", "Misty Rose");
		colors.put("#FFE4B5", "Moccasin");
		colors.put("#000080", "Navy");
		colors.put("#FDF5E6", "Old Lace");
		colors.put("#808000", "Olive");
		colors.put("#6B8E23", "Olive Drab");
		colors.put("#FFA500", "Orange");
		colors.put("#FF4500", "Orange Red");
		colors.put("#DA70D6", "Orchid");
		colors.put("#EEE8AA", "Pale Goldenrod");
		colors.put("#98FB98", "Pale Green");
		colors.put("#AFEEEE", "Pale Turquoise");
		colors.put("#DB7093", "Pale Violet Red");
		colors.put("#FFEFD5", "Papaya Whip");
		colors.put("#FFDAB9", "PeachPuff");
		colors.put("#CD853F", "Peru");
		colors.put("#FFC0CB", "Pink");
		colors.put("#DDA0DD", "Plum");
		colors.put("#B0E0E6", "Powder Blue");
		colors.put("#800080", "Purple");
		colors.put("#FF0000", "Red");
		colors.put("#BC8F8F", "Rosy Brown");
		colors.put("#4169E1", "Royal Blue");
		colors.put("#8B4513", "Sputle Brown");
		colors.put("#FA8072", "Salmon");
		colors.put("#F4A460", "Sandy Brown");
		colors.put("#2E8B57", "Sea Green");
		colors.put("#FFF5EE", "Sea Shell");
		colors.put("#A0522D", "Sienna");
		colors.put("#87CEEB", "Sky Blue");
		colors.put("#6A5ACD", "Slate Blue");
		colors.put("#708090", "Slate Gray");
		colors.put("#FFFAFA", "Snow");
		colors.put("#00FF7F", "Spring Green");
		colors.put("#4682B4", "Steel Blue");
		colors.put("#D2B48C", "Tan");
		colors.put("#008080", "Teal");
		colors.put("#D8BFD8", "Thistle");
		colors.put("#FF6347", "Tomato");
		colors.put("#40E0D0", "Turquoise");
		colors.put("#EE82EE", "Violet");
		colors.put("#F5DEB3", "Wheat");
		colors.put("#FFFFFF", "White");
		colors.put("#F5F5F5", "White Smoke");
		colors.put("#FFFF00", "Yellow");
		colors.put("#9ACD32", "Yellow Green");
	}
	
	/**
	 * Returns the name of a color 
	 * @param hex Hex value of the form #[0-9A-F]{6}
	 * @return
	 */
	public static String getColorNameForHex(String hex) {
		try {
			Color color = Color.decode(hex);
			int r = color.getRed();
			int g = color.getGreen();
			int b = color.getBlue();
			
			int minDistance = -1;
			String closestColorName = "";
			for (Map.Entry<String, String> entry : colors.entrySet()) {
				Color mapColor = Color.decode(entry.getKey()); // should never throw its exception, as long as our map is valid
				int R = mapColor.getRed();
				int G = mapColor.getGreen();
				int B = mapColor.getBlue();
				int distance = Math.abs(r - R) + Math.abs(g - G) + Math.abs(b - B);
				if (minDistance == -1 || distance < minDistance) {
					minDistance = distance;
					closestColorName = entry.getValue();
				}
				if (distance == 0) { // exact match found
					break;
				}
			}
			return closestColorName;
		} catch (NumberFormatException nfe) {
			LOGGER.debug("Could not parse color code \"" + hex + "\" -- returning empty string");
		}
		return "";
	}
}