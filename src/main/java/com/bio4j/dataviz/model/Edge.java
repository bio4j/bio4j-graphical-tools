package com.bio4j.dataviz.model;

/**
 * Created by ppareja on 10/29/2014.
 */
public class Edge {

	public String source;
	public String target;
	public String value;

	public Edge(){

	}

	public Edge(String target, String source, String value) {
		this.target = target;
		this.source = source;
		this.value = value;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
