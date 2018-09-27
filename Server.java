package com.janbro;

import java.io.IOException;
import java.net.ServerSocket;


/** Server
 *
 * Handles creating and destroying client worker threads
 *
 */
public class Server {

    public static void main(String args[]) {
        Server s = new Server();
        s.listenSocket(6096); //Integer.parseInt(args[1]);
    }

    ServerSocket server;

    public static boolean kill = false;

    // Listen for incoming connections and spawns a worker thread
    public void listenSocket(int port) {
        try {
            System.out.println("listen");
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.out.printf("Could not listen on port %i\n", port);
            System.exit(-1);
        }
        while (!kill) {
            ClientWorker w;
            try {
                //server.accept returns a client connection
                w = new ClientWorker(server.accept());
                Thread t = new Thread(w);
                t.start();
            } catch (IOException e) {
                System.out.printf("Accept failed: %i\n", port);
                System.exit(-1);
            }
        }
        finalize();
        System.exit(0);
    }

    protected void finalize(){
        //program terminates and thread exits
        try{
            server.close();
        } catch (IOException e) {
            System.out.println("Could not close socket");
            System.exit(-1);
        }
    }
}