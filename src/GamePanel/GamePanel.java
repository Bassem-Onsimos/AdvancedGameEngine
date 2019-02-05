
package GamePanel;

import GameEngine.GameContainer;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.SwingConstants.CENTER;

public class GamePanel extends JPanel implements Observer {
    
    private GameData gameData;
    //
    private GameContainer container;
    //
    private Color backgroundColor = Color.darkGray;
    private Color textColor = Color.white;
    private Color borderColor = Color.red;
    private int fontSize = 17;
    //
    private ArrayList<JLabel> labels;
            
    public GamePanel(GameData gameData, GameContainer container) {
        this.gameData = gameData;
        this.container = container;
        
        gameData.register(this);
        
        labels = new ArrayList<>();
        setupPanel();
    }
    
    public GamePanel(GameData gameData, GameContainer container, Color backgroundColor, Color textColor, Color borderColor , int fontSize) {
        this.gameData = gameData;
        this.container = container;
        
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.borderColor = borderColor;
        this.fontSize = fontSize;
        
        gameData.register(this);
        
        labels = new ArrayList<>();
        setupPanel();
    }
 
    public void setupPanel() {
        
        setLayout(new GridLayout(2, gameData.getItems().size()));
        
        for(PanelItem item : gameData.getItems()) { 
            labels.add(new JLabel(item.getTitle()));
        }
        
        for(PanelItem item : gameData.getItems()) { 
            labels.add(new JLabel(item.getData()));
        }
        
        for(JLabel label : labels) {
            label.setForeground(textColor);
            label.setFont(new Font("Arial", Font.BOLD, (int)(fontSize*container.getScale())));
            label.setHorizontalAlignment(CENTER);
            label.setVerticalAlignment(CENTER);
            add(label);
        }
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                for(JLabel label : labels) {
                    label.setFont(new Font("Arial", Font.BOLD, (int)(fontSize*container.getScale())));
                }
                setBorder(BorderFactory.createMatteBorder((int)(3*container.getScale()), 0, (int)(3*container.getScale()), 0, borderColor));
            }
        });
        
        setBorder(BorderFactory.createMatteBorder((int)(3*container.getScale()), 0, (int)(3*container.getScale()), 0, borderColor));
        setBackground(backgroundColor);
        
    }
    
    @Override
    public void update() {
        
        for(int i = 0; i < gameData.getItems().size(); i++) {
            labels.get(i).setText(gameData.getItems().get(i).getTitle());
        }
        
        for(int i = gameData.getItems().size(); i < gameData.getItems().size() * 2; i++) {
            labels.get(i).setText(gameData.getItems().get(i - gameData.getItems().size()).getData());
        }
        
    }
    
    public void setTextColor(Color color) {
        textColor = color;
        
        for(JLabel label : labels) {
            label.setForeground(textColor);
        }
    }
    
    public void Hide() {
        for(JLabel label : labels) {
            label.setVisible(false);
        }
    }
    
    public void unHide() {
        for(JLabel label : labels) {
            label.setVisible(true);
        }
    }
    
    public void setBackGroundColor(Color color) {
        setBackground(color);
    }
    
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        
        for(JLabel label : labels) {
            label.setFont(new Font("Arial", Font.BOLD, (int)(fontSize*container.getScale())));
        }      
    }
    
    public void setBorderColor(Color color) {
        borderColor = color;
        setBorder(BorderFactory.createMatteBorder((int)(3*container.getScale()), 0, (int)(3*container.getScale()), 0, borderColor));
    }
    
    
}
