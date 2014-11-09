/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package physgame;


class Screen {
    public int width, height; //of screen
    public int[] pixels;

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
    
    public void renderGameObj(int xp, int yp, Sprite s){
        if(s == null) return;
        for (int y = 0; y < s.H; y++) {
            int ya = y + yp;
            for (int x = 0; x < s.W; x++) {
                int xa = x + xp;
                if (xa < 0 - s.W || xa >= width || ya < 0 || ya >= height) {
                    break;
                }
                if (xa < 0) {
                    xa = 0;
                }
                if(s.pixels[x + (y * s.W)] != 0xffff00ff)
                    pixels[xa + (ya * width)] = s.pixels[x + (y * s.W)];
            }
        }
    }
    
    void renderBackImage(int[] pixels, int ignoreColor){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(pixels[x + (y * width)] != ignoreColor)
                    this.pixels[x + (y * width)] = pixels[x + (y * width)];
            }
        }
    }
}
