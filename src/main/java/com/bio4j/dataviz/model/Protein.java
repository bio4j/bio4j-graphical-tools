/*
JSON POJO _(Plain Old Java Object)_ for a protein
 */
package com.bio4j.dataviz.model;

public class Protein extends Node {

	public static final String PROTEIN_GROUP = "protein";

	public Protein(String id, String name){
		super(id, name, PROTEIN_GROUP);
	}
}
