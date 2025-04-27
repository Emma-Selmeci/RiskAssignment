package riskServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

class GameManager {
    private Thread serverThread;
    private ArrayList<PrintWriter> clientWriters = new ArrayList<>(2);
    private int numPlayers = 0;

    GameManager(Thread serverThread) {
        this.serverThread = serverThread; //ServerThread needs to be interrupted after connection was established with all players
    }

    void addPlayer(Socket clientSocket) throws IOException {
        clientWriters.add(new PrintWriter(clientSocket.getOutputStream()));
        BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        new Thread(new PlayerListener(clientReader, this, numPlayers++)).start();

        if(numPlayers == 3) serverThread.interrupt();

        for(PrintWriter p : clientWriters) {
            p.println("c" + numPlayers + "\r\n");
            p.flush();
        }
    }

}
