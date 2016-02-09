package es.upm.gsi.instancematching.candidateselection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �ֵ��࣬��¼�����г��ֹ������е��ʼ������
 * 
 * @author Administrator
 * 
 */
public class PredicateCount {

	private HashMap<String, Integer> dictionary;
	private int wordsCount;

	/**
	 * �ֵ������Ĺ��캯��
	 */
	public PredicateCount() {
		dictionary = new HashMap<String, Integer>();
		wordsCount = 0;
	}

	/**
	 * ���ֵ������һ������
	 * 
	 * @param word
	 */
	public void insert(String word) {
		if (dictionary.containsKey(word)) {
			int currentCount = dictionary.get(word);
			dictionary.put(word, currentCount + 1);
		} else {
			dictionary.put(word, 1);
		}
		wordsCount++;
	}

	/**
	 * ȡ���ֵ������в�ͬ�ĵ���
	 * 
	 * @return
	 */
	public int getDifferentWordsNum() {
		return dictionary.size();
	}

	/**
	 * �����ֵ�������е��� * ����ִ���
	 * 
	 * @return
	 */
	public int getAllWordsNum() {
		return wordsCount;
	}

	/**
	 * �����԰����ִ����Ķ�������
	 * 
	 */
	public List<String> sorthashMap(int num) {

		List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(
				dictionary.entrySet());

		// ����ǰ
		System.out.println("Before Sort\r\n");
		for (int i = 0; i < infoIds.size(); i++) {
			String id = infoIds.get(i).toString();
			System.out.println(id);
		}
		// ����
		Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				return (o2.getValue() / -o1.getValue());
			}
		});

		// �����
		System.out.println("After Sort\r\n");
		List<String> predicateStr=new ArrayList<String>();
		//String[] predicateStr = new String[10];
		for (int i = 0; i < infoIds.size(); i++) {
			String predicate = infoIds.get(i).getKey();
			Integer pn = infoIds.get(i).getValue();
			double temp =  ((double)pn.intValue() / num);
			//�˴���thresholdҲ��Ҫȷ��
			if (temp > 0.1) {
				predicateStr.add(predicate);;
			}

		}
		
		return predicateStr;

	}

	/**
	 * ��������
	 * 
	 */
	public double[] chaRu(double[] x) {

		for (int i = 1; i < x.length; i++) {
			for (int j = i; j > 0; j--) {
				if (x[j] < x[j - 1]) {
					double temp = x[j];
					x[j] = x[j - 1];
					x[j - 1] = temp;
				}
			}
		}
		return x;
	}
}
