package com.nio.tcp.server;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.ByteBuffer;
import java.io.IOException;

public class NIOProtocolImpl implements NIOProtocol {

	private int bufSize; // Size of I/O buffer

	public NIOProtocolImpl(int bufSize) {
		this.bufSize = bufSize;
	}

	public void handleAccept(SelectionKey key) throws IOException {
		SocketChannel clientChannel = ((ServerSocketChannel) key.channel())
				.accept();
		// Must be nonblocking to register
		clientChannel.configureBlocking(false);
		// Register the selector with new channel for read and attach byte
		// buffer
		clientChannel.register(key.selector(), SelectionKey.OP_READ,
				ByteBuffer.allocate(bufSize));

	}

	public void handleRead(SelectionKey key) throws IOException {
		// Client socket channel has pending data
		SocketChannel clientChannel = (SocketChannel) key.channel();
		ByteBuffer buf = (ByteBuffer) key.attachment();
		long bytesRead = clientChannel.read(buf);

		if (bytesRead == -1)
			clientChannel.close();
		else if (bytesRead > 0)  {
			System.out.println("bytes:" + bytesRead + ":Message: " + new String(buf.array()));
			key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		}
	}

	public void handleWrite(SelectionKey key) throws IOException {
		// Retrieve data read earlier
		ByteBuffer buf = (ByteBuffer) key.attachment();
		buf.flip(); // Prepare buffer for writing
		SocketChannel clientChannel = (SocketChannel) key.channel();
		clientChannel.write(buf);
		if (!buf.hasRemaining()) { // Buffer completely written?
			// Nothing left, so no longer interested in writes
			key.interestOps(SelectionKey.OP_READ);
		}
		buf.compact(); // Make room for more data to be read in
	}

}