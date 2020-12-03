package org.kpu.ihpweb.domain;

public class LocationVO {
	private int ai;
	private String device_name;
	private double lat;
	private double lon;

	public int getAi() {
		return ai;
	}

	public void setAi(int ai) {
		this.ai = ai;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "LocationVO [ai=" + ai + ", device_name=" + device_name + ", lat=" + lat + ", lon=" + lon + "]";
	}

}
