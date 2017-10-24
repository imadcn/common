package com.imadcn.framework.common.coordinate;

public class Coordinate {
	/**
	 * 名字
	 */
	private String name;

	/**
	 * 经度
	 */
	private double lng;

	/**
	 * 纬度
	 */
	private double lat;

	public Coordinate() {

	}

	public Coordinate(double lng, double lat, String name) {
		this.name = name;
		this.lng = lng;
		this.lat = lat;
	}

	public Coordinate(double lng, double lat) {
		this.lng = lng;
		this.lat = lat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	@Override
	public String toString() {
		return "Coordinate [name=" + name + ", lng=" + lng + ", lat=" + lat + "]";
	}

}
