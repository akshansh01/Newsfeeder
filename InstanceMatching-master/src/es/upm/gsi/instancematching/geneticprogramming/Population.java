package es.upm.gsi.instancematching.geneticprogramming;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {

	public List<double[][]> generateRandomPopulation() {
		// 初始化150个随机个体
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
		// 要排序的一组数字
		int[] seed = { 1, 2, 3 };
		int len = seed.length;
		double[] result = new double[len];
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			// 得到一个位置
			int r = random.nextInt(len - i);
			// 得到那个位置的数值
			result[i] = (double)seed[r];
			// 将最后一个未用的数字放到这里
			seed[r] = seed[len - 1 - i];
		}
		return result;
	}
}
