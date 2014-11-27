package com.bio4j.dataviz.model;

/**
 * Created by ppareja on 11/27/2014.
 */
public class NCBITaxon extends Node {

	public static final String NCBI_TAXON_GROUP = "ncbi_taxon";

	public NCBITaxon(String id, String name){
		super(id, name, NCBI_TAXON_GROUP);
	}
}
