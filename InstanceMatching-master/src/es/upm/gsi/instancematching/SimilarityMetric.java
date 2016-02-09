package es.upm.gsi.instancematching;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class SimilarityMetric {
	
	// More similarity metric should be added in future
	/*
	 * compute the label similarity
	 */

	public double label2Similarity(String label1, String label2) {
	
		// System.out.print(label1);
		label1 = tranformString(label1);
		// System.out.print(label2);
		label2 = tranformString(label2);
		Levenshtein lev = new Levenshtein();
		double labelsim = lev.getSimilarityRatio(label1, label2);
		return labelsim;

	}
	/*
	 * Compare the two instances' properties 把所有属性组成一个串比较
	 */
	public double compareProperty(OntClass h, OntClass m) {
		OntClass human = h;
		OntClass mouse = m;
		
		String hpSum = "";
		String hmSum = "";
		for (StmtIterator j = human.listProperties(); j.hasNext();) {
			Property hp = j.next().getPredicate();
			// Change into String format
			String hpStr = hp.getLocalName().toString();
			hpSum = hpStr + hpSum;
		}

		for (StmtIterator i = mouse.listProperties(); i.hasNext();) {
			Property hm = i.next().getPredicate();
			String hmStr = hm.getLocalName().toString();
			hmSum = hmStr + hmSum;
		}

		hpSum = tranformString(hpSum);
		hmSum = tranformString(hmSum);

		Levenshtein lev = new Levenshtein();
		double labelsim = lev.getSimilarityRatio(hpSum, hmSum);
		return labelsim;
	}

	// compare the numbers in two instances
	public double compareNumbers(OntClass h, OntClass m) {
		OntClass human = h;
		OntClass mouse = m;
		String hpSum = "";
		String hmSum = "";

		for (StmtIterator j = human.listProperties(); j.hasNext();) {
			RDFNode hp = j.next().getObject();
			// Change into String format
			String hpStr = hp.toString();
			hpStr=extractNumber(hpStr);
			// Only extract the numbers
			hpSum = hpStr + hpSum;
		}

		for (StmtIterator i = mouse.listProperties(); i.hasNext();) {
			RDFNode hm = i.next().getObject();
			String hmStr = hm.toString();
			hmStr=extractNumber(hmStr);
			hmSum = hmStr + hmSum;
		}
		Levenshtein lev = new Levenshtein();
		double labelsim = lev.getSimilarityRatio(hpSum, hmSum);
		return labelsim;
	}
	
	/*
	 * Transform string [lowerCase, without blank and special symbols]
	 */
	public String tranformString(String str) {

		// Regex can be used here in future
		String result = "";
		result = str.replace("_", "");
		result = result.replace(" ", "");
		result = result.replace(":", "");
		result = result.toLowerCase();
		return result;
	}
 /*
  * ExtractNumber from String
  */
	public String extractNumber(String numStr) {
		String a = numStr;
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(a);
		return m.replaceAll("").trim();
	}
	 /*
	  * Compute cosine similarity of all properties' values
	  */
	public String compareValues(OntClass h, OntClass m){
		OntClass human = h;
		OntClass mouse = m;
        List<String> humanStr= new ArrayList<String>();
        List<String> mouseStr= new ArrayList<String>();
        
		for (StmtIterator j = human.listProperties(); j.hasNext();) {
			RDFNode hp = j.next().getObject();
			// Change into String format
			String hpStr = hp.toString();
			humanStr.add(hpStr);
			
		}
		for (StmtIterator i = mouse.listProperties(); i.hasNext();) {
			RDFNode hm = i.next().getObject();
			String hmStr = hm.toString();
			mouseStr.add(hmStr);
		}
		String labelsim="" ;
		return labelsim;
	}
}
