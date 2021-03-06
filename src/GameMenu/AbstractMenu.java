
package GameMenu;

import GameEngine.AbstractGame;
import GameEngine.Graphics.BufferedImageLoader;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractMenu{

    private ArrayList<MenuItem> items;    
    private ArrayList<MenuItem> subMenuItems;
    private boolean subMenuMode = false;
    private int selectedIndex;
    //
    private BufferedImage backgroundImage;
    private Color backGroundColor = Color.black;
    //
    private String title;
    private String subMenuTitle;
    private String subTitle;
    //
    private Font titleFont;
    private Font subTitleFont;
    //
    protected static final float opaque = 1.0f; 
    protected static final float transparent = 0.7f; 
    private float backgroundOpacity = opaque;
    //
    private AbstractGame game;
    
    public AbstractMenu(AbstractGame game) {
        items = new ArrayList<>();
        selectedIndex = 0;
        this.game = game;
        initiate();     
    }
    
    public abstract void initiate();
    
    public void update() {

        if(game.getInput().isKeyDown(KeyEvent.VK_DOWN)) selectedIndex++;
        if(game.getInput().isKeyDown(KeyEvent.VK_UP)) selectedIndex--;
        
        if(subMenuMode){
            if(selectedIndex < 0) selectedIndex = subMenuItems.size() - 1;
            if(selectedIndex == subMenuItems.size()) selectedIndex = 0;
            
            subMenuItems.get(selectedIndex).hover();
        }
        else {
            if(selectedIndex < 0) selectedIndex = items.size() - 1;
            if(selectedIndex == items.size()) selectedIndex = 0;
            
            items.get(selectedIndex).hover();
        }
        
        
        if(game.getInput().isKeyUp(KeyEvent.VK_SPACE) || game.getInput().isKeyUp(KeyEvent.VK_ENTER)) chooseItem();

    }
    
    public void render(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, backgroundOpacity));
        if(backgroundImage!=null) 
            g.drawImage(backgroundImage, 0, 0, game.getWidth(), game.getHeight(), null);
        else {
            g.setColor(new Color(0, 0, 0));
            g.fillRect(0, 0, game.getWidth(), game.getHeight());
        }
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f) );
        
        if(titleFont==null) {
            titleFont = new Font("American Typewriter", Font.BOLD, 30);
        }
        
        if(subTitleFont==null) {
            subTitleFont = new Font("American Typewriter", Font.PLAIN, 20);
        }
        
        ArrayList<MenuItem> displayedItems = subMenuMode ? subMenuItems : items;
        
        Font defaultFont = g.getFont();
        
        g.setFont(titleFont);       
        FontMetrics metrics = g.getFontMetrics(titleFont);
        
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        float percentage = 0.0f;
        
        if(displayedItems.size() <= 2) percentage = 0.2f;
        else if(displayedItems.size() < 4) percentage = 0.15f;
        else if(displayedItems.size() < 6) percentage = 0.08f;
        
        float offset =  (int) ( percentage * game.getHeight() );
        
        String displayedTitle = null;
        int nTitle = 0;
        
        if(subMenuMode) {
            displayedTitle = subMenuTitle;
            nTitle++;
        }
        else if(title!=null) {
            displayedTitle = title;
            nTitle++;
            
            if(subTitle!=null) {
                nTitle++;
            }            
        }
        
        double titleSize = 0;
        if(nTitle < 2) titleSize = nTitle;
        else titleSize = 1;
        
        int verticalSpace1 = (int)( (float)(game.getHeight() - 2*offset ) / ( displayedItems.size() + titleSize ) );
        int verticalSpace2 = (int)( (float)(game.getHeight() - 2*offset ) / ( displayedItems.size() + nTitle ) );
        
        
        if(displayedTitle != null) {
            int titleX = (int)((game.getWidth() - metrics.stringWidth(displayedTitle)) / 2.0);
            int titleY = (0 * verticalSpace1) + (int)offset + (int)( (verticalSpace1 - metrics.getHeight()) / 2.0 + metrics.getAscent());
            
            int borderX = titleX;
            int borderWidth = metrics.stringWidth(displayedTitle) + 30;
            
            int subTitleX = 0;
            int subTitleY = 0;
            
            if(subTitle != null) {
                
                FontMetrics subTitleMetrics = g.getFontMetrics(subTitleFont);
                
                subTitleX = (int)((game.getWidth() - subTitleMetrics.stringWidth(subTitle)) / 2.0);
                
                if(metrics.getHeight() > subTitleMetrics.getHeight()) subTitleY = titleY + metrics.getHeight();
                else subTitleY = titleY + subTitleMetrics.getHeight();
                
                if(subTitleX < titleX) borderX = subTitleX;
                if(subTitleMetrics.stringWidth(subTitle) > metrics.stringWidth(displayedTitle)) borderWidth = subTitleMetrics.stringWidth(subTitle) + 30;
            }
            
            Stroke defaultStroke = g.getStroke();
            
            g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setColor(Color.red);
            g.drawRoundRect(borderX - 15, titleY - (metrics.getAscent() + 5), borderWidth, nTitle * metrics.getHeight() + 10, 20, 20);
            
            g.setStroke(defaultStroke);
            
            g.setColor(Color.BLACK);
            g.drawString(displayedTitle, titleX + 1, titleY + 1);
            g.setColor(Color.LIGHT_GRAY);
            g.drawString(displayedTitle, titleX, titleY);
            
            
            if(subTitle != null) {
                g.setFont(subTitleFont);
                g.setColor(Color.BLACK);
                g.drawString(subTitle, subTitleX + 1, subTitleY + 1);
                g.setColor(Color.RED);
                g.drawString(subTitle, subTitleX, subTitleY);
                
                g.setFont(titleFont);
            }
        
        }
        
        
        
        for(MenuItem item : displayedItems) {
            int index = displayedItems.indexOf(item) + nTitle;
            String str = item.getTitle();

            int centeredX = (int)((game.getWidth() - metrics.stringWidth(str)) / 2.0);           
            int centeredY = (index * verticalSpace2) + (int)offset + (int)( (verticalSpace2 - metrics.getHeight()) / 2.0 + metrics.getAscent());
            
            g.setColor(Color.BLACK);
            g.drawString(item.getTitle(), centeredX + 1, centeredY + 1);
            g.setColor( (index - nTitle == selectedIndex) ? Color.red : Color.white );
            g.drawString(item.getTitle(), centeredX, centeredY);
            
        }      
        
        g.setFont(defaultFont);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    
    private void chooseItem() {
        
        if(!subMenuMode) {
            
            if(items.get(selectedIndex) instanceof SubMenuInitializer) {
                subMenuItems = ((SubMenuInitializer) items.get(selectedIndex)).getItems();
                subMenuMode = true;
                subMenuTitle = items.get(selectedIndex).getTitle();
                selectedIndex = 0;
            }
            else {
                items.get(selectedIndex).doFunction();
            }
        
        }
        else {
            
            if(subMenuItems.get(selectedIndex) instanceof SubMenuInitializer) {
                subMenuTitle = subMenuItems.get(selectedIndex).getTitle();
                subMenuItems = ((SubMenuInitializer) subMenuItems.get(selectedIndex)).getItems();
                subMenuMode = true;
                selectedIndex = 0;
            }
            else {
                subMenuItems.get(selectedIndex).doFunction();
                subMenuMode = false;
            }
            
        }
        
    }
    
    public void addItem(MenuItem item) {
        items.add(item);
        item.setMenu(this);
        
        if(item instanceof SubMenuInitializer) {
            for(MenuItem subItem : ((SubMenuInitializer)item).getItems()) {
                subItem.setMenu(this);
            }
        }
    }
    
    public boolean removeItem(MenuItem item) {
        if(items.contains(item)) {
            items.remove(item);
            return true;
        }
        return false;
    } 
    
    public void setBackgroundImage(String backgroundPath) {
        
        try {
            BufferedImageLoader loader = new BufferedImageLoader();
            backgroundImage = loader.loadImage(backgroundPath);
        } catch (IOException e) {
            e.printStackTrace();
        }   
        
    }
    
    public void removeBackgroundImage() {
        backgroundImage.flush();
        backgroundImage = null;
    }

    public Color getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(Color backGroundColor) {
        this.backGroundColor = backGroundColor;
    }
    
    public Font getTitleFont() {
        return titleFont;
    }

    public void setTitleFont(Font titleFont) {
        this.titleFont = titleFont;
    }

    public float getBackgroundOpacity() {
        return backgroundOpacity;
    }

    public void setBackgroundOpacity(float backgroundOpacity) {
        this.backgroundOpacity = backgroundOpacity;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public void removeTitle() {
        this.title = null;
    }
    
    public void removeSubTitle() {
        this.subTitle = null;
    }
    
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setSubTitleFont(Font subTitleFont) {
        this.subTitleFont = subTitleFont;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
    
    public void resetSelectedIndex() {
        selectedIndex = 0;
    }
    
}
