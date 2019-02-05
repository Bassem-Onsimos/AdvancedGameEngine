
package GamePanel;

public class PanelItem {
    
    private GameData gameData;
    
    private String title;
    private String data;

    public PanelItem(String title, String data) {
        this.title = title;
        this.data = data;
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        gameData.notifyObservers();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        gameData.notifyObservers();
    }
    
}
