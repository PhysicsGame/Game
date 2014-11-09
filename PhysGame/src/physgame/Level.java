package physgame;

class Level {
    
    public Sprite goal = Sprite.goal;
    public int[] levelLayout = new int[250*300];
    private String path = "";
    private GameObject[] objs = new GameObject[2];
    private boolean isWon = false;
    private boolean isStart = false;
    
    
    public Level(String p, Screen screen){
        path = p;
        objs[0] = new GameObject(30, 50, 0, 0, 25, true, screen);
        objs[1] = new GameObject(180, 50, 0, 0, 25, true, screen);
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
