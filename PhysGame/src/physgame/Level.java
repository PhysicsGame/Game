package physgame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

class Level {
    
    public Sprite goal = Sprite.goal;
    public int[] levelLayout = new int[250*300];
    private String path = "";
    private GameObject[] objs = new GameObject[2];
    private boolean isWon = false;
    private boolean isStart = false;
    private BufferedImage bi;
    
    
    public Level(String p, Screen screen){
        try {
            path = p;
            objs[0] = new GameObject(30, 50, 0, 0, 25, true, screen);
            objs[1] = new GameObject(180, 50, 0, 0, 25, true, screen);
            bi = ImageIO.read(getClass().getResourceAsStream(path));
            bi.getRGB(0, 0, 250, 300, levelLayout, 0, 250);
        } catch (IOException ex) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    public void render(){
        goal.render();
        for(int i = 0; i < objs.length; i++)
            objs[i].render();
    }
    
    public GameObject[] getSpheres(){
        return objs;
    }
    
    public void update(){
        goal.update();
        for(int i = 0; i < objs.length; i++)
            objs[i].update();
    }
    
    public boolean isWon(){
        /*
         * checks to see if obj is within bounds of the goal. If they are, returns
         * true, otherwise returns false?
         */
        
        return isWon;
    }
}
