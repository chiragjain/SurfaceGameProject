package com.app.surfacegame.gameutils;

public class OverlapTester {    
    
    public static boolean overlapRectangles(Rectangle r1, Rectangle r2) {
        if(r1.upperLeft.x < r2.upperLeft.x + r2.width &&
           r1.upperLeft.x + r1.width > r2.upperLeft.x &&
           r1.upperLeft.y < r2.upperLeft.y + r2.height &&
           r1.upperLeft.y + r1.height > r2.upperLeft.y)
            return true;
        else
            return false;
    }
    
    public static boolean pointInRectangle(Rectangle r, Vector2 p) {
        return r.upperLeft.x <= p.x && r.upperLeft.x + r.width >= p.x &&
               r.upperLeft.y <= p.y && r.upperLeft.y + r.height >= p.y;
    }
    
    public static boolean pointInRectangle(Rectangle r, float x, float y) {
        return r.upperLeft.x <= x && r.upperLeft.x + r.width >= x &&
               r.upperLeft.y <= y && r.upperLeft.y + r.height >= y;
    }
}