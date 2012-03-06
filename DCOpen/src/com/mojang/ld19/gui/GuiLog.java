package com.mojang.ld19.gui;

import java.util.ArrayList;
import java.util.Random;

import com.mojang.ld19.LD19;
import com.mojang.ld19.display.ClickListener;
import com.mojang.ld19.display.Screen;

public class GuiLog extends Menu implements ClickListener
{
	private ArrayList<String> messageList = new ArrayList<String>();
	public GuiLog(LD19 game)
	{
		this.game = game;
	}
	
	public void render(Screen screen)
	{		
		for (int x = 0; x < messageList.size(); x++)
		{
			TextRenderer.drawString(messageList.get(x), screen, 0, (8 * x), this);
		}
	}
	
	public void addMessage(String message)
	{
		messageList.add(message);
		System.out.println(message);
	}
}
