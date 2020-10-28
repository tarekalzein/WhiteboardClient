package com.tarekalzein;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;

class Paper extends JPanel {
    
    private HashSet hs = new HashSet();
    ConnectionHelper connection;
    PointBean bean =new PointBean();

    public Paper(ConnectionHelper connection) {
        this.connection = connection;
        connection.setBean(bean);
        setBackground(Color.white);
        addMouseListener(new L1());
        addMouseMotionListener(new L2());
        listen();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);

        try{
            Iterator i = hs.iterator();
            while(i.hasNext()) {
                Point p = (Point)i.next();
                g.fillOval(p.x, p.y, 2, 2);
            }
        }catch(ConcurrentModificationException ex)
        {
            //TODO: Find a fix for the concurrency bug. It is caused by adding new points to
            // hs (from udp connected client) while iterating the hashset at the same time
        }

    }

    private void addPoint(Point p) {
        hs.add(p);

        repaint();
        try {
            String dataToSend = p.x + " "+p.y;
            connection.send(dataToSend);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void listen(){
        bean.addPropertyChangeListener(e ->
                addPoint((Point)e.getNewValue())
                );
    }

    class L1 extends MouseAdapter {
        public void mousePressed(MouseEvent me) {
            addPoint(me.getPoint());
        }
    }

    class L2 extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent me) {
            addPoint(me.getPoint());
        }
    }
}
