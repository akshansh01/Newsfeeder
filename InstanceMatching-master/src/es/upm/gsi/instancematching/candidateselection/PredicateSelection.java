package es.upm.gsi.instancematching.candidateselection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import es.upm.gsi.instancematching.Levenshtein;
import es.upm.gsi.instancematching.ReadOntology;

//此想法来自论文   SLINT

public class PredicateSelection {

	public static void main(String[] args) {

		PredicateSelection preSel = new PredicateSelection();
		preSel.predicateSelect();
	}

	public void predicateSelect() {

		int mousetriplecount = 0;
		int humantriplecount = 0;

		// 对所有属性的重要性排序
		OntModel ontModelmouse = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);
		ontModelmouse.read("file:./mouse.owl");
		PredicateCount pc = new PredicateCount();

		for (Iterator<OntClass> j = ontModelmouse.listClasses(); j.hasNext();) {
			OntClass mouse = (OntClass) j.next();
			mousetriplecount++;
			for (StmtIterator i = mouse.listProperties(); i.hasNext();) {
				Property hp = i.next().getPredicate();
				String hpStr = hp.getLocalName().toString();

				pc.insert(hpStr);

			}
		}
		System.out.println("The predicates of mouse.owl ");
		List<String> mouseStr = pc.sorthashMap(mousetriplecount);

		// //////////////////////////////////////////////////////////////////////////////////

		// 对所有属性的重要性排序
		OntModel ontModelhuman = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);
		ontModelhuman.read("file:./human.owl");
		PredicateCount pc1 = new PredicateCount();
		for (Iterator<OntClass> j = ontModelhuman.listClasses(); j.hasNext();) {
			OntClass human = (OntClass) j.next();

			humantriplecount++;
			for (StmtIterator i = human.listProperties(); i.hasNext();) {
				Property hp = i.next().getPredicate();
				String hpStr = hp.getLocalName().toString();
				pc1.insert(hpStr);

			}
		}

		System.out.println("The predicates of human.owl ");
		List<String> humanStr = pc1.sorthashMap(humantriplecount);

		// 得到predicate alignments
		List<String[]> predicatealignment =new ArrayList<String[]>();

        
		List<OntClass> hList = new ArrayList<OntClass>();
		List<OntClass> mList = new ArrayList<OntClass>();
		
		ReadOntology rt = new ReadOntology();
		rt.readOwlFile();

		hList = rt.humanList;
		mList = rt.mouseList;

		for (int c = 0; c < mouseStr.size(); c++) {
			
			List<String> mouseValue = getValuePredicate(mouseStr.get(c), mList);
			for (int d = 0; d < humanStr.size(); d++) {
				List<String> humanValue = getValuePredicate(humanStr.get(d),
						hList);
				double sumValuesim = 0.0;

				for (int i = 0; i < mouseValue.size(); i++) {
					String mouseV = mouseValue.get(i);

					for (int j = 0; j < humanValue.size(); j++) {
						String humanV = humanValue.get(j);

						Levenshtein lev = new Levenshtein();
						double valuesim = lev
								.getSimilarityRatio(mouseV, humanV);
						sumValuesim = sumValuesim + valuesim;

					}
				}
				double confidencevalue=0.0;
				if(mouseValue.size()!=0&& humanValue.size()!=0){
					confidencevalue = sumValuesim
						/ (mouseValue.size() * humanValue.size());
					
					System.out.println("The confidence value is " +confidencevalue);
				}
				// 此处threshold需要确定下
				if (confidencevalue > 0.02) {
					String[] tempStr=new String[2];
					tempStr[0] = mouseStr.get(c);
					tempStr[1] = humanStr.get(d);
					predicatealignment.add(tempStr);
				}

			}
		}
		

		for (int i = 0; i < predicatealignment.size(); i++) {
			String[] temp=new String[2];
			temp=predicatealignment.get(i);
			System.out.println(temp[0]+" and "+temp[1]+" are pairs");
			
		}
	}

	public List<String> getValuePredicate(String pre, List<OntClass> classhmList) {

		// 取出当前predicate所涉及的属性

		List<String> valuesList = new ArrayList<String>();
		for (int j = 0; j < classhmList.size(); j++) {
			OntClass hmClass;
			
			hmClass = (OntClass) classhmList.get(j);
			if (!hmClass.isAnon()) {
				
				for (StmtIterator k = hmClass.listProperties(); k.hasNext();) {
					Property hp = k.next().getPredicate();
					String hpStr = hp.getLocalName().toString();
					if (hpStr.equals(pre)) {

						//不考虑，如果一个class有两个或以上相同的属性
						RDFNode value = hmClass.getPropertyValue(hp);
						
						valuesList.add(value.toString());

					}
				}
			}
		}
		return valuesList;

	}
}
