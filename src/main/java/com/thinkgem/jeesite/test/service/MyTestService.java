package com.thinkgem.jeesite.test.service;

public class MyTestService {
	private String name;
	public void test() {
		System.out.println("hello, i am a service task");
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
