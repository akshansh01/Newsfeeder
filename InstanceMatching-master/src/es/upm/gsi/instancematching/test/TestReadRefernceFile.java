package es.upm.gsi.instancematching.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestReadRefernceFile {



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
				//System.out.println("Root element "
						//+ doc.getDocumentElement().getNodeName());

				NodeList nodeLst = doc.getElementsByTagName("map");
				//System.out.println("Information of all Maps");
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
				
				for(int i=0;i<referenceInstancemouseList.size();i++){
					String[] str=new String[2];
					str[0]=referenceInstancemouseList.get(i);
					str[1]=referenceInstancehumanList.get(i);
					referenceInstanceList.add(str);
					System.out.println(str[0]+" "+str[1]);
					
				}
				

			} catch (Exception e) {
				e.printStackTrace();
			}

			return referenceInstanceList;
		}
		
		public static void main(String[] args) {
			TestReadRefernceFile test= new TestReadRefernceFile();
			test.getRefernceInstancePairURL();
			
		}
}
