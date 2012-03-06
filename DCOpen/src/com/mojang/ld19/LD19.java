package com.mojang.ld19;

import java.awt.*;
import java.io.*;

import javax.swing.*;

import javax.swing.JApplet;

import com.mojang.ld19.display.*;
import com.mojang.ld19.gui.Gui;
import com.mojang.ld19.gui.GuiItBroke;
import com.mojang.ld19.gui.Menu;
import com.mojang.ld19.gui.MainMenu;
import com.mojang.ld19.item.Item;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.level.object.ThrownItem;
import com.mojang.ld19.level.walls.Walls;

public class LD19 extends JApplet implements Runnable {
    private static final long serialVersionUID = 1L;

    private boolean stopped = false;

    public static final int WIDTH = 426;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;
    private static final int NS_PER_TICK = 1000000000 / 60;

    private Screen screen = new Screen(WIDTH, HEIGHT, SCALE);
    private Input input = new Input();
    private Gui gui;
    private Menu mainMenu;
    private Thread thread;

    private Level level;
    
    public boolean isRunningExternal = false;
    
    /**
     * Readies the external client for usage
     */
    public void startExtClient()
    {
    	System.out.printf("Launching external client!\n");
    	Dimension size = new Dimension(WIDTH * SCALE - 2, HEIGHT * SCALE - 2);
		setSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		isRunningExternal = true;
    }

    /**
     * Ensures that the art, items, and entities are all ready for usage.
     */
    public void init() 
    {
    	System.out.printf("Starting DungeonCrawly!\n");
        Canvas c = screen.getComponent();
        c.setFocusable(true);
        add(c);

        c.addKeyListener(input);
        c.addFocusListener(input);
        c.addMouseListener(input);
        c.addMouseMotionListener(input);
        
        Art.init();
        EntityList.init();
        Item.init();
        
        mainMenu = new MainMenu(this);
    }
    
    /**
     * Starts a new level, at layer 0 with starting gear
     * @param playerClass The class of the player
     * @param altSkill The third skill of the player (I should just pass a Player instance honestly)
     * @param str The player's strength
     * @param dex The player's dexterity 
     * @param con The player's constitution
     * @param ints The player's intellgence
     * @param skill1 The player's skill points for skill 1
     * @param skill2 The player's skill points for skill 2
     * @param skill3 The palyer's skill points for skill 3
     */
    public void beginGame(PlayerClass playerClass, PlayerSkill altSkill, int str, int dex, int con, int ints, int skill1, int skill2, int skill3)
    {
        level = new Level(null, 0, false);
        level.init();
        level.player.currentClass = playerClass;
        level.player.skill1 = playerClass.primarySkill;
        level.player.skill2 = playerClass.secondarySkill;
        level.player.skill3 = altSkill;
        level.player.statStr = str;
        level.player.statDex = dex;
        level.player.statCon = con;
        level.player.statInt = ints;
        level.player.skill1Points = skill1;
        level.player.skill2Points = skill2;
        level.player.skill3Points = skill3;
        gui = new Gui(level.player);
        //mainMenu = null;
    }
    
    /**
     * Changes the active game menu
     * @param menu The new menu to be made active
     */
    public void changeMenu(Menu menu)
    {
    	mainMenu = menu;
    }

