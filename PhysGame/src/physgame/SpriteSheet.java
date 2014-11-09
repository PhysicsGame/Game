/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package physgame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Tripp
 */
public class SpriteSheet {
    private String path;
    public final int WIDTH, HEIGHT;
    public int[] pixels;
    
    public static SpriteSheet backgroundSheet   = new SpriteSheet("/Textures/Background.png");
    public static SpriteSheet objectSheet       = new SpriteSheet("/Textures/GameSpheres.png");
    public static SpriteSheet obj               = new SpriteSheet("/Textures/GreenSprite.png");
    /**
     * This creates an instance of a SpriteSheet, taking the path data, size in
     * pixels, and creating a storage area for the pixels until loading at the
     * end.
     *
     * @param p is the path that must be followed to find the resources
     * @param size the literal pixel size. This method only works with a square
     * spritesheet for a size.
     */
    public SpriteSheet(String p) {
        path = p;
        WIDTH = load();
        HEIGHT = pixels.length/WIDTH;
    }
    
    /**
     * The load method is used to actually pull the data from files and pack it 
     * into the SpriteSheet objects..
     * Returns width of image
     */
    private int load() {
        try {
            System.out.print("LOADING " + path + "...");
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
            getClass().getResourceAsStream(path).close();
            int w = image.getWidth();
            int h = image.getHeight();
            pixels = new int[w * h];
            image.getRGB(0, 0, w, h, pixels, 0, w);
            System.out.println("Done");
            return w;
        } catch (IOException ex) {
            Logger.getLogger(SpriteSheet.class.getName()).log(Level.SEVERE, "Could not load SpriteSheet " + path, ex);
        }
        return 0;
    }
    
    public static String getSheet(SpriteSheet ss){
        if(ss == backgroundSheet)
            return "BACKGROUND";
        if(ss == objectSheet)
            return "Floaters?";
        if(ss == obj)
            return "object";
        
        return "";
    }
}
