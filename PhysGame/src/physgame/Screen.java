/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package physgame;


class Screen {
    public int width, height; //of screen
    public int[] pixels;
    public int mapwidth = 64; //image file should be 64 px by 64 px
    private int mapsize = mapwidth * mapwidth;
    public int[] tiles = new int[mapsize]; //64 tiles in both directions
    public static int xOffs, yOffs; //position to look at    

    public Screen(int x, int y){
        width = x;
        height = y;   
        pixels = new int[x * y];
    }
    
    public void clear() {
        int voidColor = 0xff000000;
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = voidColor;
        }
    }
    
    public void renderBackground() {
        Sprite bgSprite = Sprite.background;
     for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + (y * width)] = bgSprite.pixels[x + (y * width)];
            }
        }
    }
    
    public void render(int yp, int xp, Sprite s){
        for (int y = 0; y < s.H; y++) {
            int ya = y + yp;
            for (int x = 0; x < s.W; x++) {
                int xa = x + xp;
                if (xa < 0 || xa >= width || ya < 0 || ya >= height) {
                    break;
                }
                if (xa < 0) {
                    xa = 0;
                }
                pixels[xa + (ya * width)] = s.pixels[x + (y * width)];
            }
        }
    }
}
