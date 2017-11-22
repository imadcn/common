package com.imadcn.framework.common.coordinate;

import java.util.List;

/**
 * 坐标查询工具
 * 
 */
public class CoordinateUtil {
	/**
	 * 地球的半径
	 */
	private static final double EARTH_RADIUS = 6378137;

	/**
	 * 获取两个坐标点的直线距离
	 * 
	 * @param lng1 坐标1经度
	 * @param lat1 坐标1纬度
	 * @param lng2 坐标2经度
	 * @param lat2 坐标2纬度
	 * @return 距离
	 */
	public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	/**
	 * 获取离指定坐标最近的一个坐标
	 * 
	 * @param lng 指定坐标经度
	 * @param lat 指定坐标纬度
	 * @param coordinateList 目标坐标集
	 * @return 最近的一个坐标
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Coordinate> T getShortestCoordinate(double lng, double lat, List<? extends Coordinate> coordinateList) {
		if (coordinateList.isEmpty()) {
			throw new IllegalArgumentException("There is no coordinate, because the coordinateList is empty");
		}

		T shortestCoordinate = null;
		double shortestDistance = 0.0d;

		for (Coordinate coordinate : coordinateList) {
			double distance = getDistance(lng, lat, coordinate.getLng(), coordinate.getLat());

			if (shortestCoordinate == null) {
				shortestDistance = distance;
				shortestCoordinate = (T) coordinate;
			} else {
				if (distance < shortestDistance) {
					shortestDistance = distance;
					shortestCoordinate = (T) coordinate;
				}
			}
		}

		return shortestCoordinate;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

}
