/*
JSON POJO _(Plain Old Java Object)_ for NCBI taxonomic units
 */
package com.bio4j.dataviz.model;

public class NCBITaxon extends Node {

	public static final String NCBI_TAXON_GROUP = "ncbi_taxon";

	public NCBITaxon(String id, String name){
		super(id, name, NCBI_TAXON_GROUP);
	}
}
