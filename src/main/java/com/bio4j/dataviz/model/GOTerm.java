/*
JSON POJO _(Plain Old Java Object)_ for Gene Ontology terms
 */
package com.bio4j.dataviz.model;

public class GOTerm extends Node {

	public static final String GO_TERM_GROUP = "go";

	public GOTerm(String id, String name){
		super(id, name, GO_TERM_GROUP);
	}
}
