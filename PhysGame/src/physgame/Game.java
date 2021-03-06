package physgame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
    
    private int y = 300, x = 250;
    private Screen screen = new Screen(x, y);
    public static int scale = 2;
    private Mouse mouse;
    private Keyboard key;
    private MouseWheel scroll;
    public BufferedImage image = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
    private JFrame frame = new JFrame();
    private boolean running = true;
    private Thread mThread;
    private long lastUpdate = System.currentTimeMillis();
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private SpriteSheet ss;
    private ArrayList<GameObject> normalSphere = new ArrayList<GameObject>();
    private double currentMass = 10;
    private GameObject goal;
    private GameSphere playerSphere;
    private boolean canStart = false;
    private Level lvl1;
    private int NumOfSpheres = 0;

    /**
     *
     */
    public Game() {
        lvl1  = new Level("/Textures/Levels/Level 1.png", screen);
        
        goal = new GameObject(118, 5, 0, 0, 0, true, screen);
        goal.setSprite(Sprite.goal);
        playerSphere = new GameSphere(118, 230, 0, 0, screen);
        frame.setTitle("GAME SPHERES!");
        frame.setPreferredSize(new Dimension(x * scale, y * scale));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        key = new Keyboard();
        mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        scroll = new MouseWheel();
        addMouseWheelListener(scroll);
        addKeyListener(key);
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    @Override
    public void run() {
        long time = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        requestFocus();
        
        while (running) {
            long now = System.nanoTime();
            delta += (now - time) / ns;
            time = now;
            while (delta >= 1) {
                update();
                delta--;
            }
            render();
            frames++;
            
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }

    /**
     * Start the thread.
     */
    public synchronized void start() {
        running = true;
        mThread = new Thread(this, "GameSphere");
        mThread.start();
    }

    /**
     * Exit and dispose of the thread.
     */
    public synchronized void stop() {
        running = false;
        try {
            mThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(3);
    }
    
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        screen.clear();
        
        g.fillRect(0, 0, x * scale, y * scale);
        screen.renderBackground();
        lvl1.render();
        if (!normalSphere.isEmpty()) {
            for (GameObject gs : normalSphere) {
                if(gs != null)
                    gs.render();
            }
        }
        
        goal.render();
        playerSphere.render();
        
//        center.render();

        System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);
        g.drawImage(image, 0, 0, x * scale, y * scale, this);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Tahoma", Font.PLAIN, 20));
        g.drawString("Mass:  " + currentMass, 15, 30);
        g.dispose();
        bs.show();
        mouse.update();
    }
    
    public void update() {
        if(!lvl1.isWon(playerSphere, goal) && normalSphere.isEmpty())
            for(int i = 0; i < lvl1.getSpheres().length; i++)
                normalSphere.add(lvl1.getSpheres()[i]);
        long startTime = System.currentTimeMillis();
        if (NumOfSpheres < 15){
            if (mouse.lastMouseClicked && !mouse.mouseHeld) {
            normalSphere.add(new GameObject(mouse.xPos / scale - 13, mouse.yPos / scale - 8, 0, 0, currentMass, true, screen));
            mouse.lastMouseClicked = false;
            NumOfSpheres += 1;
            }
        }
            if (scroll.notches < 0)
            {
                if (currentMass < 40)
                {
                    currentMass += 1;
                    if (currentMass == 0)
                        currentMass = 1;
                }
                scroll.notches = 0;
           }
        else if (scroll.notches > 0)
        {
            if (currentMass > -40)
            {
                currentMass -= 1;
                if (currentMass == 0)
                    currentMass = -1;
            }
                scroll.notches = 0;
        } if(key.space)
            canStart = true;
        
        if(canStart){
        double[] forces = playerSphere.calculateForce(normalSphere);
        
        double[] vel = playerSphere.getVelocity();
        double mag = Math.sqrt(vel[0] * vel[0] + vel[1] * vel[1]);
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("V: " + mag);
//        System.out.println("X: " + forces[0]);
//        System.out.println("Y: " + forces[1]);
//        System.out.println("G: " + playerSphere.getG());
//        System.out.println(scroll.notches);
        System.out.println(currentMass);
        
        playerSphere.updatePosition(startTime - lastUpdate);
        playerSphere.updateVelocity(startTime - lastUpdate);
        lvl1.update();
        if(lvl1.collide(playerSphere))
            playerSphere.setVelocity(-playerSphere.getVelocity()[0], -playerSphere.getVelocity()[1]);
        lastUpdate = startTime;
        }
        playerSphere.update();
        if (!normalSphere.isEmpty()) {
            for (GameObject objs : normalSphere) {
                if(objs != null)
                    objs.update();
            }
        }
        key.update();
        
        
    }
}
