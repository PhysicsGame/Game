package physgame;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
    
    private int y = 300, x = 250;
    private Screen screen = new Screen(x,y);
    private int scale = 2;
    private Mouse mouse;
    public BufferedImage image = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
    private JFrame frame = new JFrame();
    private boolean running = true;
    private Thread mThread;
    private long lastUpdate = System.currentTimeMillis();
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private SpriteSheet ss;
    private GameObject normalSphere;
    private GameSphere playerSphere;
    
    /**
     *
     */
    public Game(){
//        normalSphere = new GameObject(25, 50, 0, 0, 15, true, screen);
        normalSphere = new GameObject(40, 250, 0, 0, 15, true, screen);
        playerSphere = new GameSphere(25, 50, 0, 0, screen);
        frame.setTitle("GAME SPHERES!");        
        frame.setPreferredSize(new Dimension(x*scale,y*scale));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mouse = new Mouse();
        
        frame.addMouseListener(mouse);
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
    
    
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        screen.clear();
        
        g.fillRect(0, 0, x*scale, y*scale);
        screen.renderBackground();
        
        playerSphere.render();
        normalSphere.render();
        
        System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);
        g.drawImage(image, 0,0, x * scale, y * scale, this);
        
        bs.show();
    
    }
    
    public void update(){
        long startTime = System.currentTimeMillis();
        double[] forces = playerSphere.calculateForce(new GameObject[]{normalSphere});
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("X: " +forces[0]);
        System.out.println("Y: " + forces[1]);
        playerSphere.updateVelocity(startTime - lastUpdate);
        playerSphere.updatePosition(startTime - lastUpdate);
        lastUpdate = startTime;
        
        
    }
}
