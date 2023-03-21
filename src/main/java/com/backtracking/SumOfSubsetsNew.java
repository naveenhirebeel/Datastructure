package com.backtracking;

import java.util.Scanner;

//DID NOT UNDERSTANDDDDDDDDDDDDDDDDDDDDD YETTTTTTTTTTTTTTTTTTTTT
public class SumOfSubsetsNew {

	int[] w;
	int[] x;
	int sum;

	public void process() {
		getData();
	}

	private void getData() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the number of elements:");
		int n = sc.nextInt();
		w = new int[n];
		x = new int[n];
		int total = 0;
		System.out.println("Enter " + n + " Elements :");
		for (int i = 0; i < n; i++) {
			w[i] = sc.nextInt();
			total += w[i];
		}
		System.out.println("Enter the sum to be obtained: ");
		sum = sc.nextInt();
		if (total < sum) {
			System.out.println("Not possible to obtain the subset!!!");
			System.exit(1);
		}
		subset(0, 0, total);
	}

	private void subset(int k, int s, int r) {
		//int i = 0;
		x[k] = 1;
		if (s + w[k] == sum) {
			System.out.println();
			for (int i = 0; i <= k; i++) {
				System.out.print("\t" + x[i]);
			}
			return;
		} else if ((s + w[k] + w[k + 1]) <= sum) {
			subset(k + 1, s + w[k], r - w[k]);
		}

//		if (k+1<6 && (s + w[k + 1]) <= sum && (s + r - w[k]) >= sum) {
		if ((s + r - w[k]) >= sum && (s + w[k + 1]) <= sum) {
			x[k] = 0;
			subset( k + 1, s,r - w[k]);
		}
	}

	/*private void subset(int k, int s, int r) {
		x[k] = 1;
		if(s+w[k] == sum) {
			System.out.println();
			for (int i = 0; i <= k; i++) {
				System.out.print("\t" + x[i]);
			}
		} else if(s+w[k]+w[k+1] <= sum) {
			subset(k+1, s+w[k], r-w[k]);
		}

		//Exclude
		if(k+1<6 &&s+w[k+1] <= sum && s+r-w[k]>=sum) {
			x[k] = 0;
			subset(k+1, s, r-w[k]);
		}
	}*/

	public static void main(String[] args) {
		new SumOfSubsetsNew().process();
	}
}