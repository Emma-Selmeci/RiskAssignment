package riskClient;

import java.io.BufferedReader;
import java.io.IOException;

class ClientListener implements Runnable {
    private BufferedReader serverInput;
    private ClientManager manager;
    ClientListener(BufferedReader serverInput, ClientManager manager) {
        this.serverInput = serverInput;
        this.manager = manager;
    }

    @Override
    public void run() {
        while(true) {
            try {
                String line = serverInput.readLine();
                processLine(line);
            } catch (IOException e) {

            }
        }
    }

    private void processLine(String line) {
        try {
            if(line.length() < 1) return;
            synchronized(manager) {
                manager.sendLine(line);
            }
        } catch(Exception e) {
            System.out.println("Error occured while reading server output");
        }

    }
}
