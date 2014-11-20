package com.bio4j.dataviz.model;

/**
 * Created by ppareja on 10/29/2014.
 */
public class Node {

	public String name;
	public String group;

	public String getName() {
		return name;
	}

	public Node() {
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Node(String name, String group) {
		this.name = name;
		this.group = group;
	}
}
