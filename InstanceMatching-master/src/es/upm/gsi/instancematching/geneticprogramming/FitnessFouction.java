package es.upm.gsi.instancematching.geneticprogramming;

import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import es.upm.gsi.instancematching.SimilarityMetric;

public class FitnessFouction {

	public double getTrueLinkPercent(double a[][]) {

		OntModel ontModelhuman = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);
		ontModelhuman.read("file:./human.owl");

		OntModel ontModelmouse = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);
		ontModelmouse.read("file:./mouse.owl");

		List<String[]> referenceInstanceList = new ArrayList<String[]>();
		referenceInstanceList = getRefernceInstancePairURL();

		// 存储中间候选实体对
		List<OntClass[]> selectOntClassList = new ArrayList<OntClass[]>();

		List<OntClass[]> selectTempOntClassList = new ArrayList<OntClass[]>();

		// 取出reference link中的实体对
		for (int k = 0; k < referenceInstanceList.size(); k++) {
			String[] temp = new String[2];
			temp = referenceInstanceList.get(k);

			OntClass mouse = ontModelmouse.getOntClass(temp[0]);
			OntClass human = ontModelhuman.getOntClass(temp[1]);

			OntClass[] selclass = new OntClass[2];
			selclass[0] = mouse;
			selclass[1] = human;
			selectOntClassList.add(selclass);

		}

		for (int i = 0; i < 3; i++) {
			selectTempOntClassList = classcificationInstancePairs(
					selectOntClassList, i, a);
			selectOntClassList.clear();
			selectOntClassList.addAll(selectTempOntClassList);
		}
		return (double) selectOntClassList.size()
				/ referenceInstanceList.size();
	}
	

	//对实体对分类
	public List<OntClass[]> classcificationInstancePairs(
			List<OntClass[]> select1OntClassList, int i, double a[][]) {
		
		List<OntClass[]> select2OntClassList = new ArrayList<OntClass[]>();
		
		double tl = 0.0;
		double tp = 0.0;
		double tn = 0.0;
		SimilarityMetric sm = new SimilarityMetric();
		
		for (int k = 0; k < select1OntClassList.size(); k++) {

			OntClass[] tempClass = new OntClass[2];
			tempClass = select1OntClassList.get(k);
			OntClass mouse = tempClass[0];
			OntClass human = tempClass[1];
			
			if (!human.isAnon()&&!mouse.isAnon()) {
				
			if (a[i][0] == 1.0) {
				// Compare similarity based on label of instance
				tl = sm.label2Similarity(human.getLabel(null),
						mouse.getLabel(null));
				if (tl > a[i][1]) {
					OntClass[] tempSelClass = new OntClass[2];
					tempSelClass[0] = mouse;
					tempSelClass[1] = human;
					select2OntClassList.add(tempSelClass);
				}

			}

			if (a[i][0] == 2.0) {
				// Compare similarity based on the properties of instance
				tp = sm.compareProperty(human, mouse);
				if (tp > a[i][1]) {
					OntClass[] tempSelClass = new OntClass[2];
					tempSelClass[0] = mouse;
					tempSelClass[1] = human;
					select2OntClassList.add(tempSelClass);
				}
			}
			if (a[i][0] == 3.0) {
				/*
				 * Compare similarity based on the numbers of values of
				 * properties of instance
				 */
				tn = sm.compareNumbers(human, mouse);
				if (tn > a[i][1]) {
					OntClass[] tempSelClass = new OntClass[2];
					tempSelClass[0] = mouse;
					tempSelClass[1] = human;
					select2OntClassList.add(tempSelClass);
				}
			}
		}
		}
		return select2OntClassList;
	}



	public List<String[]> getRefernceInstancePairURL() {

		List<String> referenceInstancemouseList = new ArrayList<String>();
		List<String> referenceInstancehumanList = new ArrayList<String>();
		List<String[]> referenceInstanceList = new ArrayList<String[]>();
		File file = new File("D:/workspace/InstanceMatching/reference.xml");
		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			// System.out.println("Root element "
			// + doc.getDocumentElement().getNodeName());

			NodeList nodeLst = doc.getElementsByTagName("map");
			// System.out.println("Information of all Maps");
			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = nodeLst.item(s);
				// map节点的孩子节点Cell
				NodeList ls = fstNode.getChildNodes();
				int size = ls.getLength();
				for (int k = 0; k < size; k++) {
					Node n2 = ls.item(k);
					NodeList childNodes = n2.getChildNodes();

					for (int j = 0; j < childNodes.getLength(); j++) {

						Node childNode = childNodes.item(j);
						// 如果这个节点属于Element ,再进行取值
						if (childNode instanceof Element) {
							// System.out.println("子节点名为:"
							// + childNode.getNodeName());

							if (childNode.getNodeName().equalsIgnoreCase(
									"entity1")) {
								NamedNodeMap test = childNode.getAttributes();
								Node node = test.getNamedItem("rdf:resource");
								String str = node.getNodeValue();
								referenceInstancemouseList.add(str);
								// System.out.println("The value is " + str);

							}
							if (childNode.getNodeName().equalsIgnoreCase(
									"entity2")) {

								NamedNodeMap test = childNode.getAttributes();
								Node node = test.getNamedItem("rdf:resource");
								String str = node.getNodeValue();
								referenceInstancehumanList.add(str);
								// System.out.println("The value is " + str);

							}

						}

					}

				}

			}

			for (int i = 0; i < referenceInstancemouseList.size(); i++) {
				String[] str = new String[2];
				str[0] = referenceInstancemouseList.get(i);
				str[1] = referenceInstancehumanList.get(i);
				referenceInstanceList.add(str);
				// System.out.println(str[0]+" "+str[1]);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return referenceInstanceList;
	}

}
