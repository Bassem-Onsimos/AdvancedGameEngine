
package GameEngine.Graphics;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private BufferedImage image;
    
    public SpriteSheet(BufferedImage image){
        this.image = image;
    }
    
    public BufferedImage cropImage(int column, int row, int width, int height){
        BufferedImage subImage = image.getSubimage( (column*width)-width , (row*height)-height , width , height );
        return subImage;
    }
}
