package es.upm.gsi.instancematching.test;

public class TestArraySort {

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

	public static void main(String[] args) {
		TestArraySort t = new TestArraySort();
		double[][] d = new double[3][2];
		d[0][0] = 1;
		d[0][1] = 4;

		d[1][0] = 3;
		d[1][1] = 3;

		d[2][0] = 2;
		d[2][1] = 2;

		d = t.sort(d);

		for (int i = 0; i < d.length; i++) {

			for (int j = 0; j < d[i].length; j++) {

				System.out.print(d[i][j] + " ");

			}

		}
	}
}
