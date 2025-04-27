package riskServer;

import riskShared.GameState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

class GameManager {
    private Thread serverThread;
    private ArrayList<PrintWriter> clientWriters = new ArrayList<>(2);
    private boolean readyArray[] = {false, false, false};
    private int numPlayers = 0;
    private GameState state = GameState.INITIALIZATION;

    GameManager(Thread serverThread) {
        this.serverThread = serverThread; //ServerThread needs to be interrupted after connection was established with all players
    }

    GameState getState() {
        return state;
    }

    private void print(String line, int printer) {
        clientWriters.get(printer).print(line + "\r\n");
        clientWriters.get(printer).flush();
    }

    private void print(String line) {
        for(int i = 0; i < numPlayers; ++i) {
            clientWriters.get(i).print(line + "\r\n");
            clientWriters.get(i).flush();
        }
    }

    void addPlayer(Socket clientSocket) throws IOException {
        if(state != GameState.INITIALIZATION) return; //In case a third player tries to connect while the two players have already pressed ready
        for(int i = 0; i < 3; ++i) {
            readyArray[i] = false;
        }

        clientWriters.add(new PrintWriter(clientSocket.getOutputStream()));
        BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        new Thread(new PlayerListener(clientReader, this, numPlayers++)).start();

        if(numPlayers == 3) serverThread.interrupt();

        print("c" + numPlayers);
    }

    void ready(int id) {
        readyArray[id] = true;
        if(numPlayers == 2 && readyArray[0] && readyArray[1] || numPlayers == 3 && readyArray[0] && readyArray[1] && readyArray[2]) {
            if(numPlayers == 2) serverThread.interrupt();
            state = GameState.INITIAL_PLACEMENT;
        }
    }

}
