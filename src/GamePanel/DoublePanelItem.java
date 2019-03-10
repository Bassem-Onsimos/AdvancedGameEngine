
package GamePanel;

public class DoublePanelItem extends PanelItem {
    
    private double value;

    public DoublePanelItem(String title, Double value) {
        super(title, Double.toString(value));
        this.value = value;
    }
    
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
        setData(Double.toString(value));
    }
    
}
