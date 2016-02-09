package es.upm.gsi.instancematching.geneticprogramming;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {

	public List<double[][]> generateRandomPopulation() {
		// ��ʼ��150���������
		List<double[][]> thresholdList = new ArrayList<double[][]>();
		double Max = 1.0;
		double Min = 0.2;

		for (int i = 0; i < 150; i++) {
			double[][] threshold = new double[3][2];
			double [] randomArray=new double [3];
			randomArray=getRandomSort();
			
			threshold[0][0]= randomArray[0];
			threshold[0][1] = Math.random() * (Max - Min) + Min;
			// threshold[0] = ((Math.abs(new Random().nextDouble()))%7+3)/10;
			
			threshold[1][0]=randomArray[1];
			threshold[1][1] = Math.random() * (Max - Min) + Min;
			
			threshold[2][0]=randomArray[2];
			threshold[2][1] = Math.random() * (Max - Min) + Min;
			thresholdList.add(threshold);
		}
		return thresholdList;
	}

	public double[] getRandomSort() {
		// Ҫ�����һ������
		int[] seed = { 1, 2, 3 };
		int len = seed.length;
		double[] result = new double[len];
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			// �õ�һ��λ��
			int r = random.nextInt(len - i);
			// �õ��Ǹ�λ�õ���ֵ
			result[i] = (double)seed[r];
			// �����һ��δ�õ����ַŵ�����
			seed[r] = seed[len - 1 - i];
		}
		return result;
	}
}
