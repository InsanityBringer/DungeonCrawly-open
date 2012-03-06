package com.mojang.ld19.display;

public class Bitmap {
    public int w, h;
    public int xc, yc;
    public int[] pixels;

    public Bitmap(int w, int h) {
        this(w, h, new int[w*h]);
    }

    public Bitmap(int w, int h, int[] pixels) {
        this.w = w;
        this.h = h;
        this.pixels = pixels;
        
        xc = w/2;
        yc = h/2;
    }

    public void replaceWith(Bitmap b) {
        this.w = b.w;
        this.h = b.h;
        this.xc = b.xc;
        this.yc = b.yc;
        this.pixels = b.pixels;
    }
}
