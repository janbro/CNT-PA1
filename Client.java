package com.janbro;

import java.net.*;
import java.io.*;

/** Client
 *
 * Connects to server and sends test commands
 */
public class Client {

    public static void main(String [] args) {
        String serverName = "127.0.0.1"; //args[0]
        int port = 6096; //Integer.parseInt(args[1]);
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);

            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            System.out.println(in.readUTF());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int response = 0;
            while(response != -5) {
                String cmd = br.readLine();

                out.writeBytes(cmd + "\n");

                try {
                    response = Integer.parseInt(in.readUTF());

                    System.out.println("Server says " + response);
                }
                catch(EOFException e) {
                    System.out.println(e);
                }
            }
            System.out.println("exit");
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}