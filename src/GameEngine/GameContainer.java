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
    private boolean FBSlimited = true;
    
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
        
        if(FBSlimited) { // Around 60 FBS
            executor.scheduleAtFixedRate(updater, 1, 1, TimeUnit.MILLISECONDS);
            executor.scheduleAtFixedRate(renderer, 1, 16, TimeUnit.MILLISECONDS);
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

    public boolean isFBSlimited() {
        return FBSlimited;
    }

    public void setFBSlimited(boolean FBSlimited) {
        this.FBSlimited = FBSlimited;
    }
    
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        window.resize();
    }
    
}
