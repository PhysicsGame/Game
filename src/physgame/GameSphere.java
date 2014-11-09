package physicsgame;

public class GameSphere extends GameObject
{
    private double G_ = .0000000000667;
    private Vector2d force = new Vector2d();
    private double normalMass = 1;
    
    GameSphere(double x, double y, double dx, double dy)
    {
        super(x, y, dx, dy, 1, true);
    }
    
    GameSphere(Vector2d startPos, Vector2d startVel, double m)
    {
        super(startPos, startVel, m);
    }
    
    double[] calculateForce(GameObject[] spheres)
    {
        double[] forcesPrelim = {0,0};
        double[] forcesFinal = {0,0};
        double distanceX;
        double distanceY;
        for (GameObject g : spheres)
                {
                    distanceX = g.getPosition()[0]-this.getPosition()[0];
                    distanceY = g.getPosition()[1]-this.getPosition()[1];
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
        
        dx = getVelocity()[0] + (force.getX()/getMass())*t;
        dy = getVelocity()[1] + (force.getY()/getMass())*t;
        
        setVelocity(dx, dy);
    }
    
    void updatePosition(double t)
    {
        double x, y;
        
        x = getPosition()[0] + getVelocity()[0]*t + (1/2)*(force.getX()/getMass())*t*t;
        y = getPosition()[1] + getVelocity()[1]*t + (1/2)*(force.getY()/getMass())*t*t;
        
        setPosition(x, y);
    }
    
    Vector2d getForce()
    {
        return force;
    }
    
}