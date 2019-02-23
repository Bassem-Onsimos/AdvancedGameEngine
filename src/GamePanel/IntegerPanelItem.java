
package GamePanel;

public class IntegerPanelItem extends PanelItem {
    
    private int value;
    
    public IntegerPanelItem(String title, int value) {
        super(title, Integer.toString(value));
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        setData(Integer.toString(value));
    }
    
    
    
}
