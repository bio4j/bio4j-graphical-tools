/*
JSON POJO _(Plain Old Java Object)_ representing a graph.
 */
package com.bio4j.dataviz.model;

import java.util.List;

public class Graph {

	public Graph(){

	}

	public Graph(List<Node> nodes, List<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public List<Node> nodes;
	public List<Edge> edges;
}
