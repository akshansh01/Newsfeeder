package es.upm.gsi.instancematching.candidateselection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典类，记录文章中出现过的所有单词及其次数
 * 
 * @author Administrator
 * 
 */
public class PredicateCount {

	private HashMap<String, Integer> dictionary;
	private int wordsCount;

	/**
	 * 字典这个类的构造函数
	 */
	public PredicateCount() {
		dictionary = new HashMap<String, Integer>();
		wordsCount = 0;
	}

	/**
	 * 向字典里插入一个单词
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
	 * 取得字典里所有不同的单词
	 * 
	 * @return
	 */
	public int getDifferentWordsNum() {
		return dictionary.size();
	}

	/**
	 * 返回字典里的所有单词 * 其出现次数
	 * 
	 * @return
	 */
	public int getAllWordsNum() {
		return wordsCount;
	}

	/**
	 * 对属性按出现次数的多少排序
	 * 
	 */
	public List<String> sorthashMap(int num) {

		List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(
				dictionary.entrySet());

		// 排序前
		System.out.println("Before Sort\r\n");
		for (int i = 0; i < infoIds.size(); i++) {
			String id = infoIds.get(i).toString();
			System.out.println(id);
		}
		// 排序
		Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				return (o2.getValue() / -o1.getValue());
			}
		});

		// 排序后
		System.out.println("After Sort\r\n");
		List<String> predicateStr=new ArrayList<String>();
		//String[] predicateStr = new String[10];
		for (int i = 0; i < infoIds.size(); i++) {
			String predicate = infoIds.get(i).getKey();
			Integer pn = infoIds.get(i).getValue();
			double temp =  ((double)pn.intValue() / num);
			//此处的threshold也需要确定
			if (temp > 0.1) {
				predicateStr.add(predicate);;
			}

		}
		
		return predicateStr;

	}

	/**
	 * 插入排序
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
