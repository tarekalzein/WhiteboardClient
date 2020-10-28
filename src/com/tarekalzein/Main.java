package com.tarekalzein;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private static String remoteHost;
    private static int remotePort;
    private static int port;
    private static ConnectionHelper connection;

    private static Paper p;

    public static void main(String[] args) {
        try
        {
            port = Integer.parseInt(args[0]);
            remoteHost = args[1];
            remotePort = Integer.parseInt(args[2]);
            connection = new ConnectionHelper(port,remoteHost,remotePort);
            new Main();
        }catch (Exception e)
        {
            System.out.println("error parsing port and host. Please use the format: java -jar Main.jar <my port> <remote host> <remote port>");
        }
    }

    public Main(){

        p = new Paper(connection);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(p, BorderLayout.CENTER);

        setSize(640, 480);
        setVisible(true);
    }
}
