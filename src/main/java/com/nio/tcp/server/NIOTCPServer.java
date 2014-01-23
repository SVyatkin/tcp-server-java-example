package com.nio.tcp.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class NIOTCPServer {

	private static final int BUFSIZE = 256;
	private static final int TIMEOUT = 3000; // timeout in milliseconds

	public static void main(String[] args) throws IOException {

		if (args.length < 1) {
			System.err
					.println("Usage: $ java -cp tcp.server-0.0.1.jar com.nio.tcp.server.NIOTCPServer <port number> ...");
			System.exit(1);
		}

		// Create a selector
		Selector selector = Selector.open();

		// Create listening socket channel for each port and register selector with channels
		for (String arg : args) {

			ServerSocketChannel listnChannel = ServerSocketChannel.open();
			listnChannel.socket().bind(
					new InetSocketAddress(Integer.parseInt(arg)));
			listnChannel.configureBlocking(false); // must be nonblocking to register
			
			// Register selector with channel. The returned key is ignored
			listnChannel.register(selector, SelectionKey.OP_ACCEPT);
		}

		// Create a handler that will implement the protocol
		NIOProtocol protocol = new NIOProtocolImpl(BUFSIZE);

		while (true) { 
			// Wait for some channel to be ready (or timeout)
			if (selector.select(TIMEOUT) == 0) { 
				continue;
			}

			// Get iterator on set of keys with I/O to process
			Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();

			while (keyIter.hasNext()) {
				SelectionKey key = keyIter.next(); 
				// Server socket channel has pending connection requests?
				if (key.isAcceptable()) {
					protocol.handleAccept(key);
				} else
				// Client socket channel has pending data?
				if (key.isReadable()) {
					protocol.handleRead(key);
				} else
				// Client socket channel is available for writing and
				// key is valid (i.e., channel not closed)?
				if (key.isValid() && key.isWritable()) {
					protocol.handleWrite(key);
				}
				keyIter.remove(); // remove from set of selected keys
			}
		}
	}
}