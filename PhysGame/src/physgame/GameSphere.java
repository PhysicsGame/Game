package physgame;

import java.util.ArrayList;

public class GameSphere extends GameObject
{
    private double G_ = .0000667;
    private Vector2d force = new Vector2d();
    private double normalMass = 1;
    private double heading;
    
    GameSphere(double x, double y, double dx, double dy, Screen s)
    {
        super(x, y, dx, dy, 1, true, s);
        sprite = Sprite.gameObj;
    }
    
    GameSphere(Vector2d startPos, Vector2d startVel, double m, Screen s)
    {
        super(startPos, startVel, m, s);
        sprite = Sprite.gameObj;
    }
    
    double[] calculateForce(ArrayList<GameObject> spheres)
    {
        double[] forcesPrelim = {0,0};
        double[] forcesFinal = {0,0};
        double distanceX;
        double distanceY;
        for (GameObject g : spheres)
                {
                    if(g == null) continue;
                    distanceX = Math.abs(g.getPosition()[0]-this.getPosition()[0]);
                    distanceY = Math.abs(g.getPosition()[1]-this.getPosition()[1]);
                    
                    if (distanceX > 300 || distanceY > 300)
                        G_ += 1;
                    else if (distanceX < 300 && distanceY < 300)
                        G_ = .000667;
                    
                    forcesPrelim[0] = Math.abs(((G_* g.getMass() * this.getMass())/(Math.pow(distanceX, 2) + Math.pow(distanceY, 2))) * Math.cos(Math.atan(distanceY/distanceX)));
                    forcesPrelim[1] = Math.abs(((G_* g.getMass() * this.getMass())/(Math.pow(distanceX, 2) + Math.pow(distanceY, 2))) * Math.sin(Math.atan(distanceY/distanceX)));
                    
                    if (this.getPosition()[0] > g.getPosition()[0])
                    {
                        if (g.getMass() > 0)
                        {
                            forcesFinal[0] -= forcesPrelim[0];
                        }
                        else
                        {
                            forcesFinal[0] += forcesPrelim[0];
                        }
                    }
                    else if (this.getPosition()[0] < g.getPosition()[0])
                    {
                        if (g.getMass() > 0)
                        {
                            forcesFinal[0] += forcesPrelim[0];
                        }
                        else
                        {
                            forcesFinal[0] -= forcesPrelim[0];
                        }
                    }
                    
                    
                    
                    if (this.getPosition()[1] > g.getPosition()[1])
                    {
                        if (g.getMass() > 0)
                        {
                            forcesFinal[1] -= forcesPrelim[1];
                        }
                        else
                        {
                            forcesFinal[1] += forcesPrelim[1];
                        }
                    }
                    else if (this.getPosition()[1] < g.getPosition()[1])
                    {
                        if (g.getMass() > 0)
                        {
                            forcesFinal[1] += forcesPrelim[1];
                        }
                        else
                        {
                            forcesFinal[1] -= forcesPrelim[1];
                        }
                    }
                    
                }
        force.setX(forcesFinal[0]);
        force.setY(forcesFinal[1]);
        return forcesFinal;
    }
    
    void updateVelocity(double t)
    {
        double dx, dy;
        
        if (Math.abs(getVelocity()[0] + (force.getX()/getMass())*t) < .6) //Breaks physics by limiting velocity. Might not actually do anything...
            dx = getVelocity()[0] + (force.getX()/getMass())*t;
        else
            dx = getVelocity()[0];
        
        if (Math.abs(getVelocity()[1] + (force.getY()/getMass())*t) < .6)
            dy = getVelocity()[1] + (force.getY()/getMass())*t;
        else
            dy = getVelocity()[1];
        
        
        heading = Math.abs(Math.atan(dy/dx));
        
        if (dx < 0 && dy > 0)
            heading += 90;
        else if (dx < 0 && dy < 0)
            heading += 180;
        else if (dx > 0 && dy < 0)
            heading += 270;
        
        setVelocity(dx, dy);
    }
    
    void updatePosition(double t)
    {
        double x, y;
        
        x = getPosition()[0] + getVelocity()[0]*t + (1/2)*(force.getX()/getMass())*t*t;
        y = getPosition()[1] + getVelocity()[1]*t + (1/2)*(force.getY()/getMass())*t*t;
        
        setPosition(x, y);
    }
    
    public void update(){
        super.update();
        if(getPosition()[0] < 0 && getVelocity()[0] < 0){
            vel.setX(-vel.getX() * .8);
        }
        if(getPosition()[0] + sprite.W > Game.WIDTH/Game.scale && getVelocity()[0] > 0){
            vel.setX(-vel.getX() * .8);
        }
        if(getPosition()[1] < 0 && getVelocity()[1] < 0 || getPosition()[1] + sprite.H + 14 > Game.HEIGHT/Game.scale && getVelocity()[1] > 0){
            vel.setY(-vel.getY() * .8);
        }
    }
    
    Vector2d getForce()
    {
        return force;
    }
    
    double getG()
    {
        return G_;
    }
}
