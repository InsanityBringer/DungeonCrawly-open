package com.mojang.ld19.gui;

import java.util.Random;

import com.mojang.ld19.LD19;
import com.mojang.ld19.display.ClickListener;
import com.mojang.ld19.display.Screen;

public class GuiItBroke extends Menu implements ClickListener
{
	String message = "heh";
	String[] titles = {"It broke, yo'", "Fatal Error!", "The game went boom", "It decided to crash", "This is probably InsanityBringer's fault"};
	Throwable theException;
	int title = 0;
	public GuiItBroke(LD19 game, Throwable theException)
	{
		this.game = game;
		this.theException = theException;
		Random r = new Random();
		title = r.nextInt(5);
	}
	
	public void render(Screen screen)
	{
		screen.fill(0, 0, 426, 240, 0x000000);
		TextRenderer.drawString(titles[title], screen, 0, 0, this);
		
		TextRenderer.drawString("Error message: " + theException.getMessage(), screen, 0, 16, this);
		TextRenderer.drawString("Stack Trace: ", screen, 0, 24, this);
		
		StackTraceElement[] stacktrace = theException.getStackTrace();
		
		for (int x = 0; x < stacktrace.length; x++)
		{
			TextRenderer.drawString(stacktrace[x].toString(), screen, 0, 32 + (8 * x), this);
		}
	}
}
