/**
 *
 *  @author Łęczycka Dominika S24494
 *
 */

package zad2;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

public class Purchase {
    private String prod;
    private String data;
    private Double price;

    public Purchase(String prod, String data, Double price) {
        this.prod = prod;
        this.data = data;
        this.price = price;
    }

    public String getProd() {
        return prod;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) throws PropertyVetoException {
        String oldData = this.data;
        this.data = data;
        firePropertyChange("data", oldData, data);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) throws PropertyVetoException {
        if (price < 1000) {
            throw new PropertyVetoException("Price change to: " + price + " not allowed", null);
        }
        Double oldPrice = this.price;
        this.price = price;
        firePropertyChange("price", oldPrice, price);
    }

    @Override
    public String toString() {
        return "Purchase [prod=" + prod + ", data=" + data + ", price=" + price + "]";
    }

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    private VetoableChangeSupport vcs = new VetoableChangeSupport(this);

    public void addVetoableChangeListener(VetoableChangeListener listener) {
        vcs.addVetoableChangeListener(listener);
    }

    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        vcs.removeVetoableChangeListener(listener);
    }

    private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        pcs.firePropertyChange(propertyName, oldValue, newValue);
    }
}