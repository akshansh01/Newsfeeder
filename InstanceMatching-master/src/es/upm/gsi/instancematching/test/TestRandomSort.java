package es.upm.gsi.instancematching.test;

import java.util.Arrays;
import java.util.Random;

public class TestRandomSort {
	 public static void main(String[] args) {   
	     // 要排序的一组数字   
	     int[] seed = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };   
	     int len=seed.length;  
	     int[] result= new int[len];   
	     Random random = new Random();   
	     for (int i = 0; i < len; i++) {   
	       // 得到一个位置   
	       int r = random.nextInt(len - i);   
	       // 得到那个位置的数值   
	       result[i] = seed[r];   
	       // 将最后一个未用的数字放到这里   
	       seed[r] = seed[len - 1 - i];   
	     }   
	     System.out.println("result:" + Arrays.toString(result));   
	   }   
}