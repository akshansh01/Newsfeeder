package es.upm.gsi.instancematching.geneticprogramming;

import java.util.ArrayList;
import java.util.List;

public class Envolve {

	// 选择算子 ，没有采用轮盘赌选择方法
	public List<double[][]> getSelectPopulation(List<double[][]> thresholdList) {

		List<double[][]> thresholdTempList = thresholdList;

		List<double[][]> thresholdSelectList = new ArrayList<double[][]>();
		FitnessFouction ff = new FitnessFouction();
		double[][] thresholdp = new double[150][2];
		// 计算初始种群的fitness

		for (int i = 0; i < thresholdTempList.size(); i++) {
			double[][] dt = new double[3][2];
			dt = thresholdList.get(i);
			// 计算当前个体的fitness
			thresholdp[i][0] = ff.getTrueLinkPercent(dt);
			System.out.println("Now the getTrueLinkPercent is "
					+ thresholdp[i][0]);
			if (thresholdp[i][0] > 0.82) {
				System.out.print("The good value appear: ");
				System.out.println(dt[0][0] + " " + dt[1][0] + " " + dt[2][0]
						+ " " + dt[0][1] + " " + dt[1][1] + " " + dt[2][1]);
				// System.out.println("This is the "+i);

			}
			thresholdp[i][1] = i;

		}
		// 对150个初始种群进行排序，Fitness升序
		thresholdp = sort(thresholdp);
		// 选择后50个为优良个体
		for (int i = 100; i < 150; i++) {
			double[][] dt1 = new double[3][2];
			int k = (int) thresholdp[i][1];
			dt1 = thresholdTempList.get(k);
			thresholdSelectList.add(dt1);
		}
		return thresholdSelectList;
	}

	// 变异算子
	public List<double[][]> mutation(List<double[][]> selectList) {

		List<double[][]> mutationList = new ArrayList<double[][]>();

		double Max = 1.0;
		double Min = 0.2;
		for (int i = 0; i < 50; i++) {
			double[][] dt1 = new double[3][2];
			dt1 = selectList.get(i);
			double[][] dt2 = new double[3][2];

			// 第1个参数被随机数代替
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 2; k++) {
					if (j == 0 && k == 1) {
						dt2[0][1] = Math.random() * (Max - Min) + Min;
					} else {
						dt2[j][k] = dt1[j][k];
					}
				}
			}
			mutationList.add(dt2);

		}
		return mutationList;
	}

	// 交叉算子，单点交叉算子
	public List<double[][]> crossOver(List<double[][]> selectList) {

		List<double[][]> crossOverList = new ArrayList<double[][]>();

		for (int i = 0; i < 25; i++) {

			double[][] dt1 = new double[3][2];
			double[][] dt2 = new double[3][2];
			dt1 = selectList.get(i);
			dt2 = selectList.get(49 - i);

			double[][] dt11 = new double[3][2];
			double[][] dt21 = new double[3][2];

			// 后两个参数互换位置 To do

			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 2; k++) {

					if ((j == 1 && k == 1) || (j == 2 && k == 1)) {

						dt11[j][k] = dt2[j][k];
						dt21[j][k] = dt1[j][k];
						
					} else {

						dt11[j][k] = dt1[j][k];
						dt21[j][k] = dt2[j][k];
					}
				}
			}
			crossOverList.add(dt11);
			crossOverList.add(dt21);

		}
		return selectList;
	}

	// 快速排序算法
	public double[][] sort(double[][] x) {
		for (int i = 0; i < x.length; i++) {
			int lowerIndex = i;
			// 找出最小的一个索引
			for (int j = i + 1; j < x.length; j++) {
				if (x[j][0] < x[lowerIndex][0]) {
					lowerIndex = j;
				}
			}
			// 交换
			double temp = x[i][0];
			x[i][0] = x[lowerIndex][0];
			x[lowerIndex][0] = temp;

			// 同时交换索引值
			double temp1 = x[i][1];
			x[i][1] = x[lowerIndex][1];
			x[lowerIndex][1] = temp1;
		}
		return x;

	}

	public void envolve() {
		// 迭代3次
		int i = 0;

		List<double[][]> envolveList = new ArrayList<double[][]>();
		Population p = new Population();
		envolveList = p.generateRandomPopulation();
		System.out.println("The envolvement starts");

		while (i < 3) {
			// 选择优良个体
			System.out.println("Attention");
			System.out.println("This is the " + (i + 1) + " envolvement");

			List<double[][]> thresholdSelectList = new ArrayList<double[][]>();

			thresholdSelectList = getSelectPopulation(envolveList);

			envolveList.clear();

			envolveList.addAll(thresholdSelectList);

			// 变异
			List<double[][]> thresholdMutationList = new ArrayList<double[][]>();
			thresholdMutationList = mutation(thresholdSelectList);

			envolveList.addAll(thresholdMutationList);

			// 交叉
			List<double[][]> thresholdCrossOver = new ArrayList<double[][]>();
			thresholdCrossOver = crossOver(thresholdSelectList);

			envolveList.addAll(thresholdCrossOver);

			i++;
		}
		System.out.println("The envolvement ends");

	}

	public static void main(String[] args) {
		Envolve env = new Envolve();
		env.envolve();
	}

}
