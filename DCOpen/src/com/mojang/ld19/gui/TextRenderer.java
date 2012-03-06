package com.mojang.ld19.gui;

import com.mojang.ld19.Art;
import com.mojang.ld19.display.ClickListener;
import com.mojang.ld19.display.Screen;

public class TextRenderer 
{
	private static final String chars = "" + //
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!?\"'/\\<>()[]{}" + //
			"abcdefghijklmnopqrstuvwxyz_               " + //
			"0123456789+-=*:;%                         " + //
			"";
	
	public static void drawString(String string, Screen screen, int x, int y, ClickListener clickHandler) 
	{
		int localy = 0;
		int localx = 0;
		for (int i = 0; i < string.length(); i++) 
		{
			int ch = chars.indexOf(string.charAt(i));
			
			if (string.charAt(i) == '\n')
			{
				localy += 7;
				localx = 0;
			}
			
			if (ch < 0) continue;
		
			screen.blit(Art.font[ch], x + (localx * 6), y + localy, clickHandler);
			localx++;
		}
	}
}
