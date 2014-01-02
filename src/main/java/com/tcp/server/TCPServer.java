package com.tcp.server;

import java.net.*;
import java.io.*;


public class TCPServer {
    private static final int BACKLOG = 1000;

	public static void main(String[] args) throws IOException {

    if (args.length != 1) {
        System.err.println("Usage: java -jar TCPServer.jar <port number>");
        System.exit(1);
    }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;
        
        ServerSocket serverSocket = new ServerSocket(portNumber, BACKLOG);
        System.out.println("*** TCP Server start @ port:" + portNumber + " with backlog: " + BACKLOG);
        try  { 
            while (listening) {
	            new TCPServerThread(serverSocket.accept()).start();
	        }
	    } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}