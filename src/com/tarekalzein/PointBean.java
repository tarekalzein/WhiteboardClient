package com.tarekalzein;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/***
 * This class creates a bean that listens and fires a property change action when a new "point" is received from a connected UDP client/server
 */
public class PointBean {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private Point value;

    /***
     * Method to add /subscribe to a property change listener.
     * @param listener the listener to subscribe to.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /***
     * Method to remove /unsubscribe to a property change listener.
     * @param listener the listener to unsubscribe from.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener){
        support.removePropertyChangeListener(listener);
    }

    /***
     * Method to get the current value of a bean.
     * @return
     */
    public Point getValue(){
        return value;
    }

    /***
     * Method to set the new Point data and calls/fires the property change action to all subscribed listeners.
     * @param newValue the new value (new Point)
     */
    public void setValue(Point newValue){
        Point oldValue = value;
        value= newValue;
        support.firePropertyChange("value",oldValue,newValue);
    }
}
