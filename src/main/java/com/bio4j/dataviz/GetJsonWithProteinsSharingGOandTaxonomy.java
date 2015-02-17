package com.bio4j.dataviz;

import com.bio4j.dataviz.model.Edge;
import com.bio4j.dataviz.model.GOTerm;
import com.bio4j.dataviz.model.Graph;
import com.bio4j.dataviz.model.Node;
import com.bio4j.titan.model.go.TitanGoGraph;
import com.bio4j.titan.model.ncbiTaxonomy.TitanNCBITaxonomyGraph;
import com.bio4j.titan.model.uniprot.TitanUniProtGraph;
import com.bio4j.titan.model.uniprot_go.TitanUniProtGoGraph;
import com.bio4j.titan.model.uniprot_ncbiTaxonomy.TitanUniProtNCBITaxonomyGraph;
import com.bio4j.titan.util.DefaultTitanGraph;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ohnosequences.util.Executable;
import com.thinkaurelius.titan.core.*;
import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ppareja on 9/24/2014.
 *
 * This program gets the Interpro motifs which are unique for an specific child of the NCBI taxon provided
 */
public class GetJsonWithProteinsSharingGOandTaxonomy implements Executable{

	public static final String PROTEIN_GO_GROUP = "protein_go";
	public static final String PROTEIN_NCBI_TAXON_GROUP = "protein_ncbi_taxon";
	public static final String NCBI_TAXON_PARENT_GROUP = "ncbi_taxon_parent";

	@Override
	public void execute(ArrayList<String> array) {
		String[] args = new String[array.size()];
		for (int i = 0; i < array.size(); i++) {
			args[i] = array.get(i);
		}
		main(args);
	}

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

				//----------DB configuration------------------
				Configuration conf = new BaseConfiguration();
				conf.setProperty("storage.directory", dbFolder);
				conf.setProperty("storage.backend", "local");
				conf.setProperty("autotype", "none");
				//-------creating graph handlers---------------------
				TitanGraph titanGraph = TitanFactory.open(conf);
				DefaultTitanGraph defGraph = new DefaultTitanGraph(titanGraph);

				System.out.println("Creating the graph managers....");

				TitanGoGraph titanGoGraph = new TitanGoGraph(defGraph);
				TitanUniProtGraph titanUniProtGraph = new TitanUniProtGraph(defGraph);
				TitanNCBITaxonomyGraph titanNCBITaxonomyGraph = new TitanNCBITaxonomyGraph(defGraph);

				TitanUniProtNCBITaxonomyGraph titanUniprotNCBITaxonomyGraph = new TitanUniProtNCBITaxonomyGraph(defGraph, titanUniProtGraph, titanNCBITaxonomyGraph);
				TitanUniProtGoGraph titanUniProtGoGraph = new TitanUniProtGoGraph(defGraph, titanUniProtGraph, titanGoGraph);

				titanGoGraph.withUniProt(titanUniProtGoGraph);
				titanUniProtGraph.withGo(titanUniProtGoGraph);
				titanUniProtGraph.withNCBITaxonomy(titanUniprotNCBITaxonomyGraph);
				titanNCBITaxonomyGraph.withUniProt(titanUniprotNCBITaxonomyGraph);

				boolean firstGo = true;


				System.out.println("Retrieving GO terms...");

				HashMap<String, String> goNames = new HashMap<>();

