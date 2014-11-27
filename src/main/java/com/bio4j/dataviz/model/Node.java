package com.bio4j.dataviz.model;

/**
 * Created by ppareja on 10/29/2014.
 */
public class Node {



	public String id;
	public String name;
	public String group;

	public Node() {
	}

	public Node(String id, String name, String group) {
		this.id = id;
		this.name = name;
		this.group = group;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}


}
