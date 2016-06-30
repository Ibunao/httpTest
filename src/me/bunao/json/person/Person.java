package me.bunao.json.person;

import java.util.List;

public class Person {
	private String name;
	private int age;
	private String url;
	private List<SchoolInfo> school_info;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<SchoolInfo>  getSchool_info() {
		return school_info;
	}

	public void setSchool_info(List<SchoolInfo> school_info) {
		this.school_info = school_info;
	}

}
