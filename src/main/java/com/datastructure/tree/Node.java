package com.datastructure.tree;
public class Node
{
	public int val;
	public int height;
	public Node left, right;
	public Node(int item)
	{
		val = item;
		left = right = null;
	}
}