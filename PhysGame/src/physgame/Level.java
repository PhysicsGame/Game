package physgame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

class Level {

    public Sprite goal = Sprite.goal;
    public int[] levelLayout = new int[250 * 300];
    private String path = "";
    private GameObject[] objs = new GameObject[2];
    private boolean isWon = false;
    private boolean isStart = false;
    private BufferedImage bi;
    private Screen screen;

    public Level(String p, Screen s) {
        try {
            path = p;
            bi = ImageIO.read(getClass().getResourceAsStream(path));
            bi.getRGB(0, 0, 250, 300, levelLayout, 0, 250);
            screen = s;
        } catch (IOException ex) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public void render() {
        goal.render();
        for (int i = 0; i < objs.length; i++) {
            if(objs[i] != null)
                objs[i].render();
        }
        screen.renderBackImage(levelLayout, 0xFFB5E61D);
    }

    public GameObject[] getSpheres() {
        return objs;
    }

    public void update() {
        goal.update();
        for (int i = 0; i < objs.length; i++) {
            if(objs[i] != null)
                objs[i].update();
        }
    }

    public boolean isWon(GameSphere player, GameObject g) {
        /*
         * checks to see if obj is within bounds of the goal. If they are, returns
         * true, otherwise returns false?
         */

        for (int x = 118; x < 134; x++) {
            for (int y = 5; y < 21; y++) {
                if ((int) player.getPosition()[0] == x && (int) player.getPosition()[1] == y) {
                    isWon = true;
                }
            }
        }


        return isWon;
    }
    
    public boolean collide(GameSphere gs){
        int newX = (int)(gs.getPosition()[0] + gs.getVelocity()[0]);
        int newY = (int)(gs.getPosition()[1] + gs.getVelocity()[1]);
        if(newX < 0 || newX > screen.width || newY < 0 || newY > screen.height) return false;
        if(levelLayout[newX + newY * screen.width] == 0xFFC3C3C3) return true;
        return false;
    }
}
