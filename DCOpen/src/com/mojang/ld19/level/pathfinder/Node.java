package com.mojang.ld19.level.pathfinder;

public class Node 
{
	public int x;
	public int y;
	public int h; 
	public int g;
	public Node parent;
	
	public Node(int x, int y, int h, int g, Node parent)
	{
		this.x = x;
		this.y = y;
		this.h = h;
		this.g = g;
		this.parent = parent;
	}
	
	public boolean equals(Object o)
	{
		Node otherNode = (Node)o;
		if (otherNode.x == x && otherNode.y == y)
		{
			return true;
		}
		return false;
	}
	
	public boolean equalsIgnoreH(Node otherNode)
	{
		if (otherNode.x == x && otherNode.y == y)
		{
			return true;
		}
		return false;
	}
}
