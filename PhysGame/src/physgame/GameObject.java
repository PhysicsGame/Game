// Basic game object, whether it's a positive or negative gravity object

package physgame;

public class GameObject 
{
    private Vector2d pos;
    private Vector2d vel;
    private double mass;
    private boolean isAttractive;
    private boolean isPassive;
    private Sprite sprite = Sprite.gameObj;
    private Screen screen;
    
    GameObject(Screen s)
    {
        pos = new Vector2d(0,0);
        vel = new Vector2d(0,0);
        mass = 1;
        isPassive = true;
        isAttractive = false;
        screen = s;
    }
    
    GameObject(double x, double y, double dx, double dy, double m, boolean active, Screen s)
    {
        screen = s;
        pos = new Vector2d(x,y);
        vel = new Vector2d(dx,dy);
        mass = m;
        
        if (active)
        {
            isPassive = false;
            if (m > 0)
                isAttractive = true;
            else
                isAttractive = false;
        }
        else
        {
            isPassive = true;
            isAttractive = false;
        }
     }
    
    GameObject(Vector2d p, Vector2d v, double m, Screen s)
    {
        screen = s;
        pos = new Vector2d(p.getX(),p.getY());
        vel = new Vector2d(v.getX(),v.getY());
        mass = m;
    }
    
    double[] getPosition()
    {
        return pos.getVector();
    }
    
    double[] getVelocity()
    {
        return vel.getVector();
    }
    
    double getMass()
    {
        return mass;
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
    
    void setMass(double m)
    {
        mass = m;
    }
    
    boolean isPassiveCheck()
    {
        if (isPassive)
            return true;
        else
            return false;
    }
    
    public void render(){
        screen.render((int)pos.getX(),(int)pos.getY(),sprite);
    }
    
}
