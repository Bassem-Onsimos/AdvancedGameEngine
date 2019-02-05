
package GameMenu;

import java.util.ArrayList;

public abstract class SubMenuInitializer extends MenuItem {
    
    private ArrayList<MenuItem> items;
    
    public SubMenuInitializer(String title) {
        super(title);
        items = new ArrayList<>();
        initiate();
    }
    
    public abstract void initiate();
    
    @Override
    public final void function() {    
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }
    
    public void addSubMenuItem(MenuItem item) {
        items.add(item);
        item.setMenu(menu);
    }
    
    public boolean removeSubMenuItem(MenuItem item) {
        if(items.contains(item)) {
            items.remove(item);
            return true;
        }
        return false;
    }
    
    
}
