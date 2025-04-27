package riskServer;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.IOException;

public class RiskServer implements Runnable {
    public static final int PORT_NUMBER = 10220;

    public void run() {
        GameManager manager = new GameManager(Thread.currentThread());

        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
        } catch (IOException e) {
            System.err.println("Could not open server socket");
            return;
        }

        System.out.println("Risk server started");

        try {
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                synchronized(manager) {
                    manager.addPlayer(clientSocket);
                }
            }
        } catch (IOException e) {
            System.err.println("Accept failed!");
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.err.println("Risk server stops");
    }

    public static void main(String[] args) {
        new Thread(new RiskServer()).start();
    }

    protected ServerSocket serverSocket;
}
