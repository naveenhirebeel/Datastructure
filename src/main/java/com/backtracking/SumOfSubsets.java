package com.backtracking;

import java.util.Scanner;
//DID NOT UNDERSTANDDDDDDDDDDDDDDDDDDDDD YETTTTTTTTTTTTTTTTTTTTT
public class SumOfSubsets {

	int[] w = {5, 10, 12, 13, 15, 18};
	int[] x = new int[6];
	int sum = 30;
	int total = 0;

	public void process() {
		getData();
	}

	private void getData() {
		for (int i = 0; i < w.length; i++) {
			System.out.print("\t" + x[i]);
		}
		for(int i : w) {
			total += i;
		}
		System.out.println("\nTotal "+total);
		if (total < sum) {
			System.out.println("Not possible to obtain the subset!!!");
			System.exit(1);
		}
		subset(0, 0, total);
	}

	private void subset(int k, int s, int remaining) {
		x[k] = 1;
		if(s + w[k] == sum) {
			System.out.println("\nPrint Selected arrays");
			for (int i = 0; i <= k; i++) {
				System.out.print("\t" + x[i]);
			}
		} else if(s + w[k] +w[k+1] <= sum) {
			subset(k+1,s + w[k], remaining - w[k]);
		}
		if(s + remaining - w[k] >= sum && s + w[k+1] <= sum) {
			x[k] = 0;
			subset(k+1, s, remaining - w[k]);
		}
	}


	public static void main(String[] args) {
		new SumOfSubsets().process();
	}
}