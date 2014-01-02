package com.tcp.server;

import java.net.*;
import java.io.*;

public class TCPServerThread extends Thread {
	private Socket socket = null;

	public TCPServerThread(Socket socket) {
		super("TCPServerThread");
		this.socket = socket;
	}

	public void run() {

		try  {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			// version 1 byte
			int version = in.read();

			// message type 2 chars
			char[] messageType = new char[2];
			in.read(messageType, 0, 2);

			// user Id 4 chars

			char[] userId = new char[4];
			in.read(userId, 0, 4);

			String inputLine, message = "";

			while ((inputLine = in.readLine()) != null) {
				message += inputLine;
			}
			OutMessage(version, messageType, userId, message);
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void OutMessage(int version, char[] messageType, char[] userId,
			String message) {
		System.out.print("v:" + version);
		System.out.print(", mt:" + charToInt(messageType));
		System.out.print(", userId:" + charToInt(userId));
		System.out.println(", message:" + message);
	}

	private int charToInt(char[] chr) {
		int value = 0;

		for (int i = 0; i < chr.length; i++) {
			value = (value << 8) + ((byte)chr[i] & 0xff);
		}
		return value;
	}
}