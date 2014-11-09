package physgame;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
    
    private int y = 300, x = 250;
    private Screen screen = new Screen(x, y);
    private int scale = 2;
    private Mouse mouse;
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
    private GameObject center;
    private GameSphere playerSphere;

    /**
     *
     */
    public Game() {
//        double mc=0, xc=0, yc=0;
//        for (int i = 0; i < normalSphere.length; i++) {
//            double tempMass = Math.random() * 12 - 6;
//            while(tempMass == 0)
//                tempMass = Math.random() * 12 - 6;
//            normalSphere[i] = new GameObject(Math.random() * (screen.width - 26), Math.random() * (screen.height - 32), 0, 0, tempMass, true, screen);
//            mc += normalSphere[i].getMass();
//            xc += normalSphere[i].getPosition()[0] * normalSphere[i].getMass();
//            yc += normalSphere[i].getPosition()[1] * normalSphere[i].getMass();
//        }
//        
//        center = new GameObject(xc/mc, yc/mc, 0,0,mc,true,screen);
//        center.setSprite(Sprite.gameObj);
        
        playerSphere = new GameSphere(118, 260, 0, 0, screen);
        frame.setTitle("GAME SPHERES!");
        frame.setPreferredSize(new Dimension(x * scale, y * scale));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        scroll = new MouseWheel();
        addMouseWheelListener(scroll);
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
        
        if (!normalSphere.isEmpty()) {
            for (GameObject gs : normalSphere) {
                gs.render();
            }
        }
        playerSphere.render();

//        center.render();

        System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);
        g.drawImage(image, 0, 0, x * scale, y * scale, this);
        
        bs.show();
        mouse.update();
    }
    
    public void update() {
        long startTime = System.currentTimeMillis();
        if (mouse.lastMouseClicked && !mouse.mouseHeld) {
            normalSphere.add(new GameObject(mouse.xPos / scale - 13, mouse.yPos / scale - 8, 0, 0, currentMass, true, screen));
            mouse.lastMouseClicked = false;
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
        }
        
        
        double[] forces = playerSphere.calculateForce(normalSphere);
        
        double[] vel = playerSphere.getVelocity();
        double mag = Math.sqrt(vel[0] * vel[0] + vel[1] * vel[1]);
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        System.out.println("V: " + mag);
//        System.out.println("X: " + forces[0]);
//        System.out.println("Y: " + forces[1]);
//        System.out.println("G: " + playerSphere.getG());
//        System.out.println(scroll.notches);
        System.out.println(currentMass);
        playerSphere.updatePosition(startTime - lastUpdate);
        playerSphere.updateVelocity(startTime - lastUpdate);
        lastUpdate = startTime;
        
        playerSphere.update();
        if (!normalSphere.isEmpty()) {
            for (GameObject objs : normalSphere) {
                objs.update();
            }
        }
        
        
    }
}
