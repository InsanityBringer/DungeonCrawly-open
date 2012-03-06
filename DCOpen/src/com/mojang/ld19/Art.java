package com.mojang.ld19;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mojang.ld19.display.Bitmap;


public class Art {
    public static Bitmap sky = Art.load("/res/sky.png");
    public static Bitmap hellSky = Art.load("/res/hellsky.png");

    public static Bitmap brick = Art.load("/res/brick.png");
    public static Bitmap grass = Art.load("/res/grass.png");
    public static Bitmap dirt = Art.load("/res/dirt.png");
    public static Bitmap wood = Art.load("/res/wood.png");
    public static Bitmap flower = Art.load("/res/flower.png");
    public static Bitmap water = Art.load("/res/water.png");
    public static Bitmap ocean = Art.load("/res/ocean.png");
    public static Bitmap iron = Art.load("/res/iron.png");
    public static Bitmap stonePath = Art.load("/res/stonepath.png");
    public static Bitmap lightPost = Art.load("/res/lamppost.png");
    public static Bitmap window = Art.load("/res/window.png");
    public static Bitmap sparkle = Art.load("/res/gatesparkle.png");
    public static Bitmap magicsparkle = Art.load("/res/magicsparkle.png");
    public static Bitmap hellStone = Art.load("/res/hellstone.png");
    public static Bitmap skystar = Art.load("/res/skystar.png");

    public static Bitmap normalBrick = Art.load("/res/brick.png");
    public static Bitmap mossBrick = Art.load("/res/mossbrick.png");
    public static Bitmap inventory = Art.load("/res/gui/inventory.png");
    public static Bitmap slot = Art.load("/res/gui/slot.png");

    public static Bitmap leaves = Art.load("/res/leaves.png");
    public static Bitmap branch = Art.load("/res/branch.png");
    public static Bitmap trunk = Art.load("/res/trunk.png");

    public static Bitmap button_1 = Art.load("/res/button_1.png");
    public static Bitmap button_2 = Art.load("/res/button_2.png");
    public static Bitmap keyhole = Art.load("/res/keyhole.png");
    public static Bitmap arrow = Art.load("/res/arrow.png");
    public static Bitmap knife = Art.load("/res/knife.png");
    public static Bitmap spellGhoul = Art.load("/res/magicGhoul.png");
    public static Bitmap magmaBall = Art.load("/res/magmaball.png");
    public static Bitmap poisonCloud = Art.load("/res/poisoncloud.png");
    public static Bitmap fireball = Art.load("/res/fireball.png");
    public static Bitmap fire = Art.load("/res/fire.png");
    public static Bitmap gemSlot = Art.load("/res/gemReceptor.png");
    public static Bitmap blueGem = Art.load("/res/bluegem.png");
    public static Bitmap redGem = Art.load("/res/redgem.png");
    public static Bitmap greenGem = Art.load("/res/greenGem.png");
    public static Bitmap cyanGem = Art.load("/res/cyangem.png");
    public static Bitmap purpleGem = Art.load("/res/purplegem.png");
    public static Bitmap markedBrick = Art.load("/res/writingBrick.png");
    
