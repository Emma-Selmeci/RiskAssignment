package riskClient;

import riskShared.GameState;

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

            synchronized (manager) {
                switch(manager.getState()) {
                    case INITIALIZATION : {
                        if(line.charAt(0) == 'c')
                            manager.numPlayers(Integer.parseInt(line.substring(1,2)));
                    } break;
                    default : {

                    }
                }
            }
        } catch(Exception e) {
            System.out.println("Error occured while reading server output : " + line);
        }

    }
}
