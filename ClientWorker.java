package com.janbro;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

/** Client Worker
 *
 * Handler for individual client connections
 * Command parser
 */
class ClientWorker implements Runnable {

    private Socket client;

    ClientWorker(Socket client) {
        this.client = client;
    }

    public void run(){
        System.out.println("New client connected!");

        // Setup input/output streams
        String line;
        BufferedReader in = null;
        DataOutputStream out = null;
        try{
            in = new BufferedReader(new
                    InputStreamReader(client.getInputStream()));
            out = new
                    DataOutputStream(client.getOutputStream());

            // Send an initial response
            out.writeUTF("Hello!\n");
        } catch (IOException e) {
            System.out.println("in or out failed");
            System.exit(-1);
        }

        int result = 0;

        // While we haven't received an exit command, read input
        while(result != -5){
            try{
                line = in.readLine();
                if(line == null) continue;

                System.out.printf("Command: %s, executed by %s\n", line, client.getLocalSocketAddress());
                result = parseCommand(line);

                //Send data back to client
                out.writeUTF(Integer.toString(result));
                out.flush();
            }catch (IOException e) {
                System.out.println("Read failed");
                System.exit(-1);
            }
        }

        if(Server.kill) System.exit(0);
    }

    private int parseCommand(String cmd) {
        String[] args = cmd.toLowerCase().replaceAll("\\P{Print}","").split(" ");

        // Command does not exist
        if(Arrays.stream(CommandFactory.ALL).noneMatch(args[0]::equals)) return -1;

        if(args.length == 1 && args[0].equals(CommandFactory.BYE)) {
            // Kill client worker
            return -5;
        }
        else if(args.length == 1 && args[0].equals(CommandFactory.TERMINATE)) {
            // Kill server
            Server.kill = true;
            return -5;
        }

        // Too little input arguments
        if(args.length < 3) return -2;

        // Too many input arguments
        if(args.length > 5) return -3;

        // Parse input numbers
        int[] iargs = new int[args.length - 1];
        try {
            for(int i=0; i < iargs.length; i++) {
                iargs[i] = Integer.parseInt(args[i + 1]); // Ignore first argument
            }
        }
        catch(Exception e) {
            //One or more input is NaN
            return -4;
        }

        if(args[0].equals(CommandFactory.ADD)) {
            return CommandFactory.add(iargs);
        }
        else if(args[0].equals(CommandFactory.SUBTRACT)) {
            return CommandFactory.subtract(iargs);
        }
        else if(args[0].equals(CommandFactory.MULTIPLY)) {
            return CommandFactory.multiply(iargs);
        }

        // If we've reached this there's a dev issue
        return -99;
    }
}
