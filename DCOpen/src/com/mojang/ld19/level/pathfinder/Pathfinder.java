package com.mojang.ld19.level.pathfinder;

import com.mojang.ld19.Entity;
import com.mojang.ld19.level.*;
import com.mojang.ld19.level.object.TeleporterParticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pathfinder 
{
	public static List<Node> getPath(int x, int y, Level level, Entity entity)
	{
		ArrayList<Node> closedlist = new ArrayList<Node>();
		ArrayList<Node> openlist = new ArrayList<Node>();
		
		int step = 0;
		
		int localx = entity.location.x;
		int localy = entity.location.y;
		Node startNode = new Node(localx, localy, getHValue(localx, localy, x, y), 0, null);
		Node destNode = new Node(x, y, 0, 0, null);
		Node currentNode = startNode;
		
		openlist.add(startNode);
		
		//System.out.printf("Starting path at %d, %d\n", localx, localy);
		
		while (step <= 128)
		{
			//look for lowest f cost
			Node checkNode = getLowestCost(openlist);
			if (closedlist.contains(checkNode))
			{
				//System.out.printf("Stepping into the same node!!!!\n");
				//return;
			}
			Node nextNode = null;
			Node prevNode = null;
			
			//switch it to the closed list
			openlist.remove(checkNode);
			closedlist.add(checkNode);
			prevNode = currentNode;
			currentNode = checkNode;
			
			localx = currentNode.x;
			localy = currentNode.y;
			
			if (currentNode.equals(prevNode) && step != 0)
			{
				//System.out.printf("Trying to step into the same node!\n");
			}
			
			if (checkNode.equalsIgnoreH(destNode))
			{
				//System.out.println("Path found!");
				break;
			}
			
			//System.out.printf("weight %d\n", currentNode.g);
			
			//for each of the 4 squares adjacent
			for (int lx = -1; lx <= 1; lx += 2)
			{
				Tile tile = level.getTile(localx + lx, localy);
				Node node = new Node(localx + lx, localy, getHValue(localx+lx, localy, x, y), currentNode.g + 10, currentNode);
				int index = openlist.indexOf(node);
				Node oldNode = null;
				if (index != -1)
				{
					oldNode = openlist.get(index);
				}
				if (!tile.blocks(entity, -1, false) && !closedlist.contains(node))
				{
					if (!openlist.contains(node))
					{
						openlist.add(node);
						//System.out.printf("added node at %d, %d\n", node.x, node.y);
					}
					else if (oldNode != null)
					{
						if (oldNode.g < currentNode.g)
						{

							nextNode = node;
							oldNode.g = oldNode.g + 10;
							oldNode.parent = currentNode;
							openlist.set(index, oldNode);
							//System.out.printf("changed node at %d, %d\n", node.x, node.y);
						}
					}
				}
			}
			for (int ly = -1; ly <= 1; ly += 2)
			{
				Tile tile = level.getTile(localx, localy+ly);
				Node node = new Node(localx, localy+ly, getHValue(localx, localy+ly, x, y), currentNode.g + 10, currentNode);
				int index = openlist.indexOf(node);
				Node oldNode = null;
				if (index != -1)
				{
					oldNode = openlist.get(index);
				}
				if (!tile.blocks(entity, -1, false) && !closedlist.contains(node))
				{
					if (!openlist.contains(node))
					{
						openlist.add(node);
						//System.out.printf("added node at %d, %d\n", node.x, node.y);
					}
					else if (oldNode != null)
					{
						if (oldNode.g < currentNode.g)
						{
							nextNode = node;
							oldNode.g = oldNode.g + 10;
							oldNode.parent = currentNode;
							openlist.set(index, oldNode);
							//System.out.printf("changed node at %d, %d\n", node.x, node.y);
						}
					}
				}
			}
			int weight = 0;
			if (nextNode != null)
			{

			}
			//System.out.printf("adds %d changes %d weight %d\n", adds, changes, weight);
			if (openlist.size() == 0)
			{
				//System.out.println("Failed: No path found!");
				return null;
			}
			step++;
		}
		
		int startPos = closedlist.size() - 1;
		
		ArrayList<Node> path = new ArrayList<Node>();
		
		Node searchNode = closedlist.get(startPos);
		while (searchNode != startNode)
		{
			path.add(searchNode);
			searchNode = searchNode.parent;
		}
		
		//System.out.printf("count: %d\n", count);
		path.add(startNode);
		ArrayList<Node> truepath = new ArrayList<Node>();
		for (int lx = 0; lx < path.size(); lx++)
		{
			int offset = (path.size() - 1) - lx;
			truepath.add(path.get(offset));
		}
		
		return truepath;
	}
	
	//old
	/*while (step < 128)
	{
		Node prevNode = null;
		closedlist.add(currentNode);
		int adds = 0;
		for (int lx = -1; lx <= 1; lx += 2)
		{
			Tile tile = level.getTile(localx + lx, localy);
			if (!tile.blocks(entity, -1, false))
			{
				Node node = new Node(localx + lx, localy, getHValue(localx+lx, localy, x, y), currentNode);
				openlist.add(node);
				adds++;
			}
		}
		for (int ly = -1; ly <= 1; ly += 2)
		{
			Tile tile = level.getTile(localx, localy+ly);
			if (!tile.blocks(entity, -1, false))
			{
				Node node = new Node(localx, localy+ly, getHValue(localx, localy+ly, x, y), currentNode);
				openlist.add(node);
				adds++;
			}
		}
		int localmin = 2147483647;
		if (adds == 0)
		{
			System.out.println("FAIL CONDITION");
		}
		if (openlist.size() == 0)
		{
			return;
		}
		Node addNode = null;
		for (int nx = 0; nx < openlist.size(); nx++)
		{
			Node node = openlist.get(nx);
			if (node.h < localmin)
			{
				localmin = node.h;
				//prevNode = currentNode;
				addNode = node;
			}
		}
		if (addNode != null)
		{
			prevNode = currentNode;
			currentNode = addNode;
			//closedlist.add(addNode);
		}
		if (prevNode != null)
		{
			if (prevNode.x == currentNode.x && prevNode.y == currentNode.y)
			{
				System.out.println("FAIL CONDITION -- Stepping into itself!!!");
				//break;
			}
		}
		if (closedlist.contains(currentNode))
		{
			System.out.println("FAIL CONDITION -- Recursive Step!!!");
		}
		openlist.clear();
		localx = currentNode.x;
		localy = currentNode.y;
		if (localx == x && localy == y)
		{
			break;
		}
		step++;
	}*/
	
	public static int getHValue(int currentX, int currentY, int targetX, int targetY)
	{
		return 10*(Math.abs(currentX-targetX) + Math.abs(currentY-targetY));
	}
	
	public static Node getLowestCost(ArrayList<Node> list)
	{
		int lowestCost = Integer.MAX_VALUE;
		Node returnNode = null;
		for (int x = 0; x < list.size(); x++)
		{
			Node checkNode = list.get(x);
			if ((checkNode.h + checkNode.g) < lowestCost)
			{
				returnNode = checkNode;
				lowestCost = checkNode.h + checkNode.g;
			}
		}
		return returnNode;
	}
}
