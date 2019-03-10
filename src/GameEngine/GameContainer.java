package GameEngine; 

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GameContainer{
    
    private AbstractGame game;
   
    private int width, height;
    private float scale;
    //
    private Window window;
    private Input input;
    //
    private ScheduledExecutorService executor;
    //
    private boolean FPSlimited = true;
    private int FPStarget = 60;
    //
    
    public GameContainer(AbstractGame game, int width, int height, float scale) {
        this.game = game;
        this.height = height;
        this.width = width;
        this.scale = scale;
    }
    
    public void start() {
        
        window = new Window(this);
        input = new Input(this);
        
        Runnable updater = new Runnable() {
            
            @Override
            public void run() {           
                game.updateGame();
                input.update();
            }

        };
        
        Runnable renderer = new Runnable() {
            
            @Override
            public void run() {
                window.repaintPanel();
            }

        };

        executor = new ScheduledThreadPoolExecutor(2);
        
        if(FPSlimited) { 
            executor.scheduleAtFixedRate(updater, 1, 1, TimeUnit.MILLISECONDS);
            executor.scheduleAtFixedRate(renderer, 1, 1000/FPStarget, TimeUnit.MILLISECONDS);
        }
        else {
            executor.scheduleWithFixedDelay(updater, 1, 1, TimeUnit.MILLISECONDS);
            executor.scheduleWithFixedDelay(renderer, 1, 1, TimeUnit.MILLISECONDS);
        }
    }

    public AbstractGame getGame() {
        return game;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
    
    public float getScale() {
        return scale;
    }
    
    public Window getWindow() {
        return window;
    }
    
    public Input getInput() {
        return input;
    }

    public boolean isFPSlimited() {
        return FPSlimited;
    }

    public void setFPSlimited(boolean FPSlimited) {
        this.FPSlimited = FPSlimited;
        this.FPStarget = 60;
    }
    
    public void setFPSlimited(boolean FPSlimited, int FPStarget) {
        this.FPSlimited = FPSlimited;
        FPStarget = (FPStarget <= 1000) ? FPStarget : 1000;
        FPStarget = (FPStarget > 0) ? FPStarget : 1;
        this.FPStarget = FPStarget;
    }
    
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        window.resize();
    }
    
}
