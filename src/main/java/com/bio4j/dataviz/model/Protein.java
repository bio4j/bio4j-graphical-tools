package com.bio4j.dataviz.model;

/**
 * Created by ppareja on 11/27/2014.
 */
public class Protein extends Node {

	public static final String PROTEIN_GROUP = "protein";

	public Protein(String id, String name){
		super(id, name, PROTEIN_GROUP);
	}
}
