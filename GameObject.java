// Basic game object, whether it's a positive or negative gravity object

package physicsgame;

public class GameObject 
{
    private Vector2d pos;
    private Vector2d vel;
    private double mass;
    
    GameObject()
    {
        pos = new Vector2d(0,0);
        vel = new Vector2d(0,0);
        mass = 1;
    }
    
    GameObject(double a, double b, double m)
    {
        pos = new Vector2d(a,b);
        vel = new Vector2d(a,b);
        mass = m;
    }
    
    double[] getPosition()
    {
        return pos.getVector();
    }
    
    double [] getVelocity()
    {
        return vel.getVector();
    }
    
    void setPosition(double a, double b)
    {
        pos.setX(a);
        pos.setY(b);
    }
    
    void setVelocity(double a, double b)
    {
        vel.setX(a);
        vel.setY(b);
    }
    
}
