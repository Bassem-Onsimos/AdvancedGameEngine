package GameEngine;

import GameEngine.GameState.State;
import GameMenu.AbstractMenu;
import GamePanel.GameData;
import GamePanel.GamePanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public abstract class AbstractGame {

    private GameContainer container;
    //
    private AbstractMenu startMenu;
    private AbstractMenu pauseMenu;
    private AbstractMenu postGameMenu;
    //
    private State state;
    //
    private GameData gameData;
    private GamePanel panel;
    //
    private boolean debugInfoDisplayed = true;
    private long lastUpdateTime = -1;
    private float elapsedTime = 0;
    //
    private boolean pausable = true;
    //
    private boolean resizable = true;
    
    public AbstractGame(int width, int height, float scale) {
        container = new GameContainer(this, width, height, scale);
        state = State.inGame;
        initiate();
        container.start();
    }

    public abstract void initiate();

    public abstract void update();

    public abstract void render(Graphics2D g);
    
    public void reset() {
        
    }

    protected void updateGame() {
        
        if(pausable && getInput().isKeyUp(KeyEvent.VK_ESCAPE)) togglePause();
        
        switch(state) {
            
            case startMenu:
                startMenu.update();
                break;
            
            case inGame:
                update();
                break;
                
            case pause:
                if(pauseMenu != null) pauseMenu.update();
                break;
                
            case postGame: {
                
                if(postGameMenu != null) {
                    postGameMenu.update();
                }
                else {
                    if(getInput().isKeyUp(KeyEvent.VK_SPACE)) {
                        setState(State.inGame);
                        reset();
                    }
                }
                break;  
            }
            
        }
        
        if (lastUpdateTime < 0) {
            lastUpdateTime = System.currentTimeMillis();
            elapsedTime = 0;

        } else {
            long now = System.currentTimeMillis();
            elapsedTime = (float) (now - lastUpdateTime) * 0.001f;
            lastUpdateTime = now;
        }
        
    }
    
    protected void renderGame(Graphics2D g) {
        if(state != State.startMenu) render(g);
        else startMenu.render(g);
        
        if(state == State.pause) { 
            if(pauseMenu == null) {
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g.setColor(Color.black);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
            else {
                pauseMenu.render(g);
            }
        }
        if(state == State.postGame) { 
            if(postGameMenu == null){
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g.setColor(Color.black);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
            else {
                postGameMenu.render(g);
            }
        }
    }

    protected void displayElapsedTime(Graphics2D g2) {
        String res = String.format("Elapsed Time: %fs, UPS: %.1f", elapsedTime, 1.0 / elapsedTime);
        
        int sx = container.getWidth() - 250;
        int sy = container.getHeight() - 5;
        
        g2.setColor(Color.BLACK);
        g2.drawString(res, sx + 1, sy + 1);
        g2.setColor(Color.WHITE);
        g2.drawString(res, sx, sy);
    }
    
    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
    
    public float getElapsedTime() {
        return elapsedTime;
    }
    
    public boolean isFBSlimited() {
        return container.isFBSlimited();
    }

    public void setFBSlimited(boolean FBSlimited) {
        container.setFBSlimited(FBSlimited);
    }
    
    public Input getInput() {
        return container.getInput();
    }

    protected GameContainer getContainer() {
        return container;
    }
    
    public int getWidth() {
        return container.getWidth();
    }
    
    public int getHeight() {
        return container.getHeight();
    }
    
    public void setSize(int width, int height) {
        container.setSize(width, height);
    }
    
    public void setWidth(int width) {
        container.setSize(width, container.getHeight());
    }
    
    public void setHeight(int height) {
        container.setSize(container.getWidth(), height);
    }

    public void setStartMenu(AbstractMenu menu) {
        this.startMenu = menu;
        setState(State.startMenu);
    }

    public void setPauseMenu(AbstractMenu pauseMenu) {
        this.pauseMenu = pauseMenu;
    }

    public void setPostGameMenu(AbstractMenu postGameMenu) {
        this.postGameMenu = postGameMenu;
    }
    
    public void setState(State state) {
        this.state = state;
        
        if(panel!=null) {
            if(state==State.inGame)
                panel.unHide();
            else
                panel.Hide();
        }
    }

    public State getState() {
        return state;
    }

    public AbstractMenu getStartMenu() {
        return startMenu;
    }

    public AbstractMenu getPauseMenu() {
        return pauseMenu;
    }

    public AbstractMenu getPostGameMenu() {
        return postGameMenu;
    }
    
    public void setPausable(boolean pausable) {
        this.pausable = pausable;
    }
    
    public boolean isPausable() {
        return pausable;
    }
    
    public void togglePause() {
        if(state == State.inGame) {
            if(pauseMenu != null) pauseMenu.resetSelectedIndex();
            state = State.pause;
            return;
        }
        
        if(state == state.pause) {
            state = State.inGame;
            return;
        }
    }

    public boolean isDebugInfoDisplayed() {
        return debugInfoDisplayed;
    }

    public void setDebugInfoDisplayed(boolean debugInfoDisplayed) {
        this.debugInfoDisplayed = debugInfoDisplayed;
    }
    
    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }
    
    public void addGamePanel(GameData gameData) {
        this.gameData = gameData;
        
        this.panel = new GamePanel(gameData, container);      
    }
    
    public void addGamePanel(GameData gameData, Color backgroundColor, Color textColor, Color borderColor, int fontSize) {
        this.gameData = gameData;
        
        this.panel = new GamePanel(gameData, container, backgroundColor, textColor, borderColor, fontSize);
    }

    public GamePanel getPanel() {
        return panel;
    }
    
    
    
}
