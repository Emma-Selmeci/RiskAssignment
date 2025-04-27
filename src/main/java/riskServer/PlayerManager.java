package riskServer;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.io.IOException;

public class PlayerManager extends Thread {
    public PlayerManager(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.clientWriter = new PrintWriter(clientSocket.getOutputStream());
    }

    protected void sendLine(String line) throws IOException {
        clientWriter.print(line + "\r\n");
        clientWriter.flush();
    }

    protected boolean expect(String line) throws IOException {
        String clientLine = clientReader.readLine();
        if (! clientLine.equals(line)) {
            return false;
        }

        return true;
    }

    public void run() {
        System.out.println("Klienssel kommunikáció indul");

        try {
            sendLine("Hello World!");
            if (! expect("Who's there?")) {
                sendLine("You're supposed to say \"Who's there?\"");
                clientSocket.close();
                System.out.println("A klienssel a kommunikáció lezárult!");
                return;
            }

            String[] joke = {"This is a joke","This is a punchline"};
            sendLine(joke[0]);

            if (! expect(joke[0] + " who?")) {
                sendLine("You're supposed to say \"" + joke[0] + " who?\"");
                clientSocket.close();
                System.out.println("A klienssel a kommunikáció lezárult!");
                return;
            }

            sendLine(joke[1]);
            sendLine("BA-DUM TSS");

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("A klienssel a kommunikáció sikeres volt!");
    }

    protected Socket clientSocket;
    protected BufferedReader clientReader;
    protected PrintWriter clientWriter;

}

