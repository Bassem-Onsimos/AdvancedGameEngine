
package GameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener {
    
    private GameContainer container;
    
    private final int nKeys = 250;
    private boolean[] keys = new boolean[nKeys];
    private boolean[] keysPrevious = new boolean[nKeys];
    
    private final int nButtons = 5;
    private boolean[] buttons = new boolean[nKeys];
    private boolean[] buttonsPrevious = new boolean[nKeys];
    
    private int mouseX = 0, mouseY = 0;
    private int scroll = 0;
    
    public Input(GameContainer container) {
        
        this.container = container;
        
        container.getWindow().addKeyListener(this);
        container.getWindow().addMouseListener(this);
        container.getWindow().addMouseMotionListener(this);
        container.getWindow().addMouseWheelListener(this);
        
    }
    
    public void update() {
        scroll = 0;
        
        for(int i=0; i<nKeys; i++)
            keysPrevious[i] = keys[i];
        
        for(int i=0; i<nButtons; i++)
            buttonsPrevious[i] = buttons[i];
    }
    
    public boolean isKey(int keyCode) {
        return keys[keyCode];
    }
    
    public boolean isKeyUp(int keyCode) {
        return !keys[keyCode] && keysPrevious[keyCode];
    }
    
    public boolean isKeyDown(int keyCode) {
        return keys[keyCode] && !keysPrevious[keyCode];
    }
    
    public boolean isButton(int button) {
        return buttons[button];
    }
    
    public boolean isButtonUp(int button) {
        return !buttons[button] && buttonsPrevious[button];
    }
    
    public boolean isButtonDown(int button) {
        return buttons[button] && !buttonsPrevious[button];
    }
    
    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = (int)(e.getX() / container.getScale());
        mouseY = (int)(e.getY() / container.getScale());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = (int)(e.getX() / container.getScale());
        mouseY = (int)(e.getY() / container.getScale());
    }
    
   
    
}
