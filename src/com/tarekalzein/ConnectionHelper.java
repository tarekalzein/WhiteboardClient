package com.tarekalzein;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ConnectionHelper {

    DatagramSocket socket;
    String remoteHost;
    int remotePort;
    PointBean bean;

    byte[] receiveBuffer = new byte[1024];

    /***
     * Constructor that creates a DatagramSocket with the given port number and sets the remote host and port number.
     * Runs the UDP receive method on a different thread.
     * @param port The port used to create the UDP DatagramSocket.
     * @param remoteHost The remote host to send data to.
     * @param remotePort The remote Port number to send data to.
     */
    public ConnectionHelper(int port, String remoteHost,int remotePort) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;

        try{
            socket = new DatagramSocket(port);
            System.out.println("Connected to: " + port);
            socket.setSoTimeout(10000);

            Thread receiverThread = new Thread(() -> {
                while(true)
                {
                    receive();
                }
            });
            receiverThread.start();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    /***
     * Method to send a a string that has the point's x and y to a UDP listener.
     * @param s two integers (a point's x and y) in a string.
     * @throws IOException
     */
    public void send(String s) throws IOException {
        byte [] dataToSend;
        try {
            InetAddress host = InetAddress.getByName(remoteHost);

           dataToSend = s.getBytes();
            DatagramPacket request = new DatagramPacket(
                    dataToSend,
                    dataToSend.length,
                    host,
                    remotePort);
            socket.send(request);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /***
     * This method keeps listning to the socket to fetch/receive data, transforms the data into a Point instance
     * and fires a property change event with the newly created point.
     */
    private void receive(){
        DatagramPacket receivedPacket =
                new DatagramPacket(receiveBuffer, receiveBuffer.length);
        try {
            socket.receive(receivedPacket);

            String s= new String(receivedPacket.getData(),0,receivedPacket.getLength());
            String[]xy = s.split(" ");

            Point point = new Point(Integer.parseInt(xy[0]),Integer.parseInt(xy[1]));

            bean.setValue(point);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /***
     * Method to set/pass the PointBean so Paper can listen to it.
     * @param bean Has the property change listener and value change methods.
     */
    public void setBean(PointBean bean){
        this.bean = bean;
    }
}
//TODO: Add protocol to receive brush color/size change and apply that in Paper with property change listener.