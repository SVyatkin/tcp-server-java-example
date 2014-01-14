package com.tcp.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPClient {
		 public static void main(String argv[])
		 {
		  String sentence = "Test Message from TCP Client";
		  Socket clientSocket = null;
		try {
			clientSocket = new Socket("localhost", 5555);

		  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		  outToServer.writeBytes(sentence + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
		  finally {try {
			if (clientSocket != null) clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}}
		 }
		}