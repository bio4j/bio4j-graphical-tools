package com.bio4j.dataviz.model;

/**
 * Created by ppareja on 11/27/2014.
 */
public class GOTerm extends Node {

	public static final String GO_TERM_GROUP = "go";

	public GOTerm(String id, String name){
		super(id, name, GO_TERM_GROUP);
	}
}
