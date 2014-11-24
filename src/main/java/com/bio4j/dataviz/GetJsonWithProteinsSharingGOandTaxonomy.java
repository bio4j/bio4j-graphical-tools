package com.bio4j.dataviz;

import com.bio4j.dataviz.model.Edge;
import com.bio4j.dataviz.model.Graph;
import com.bio4j.dataviz.model.Node;
import com.bio4j.model.go.vertices.GoTerm;
import com.bio4j.model.ncbiTaxonomy.vertices.NCBITaxon;
import com.bio4j.model.uniprot.vertices.Interpro;
import com.bio4j.model.uniprot.vertices.Protein;
import com.bio4j.model.uniprot_ncbiTaxonomy.UniprotNCBITaxonomyGraph;
import com.bio4j.titan.model.ncbiTaxonomy.TitanNCBITaxonomyGraph;
import com.bio4j.titan.model.uniprot.TitanUniprotGraph;
import com.bio4j.titan.model.uniprot_ncbiTaxonomy.TitanUniprotNCBITaxonomyGraph;
import com.bio4j.titan.util.DefaultTitanGraph;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thinkaurelius.titan.core.*;
import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ppareja on 9/24/2014.
 *
 * This program gets the Interpro motifs which are unique for an specific child of the NCBI taxon provided
 */
public class GetJsonWithProteinsSharingGOandTaxonomy {

	public static final String HEADER = "Protein accession\tProtein name";
	public static final String PROTEIN_GROUP = "protein";
	public static final String GO_GROUP = "go";
	public static final String NCBI_TAXON_GROUP = "ncbi_taxon";
	public static final String PROTEIN_GO_GROUP = "protein_go";
	public static final String PROTEIN_NCBI_TAXON_GROUP = "protein_ncbi_taxon";