    /**
     * Starts the main game thread
     */
    public synchronized void start() 
    {
        stopped = false;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Stops the main game thread, and ensures that all of the graphics are cleared
     */
    public synchronized void stop() 
    {
    	System.out.println("Shutting down...");
    	Level.clearLevels();
        stopped = true;
		try 
		{
			thread.join();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		System.out.println("I don't blame you...");
    }

    public void paint(Graphics g) 
    {
    }

    public void update(Graphics g) 
    {
    }

    /**
     * Main game loop for DungeonCrawly
     */
    public void run() 
    {
        long lastTime = System.nanoTime();
        long unprocessedTime = 0;

        int frames = 0;
        long lastFrameTime = System.currentTimeMillis();
        while (!stopped) 
        {
            long now = System.nanoTime();
            unprocessedTime += now - lastTime;
            lastTime = now;
            int click = input.click;

            boolean shouldRender = false;

            while (unprocessedTime > NS_PER_TICK) 
            {
                unprocessedTime -= NS_PER_TICK;
                try
                {
                	tick();
                }
                catch (Exception exc)
                {
                	level = null;
                	mainMenu = new GuiItBroke(this, exc);
                }
                shouldRender = true;
            }

            if (shouldRender) 
            {
                screen.clear();
                render();
                if (level != null)
                {
	                gui.render(screen);
	                if (click > 0) 
	                {
	                    if (level.player.health > 0) 
	                    {
	                        if (screen.handleClick(level, click)) 
	                        {
	                        } 
	                        else 
	                        {
	                            if (click == 1 && level.player.carried != null) 
	                            {
	                                if (screen.yMouse < LD19.HEIGHT / 2) 
	                                {
	                                    throwItem(screen.xMouse < LD19.WIDTH / 2);
	                                }
	                            }
	                        }
	                    }
	                    input.click = 0;
	                }
	                if (level.player.carried != null) 
	                {
	                    Sprite sprite = new Sprite(0, 0, 0, level.player.carried.icon);
	                    screen.blit(sprite, screen.xMouse - 8, screen.yMouse - 8, null);
	                }
                }
                else if (mainMenu != null)
                {
                	mainMenu.render(screen);
	                if (click > 0) 
	                {
		                screen.handleClick(null, click);
		                input.click = 0;
	                }
                }
	
                try 
                {
                    screen.show();
                }
                catch (Exception e) 
                {
                }
                frames++; 
            } 
            else 
            {
                try 
                {
                    Thread.sleep(1);
                } 
                catch (InterruptedException e) 
                {
                    e.printStackTrace();
                }
            }

            if (System.currentTimeMillis() - lastFrameTime > 1000) 
            {
                int fps = frames;
                frames = 0;
                lastFrameTime += 1000;
                System.out.println("Fps: " + fps);
            }
        }
    }

    /**
     * Throws the item in the player's hands
     * @param left Specifies if the weapon should be on the left. 
     */
    private void throwItem(boolean left) {
        int corner = -1;
        if (level.player.rot == 0) corner = left ? 3 : 2;
        if (level.player.rot == 1) corner = left ? 1 : 3;
        if (level.player.rot == 2) corner = left ? 0 : 1;
        if (level.player.rot == 3) corner = left ? 2 : 0;

        if (corner >= 0) {
            level.getTile(level.player.x, level.player.z).addEntity(corner, new ThrownItem(level.player.carried, level.player.rot));
            level.player.carried = null;
        }
    }

    /**
     * Handles game input and lets all the tiles tick 
     * @throws Exception
     */
    private void tick() throws Exception 
    {
    	if (level != null)
    	{
	        Walls.tick();
	
	        Player player = level.player;
	        if (input.keys[Input.TURN_LEFT]) {
	            player.queueMove(Player.TURN_LEFT);
	            input.keys[Input.TURN_LEFT] = false;
	        }
	        if (input.keys[Input.TURN_RIGHT]) {
	            player.queueMove(Player.TURN_RIGHT);
	            input.keys[Input.TURN_RIGHT] = false;
	        }
	        if (input.keys[Input.UP]) {
	            player.queueMove(Player.MOVE_FORWARD);
	            input.keys[Input.UP] = false;
	        }
	        if (input.keys[Input.DOWN]) {
	            player.queueMove(Player.MOVE_BACK);
	            input.keys[Input.DOWN] = false;
	        }
	        if (input.keys[Input.LEFT]) {
	            player.queueMove(Player.MOVE_LEFT);
	            input.keys[Input.LEFT] = false;
	        }
	        if (input.keys[Input.RIGHT]) {
	            player.queueMove(Player.MOVE_RIGHT);
	            input.keys[Input.RIGHT] = false;
	        }
	        
	        if (input.keys[Input.SAVE_TEST]) 
	        {
	        	saveFile();
	        	input.keys[Input.SAVE_TEST] = false;
	        }
	
	        level.tick();
	        gui.tick();
	        if (level.replaceWith != null) {
	            Level next = level.replaceWith;
	            level.replaceWith = null;
	            level = next;
	            level.player.level = level;
	            level.init();
	        }
    	}
    	else if (mainMenu != null)
    	{
    		mainMenu.tick();
    	}
    }


    /**
     * Renders the current game
     */
    private void render() 
    {
        screen.xMouse = input.xMouse;
        screen.yMouse = input.yMouse;
        if (level != null)
        {
	        level.player.setCamera(screen);
	        if (level.player.blackout == 0) 
	        {
	        	if (level.player.statusTime[Player.STATUS_BLACKNESS] > 0)
	        	{
	        		screen.blacknessFactor = -80;
	        	}
	        	else screen.blacknessFactor = 20;
	        	if (level.layer <= -100)
	        	{
	        		screen.drawSky(Art.sky);
	        	}
	        	else if (level.layer > 0)
	        	{
	        		screen.drawSky(Art.skystar);
	        	}
	        	else screen.drawSky(Art.sky);
	            //        screen.setCamera(xPos, yPos, zPos, rot);
	
	            int xp = (int) Math.floor(screen.xCam);
	            int zp = (int) Math.floor(screen.zCam);
	            int r = 4;
	            int maxd = r * r * 140 / 100;
	            for (int x = -r; x <= r; x++)
	            {
	                for (int z = -r; z <= r; z++) 
	                {
	                    int dd = x * x + z * z;
	                    if (dd <= maxd) 
	                    {
	                        int xx = xp + x;
	                        int zz = zp + z;
	                        level.getTile(xx, zz).render(screen);
	                    }
	                }
	            }
	        }
        }
    }
    
    /**
     * Replicates the gamestate from a file on disk
     * @param loadfile The file to read from
     * @throws IOException
     */
    public void loadGame(RandomAccessFile loadfile) throws IOException
    {
    	int layer = loadfile.readInt();
    	Player thePlayer = Player.loadPlayer(loadfile);
    	if (thePlayer == null)
    	{
    		throw new IOException("Critical error when loading player!!!!!11!1!11one!1one!!\n");
    	}
    	Level.loadAllOpenLevels(thePlayer, loadfile);
    	
    	Level startLevel = Level.get(layer, thePlayer);
    	thePlayer.level = startLevel;
    	level = startLevel;
    	level.player = thePlayer;
    	gui = new Gui(level.player);
    	gui.loadInventory(loadfile);
    	
        if (layer >= 0) 
        {
            Art.brick.replaceWith(Art.normalBrick);
        } 
        else if (layer < 0) 
        {
            Art.brick.replaceWith(Art.mossBrick);
        }
    }
    
    /**
     * Saves the gamestate to a file on disk
     * @param savefile The file to save to
     * @throws IOException
     */
    public void saveGame(RandomAccessFile savefile) throws IOException
    {
    	savefile.writeInt(level.layer);
    	level.player.savePlayer(savefile);
    	level.saveAllOpenLevels(savefile);
    	gui.saveInventory(savefile);
    }
    
    /**
     * Actual function for loading the game state
     */
    public void loadFile()
    {
    	try 
    	{
			RandomAccessFile theFile = new RandomAccessFile("C:/dcsave.heh", "r");
			try
			{
				loadGame(theFile);
			}
			catch (IOException exc)
			{
				System.out.printf("Load file error!\n");
				exc.printStackTrace();
			}
		} 
    	catch (FileNotFoundException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	catch (Exception e)
    	{
    		System.out.printf("Loadfile system not working in browser\n");
    	}
    }
    
    /**
     * Actual function for saving the game state
     */
    public void saveFile()
    {
    	try 
    	{
			RandomAccessFile theFile = new RandomAccessFile("C:/dcsave.heh", "rw");
			try
			{
				saveGame(theFile);
				level.player.addString("Saved successfully!");
			}
			catch (IOException exc)
			{
				level.player.addString("Save file error!");
				exc.printStackTrace();
			}
		} 
    	catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	catch (Exception e)
    	{
    		level.player.addString("Saving not working in browser version!");
    	}
    }
    
	/**
	 * Entry point for the game, as a local application
	 * @param args
	 */
    public static void main(String[] args) 
	{
		LD19 game = new LD19();
		game.startExtClient();

		JFrame frame = new JFrame("Dungeon Crawly");

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(game, BorderLayout.CENTER);

		frame.setContentPane(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		game.start();
		game.init();
	}
}
