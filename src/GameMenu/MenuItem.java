
package GameMenu;

public abstract class MenuItem {
    
    private String title;
    protected AbstractMenu menu;
    
    public MenuItem(String title) {
        this.title = title;
    }
    
    public void setMenu(AbstractMenu menu) {
        this.menu = menu;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public abstract void function();
     
    public void doFunction() {
        function();
        menu.resetSelectedIndex();
    }
}
