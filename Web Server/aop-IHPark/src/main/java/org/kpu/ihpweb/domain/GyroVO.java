package org.kpu.ihpweb.domain;

public class GyroVO {
	private int ai;
	private String device_name;
	private int count;

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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "GyroVO [ai=" + ai + ", device_name=" + device_name + ", count=" + count + "]";
	}

}
