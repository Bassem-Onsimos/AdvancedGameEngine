
package GameClock;

import GameEngine.AbstractGame;
import GamePanel.PanelItem;

public class StopWatch extends PanelItem {
    
    private double minutes, seconds;
    //
    private boolean started = false, paused = false;
    //
    private AbstractGame game;
    
    public StopWatch(AbstractGame game) {
        super("Time", "0:0");
        this.game = game;
    }
    
    public void update() {
        
        if(started && !paused) {
            seconds += game.getElapsedTime();

            if(seconds >= 60) {
                seconds = 0;
                minutes++;
            }
        }
        
        setData(Integer.toString((int)minutes) + ":" + Integer.toString((int)seconds));
        
    }
    
    public void start() {
        started = true;
        reset();
    }
    
    public void pause() {
        paused = true;
    }
    
    public void resume() {
        paused = false;
    }
    
    public void reset() {
        seconds = 0;
        minutes = 0;
        paused = false;
    }
    
    public void stop() {
        started = false;
    }
    
    public double getTimeSeconds() {
        return seconds + (minutes * 60);
    }
}
