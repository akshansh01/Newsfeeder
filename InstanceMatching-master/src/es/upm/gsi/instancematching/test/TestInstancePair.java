package es.upm.gsi.instancematching.test;

import java.util.ArrayList;
import java.util.List;

import es.upm.gsi.instancematching.geneticprogramming.FitnessFouction;

public class TestInstancePair {

	public static void main(String[] args) {

		FitnessFouction ff = new FitnessFouction();
		List<String[]> l = new ArrayList<String[]>();
		l = ff.getRefernceInstancePairURL();
		String[] str = new String[2];

		for (int i = 0; i < l.size(); i++) {
			str = l.get(i);
			System.out.println(str[0]);
			System.out.println(str[1]);

		}

	}

}