				//---iterating through GO terms provided--
				for (String goId : goTermIds){

					Optional<GoTerm<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel>> goOptional = titanGoGraph.goTermIdIndex().getVertex(goId);

					if(goOptional.isPresent()){

						GoTerm<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel> go = goOptional.get();
						goNames.put(go.id(), go.name());

						//---proteins linked to GO term---
						Optional<Stream<Protein<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel>>> goAnnotationOptional = go.goAnnotation_inV();

						if(goAnnotationOptional.isPresent()){

							Stream<Protein<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel>> proteinsStream = goAnnotationOptional.get();
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
									String proteinAccession = protein.accession();
									proteinsFulfillingGO.add(proteinAccession);
									System.out.println("Protein found for GO term: " + proteinAccession);
								}
							}

						}

					}else{
						throw new Exception("The GO term provided: " + goId + " does not exist... Finishing the program... :(");
					}
				}

				//Here we already have the set of proteins that fulfill the GO links provided
				//Now it's time to check their taxonomy

				System.out.println("Checking up proteins taxonomy...");
				List<String> taxonNodesAlreadyCreated = new LinkedList<>();

				for (String proteinId : proteinsFulfillingGO){

					//System.out.println("Current protein: " + proteinId);

					Protein<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel> protein = titanUniProtGraph.proteinAccessionIndex().getVertex(proteinId).get();
					Optional<NCBITaxon<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel>> taxonOptional = protein.proteinNCBITaxon_outV();

					if(taxonOptional.isPresent()){
						NCBITaxon<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel> taxon = taxonOptional.get();

						System.out.println("Protein taxon: " + taxon.scientificName());

						if(ncbiTaxonIds.contains(taxon.id())){

							finalListOfProteins.add(proteinId);
							if(!taxonNodesAlreadyCreated.contains(taxon.id())){
								com.bio4j.dataviz.model.NCBITaxon ncbiTaxonNode = new com.bio4j.dataviz.model.NCBITaxon(taxon.id(),taxon.scientificName());
								nodes.add(ncbiTaxonNode);
								taxonNodesAlreadyCreated.add(taxon.id());
							}
							System.out.print("The taxon passed the NCBI filer!");

						}else{

							System.out.println("Looking for taxon in ancestors...");

							List<String> tempListOfNCBITaxon = new LinkedList<>();
							HashMap<String, String> taxonNames = new HashMap<>();
							HashSet<String> targetsInvolvedInEdgesAlreadyCreated = new HashSet<>();

							tempListOfNCBITaxon.add(taxon.id());
							taxonNames.put(taxon.id(), taxon.scientificName());

							while(taxon.ncbiTaxonParent_inV().isPresent()){

								System.out.println("Current taxon: " + taxon.scientificName() + " " + taxon.id());

								taxon = taxon.ncbiTaxonParent_inV().get();

								tempListOfNCBITaxon.add(taxon.id());
								taxonNames.put(taxon.id(), taxon.scientificName());

								if(ncbiTaxonIds.contains(taxon.id())){

									finalListOfProteins.add(proteinId);
									System.out.print("The taxon passed the NCBI filer!");

									tempListOfNCBITaxon.add(taxon.id());
									taxonNames.put(taxon.id(), taxon.scientificName());


									//---Creating hierarchy nodes plus the edges among them---

									for (int i=0; i< tempListOfNCBITaxon.size(); i++){

										String tempTaxonId = tempListOfNCBITaxon.get(i);

										if(!taxonNodesAlreadyCreated.contains(tempTaxonId)){
											com.bio4j.dataviz.model.NCBITaxon ncbiTaxonNode = new com.bio4j.dataviz.model.NCBITaxon(tempTaxonId, taxonNames.get(tempTaxonId));
											nodes.add(ncbiTaxonNode);
											taxonNodesAlreadyCreated.add(tempTaxonId);
										}

										if(i < (tempListOfNCBITaxon.size() - 1)){
											String target = tempListOfNCBITaxon.get(i+1);
											String source = tempListOfNCBITaxon.get(i);

											if(!targetsInvolvedInEdgesAlreadyCreated.contains(target)){
												Edge edge = new Edge(source, target, "1", NCBI_TAXON_PARENT_GROUP);
												edges.add(edge);
												targetsInvolvedInEdgesAlreadyCreated.add(target);
											}
										}
									}
									break;
								}
							}
							System.out.println("It was not found... filter not passed... :(");
						}
					}

				}


				System.out.println("The final list of proteins has the following size: " + finalListOfProteins.size());


				//-----PROTEINS-----
				for (String proteinId : finalListOfProteins){

					Protein<DefaultTitanGraph, TitanVertex, TitanKey, TitanEdge, TitanLabel> protein = titanUniProtGraph.proteinAccessionIndex().getVertex(proteinId).get();

					com.bio4j.dataviz.model.Protein proteinNode = new com.bio4j.dataviz.model.Protein(protein.accession(), protein.fullName());
					nodes.add(proteinNode);

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
					GOTerm goNode = new GOTerm(goId, goNames.get(goId));
					nodes.add(goNode);
				}

				System.out.println("Writing output file....");
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				outBuff.write(gson.toJson(graph));
				System.out.println("Closing output file...");
				outBuff.close();

				System.out.println("Closing the manager....");
				titanUniprotNCBITaxonomyGraph.raw().shutdown();

			}catch(Exception e){
				e.printStackTrace();
			}

		}
	}


}