    public static Bitmap guiButtonup = Art.load("/res/gui/button-up.png");
    public static Bitmap guiButtondown = Art.load("/res/gui/button-down.png");
    public static Bitmap guiinfoPanel = Art.load("/res/gui/infopanel.png");
    public static Bitmap guiMainMenu = Art.load("/res/gui/MainMenu.png");
    public static Bitmap guiMenuBDown = Art.load("/res/gui/menuButtonDown.png");
    public static Bitmap guiMenuBUp = Art.load("/res/gui/menuButtonUp.png");
    public static Bitmap guiMultiButton = Art.load("/res/gui/menuButtonSmall.png");
    public static Bitmap guiAmountInc = Art.load("/res/gui/amountInc.png");
    public static Bitmap guiAmountDec = Art.load("/res/gui/amountDec.png");
    public static Bitmap guiUpDown = Art.load("/res/gui/UpDownControl.png");
    public static Bitmap guiCharCreation = Art.load("/res/gui/charCreation.png");
    public static Bitmap guiInputField = Art.load("/res/gui/menuField.png");
    public static Bitmap guiPoisonBar = Art.load("/res/gui/poisonBar.png");
    public static Bitmap guiCastPanel = Art.load("/res/gui/cast.png");
    public static Bitmap guiCastBtnup = Art.load("/res/gui/castBtnUp.png");
    public static Bitmap guiCastBtndn = Art.load("/res/gui/castBtnDn.png");
    public static Bitmap guiCastBtn = Art.load("/res/gui/castButton.png");
    public static Bitmap guiCastAbort = Art.load("/res/gui/abortButton.png");
    public static Bitmap guiSkillInc = Art.load("/res/gui/skillincbutton.png");

    public static Bitmap[] itemSheet = Art.loadSplit("/res/items.png", 16, 16);
    public static Bitmap[] font = Art.loadSplit("/res/ffont.png", 6, 6); 

    public static Bitmap[] slimes = Art.loadSeries("/res/char/slime", 4);
    public static Bitmap[] skeleton = Art.loadSeries("/res/char/skeleton", 4);
    public static Bitmap[] goblin = Art.loadSeries("/res/char/goblin", 4);
    public static Bitmap[] goblinArcher = Art.loadSeries("/res/char/goblinarcher", 4);
    public static Bitmap[] zombie = Art.loadSeries("/res/char/zombie", 4);
    public static Bitmap[] ghoul = Art.loadSeries("/res/char/ghoul", 4);
    public static Bitmap[] spider = Art.loadSeries("/res/char/spider", 4);
    public static Bitmap[] fireDemon = Art.loadSeries("/res/char/newfiredemon", 4);
    public static Bitmap[] imp = Art.loadSeries("/res/char/imp", 4);


    /**
     * Loads a set of images with the format name(number).png
     * @param name The base name of the series
     * @param len The amount of images to load
     * @return An array of bitmaps corresponding to the images
     */
    public static Bitmap[] loadSeries(String name, int len) {
        Bitmap[] result = new Bitmap[len];
        for (int i = 0; i < len; i++) {
            result[i] = load(name + i + ".png");
        }
        return result;
    }
    
    /**
     * Ensures that the images have been loaded
     */
    public static void init()
    {
    	System.out.printf("loading game assets\n");
    }

    /**
     * Loads an individual bitmap
     * @param name the name of the bitmap on disk
     * @return The loaded Bitmap
     */
    public static Bitmap load(String name) {
        try {
            BufferedImage bi = ImageIO.read(Art.class.getResource(name));
            int w = bi.getWidth();
            int h = bi.getHeight();
            int[] pixels = new int[w * h];
            bi.getRGB(0, 0, w, h, pixels, 0, w);
            return new Bitmap(w, h, pixels);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + name, e);
        }
    }

    /**
     * Splits an atlas into multiple images
     * @param name The filename of the atlas on disk
     * @param sw The amount of images horizontally
     * @param sh The amount of images vertically
     * @return An array of all the loaded Bitmaps
     */
    private static Bitmap[] loadSplit(String name, int sw, int sh) {
        try {
            BufferedImage bi = ImageIO.read(Art.class.getResource(name));
            int w = bi.getWidth();
            int h = bi.getHeight();

            int xs = w / sw;
            int ys = h / sh;

            Bitmap[] bitmaps = new Bitmap[xs * ys];
            for (int y = 0; y < ys; y++)
                for (int x = 0; x < xs; x++) {
                    int[] pixels = new int[sw * sh];
                    bi.getRGB(x * sw, y * sh, sw, sh, pixels, 0, sw);
                    bitmaps[x + y * xs] = new Bitmap(sw, sh, pixels);
                }

            return bitmaps;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + name, e);
        }
    }
}
