package org.kpu.ihpweb.domain;

public class ChildVO {
	private String device_name;
	private String username;
	private String sex;
	private String age;
	private String address;
	private String kindergarden;
	private String classname;
	private String parentid;

	public String getDevice_name() {
		return device_name;
	}

	public String getKindergarden() {
		return kindergarden;
	}

	public void setKindergarden(String kindergarden) {
		this.kindergarden = kindergarden;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Override
	public String toString() {
		return "ChildVO [device_name=" + device_name + ", username=" + username + ", sex=" + sex + ", age=" + age
				+ ", address=" + address + ", kindergarden=" + kindergarden + ", classname=" + classname + ", parentid="
				+ parentid + "]";
	}

}
