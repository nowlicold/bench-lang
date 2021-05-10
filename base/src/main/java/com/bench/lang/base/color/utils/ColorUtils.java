package com.bench.lang.base.color.utils;

import java.awt.Color;
import java.util.List;

public class ColorUtils {

	/**
	 * 判断在colorList中是否存在color，误差为colorMatchRange
	 * 
	 * @param colorList
	 * @param color
	 * @param colorMatchRage
	 * @return
	 */
	public static boolean contains(List<Color> colorList, Color color, int colorMatchRange) {
		if (colorMatchRange < 0) {
			colorMatchRange = Math.abs(colorMatchRange);
		}
		for (Color checkColor : colorList) {
			if (equals(checkColor, color, colorMatchRange)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 颜色是否相等
	 * 
	 * @param color1
	 * @param color2
	 * @return
	 */
	public static boolean equal(Color color1, Color color2) {
		return equals(color1, color2, 0);
	}

	/**
	 * 颜色是否相等
	 * 
	 * @param color1
	 * @param color2
	 * @param colorMatchRange
	 * @return
	 */
	public static boolean equals(Color color1, Color color2, int colorMatchRange) {
		return (Math.abs(color1.getRed() - color2.getRed()) <= colorMatchRange && Math.abs(color1.getGreen() - color2.getGreen()) <= colorMatchRange
				&& Math.abs(color1.getBlue() - color2.getBlue()) <= colorMatchRange);
	}

	/**
	 * 颜色是否相等
	 * 
	 * @param color1
	 * @param color2
	 * @param colorMatchRange
	 * @param colorMatchRange
	 * @return
	 */
	public static boolean equals(Color color1, Color color2, int colorMatchRange, int rgbMatchCount) {
		int count = 0;
		count += Math.abs(color1.getRed() - color2.getRed()) <= colorMatchRange ? 1 : 0;
		count += Math.abs(color1.getGreen() - color2.getGreen()) <= colorMatchRange ? 1 : 0;
		count += Math.abs(color1.getBlue() - color2.getBlue()) <= colorMatchRange ? 1 : 0;
		return count >= rgbMatchCount;
	}
}
