package riskServer;

import java.io.BufferedReader;
import java.io.IOException;

class PlayerListener implements Runnable {
    private GameManager manager;
    private BufferedReader clientReader;
    private int id;

    PlayerListener(BufferedReader clientReader, GameManager manager, int id) {
        this.id = id;
        this.clientReader = clientReader;
        this.manager = manager;
    }

    @Override
    public void run() {
        while(true) {
            try {
                String line = clientReader.readLine();
            } catch (IOException e) {
                System.err.println("Error reading client " + id);
            }
        }
    }
}
