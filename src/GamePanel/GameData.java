
package GamePanel;

import java.util.ArrayList;

public abstract class GameData implements Subject {
    
    private ArrayList<PanelItem> items;
    
    private ArrayList<Observer> observers;
    
    public GameData() {
        items = new ArrayList<>();
        observers = new ArrayList<>();
        initiate();
    }
    
    public abstract void initiate();

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unRegister(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers){
            observer.update();
        }
    }
    
    public void addItem(PanelItem item) {
        items.add(item);
        item.setGameData(this);
    }
    
    public void removeItem(PanelItem item) {
        items.remove(item);
    }

    public ArrayList<PanelItem> getItems() {
        return items;
    }
    
    
    
}
