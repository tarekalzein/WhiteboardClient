package com.tarekalzein;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;

public class ConnectionHelper {

    DatagramSocket socket;
    String remoteHost;
    int remotePort;

    byte[] receiveBuffer = new byte[1024];


    public ConnectionHelper(int port, String remoteHost,int remotePort)
    {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;


        try{
            socket = new DatagramSocket(port);
            System.out.println("Connected to: " + port);
//            socket.setSoTimeout(10000);

            Thread receiverThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true)
                    {
                        receive();
                    }
                }
            });
            receiverThread.start();
        }catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }


    public void send(String s) throws IOException {
        byte [] dataToSend;
        try
        {
            InetAddress host = InetAddress.getByName(remoteHost);

           dataToSend = s.getBytes();
//            String test = "testing";
//            dataToSend = test.getBytes();
            DatagramPacket request = new DatagramPacket(
                    dataToSend,
                    dataToSend.length,
                    host,
                    remotePort);
            socket.send(request);
            System.out.println(socket.getLocalPort());
            System.out.println( "Data should be sent");

        }catch(IOException e)
        {

        }
    }

    private void receive(){
        DatagramPacket receivedPacket =
                new DatagramPacket(receiveBuffer, receiveBuffer.length);
        try {
            socket.receive(receivedPacket);

            String s= new String(receivedPacket.getData(),0,receivedPacket.getLength());
            String[]xy = s.split(" ");

//            paper.addRemotePoints(Integer.parseInt(xy[0]),Integer.parseInt(xy[1]));
            System.out.println("received data from: ");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