	public static void main(String[] args){
		if(args.length != 5){
			System.out.println("This program expects the following arguments: \n" +
					"1. Bio4j folder \n" +
					"2. TXT file including GO terms \n" +
					"3. TXT file including NCBI taxon IDs\n" +
					"4. Output file name\n" +
					"5. GO terms constraint (all/any)");
		}else{


			String dbFolder = args[0];
			String goTermsFileSt = args[1];
			String ncbiTaxonIdsFileSt = args[2];
			String outFileSt = args[3];
			String goTermsConstraint = args[4];

			try{

				List<String> goTermIds = new LinkedList<>();
				List<String> ncbiTaxonIds = new LinkedList<>();
				List<String> proteinsFulfillingGO = new LinkedList<>();
				List<String> finalListOfProteins = new LinkedList<>();

				File goFile = new File(goTermsFileSt);
				File ncbiTaxonFile = new File(ncbiTaxonIdsFileSt);
				File outFile = new File(outFileSt);

				//-----------------------DATAVIZ/JSON PART -------------------------------------
				Graph graph = new Graph();
				List<Node> nodes = new LinkedList<Node>();
				graph.setNodes(nodes);
				List<Edge> edges = new LinkedList<Edge>();
				graph.setEdges(edges);


				System.out.println("Reading GO term IDs....");
				BufferedReader reader = new BufferedReader(new FileReader(goFile));
				String line;
				while((line = reader.readLine()) != null){
					goTermIds.add(line.trim());
				}
				reader.close();

				System.out.println("Reading NCBI taxon IDs....");
				reader = new BufferedReader(new FileReader(ncbiTaxonFile));
				while((line = reader.readLine()) != null){
					ncbiTaxonIds.add(line.trim());
				}
				reader.close();

				BufferedWriter outBuff = new BufferedWriter(new FileWriter(outFile));
				outBuff.write(HEADER + "\n");

				//----------DB configuration------------------
				Configuration conf = new BaseConfiguration();
				conf.setProperty("storage.directory", dbFolder);
				conf.setProperty("storage.backend", "local");
				conf.setProperty("autotype", "none");
				//-------creating graph handlers---------------------
				TitanGraph titanGraph = TitanFactory.open(conf);
				DefaultTitanGraph defGraph = new DefaultTitanGraph(titanGraph);

				System.out.println("Creating the graph manager....");
				TitanUniprotNCBITaxonomyGraph uniprotNCBITaxonomyGraph = new TitanUniprotNCBITaxonomyGraph(defGraph, new TitanUniprotGraph(defGraph), new TitanNCBITaxonomyGraph(defGraph));

				boolean firstGo = true;



				//---iterating through GO terms provided--
				for (String goId : goTermIds){
					Optional<GoTerm<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel>> goOptional = uniprotNCBITaxonomyGraph.uniprotGraph().uniprotGoGraph().goGraph().goTermIdIndex().getVertex(goId);
					if(goOptional.isPresent()){

						GoTerm<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel> go = goOptional.get();
						//---proteins linked to interpro motif---
						Stream<Protein<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel>> proteinsStream = go.goAnnotation_inV();
						List<Protein<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel>> proteins = proteinsStream.collect(Collectors.toList());

						//-----------------ALL-----------------------------------
						if(goTermsConstraint.equals("all")){
							if(firstGo){
								firstGo = false;
								for (Protein<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel> protein : proteins){
									proteinsFulfillingGO.add(protein.accession());
								}
							}

							List<String> tempProteins = new LinkedList<>();
							for (Protein<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel> protein : proteins){
								tempProteins.add(protein.accession());
							}
							for (String tempProteinId : tempProteins){
								if(!proteinsFulfillingGO.contains(tempProteinId)){
									proteinsFulfillingGO.remove(tempProteinId);
									break;
								}
							}
							//-----------------ANY-----------------------------------
						}else{
							for (Protein<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel> protein : proteins){
								proteinsFulfillingGO.add(protein.accession());
							}
						}



					}else{
						throw new Exception("The GO term provided: " + goId + " does not exist... Finishing the program... :(");
					}
				}

				//Here we already have the set of proteins that fulfill the GO links provided
				//Now it's time to check their taxonomy

				System.out.println("Checking up proteins taxonomy...");
				for (String proteinId : proteinsFulfillingGO){

					Protein<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel> protein = uniprotNCBITaxonomyGraph.uniprotGraph().proteinAccessionIndex().getVertex(proteinId).get();
					Optional<NCBITaxon<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel>> taxonOptional = protein.proteinNCBITaxon_outV();

					if(taxonOptional.isPresent()){
						NCBITaxon<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel> taxon = taxonOptional.get();
						if(ncbiTaxonIds.contains(taxon.id())){
							finalListOfProteins.add(proteinId);
						}else{
							while(taxon.ncbiTaxonParent_outV().isPresent()){
								taxon = taxon.ncbiTaxonParent_outV().get();
								if(ncbiTaxonIds.contains(taxon.id())){
									finalListOfProteins.add(proteinId);
									break;
								}
							}
						}
					}

				}



				//-----PROTEINS-----
				for (String proteinId : finalListOfProteins){
					Node proteinNode = new Node(proteinId, PROTEIN_GROUP);
					nodes.add(proteinNode);
					Protein<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel> protein = uniprotNCBITaxonomyGraph.uniprotGraph().proteinAccessionIndex().getVertex(proteinId).get();
					for(GoTerm<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel> goTerm : protein.goAnnotation_outV().get().collect(Collectors.toList())){
						if(goTermIds.contains(goTerm.id())){
							Edge edge = new Edge(proteinId, goTerm.id(), "1", PROTEIN_GO_GROUP);
							edges.add(edge);
						}
					}
					Edge edge = new Edge(proteinId, protein.proteinNCBITaxon_outV().get().id(), "1", PROTEIN_NCBI_TAXON_GROUP);
					edges.add(edge);

				}
				//-----GO-----
				for (String goId : goTermIds){
					Node goNode = new Node(goId, GO_GROUP);
					nodes.add(goNode);
				}
				//-----NCBI TAXONS-----
				for (String ncbiTaxonId : ncbiTaxonIds){
					Node ncbiTaxonNode = new Node(ncbiTaxonId, NCBI_TAXON_GROUP);
					nodes.add(ncbiTaxonNode);
				}


				System.out.println("Writing output file....");
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				outBuff.write(gson.toJson(graph));
				System.out.println("Closing output file...");
				outBuff.close();

				System.out.println("Closing the manager....");
				uniprotNCBITaxonomyGraph.raw().shutdown();

			}catch(Exception e){
				e.printStackTrace();
			}

		}
	}
}
