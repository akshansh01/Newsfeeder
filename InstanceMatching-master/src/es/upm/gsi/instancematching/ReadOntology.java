package es.upm.gsi.instancematching;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import es.upm.gsi.instancematching.geneticprogramming.FitnessFouction;

public class ReadOntology {
	
	public List<OntClass> humanList = new ArrayList<OntClass>();
	public List<OntClass> mouseList = new ArrayList<OntClass>();

	public static void main(String[] args) {
		
		ReadOntology ro=new ReadOntology();
		ro.readOwlFile();
	}
	
	public void readOwlFile(){

		OntModel ontModelmouse = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);
		ontModelmouse.read("file:./mouse.owl");

		OntModel ontModelhuman = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);
		ontModelhuman.read("file:./human.owl");

		
		for (Iterator<OntClass> i = ontModelhuman.listClasses(); i.hasNext();) {
			OntClass human = (OntClass) i.next();
			// Filter non human class
			if (!human.isAnon()) {
				// Filter the non-human class to list
				if (human.getLocalName().startsWith("NCI_C")) {
					// System.out.print("Class£∫");
					// System.out.println(human.getURI());
					humanList.add(human);
				}
				/*
				 * if (!c.isAnon()) { // print all the class name
				 * System.out.print("Class:");
				 * System.out.println(c.getModel().getGraph().
				 * getPrefixMapping().shortForm(c.getURI())); }
				 */
			}
		}

		for (Iterator<OntClass> j = ontModelmouse.listClasses(); j.hasNext();) {
			OntClass mouse = (OntClass) j.next();
			if (!mouse.isAnon()) {
				// Filter non-mouse class
				if (mouse.getLocalName().startsWith("MA_")) {
					// System.out.print("Class£∫");
					// System.out.println(mouse.getURI());
					mouseList.add(mouse);
				}
			}
		}
	}
		// Compare two instances' similarity
	public void compareSimilarity(){
		
		double lsvalue = 0.0;
		double psvalue = 0.0;
		double nsvalue = 0.0;
		List<String[]> matchedList = new ArrayList<String[]>();
		SimilarityMetric sm = new SimilarityMetric();
		
		// for test 
		int k=0;
		for (int i = 0; i < humanList.size(); i++) {
			OntClass human;
			human = (OntClass) humanList.get(i);
			for (int j = 0; j < mouseList.size(); j++) {
				OntClass mouse;
				mouse = (OntClass) mouseList.get(j);
				if (!mouse.isAnon() && !human.isAnon()) {
					// Compare similarity based on label of instance
					lsvalue = sm.label2Similarity(human.getLabel(null),
							mouse.getLabel(null));

					// Compare similarity based on the properties of instance
					psvalue = sm.compareProperty(human, mouse);

					//
					// Compare similarity based on the numbers of values of
					// properties of instance
					nsvalue = sm.compareNumbers(human, mouse);

					if (lsvalue > 0.4 && psvalue > 0.2 && nsvalue > 0.3) {
						String[] match = new String[2];
								                
						match[0] = mouse.getLabel(null);
						match[1] = human.getLabel(null);
						k++;
						System.out.println("I am here" + k);
						matchedList.add(match);

					}

				}
			}
		}

		// º∆À„Recall,Precision value
		List<String[]> referenceList = new ArrayList<String[]>();
		FitnessFouction ff = new FitnessFouction();
		System.out.print("I am here");
		referenceList = ff.getRefernceInstancePairURL();
	
		int positiveNum = 0;
		double positivePercent = 0.0;
		for (int i = 0; i < matchedList.size(); i++) {
			for (int j = 0; j < referenceList.size(); j++) {
				
				System.out.print("I am here");
				String[] firstLabel = new String[2];
				String[] sndLabel = new String[2];
				firstLabel = matchedList.get(i);
				sndLabel = referenceList.get(j);
				if (firstLabel[0] == sndLabel[0]
						&& firstLabel[1] == sndLabel[1]) {
					positiveNum++;
				}

			}
		}

		positivePercent = positiveNum / referenceList.size();
		System.out.println("The Precision value is " + positivePercent);

	}
}
