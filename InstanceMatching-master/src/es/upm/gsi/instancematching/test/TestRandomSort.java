package es.upm.gsi.instancematching.test;

import java.util.Arrays;
import java.util.Random;

public class TestRandomSort {
	 public static void main(String[] args) {   
	     // Ҫ�����һ������   
	     int[] seed = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };   
	     int len=seed.length;  
	     int[] result= new int[len];   
	     Random random = new Random();   
	     for (int i = 0; i < len; i++) {   
	       // �õ�һ��λ��   
	       int r = random.nextInt(len - i);   
	       // �õ��Ǹ�λ�õ���ֵ   
	       result[i] = seed[r];   
	       // �����һ��δ�õ����ַŵ�����   
	       seed[r] = seed[len - 1 - i];   
	     }   
	     System.out.println("result:" + Arrays.toString(result));   
	   }   
}