package physgame;

public class Sprite {
    
    public static Sprite background = new Sprite(250, 300, 0, 0, SpriteSheet.backgroundSheet, 1);
    public static Sprite gameObj    = new Sprite(16, 0, 0, SpriteSheet.objectSheet, 1);
    public static Sprite gameObj2    = new Sprite(8, 0, 0, SpriteSheet.objectSheet, 1);
    
    
    public final int W, H;
    private int xInSheet, yInSheet,//coords of sprite in sprite sheet
            frame = 0;
    private boolean flipped = false;
    private final int MAX_FRAMES;
    public int[] pixels;
    private SpriteSheet sheet;
    private int animationSpeed = 1;
    
    public Sprite(int width, int height, int x, int y, SpriteSheet ss, int frames){
       W = width;
        H = height;
        this.xInSheet = x * width;
        this.yInSheet = y * height;
        pixels = new int[W * H];
        if(frames < 1) frames = 1;
        MAX_FRAMES = frames - 1;
        sheet = ss;
        load();
    }
    
    public Sprite(int width, int height, int x, int y, SpriteSheet ss, int frames, int animSpeed)
    {
        this(width, height, x, y, ss, frames);
        animationSpeed = (byte) animSpeed;
    }

    /**
     * Creates a square sprite
     * @param size The height/length in pixels of the Sprite
     * @param x X position in Sprite Sheet
     * @param y Y position in Sprite Sheet
     * @param ss The Sprite Sheet that the sprite is pulled from
     * @param frames If animated, frames above 1, minimum 1 frame (not animated)
     */
    public Sprite(int size, int x, int y, SpriteSheet ss, int frames)
    {
        this(size, size, x, y, ss, frames);
    }
    
    public Sprite(int size, int x, int y, SpriteSheet ss, int frames, int animSpeed)
    {
        this(size, size, x, y, ss, frames, animSpeed);
    }
    
    /**
     * Sprite of a solid color
     * @param color the integer of the color to turn sprite
     */
    public Sprite(int width, int height, int color) {
        MAX_FRAMES = 1 - 1;
        W = width;
        H = height;  
        pixels = new int[W * H];
        setColor(color);
    }
    
    /**
     * Loads a horizontally flipped version of s
     * @param s Sprite to flip
     */
    public Sprite(Sprite s) {
        animationSpeed = s.animationSpeed;
        W = s.W;
        H = s.H;
        pixels = new int[W*H];
        MAX_FRAMES = s.MAX_FRAMES;
        sheet = s.sheet;
        xInSheet = s.xInSheet;
        yInSheet = s.yInSheet;
        flipped = true;
        loadFlippedSprite();
    }
    
    /**
     * Pulls the image from the sprite sheet
     */
    private void load() {
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                pixels[x + y * W] = sheet.pixels[(x + (frame * W) + this.xInSheet) + (this.yInSheet + y)*sheet.WIDTH]; // should frames be frames - 1? if changed, max frames constructor -1ing should change.
            }
        }
    }
    
    /**
     * This creates a sprite from a file indicated by p
     * Besides not requiring an already made SpriteSheet object it's like all the other constructors
     */
    public Sprite(int width, int height, int x, int y, String p, int frames) {
        MAX_FRAMES = frames - 1;
        sheet = new SpriteSheet(p);
        W = sheet.WIDTH;
        H = sheet.HEIGHT;
        pixels = new int[W*H];
        xInSheet = x * W;
        yInSheet = y * H;        
        load();
    }

    /**
     * Set the color of sprite to a solid color
     * @param color color to fill array with (makes solid color)
     */
    private void setColor(int color) {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = color;
        }
    }

    /**
     * Pulls the image from the sprite sheet
     */
    private void loadFlippedSprite() {
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                pixels[(W - 1 - x) + y * W] = sheet.pixels[(x + (frame * W) + this.xInSheet) + (this.yInSheet + y)*sheet.WIDTH]; // should frames be frames - 1? if changed, max frames constructor -1ing should change.
            }
        }
    }
}
