package com.mojang.ld19.display;

public class Sprite {
    public double x, y, z;
    public Bitmap bitmap;

    public double xOrg;
    public double yOrg;
    public double zOrg;

    public Sprite(double x, double y, double z, Bitmap bitmap) {
        xOrg = this.x = x;
        yOrg = this.y = y;
        zOrg = this.z = z;
        this.bitmap = bitmap;
    }

    public void rotate(double cos, double sin, boolean offs) {
        if (offs) {
            double cc = 0.5;
            cc -= 0.5 / 6.0;
            x = cos * (xOrg - cc) - sin * (zOrg - cc) + cc;
            z = cos * (zOrg - cc) + sin * (xOrg - cc) + cc;
        } else {
            x = cos * (xOrg) - sin * (zOrg);
            z = cos * (zOrg) + sin * (xOrg);
        }
    }
}
