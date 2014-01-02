package com.tcp.client;

import java.io.DataOutputStream;
import java.net.Socket;

public class TCPClient {
		 public static void main(String argv[]) throws Exception
		 {
		  String sentence = "Test Message from TCP Client";
		  Socket clientSocket = new Socket("localhost", 5555);
		  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		  outToServer.writeBytes(sentence + '\n');
		  clientSocket.close();
		 }
		}